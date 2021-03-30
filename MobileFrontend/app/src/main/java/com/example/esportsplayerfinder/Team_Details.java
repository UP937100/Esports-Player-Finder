package com.example.esportsplayerfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Team_Details extends AppCompatActivity {

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team__details);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.Dashbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setLogo(R.drawable.logo6);
        getSupportActionBar().setTitle(R.string.Empty_String);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
// Checks if the user is logged in and edits menu options
        if(ProfileMan.username==null){
            this.menu.findItem(R.id.myProfile).setVisible(false);
            this.menu.findItem(R.id.accountSettings).setVisible(false);
            this.menu.findItem(R.id.logout).setVisible(false);
            this.menu.findItem(R.id.loginOption).setVisible(true);
            this.menu.findItem(R.id.registerOption).setVisible(true);
        }else{
            this.menu.findItem(R.id.myProfile).setVisible(true);
            this.menu.findItem(R.id.accountSettings).setVisible(true);
            this.menu.findItem(R.id.logout).setVisible(true);
            this.menu.findItem(R.id.loginOption).setVisible(false);
            this.menu.findItem(R.id.registerOption).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch(item.getItemId()){
            case R.id.myProfile:
                // Redirect to profile page
                Intent intentProfile = new Intent(Team_Details.this, Activity_Profile.class);
                startActivity(intentProfile);;
                return true;
            case R.id.loginOption:
                // Redirect to Login page
                Intent intentLogin = new Intent(Team_Details.this, Login.class);
                startActivity(intentLogin);;
                return true;
            case R.id.registerOption:
                //Redirect to register page
                Intent intentRegister = new Intent(Team_Details.this, Register.class);
                startActivity(intentRegister);
                return true;
            case R.id.dashboard:
                //Redirect to dashboard
                Intent intentDashboard = new Intent(Team_Details.this, MainActivity.class);
                startActivity(intentDashboard);
                return true;
            case R.id.logout:
                //Reset stored information
                ProfileMan.username = null;
                ProfileMan.ID = -1;
                ProfileMan.email = null;
                ProfileMan.token = "";
                //Redirect to register page
                Intent intentLogout = new Intent(Team_Details.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            case R.id.accountSettings:
                //Redirect to account settings page
                Intent intentAccountSettings = new Intent(Team_Details.this, Account_Settings.class);
                startActivity(intentAccountSettings);
                return true;
            case R.id.findOrCreateTeam:
                //Redirect to find team page
                Intent intentFindTeam = new Intent(Team_Details.this, Find_Team.class);
                startActivity(intentFindTeam);
                return true;
            case R.id.myTeams:
                //Redirect to find team page
                Intent intentMyTeams = new Intent(Team_Details.this, My_Teams.class);
                startActivity(intentMyTeams);
                return true;


        }



        return super.onOptionsItemSelected(item);
    }
}