package space.mosk.checkbrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.text.format.DateFormat;

import space.mosk.checkbrain.Models.Message;

public class ChatActivity extends AppCompatActivity {

    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_chat;
    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendBtn;
    private String username;

    Button btn_back;



    FirebaseDatabase database = FirebaseDatabase.getInstance("https://checkbrain-aaf80-default-rtdb.firebaseio.com/");
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // back_btn
        btn_back = findViewById(R.id.btn_back_chat);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatActivity.this, DashBoard.class));
                overridePendingTransition(0,0);
            }
        });

        activity_chat = findViewById(R.id.activity_chat);


        users = database.getReference("Users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = (String) dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        sendBtn = findViewById(R.id.btnSend_chat);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = findViewById(R.id.messageField);
                String txt = textField.getText().toString();
                if (txt.isEmpty()){
                    return;
                }
                database.getReference("Chat").push().setValue(
                        new Message(username,txt, FirebaseAuth.getInstance().getCurrentUser().getEmail())
                );
                textField.setText("");
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        }
        displayAllMessages();
    }

    private void displayAllMessages() {
        ListView list_of_msgs = findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item_msg, database.getReference("Chat")) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView mess_user, mess_time, mess_text;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mess_text.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mess_text.setLayoutParams(params);
                if (model.getUserEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    mess_text.setBackgroundColor(Color.rgb(204, 228, 255));
                    params = (RelativeLayout.LayoutParams) mess_text.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    mess_text.setLayoutParams(params);
                } else {
                    mess_text.setBackgroundColor(Color.rgb(236, 237,241));
                    params = (RelativeLayout.LayoutParams) mess_text.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    mess_text.setLayoutParams(params);
                }

                mess_user.setText(model.getUserName());
                mess_time.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", model.getMessageTime()));
                mess_text.setText(model.getTextMessage());
            }
        };


        list_of_msgs.setAdapter(adapter);

    }
}