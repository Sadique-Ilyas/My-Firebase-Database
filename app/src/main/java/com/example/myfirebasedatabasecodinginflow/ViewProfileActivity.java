package com.example.myfirebasedatabasecodinginflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileActivity extends AppCompatActivity {
    ListView myListView;
    DatabaseReference databaseReference;
    List<MyProfile> myProfileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        myListView = findViewById(R.id.myListView);
        myProfileList = new ArrayList<>();
        viewAllInfo();
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyProfile myProfile = myProfileList.get(position);
                showUpdateDialogue(position,myProfile.getName(),myProfile.getSurname());
                return false;
            }
        });
    }

    private void showUpdateDialogue(final int position, String name, String surname)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_alert_dialogue,null);
        builder.setView(dialogView);

        final EditText newFirstName = dialogView.findViewById(R.id.newFirstName);
        final EditText newSurName = dialogView.findViewById(R.id.newSurName);
        final EditText newCity = dialogView.findViewById(R.id.newCity);

        builder.setTitle("Updating " + name +" "+ surname + " Profile");
        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String firstName = newFirstName.getText().toString().trim();
                String surName = newSurName.getText().toString().trim();
                String city = newCity.getText().toString().trim();

                updateProfile(position, firstName, surName, city);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("DELETE PROFILE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProfile(position);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProfile(int position)
    {
        MyProfile selectedItem = myProfileList.get(position);
        final String selectedKey = selectedItem.getKey();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile").child(selectedKey);
        databaseReference.removeValue();
        myProfileList.clear();
        Toast.makeText(this, "Profile Deleted !!!", Toast.LENGTH_SHORT).show();
    }

    private void updateProfile(int position, String firstName, String surName, String city)
    {
        MyProfile selectedItem = myProfileList.get(position);
        final String selectedKey = selectedItem.getKey();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile").child(selectedKey);

        MyProfile myProfile = new MyProfile(firstName,surName,city);
        myProfileList.clear();
        databaseReference.setValue(myProfile);
        Toast.makeText(this, "Profile Updated !!!", Toast.LENGTH_SHORT).show();
    }

    private void viewAllInfo()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Profile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myProfileList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren())
                {
                    MyProfile myProfile = postSnapshot.getValue(MyProfile.class);
                    myProfile.setKey(postSnapshot.getKey());
                    myProfileList.add(myProfile);

                    CustomListAdapter adapter = new CustomListAdapter(ViewProfileActivity.this,myProfileList);
                    myListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}