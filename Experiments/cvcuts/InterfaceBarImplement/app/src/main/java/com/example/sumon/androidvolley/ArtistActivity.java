package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArtistActivity extends AppCompatActivity {

    private final String TAG = ArtistActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView msgResponse;
    private EditText searchText;
    private TextView boolText;
    private String tag_string_req = "string_req";
    private EditText deleteNum, orgnArtist, newArtist, artistName, numPlat, numGrammies,song,songGenre;

    private Navigation navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_activity);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        Button btnBack = findViewById(R.id.btnBack);
        Button deleteButton = findViewById(R.id.deleteArtist);
        Button addButton = findViewById(R.id.addArtist);
        artistName = findViewById(R.id.artistName);
        numPlat = findViewById(R.id.numPlat);
        numGrammies = findViewById(R.id.numGrammies);
        song = findViewById(R.id.song);
        songGenre = findViewById(R.id.songGenre);

        msgResponse = findViewById(R.id.msgResponse);
        searchText = findViewById(R.id.searchText);
        boolText = findViewById(R.id.boolText);
        deleteNum = findViewById(R.id.deleteNum);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        makeStringReq();

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) { // Use EditorInfo.IME_ACTION_DONE

                    String jsonString = msgResponse.getText().toString();
                    String artistToSearch = searchText.getText().toString();

                    boolText.setText(searchArtistInJson(jsonString, artistToSearch));

                    return true; // Return true to indicate that the action has been handled
                }
                return false; // Return false if not handled
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addArtistToDB(artistName.getText().toString(), Integer.parseInt(numPlat.getText().toString()), Integer.parseInt(numGrammies.getText().toString()) ,song.getText().toString(),songGenre.getText().toString());
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteArtistInDB(deleteNum.getText().toString());
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArtistActivity.this,
                        MainActivity.class));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private String searchArtistInJson(String jsonString, String artistToSearch) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String artistName = jsonObject.getString("name").trim(); // Trim whitespace

                if (artistName.equals(artistToSearch)) { // Use equals for case-sensitive comparison
                    // Match found, display the associated song
                    String songName = jsonObject.getJSONObject("song").getString("songName");
                    showToast("Artist Found");
                    return "Song: " + songName;
                }
            }

            showToast("Artist not found.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Song not found for " + artistToSearch;
    }
    private void addArtistToDB(String artistName, int numPlatinums, int numGrammys, String songName, String songGenre) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists"; // Replace with your API endpoint URL

        // Create a JSONObject to send the artist and song data
        JSONObject artistData = new JSONObject();
        try {
            artistData.put("name", artistName);
            artistData.put("numPlatinums", numPlatinums);
            artistData.put("numGrammys", numGrammys);

            // Create a JSONObject for the song
            JSONObject songData = new JSONObject();
            songData.put("songName", songName);
            songData.put("genre", songGenre);

            // Add the song data to the artist data
            artistData.put("song", songData);
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("Error creating JSON object.");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                artistData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showToast("Artist and song added successfully.");
                        // Handle any additional logic after successfully adding the artist and song
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error adding artist and song: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void updateArtistInDB(String currentArtist, String newArtistName) {
        // Create a JSONObject to send the old and new artist names
        JSONObject artistData = new JSONObject();
        if(currentArtist == null || newArtistName == null) {
            showToast("One of the names are Null");
        }
        else {
            try {
                artistData.put("currentName", currentArtist);
                artistData.put("newName", newArtistName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Define the URL for your API endpoint
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                artistData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showToast("Artist name updated successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error updating artist name: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void deleteArtistInDB(String idNum){
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + idNum; // Include the artist ID in the URL

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        makeStringReq();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error deleting artist: " + error.getMessage());
                    }
                });

        // Add the request to the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     * */
    private void makeStringReq() {
        showProgressDialog();
        StringRequest strReq = new StringRequest(Method.GET, "http://coms-309-022.class.las.iastate.edu:8080/artists/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());


                msgResponse.setText(response.toString());
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print(TAG);
                System.out.print(TAG);
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        String tag_string_req = "string_req";
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}

