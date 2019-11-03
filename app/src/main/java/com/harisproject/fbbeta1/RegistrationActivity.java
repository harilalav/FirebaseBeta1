package com.harisproject.fbbeta1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText regusr;
    EditText pswd;
    EditText cnfpswd;
    Button register;
    TextView signin;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        regusr=findViewById(R.id.regusr);
        pswd=findViewById(R.id.regpswd);
        cnfpswd=findViewById(R.id.confpswd);
        register=findViewById(R.id.Register);
        signin=findViewById(R.id.signin);
        mAuth=FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String usrid=regusr.getText().toString().trim();
            String usrpswd=pswd.getText().toString().trim();
            String usrpswdconf=cnfpswd.getText().toString().trim();
            mDialog=new ProgressDialog(RegistrationActivity.this);
            if(TextUtils.isEmpty(usrid))
            {
                regusr.setError("Required Field");
                return;
            }
            if(TextUtils.isEmpty(usrpswd))
            {

                pswd.setError("Password is required");
                return;
            }
            Boolean check;
            check=usrpswd.equals(usrpswdconf);
            if(check != true)
            {
               cnfpswd.setError("Passwords don't match");
               return;
            }
            mDialog.setMessage("Registering");
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(usrid,usrpswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Registration complete",Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
                        mDialog.dismiss();
                    }

                }
            });




            }
        });

    }
}
