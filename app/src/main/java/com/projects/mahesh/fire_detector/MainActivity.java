package com.projects.mahesh.fire_detector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

    FirebaseAuth fAuth;
    DatabaseReference fDatabase;
    FirebaseUser user;

    ArrayList <RoomData> array = new ArrayList<>();


    Users usr;
    myAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.room_view);
        name = findViewById(R.id.name);
        society = findViewById(R.id.building_name);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        if(user!=null){
            Toast.makeText(this, "User exist.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not Exist", Toast.LENGTH_SHORT).show();
        }
        adapter = new myAdapter(MainActivity.this,array,R.layout.room_element);
        userCall();
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
