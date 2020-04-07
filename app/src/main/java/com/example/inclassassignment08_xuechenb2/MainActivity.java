package com.example.inclassassignment08_xuechenb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");

    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference personRef = database.getReference("people");
    private EditText keyEditText;
    private EditText valueEditText;
    private TextView keyTextView;
    private TextView valueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRef.setValue("this is the String we are storing in the database");
        myRef = database.getReference("Messages");
        myRef.child("message1").setValue("this is the first child");

        keyEditText = findViewById(R.id.key_edit_text_field);
        valueEditText = findViewById(R.id.value_edit_text_field);

    }

    public void set(View view) {
        personRef.setValue(new Person("Nigel", 35, true));
    }

    public void add(View view) {
        personRef.push().setValue(new Person("Mike", 22, true));
        personRef.child("I NAME THE ID").setValue(new Person("Beyonce", 37, true));
    }

    public void writeToCloud(View view) {
        myRef = database.getReference(keyEditText.getText().toString());
        myRef.setValue(valueEditText.getText().toString());

    }

    public void readFromCloud(View view) {
        DatabaseReference childRef = myRef.child(keyEditText.getText().toString());
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String loadedData = dataSnapshot.getValue(String.class);
                    valueEditText.setText(loadedData);
                } else {
                    valueEditText.setText("");
                    Toast.makeText(MainActivity.this, "Cannot find key", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeFromCloud(View view) {
        myRef = database.getReference(keyEditText.getText().toString());
        myRef.removeValue();
    }
}


