package com.example.sumon.androidvolley;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private EditText messageInput;
    private Button sendButton;
    private TextView toolbarTitle;
    private ScrollView chatScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_layout);

        // Initialize views
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        toolbarTitle = findViewById(R.id.toolbar_title);
        chatScrollView = findViewById(R.id.chat_scrollview);

        // Set the toolbar title
        toolbarTitle.setText("Chat Room");

        // Set a click listener for the send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sending the message
                String message = messageInput.getText().toString();
                // Implement message sending logic here
                // You can add the message to the chat container
                // dynamically within the ScrollView.
            }
        });
    }
}
