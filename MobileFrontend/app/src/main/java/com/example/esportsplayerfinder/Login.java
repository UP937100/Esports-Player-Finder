package com.example.esportsplayerfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText eUsername;
    private EditText ePassword;
    private Button eLogin;
    private Button registerLink;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsername = findViewById(R.id.loginUsername);
        ePassword = findViewById(R.id.loginPassword);
        eLogin = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.linkToRegister);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.Dashbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setLogo(R.drawable.logo6);
        getSupportActionBar().setTitle(R.string.Empty_String);

        eLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String inputUsername = eUsername.getText().toString();
                String inputPassword = ePassword.getText().toString();

//                if inputUsername.isEmpty() || inputPassword.isEmpty()
//                {
//                    Toast.makeText( context: MainActivity.this, resId: "Please enter all details correctly.", Toast.LENGTH_SHORT).show()
//                }



                RequestQueue queue = Volley.newRequestQueue(Login.this);
                String url = "http://192.168.0.15:80/api/user";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Login.this, "works", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Login.this, response.toString(), Toast.LENGTH_LONG).show();
                                Log.d("load up profile suc:", response.toString());
                                try {
                                    ProfileMan.ID = (new Integer( response.getString("id")));
                                    Log.d("ID:", response.getString("id"));
                                    ProfileMan.username = ( response.getString("name"));
                                    Log.d("name:", response.getString("name"));
                                    ProfileMan.email = ( response.getString("email"));
                                    Log.d("email:", response.getString("email"));
                                    changeToProfilePage(findViewById(R.id.btnLogin));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                                Log.d("load up profile error:", error.toString());
                                error.printStackTrace();

                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Authorization", "Bearer " + ProfileMan.token);

                        return params;
                    }
                };
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(jsonObjectRequest);
            }
        });




    }

//    private boolean validate(String username, String password)
//    {
//
//    }
public void changeToRegisterPage(View view) {
    Intent intentRegister = new Intent(Login.this, Register.class);
    startActivity(intentRegister);
}
    public void changeToProfilePage(View view) {
        Intent intentProfile = new Intent(Login.this, Activity_Profile.class);
        startActivity(intentProfile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
// Checks if the user is logged in and edits menu options
        if(ProfileMan.username==null){
            this.menu.findItem(R.id.myProfile).setVisible(false);
            this.menu.findItem(R.id.logout).setVisible(false);
            this.menu.findItem(R.id.loginOption).setVisible(true);
            this.menu.findItem(R.id.registerOption).setVisible(true);
        }else{
            this.menu.findItem(R.id.myProfile).setVisible(true);
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
                Intent intentProfile = new Intent(Login.this, Activity_Profile.class);
                startActivity(intentProfile);;
                return true;
            case R.id.loginOption:
                // Redirect to Login page
                Intent intentLogin = new Intent(Login.this, Login.class);
                startActivity(intentLogin);;
                return true;
            case R.id.registerOption:
                //Redirect to register page
                Intent intentRegister = new Intent(Login.this, Register.class);
                startActivity(intentRegister);
                return true;
            case R.id.dashboard:
                //Redirect to dashboard
                Intent intentDashboard = new Intent(Login.this, MainActivity.class);
                startActivity(intentDashboard);
                return true;
            case R.id.logout:
                //Reset stored information
                ProfileMan.username = null;
                ProfileMan.ID = -1;
                ProfileMan.email = null;
                //Redirect to register page
                Intent intentLogout = new Intent(Login.this, MainActivity.class);
                startActivity(intentLogout);
                return true;


        }


        return super.onOptionsItemSelected(item);
    }



}