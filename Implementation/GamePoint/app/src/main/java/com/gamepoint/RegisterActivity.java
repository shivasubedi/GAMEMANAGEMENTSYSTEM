package com.gamepoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by Shiva_Subedi
 * computing project android Devlopment
 */

@SuppressWarnings("ALL")
public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputPhone, inputAddress, inputFullname;
    private FirebaseAuth auth;
    private Button btnSignUp, btnLogin;
    private ProgressDialog PD;
    private DatabaseReference databaseref;




    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...wait");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();
        //add on firebase realtime storage
        databaseref= FirebaseDatabase.getInstance().getReference("user").push();//refrence for user table

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
        //configure all buttons textview edittext...

        inputEmail = (EditText) findViewById(R.id.email);
        inputPhone = (EditText) findViewById(R.id.phonenum);
        inputAddress = (EditText) findViewById(R.id.address);
        inputFullname = (EditText) findViewById(R.id.fullname);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnLogin = (Button) findViewById(R.id.sign_in_button);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {


                //firle store for authentcation
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();


                try {
                    if (password.length() > 0 && email.length() > 0) {
                        PD.show();
                        adduser();//use function
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override

                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(
                                                    RegisterActivity.this,
                                                    "login Failed",
                                                    Toast.LENGTH_LONG).show();
                                            Log.v("error", task.getResult().toString());
                                        } else {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        PD.dismiss();
                                    }
                                });
                    } else {
                        Toast.makeText(
                                RegisterActivity.this,
                                "Fill All Fields",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                finish();
            }
        });



    }
    public  void adduser(){
        //file store in database
        final String fullname = inputFullname.getText().toString();
        final String email = inputEmail.getText().toString();
        final String phone = inputPhone.getText().toString();
        final String address = inputAddress.getText().toString();
        UserSave saver = new UserSave(fullname,email,phone,address);
        databaseref.setValue(saver);

    }
}