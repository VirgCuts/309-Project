package com.example.sumon.androidvolley;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamMultiplayerGame extends MultiPlayerGame{

    private String Player1 = "Carter", Player2 = "Conor", Player3 = "Keenan", Player4 = "Sam";
    private String BASE_URL = "ws://coms-309-022.class.las.iastate.edu:8080/multiplayer/";

    /**
     * Sends the current game board state to the opponent via WebSocket.
     *
     * @param board The current game board state.
     */
    private void sendBoardState(sendBoard board) {
        try {
            // sends the edit text message

            String boardState = "{" +
                    "  \"name1\": \""+ Player1 + "\"," +
                    "  \"name2\": \""+ Player2 + "\"," +
                    "  \"board\": {" +
                    "    \"game\": [" +
                    board.toString() +
                    "    ]," +
                    "    \"won\": false," +
                    "    \"score\": 0" +
                    "  }" +
                    "}";
            Log.d("SENDBOARD",boardState);
            WebSocketManager.getInstance().sendMessage(boardState);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage().toString());
        }
    }

    public void onWebSocketMessage(String message) {
        Log.d("RECIEVED", message);

        try {
            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(message);
            Log.d("GAMIE",jsonObject.toString());
            // Extract the "game" object
            JSONArray gameS = jsonObject.getJSONArray("game");
            //creates a string representation of the board to be changed
            String boardGrid = gameS.toString();
            Log.d("ARRIQ",boardGrid.toString());
            //[[1,1,1],[1,1,0],[0,0,0]]
            boardGrid = boardGrid.replace("[", "").replace("]", "");
            Log.d("ARRCL",boardGrid);
            //1,1,1,1,1,0,0,0,0

            String[] boardValues = boardGrid.split(",");
            int winTally = 0;
            for (int i =0; i < boardValues.length; i++) {
                if(Integer.parseInt(boardValues[i]) == 1) {
                    changeOppColor(i);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
