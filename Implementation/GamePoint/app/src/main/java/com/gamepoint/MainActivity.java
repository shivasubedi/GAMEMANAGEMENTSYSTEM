package com.gamepoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * Created by Shiva_Subedi
 * computing project android Devlopment
 */

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mdLayout;
    private ActionBarDrawerToggle atoggle;
    private NavigationView nview;

    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog PD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        PD = new ProgressDialog(this);// set progress bar custom setting
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        mdLayout=(DrawerLayout) findViewById(R.id.drawer);
        atoggle= new ActionBarDrawerToggle(MainActivity.this,mdLayout,R.string.open,R.string.close);
        mdLayout.addDrawerListener(atoggle);
        atoggle.syncState();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        nview = (NavigationView)findViewById(R.id.navigation_view);

        nview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {

                    case R.id.account_logout:
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        /*FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                            @Override

                            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                if (user == null) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();

                                }
                            }
                        };*/
                        break;
                    case R.id.my_account:
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.winner_list:
                        startActivity(new Intent(getApplicationContext(), GamePoints.class));
                        break;
                    case R.id.Post_game:
                        startActivity(new Intent(getApplicationContext(), PostActivity.class));
                        break;
                    case R.id.live_chat:
                        startActivity(new Intent(getApplicationContext(), Discuss.class));
                        break;

                    case R.id.location_map:
                        startActivity(new Intent(getApplicationContext(), Location.class));
                        break;
                    case R.id.change_password:
                        startActivity(new Intent(getApplicationContext(), ForgetAndChangePasswordActivity.class).putExtra("Mode", 1));
                        break;
                    case R.id.change_email:
                        startActivity(new Intent(getApplicationContext(), ForgetAndChangePasswordActivity.class).putExtra("Mode", 2));
                        break;


                }

                return false;
            }
        });



        /*
        //apply click listenger on  Button to delete user by admin*****

        findViewById(R.id.delete_user_button).setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetAndChangePasswordActivity.class).putExtra("Mode", 3));
            }
        });*/
    }

    @Override    protected void onResume() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        super.onResume();
    }
}