package com.example.myfirebasedatabasecodinginflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText myName, mySurname, myCity;
    Button saveBtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Profile");

        myName = findViewById(R.id.myName);
        mySurname = findViewById(R.id.mySurname);
        myCity = findViewById(R.id.myCity);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = myName.getText().toString().trim();
                String surname = mySurname.getText().toString().trim();
                String city = myCity.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(city))
                {
                    Toast.makeText(MainActivity.this,"Fields empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MyProfile myProfile = new MyProfile(name,surname,city);
                    String myProfileId = databaseReference.push().getKey();
                    databaseReference.child(myProfileId).setValue(myProfile);
                    Toast.makeText(MainActivity.this, "Profile Saved", Toast.LENGTH_SHORT).show();
                    myName.setText("");
                    mySurname.setText("");
                    myCity.setText("");
                    myName.requestFocus();
                }
            }
        });
    }

    public void viewInfoActivity(View view)
    {
        startActivity(new Intent(MainActivity.this,ViewProfileActivity.class));
    }
}