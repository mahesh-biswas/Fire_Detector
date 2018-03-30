package com.projects.mahesh.fire_detector;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<layoutView> temp;
    GridView grid;
    TextView name,society;
    Button signout;
    LinearLayout back;

    FirebaseAuth fAuth;
    DatabaseReference fDatabase;
    FirebaseUser user;

   // ff4444    red     ffffff   white

    ArrayList <RoomData> array = new ArrayList<>();
    boolean action=false;

    Users usr;
    myAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.room_view);
        name = findViewById(R.id.name);
        society = findViewById(R.id.building_name);
        signout = findViewById(R.id.signout);
        back = findViewById(R.id.back);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        if(user!=null){
            Toast.makeText(this, "User exist.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not Exist", Toast.LENGTH_SHORT).show();
        }
        adapter = new myAdapter(MainActivity.this,array,R.layout.room_element);
        userCall();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(MainActivity.this,SignIn.class));
                finish();
            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String rno = array.get(i).getRoom_no();
                Toast.makeText(MainActivity.this, "Clicked: "+rno, Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                if(action){
                    builder.setTitle("Override Action");
                    builder.setMessage("Stop Fire-Extinguisher??");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            fDatabase = FirebaseDatabase.getInstance().getReference("Action");
                            fDatabase.child(usr.getSociety()).child(usr.getFlatId()).child(rno).setValue("no")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(MainActivity.this, "Action Overridden....", Toast.LENGTH_SHORT).show();
                                                action = false;
                                                back.setBackgroundColor(Color.parseColor("#ffffff"));
                                                dialogInterface.dismiss();
                                            }
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();
                }else{
                    builder.setTitle("Manual Action");
                    builder.setMessage("Deploy Fire-Extinguisher...");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            fDatabase = FirebaseDatabase.getInstance().getReference("Action");
                            fDatabase.child(usr.getSociety()).child(usr.getFlatId()).child(rno).setValue("yes")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(MainActivity.this, "Deploying Action....", Toast.LENGTH_SHORT).show();
                                                action = true;
                                                back.setBackgroundColor(Color.parseColor("#ff4444"));
                                                dialogInterface.dismiss();
                                            }
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();
                }
                return true;
            }
        });
    }

    private void userCall(){
        fDatabase = FirebaseDatabase.getInstance().getReference("Users");
        fDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*System.out.println("||||||||||||||||||||||||||||----------------------||||||||||||||||||||||||||||||||||");
                System.out.println(dataSnapshot.getKey());
                System.out.println(dataSnapshot.getValue().toString());*/
                usr = (Users) dataSnapshot.getValue(Users.class);
                if(usr!=null) {
                    Toast.makeText(MainActivity.this, "Name: " + usr.getName() + "\n society: " + usr.getSociety() + "\n flatId: " + usr.getFlatId(), Toast.LENGTH_LONG).show();
                    name.setText(usr.getName());
                    society.setText(usr.getSociety());
                    SocietyCall();
                }else {
                    Toast.makeText(MainActivity.this, "kya pata", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void SocietyCall(){
        fDatabase = FirebaseDatabase.getInstance().getReference("Society");
        fDatabase.child(usr.getSociety()).child(usr.getFlatId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                array.clear();
                System.out.println("||||||||||||||||||||||||||||----------------------||||||||||||||||||||||||||||||||||");
                System.out.println(usr.getSociety());
                System.out.println(usr.getFlatId());
                System.out.println(dataSnapshot.getChildrenCount());
                String room = dataSnapshot.getValue().toString();
                System.out.println(room);
                String Room="";
                for(int i = 0; i<room.length();i++){
                    if(room.charAt(i)=='{' || room.charAt(i)=='}' || room.charAt(i)==','){

                    }else{
                        Room+=room.charAt(i);
                    }
                }
                System.out.println("|<"+Room+">|");
                String rooms[] = Room.split(" ");
                for(int i=0;i<rooms.length;i++){
                    String Rooms[] = rooms[i].split("=");
//                    System.out.println(Rooms[0]+" ||| "+Rooms[1]);
                    array.add(new RoomData(Rooms[0],Rooms[1]));
                }
                grid.setAdapter(adapter);
                for(int i =0 ; i<array.size();i++){
                    System.out.println(array.get(i).getRoom_no()+" <->"+array.get(i).getTemperature());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
