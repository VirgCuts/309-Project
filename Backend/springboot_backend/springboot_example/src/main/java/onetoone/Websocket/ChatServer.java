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
@ServerEndpoint("/chat/{username}")
@Component
public class ChatServer {

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    //user database
    private static UserRepository userRepository;
    private static MessageRepository messageRepository;


    @Autowired
    public void setUserRepository(UserRepository usrRepo, MessageRepository msgRepo) {
        userRepository = usrRepo;  // we are setting the static variable
        messageRepository = msgRepo;
    }

    //banned words
    private final List<String> bannedWords = Arrays.asList("test", "testing");
    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {

        // server side log
        logger.info("[onOpen] " + username);

        User existingUser = userRepository.findByName(username);

        // Handle the case of a duplicate username
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username is already in use");
            session.close();
        } else if (existingUser == null) {
            session.getBasicRemote().sendText("Username doesn't exist within database");
            session.close();
        } else {
            // map current session with username
            sessionUsernameMap.put(session, username);

            // map current username with session
            usernameSessionMap.put(username, session);

            sendMessageToPArticularUser(username, getChatHistory());

            if (existingUser.getCanChat()){
                // send to the user joining in
                sendMessageToPArticularUser(username, "Welcome to the chat, " + username);

                // send to everyone in the chat
                broadcast("User: " + username + " has joined the chat");
            } else {
                sendMessageToPArticularUser(username, "Welcome back, " + username + ". You are still banned though. :)");

                // send to everyone in the chat
                broadcast("User: " + username + " has joined the chat, but can't say anything because they've been bad.");
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

        if (user.getCanChat()) {
            // Direct message to a user using the format "@username <message>"
            if (message.startsWith("@")) {

                // split by space
                String[] split_msg = message.split("\\s+");

                // Combine the rest of message
                StringBuilder actualMessageBuilder = new StringBuilder();
                for (int i = 1; i < split_msg.length; i++) {
                    actualMessageBuilder.append(split_msg[i]).append(" ");
                }
                String destUserName = split_msg[0].substring(1);    //@username and get rid of @
                String actualMessage = actualMessageBuilder.toString();

                boolean containsBannedWord = messageCheck(actualMessage, username);

                if (!containsBannedWord) {
                    sendMessageToPArticularUser(destUserName, "[DM from " + username + "]: " + actualMessage);
                    sendMessageToPArticularUser(username, "[DM from " + username + "]: " + actualMessage);
                    messageRepository.save(new Message(username, message));
                }
            } else { // Message to whole chat
                boolean containsBannedWord = messageCheck(message, username);
                if (!containsBannedWord) {
                    broadcast(username + ": " + message);
                    messageRepository.save(new Message(username, message));
                }
            }
        } else {
            sendMessageToPArticularUser(username, "[DM from server]: sorry buddy, you've been banned, no messaging for you.");
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

        // server side log
        logger.info("[onClose] " + username);

        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // send the message to chat
        broadcast(username + " disconnected");
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

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param username The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

    private boolean messageCheck(String message, String sender) {
        for (String bannedWord : bannedWords) {
            if (message.contains(bannedWord)) {
                User user = userRepository.findByName(sender);
                sendMessageToPArticularUser(sender, "Please refrain from using that language. You have received one strike on your account.");
                user.setBanStrikes(user.getBanStrikes() + 1);
                sendMessageToPArticularUser(sender, "Current strikes: " + user.getBanStrikes() + "/3");
                if (user.getBanStrikes() >= 3) {
                    user.setCanChat(false);
                }
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    private String getChatHistory() {
        List<Message> messages = messageRepository.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages != null && messages.size() != 0) {
            for (Message message : messages) {
                sb.append(message.getUserName() + ": " + message.getContent() + "\n");
            }
        }
        return sb.toString();
    }

}