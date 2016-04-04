package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class AdminHome extends AppCompatActivity {

    /**
     * the constant to pass between Intents
     */
    private static final String USER_NAME = "USER_NAME";


    /**
     * the username to pass between intents
     */
    private String username;

    /**
     * the major within this activity
     */
    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        /**
         * This code in the oncreate method stores the username to reference the database for any further information
         */
        final Intent intent = getIntent();
        username = intent.getStringExtra(USER_NAME);
        major = intent.getStringExtra("MAJOR");
        final TextView dispUserName = (TextView) findViewById(R.id.disp_username);
        dispUserName.setText("Welcome " + username);
        //Log.d("anyString", username);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Making an http request to change the loginstatus to 0 after the logout button is pressed
     * @param view The view
     */
    public void logout(View view) {
        new Logout().execute("https://pandango.herokuapp.com/changeLoginStatus/" + username);
    }


    /**
     * Transitions to the profile page
     * @param view The view
     */
    public void goProfilePage(View view) {
        final Intent intent = new Intent(this, MainProfilePage.class);
        intent.putExtra(USER_NAME, username);
        startActivity(intent);
    }


    /**
     * Transitions to the search pages
     * @param view The view
     */
    public void goToSearchPages(View view) {
        final Intent intent = new Intent(this, SearchPages.class);
        intent.putExtra(USER_NAME, username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }


    /**
     * Transitions to the rate pages
     * @param view The view
     */
    public void goRatePage(View view) {
        final Intent intent = new Intent(this, RateMovie.class);
        intent.putExtra(USER_NAME, username);
        startActivity(intent);
    }


    /**
     * Transitions to the recently rated pages
     * @param view The view
     */
    public void goRecentlyRated(View view) {
        final Intent intent = new Intent(this, RecentlyRated.class);
        intent.putExtra(USER_NAME, username);
        startActivity(intent);
    }


    /**
     * Transitions to the user modification class
     * @param view The view
     */
    public void userMod(View view) {
        final Intent intent = new Intent(this, UserMod.class);
        intent.putExtra(USER_NAME, username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }


    /**
     * get User Name
     * @return returns the username
     */
    public String getUserName() {
        return username;
    }


    /**
     * Set username
     * @param any anyString
     */
    public void setUserName(String any) {
        username = any;
    }


    /**
     * get Major
     * @return returns the major
     */
    public String getMajor() {
        return major;
    }


    /**
     * Set the major to anyString
     * @param any anyString
     */
    public void setMajor(String any) {
        major = any;
    }

    private class Logout extends AsyncTask<String, Long, String> {
        /**
         * Initializes and executes the http request
         * @param urls The url to send the http request to
         * @return returns a response as a string
         */
        protected String doInBackground(String... urls) {
            try {

                final HttpRequest request = HttpRequest.post(urls[0]);
                String result = null;
                if (request.ok()) {
                    result = request.body();
                }
                return result;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        /**
         * while the request is going on
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }


        /**
         * What happens after the request is done
         * @param file the response from request
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                final Intent intent = new Intent(AdminHome.this, WelcomeScreen.class);
                startActivity(intent);
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }



}
