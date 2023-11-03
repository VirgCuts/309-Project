package onetoone.Websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import onetoone.Users.User;
import onetoone.Users.Board;

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
@ServerEndpoint("/multiplayer/{username}")
@Component
public class MultiplayerServer {

    @Autowired
    UserRepository userRepository;

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(MultiplayerServer.class);

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

        // Handle the case of a duplicate username
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        }
        else {
            // map current session with username
            sessionUsernameMap.put(session, username);

            // map current username with session
            usernameSessionMap.put(username, session);

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

        // server side log
        logger.info("[onMessage] " + username + ": " + message);

        // get the message which will be a json containing user1 and their updated board state
        ObjectMapper mapper = new ObjectMapper();
        // somehow split the username and board class

        String name1 = mapper.readValue(message, String.class);
//        String name2 = mapper.readValue(message, String.class);

        User user1old = userRepository.findByName(name1);
        Board board1 = mapper.readValue(message, Board.class);
        // update the board state of user1 in backend
        user1old.setBoard(board1);
        userRepository.save(user1old);
        // so frontend does not have access to user object, so need to make the board manually and
        // retrieve the users by username given

        // send updated user1 board to opponent (other user2)
        // this will be jsonified board data
        String boardData = mapper.writeValueAsString(board1);

//        UNCOMMENT when it is solved how to split given data and get opponent username
//        sendBoardDataToOpponent(name2, boardData);

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

        // save new high score and send final board to opponent
        User user1 = userRepository.findByName(username);
        Board board1 = user1.getBoard();
        if (user1.getHighScore() < board1.getScore()) {
            user1.setHighScore(board1.getScore());
            userRepository.save(user1);
        }
        ObjectMapper mapper = new ObjectMapper();
        String boardData = mapper.writeValueAsString(board1);

        Set<Session> keys = sessionUsernameMap.keySet();
        String user2 = "opponent";
        for(Session key: keys){
            if (!key.getId().equals(session.getId())) {
                user2 = sessionUsernameMap.get(key);
                if (!user2.equals(username)) {
                    break;
                }
            }
        }
        sendBoardDataToOpponent(user2, boardData);

        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);


        // send user score to db for high score and send opponents final board
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
    private void sendBoardDataToOpponent(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[User Data Exception] " + e.getMessage());
        }
    }

}