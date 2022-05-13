package com.example.renu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public TextView Moist;
    public TextView Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Temp = findViewById(R.id.temp);
        Moist = findViewById(R.id.moist);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference renuRef = db.child("Renu");
       renuRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {


                   String key1 = snapshot.child("Key1").getValue(String.class);
                   String t = key1.split("=")[1];
                   String moisture = key1.split("=")[2];
                   String temperature = t.split(" ")[0];

                   Log.d("TAG", key1);
                   Temp.setText(temperature);
                   Moist.setText(moisture);

               }



           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


    }
}