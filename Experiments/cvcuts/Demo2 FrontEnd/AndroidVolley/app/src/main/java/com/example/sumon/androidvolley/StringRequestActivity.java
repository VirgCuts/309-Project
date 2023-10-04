package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StringRequestActivity extends Activity {

    private String TAG = StringRequestActivity.class.getSimpleName();
    private Button btnBack;
    private ProgressDialog pDialog;
    private TextView msgResponse;
    private EditText searchText;
    private TextView boolText;

    // This tag will be used to cancel the request
    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.string_request);


        btnBack = (Button) findViewById(R.id.btnBack);

        msgResponse = (TextView) findViewById(R.id.msgResponse);
        searchText = findViewById(R.id.searchText);
        boolText = findViewById(R.id.boolText);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    makeStringReq();
                    handled = true;
                    String jsonString = msgResponse.getText().toString();

                    // Get the artist name from the EditText
                    String artistToSearch = searchText.getText().toString();
                    boolText.setText(searchArtistInJson(jsonString, artistToSearch));
                }
                return handled;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(StringRequestActivity.this,
                        MainActivity.class));

            }
        });
    }
    //Checks edit text and compares it to the users in the data returns true of false depending on inclusion.
    private String searchArtistInJson(String jsonString, String artistToSearch) {

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                if (name.equals(artistToSearch)) {
                    JSONObject laptopObject = jsonObject.getJSONObject("laptop");
                    return "Artist: " + name + " with song name: " + laptopObject.getString("songName") + " with " + jsonObject.getString("numPlatinums") + " platinum albums";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Song not found for " + artistToSearch;
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

        StringRequest strReq = new StringRequest(Method.GET, Const.URL_STRING_REQ, new Response.Listener<String>() {
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}