package com.projects.mahesh.fire_detector;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    Button submit,create;
    EditText uname,upass;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        submit = (Button)findViewById(R.id.submit);
        create = findViewById(R.id.create);
        uname = findViewById(R.id.uname);
        upass = findViewById(R.id.upass);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser usr = fAuth.getCurrentUser();
        if(usr!=null){
            startActivity(new Intent(SignIn.this,MainActivity.class));
        }
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate()){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                    builder.setTitle("loggingIn");
                    builder.setMessage("wait..Loading");
                    builder.create().show();
                    fAuth.signInWithEmailAndPassword(E2T(uname),E2T(upass))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(SignIn.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(SignIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean Validate() {
        if(E2T(uname).equals("") | E2T(uname)==null )
            return false;
        if(E2T(upass).equals("") | E2T(upass)==null )
            return false;
        return true;
    }

    private String E2T(EditText field){
        return  field.getText().toString();
    }
}
