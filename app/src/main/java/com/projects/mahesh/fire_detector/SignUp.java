package com.projects.mahesh.fire_detector;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SignUp extends AppCompatActivity {

    EditText name,email,uname,upass,verCode,society;
    Button signUp,signIn;
    String flatId="";
    String uid ="";

    FirebaseAuth fAuth;
    DatabaseReference fDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        uname = findViewById(R.id.uname);
        upass =findViewById(R.id.upass);
        verCode = findViewById(R.id.flatId);
        signUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.signIn);
        society = findViewById(R.id.society);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser usr = fAuth.getCurrentUser();
        if(usr!=null){
            startActivity(new Intent(SignUp.this,MainActivity.class));
            finish();
        }else{
            Toast.makeText(this, "User NOt SignedIn", Toast.LENGTH_SHORT).show();
        }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,SignIn.class));
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate())
                    return;
                Toast.makeText(SignUp.this, "Valid", Toast.LENGTH_SHORT).show();
                fAuth.createUserWithEmailAndPassword(E2T(email),E2T(upass))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "User added succesfully", Toast.LENGTH_SHORT).show();
                                    FirebaseUser usr = fAuth.getCurrentUser();
                                    Toast.makeText(SignUp.this, "uid: "+usr.getUid(), Toast.LENGTH_SHORT).show();
                                    uid = usr.getUid();
                                    Users user = new Users(uid,E2T(name),E2T(uname),E2T(upass),E2T(society),E2T(verCode));
                                    fDatabase.child(uid).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUp.this, "Signed Up Succesfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this,MainActivity.class));
                                                finish();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(SignUp.this, "Either Network Connection issue \n Or user Exist. Try SignIn", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    
            }
        });
    }

    private boolean userExist() {
        fAuth.signInWithEmailAndPassword("","")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            return;
                        }
                    }
                });
        return true;
    }

    private boolean validate(){
        if(E2T(email).equals("") | E2T(email)==null )
            return false;
        if(E2T(uname).equals("") | E2T(uname)==null )
            return false;
        if(E2T(upass).equals("") | E2T(upass)==null )
            return false;
        if(E2T(verCode).equals("") | E2T(verCode)==null )
            return false;
        if(E2T(name).equals("") | E2T(name)==null )
            return false;
        if(E2T(society).equals("") | E2T(society)==null )
            return false;
        return true;
    }
    private String E2T(EditText field){
        return  field.getText().toString();
    }
}
