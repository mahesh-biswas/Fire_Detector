package com.projects.mahesh.fire_detector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ExampleData extends AppCompatActivity {

    Button generate,generate1,generate2,generate3,generate4;

    DatabaseReference fDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_data);
        generate = findViewById(R.id.generate);
        generate1 = findViewById(R.id.generate1);
        generate2 = findViewById(R.id.generate2);
        generate3 = findViewById(R.id.generate3);
        generate4 = findViewById(R.id.generate4);
        fDatabase = FirebaseDatabase.getInstance().getReference("Society");

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fDatabase.child("Rama_n_Rama").child("rand1").child("Room1")
                        .setValue(new Random().nextInt());
            }
        });
        generate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fDatabase.child("Rama_n_Rama").child("rand1").child("Room2")
                        .setValue(new Random().nextInt());
            }
        });
        generate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fDatabase.child("Rama_n_Rama").child("rand1").child("Room3")
                        .setValue(new Random().nextInt());
            }
        });
        generate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fDatabase.child("Rama_n_Rama").child("rand1").child("Room4")
                        .setValue(new Random().nextInt());
            }
        });
        generate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fDatabase.child("Rama_n_Rama").child("rand1").child("Room5")
                        .setValue(new Random().nextInt());
            }
        });
    }
}
