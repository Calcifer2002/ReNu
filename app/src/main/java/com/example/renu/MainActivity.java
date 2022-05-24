package com.example.renu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    public TextView Status;
    ImageView Istatus;
    public TextView greeting;
    ImageButton buttonGuide;
    TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGuide = findViewById(R.id.buttonG);
        Temp = findViewById(R.id.temp);
        greeting = findViewById(R.id.greeting);
        Time = findViewById(R.id.time);
        Moist = findViewById(R.id.moist);
        Status = findViewById(R.id.status);
        Istatus = findViewById(R.id.statusI);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference renuRef = db.child("sensor");
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d("aa", "idk");
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference idRef = db.child("users").child(currentuser);

      idRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
         @Override
       public void onComplete(@NonNull Task<DataSnapshot> task) {
               DataSnapshot snapshot = task.getResult();
              greeting.setText("Hello " + snapshot.child("username").getValue(String.class) +"!");
         }
});
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
                String notifTemp = temperature.split("\\.")[0];
                String notifMoisture = moisture.split("\\.")[0];

               int nM = Integer.parseInt(notifMoisture);

               int nT= Integer.parseInt(notifTemp);
               sendNotif(nM, nT);

            }

            private void sendNotif(int moisture, int temp) {
                if (moisture <= 40) {
                     Status.setText("Its too dry in here, please add more water!");
                     Istatus.setImageResource(R.drawable.ic_baseline_flare_24);
                     Status.setTextColor(Color.RED);
                }
                if (moisture >= 60) {
                    Status.setText("Its too wet in here,\n   please add more greens/browns!");
                    Status.setTextColor(Color.BLUE);
                    Istatus.setImageResource(R.drawable.ic_baseline_waves_24);
                }
                if (temp <= 50) {
                    Status.setText("Its too cold in here,\n   please turn me!");
                    Istatus.setImageResource(R.drawable.ic_baseline_ac_unit_24);
                    Status.setTextColor(Color.BLUE);
                }
                if (temp >= 70) {
                    Status.setText("Its too warm in here,\n   please turn me!");
                    Istatus.setImageResource(R.drawable.ic_baseline_local_fire_department_24);
                    Status.setTextColor(Color.RED);
                }
            }

            @Override
        public void onCancelled (@NonNull DatabaseError error){

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


