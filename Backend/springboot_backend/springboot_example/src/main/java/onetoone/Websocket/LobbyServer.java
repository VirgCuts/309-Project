package onetoone.Websocket;

import java.io.IOException;
import java.util.*;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Represents a WebSocket chat server for handling real-time communication
 * between users. Each user connects to the server using their unique
 * username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for broadcasting messages to all connected
 * users and sending messages to specific users.
 */
@ServerEndpoint("/lobby/{lobbyNumber}/{username}")
@Component
public class LobbyServer {

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();
    private static Map < String, Integer > usernameLobbyMap = new Hashtable<>();
    private static Map < Integer, Integer > lobbyPopulation = new Hashtable<>() {{
        put(1, 0); put(2, 0); put(3, 0); put(4, 0); put(5, 0);
        put(6, 0); put(7, 0); put(8, 0); put(9, 0); put(10, 0);
    }};

    private static Map < String, Boolean > userReady = new Hashtable<>();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(LobbyServer.class);

    //user database
    private static UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository usrRepo) {
        userRepository = usrRepo;  // we are setting the static variable
    }

    private final List<String> bannedWords = Arrays.asList("test", "testing");

    @OnOpen
    public void onOpen(Session session, @PathParam("lobbyNumber") int lobbyNumber, @PathParam("username") String username) throws IOException {

        // server side log
        logger.info("[LobbyonOpen] "+ lobbyNumber + username);

        User existingUser = userRepository.findByName(username);

        // Handle the case of a duplicate username
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username is already in use");
            session.close();
        } else if (existingUser == null) {
            session.getBasicRemote().sendText("Username doesn't exist within database");
            session.close();
        } else if (lobbyPopulation.get(lobbyNumber) > 1) {
            session.getBasicRemote().sendText("Lobby is full.");
            session.close();
        } else{
            // map current session with username
            sessionUsernameMap.put(session, username);
            // map current username with session
            usernameSessionMap.put(username, session);

            usernameLobbyMap.put(username, lobbyNumber);

            lobbyPopulation.replace(lobbyNumber, lobbyPopulation.get(lobbyNumber) + 1);

            userReady.put(username, false);

            if (existingUser.getCanChat()){
                // send to everyone in the lobby
                sendMessageToLobby(username, "User: " + username + " has joined the lobby");
            } else {
                // send to everyone in the lobby
                sendMessageToLobby(username, "User: " + username + " has joined the lobby, but can't say anything because they've been bad.");
            }
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the username by session
        String username = sessionUsernameMap.get(session);
        User user = userRepository.findByName(username);

        // server side log
        logger.info("[onMessage] " + username + ": " + message);

        if (message.equals("@ready")) {
            userReady.replace(username, true);
            sendMessageToLobby(username, "@ready");
            lobbyReadyCheck(username);
        } else if (message.equals("@unready")) {
            userReady.replace(username, false);
            sendMessageToLobby(username, "@unready");
        } else if (user.getCanChat()) {
            boolean containsBannedWord = messageCheck(message, username);
            if (!containsBannedWord) {
                sendMessageToLobby(username,username + ": " + message);
            }
        } else {
            sendMessageToParticularUser(username, "[DM from server]: sorry buddy, you've been banned, no messaging for you.");
        }
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);
        int lobby = usernameLobbyMap.get(username);

        // server side log
        logger.info("[onClose] " + username);


        // send the message to chat
        sendMessageToLobby(username, username + " disconnected");
        
        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        lobbyPopulation.replace(lobby, lobbyPopulation.get(lobby) - 1);
        if (lobbyPopulation.get(lobby) < 0)
            lobbyPopulation.replace(lobby, 0);
        usernameLobbyMap.remove(username);
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    private void lobbyReadyCheck(String username) {
        int[] count = new int[1];
        int lobbyNumber = usernameLobbyMap.get(username);
        usernameLobbyMap.forEach((name, lobby) -> {
            if (lobby == lobbyNumber && userReady.get(name)) {
                count[0]++;
            }
        });
        if (count[0] == 2)
            sendMessageToLobby(username, "Start game");
    }

    private void sendMessageToLobby(String username, String message) {
        try {
            int lobbyNumber = usernameLobbyMap.get(username);
            usernameLobbyMap.forEach((name, lobby) -> {
                if (lobby == lobbyNumber) {
                    sendMessageToParticularUser(name, message);
                }
            });
        } catch (Exception e) {
            logger.info("[Lobby Exception, not in lobby for message] " + e.getMessage());
        }
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    private boolean messageCheck(String message, String sender) {
        for (String bannedWord : bannedWords) {
            if (message.contains(bannedWord)) {
                User user = userRepository.findByName(sender);
                sendMessageToParticularUser(sender, "Please refrain from using that language. You have received one strike on your account.");
                user.setBanStrikes(user.getBanStrikes() + 1);
                sendMessageToParticularUser(sender, "Current strikes: " + user.getBanStrikes() + "/3");
                if (user.getBanStrikes() >= 3) {
                    user.setCanChat(false);
                }
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}