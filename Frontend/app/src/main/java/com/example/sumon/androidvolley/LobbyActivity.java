package com.example.sumon.androidvolley;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LobbyActivity extends Activity implements OnClickListener {
//    private Button btnArtists, btnChatRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        btnArtists = (Button) findViewById(R.id.btnArtists);
//        btnChatRoom = (Button) findViewById(R.id.btnChatRoom);


        // button click listeners


//        btnArtists.setOnClickListener(this);
//        btnChatRoom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnArtists:
//                startActivity(new Intent(LobbyActivity.this,
//                        ArtistActivity.class));
//                break;
//            case R.id.btnChatRoom:
//                startActivity(new Intent(LobbyActivity.this,
//                        ChatActivity.class));
//                break;
//            default:
//                break;
//        }
    }

}
