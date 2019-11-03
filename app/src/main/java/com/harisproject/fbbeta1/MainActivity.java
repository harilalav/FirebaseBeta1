package com.harisproject.fbbeta1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText usr;
    EditText pswd;
    Button login;
    TextView newusr;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usr=findViewById(R.id.usr);
        pswd=findViewById(R.id.pswd);
        login=findViewById(R.id.login);
        newusr=findViewById(R.id.newusr);
        mAuth=FirebaseAuth.getInstance();
       //To avoid multiple logins for users
        /* if (mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        } */
        mDialog=new ProgressDialog(this);
        newusr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrid=usr.getText().toString().trim();
                String usrpass=pswd.getText().toString().trim();
                if (TextUtils.isEmpty(usrid))
                {
                    usr.setError("This field is mandatory");
                    return;
                }
                if (TextUtils.isEmpty(usrpass))
                {
                    pswd.setError("Please enter a password");
                    return;
                }
                mDialog.setMessage("Logging In");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(usrid,usrpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            mDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Problem Logging In",Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}
