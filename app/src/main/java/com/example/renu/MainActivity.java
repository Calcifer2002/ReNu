package com.example.renu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
    public TextView Time;
   ImageButton buttonGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGuide = findViewById(R.id.buttonG);
        Temp = findViewById(R.id.temp);
        Time = findViewById(R.id.time);
        Moist = findViewById(R.id.moist);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference renuRef = db.child("sensor");
       renuRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {


                   String key1 = snapshot.child("Key 1").getValue(String.class);
                   String t = key1.split("=")[1];
                   String time = key1.split("=")[3];
                   String moisturetime = key1.split("=")[2];
                   String moisture = moisturetime.split(" ")[0];

                   String temperature = t.split(" ")[0];

                   Log.d("TAG", key1);
                   String finaltime = "Last updated" + time;
                   Time.setText(finaltime);
                   Temp.setText(temperature);
                   Moist.setText(moisture);

               }



           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    buttonGuide.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, Guidelines.class));
        }
    });

    }
}