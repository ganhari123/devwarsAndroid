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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

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
        setContentView(R.layout.home);
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
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
     * @param view the view
     */
    public void goProfilePage(View view) {
        final Intent intent = new Intent(this, MainProfilePage.class);
        intent.putExtra(USER_NAME, username);
        startActivity(intent);
    }

    /**
     * Transitions to the search pages
     * @param view the view
     */
    public void goToSearchPages(View view) {
        final Intent intent = new Intent(this, SearchPages.class);
        intent.putExtra(USER_NAME, username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }

    /**
     * Transitions to the rating page
     * @param view the view
     */
    public void goRatePage(View view) {
        final Intent intent = new Intent(this, RateMovie.class);
        intent.putExtra(USER_NAME, username);
        startActivity(intent);
    }

    /**
     * Transitions to the recently rated page
     * @param view the view
     */
    public void goRecentlyRated(View view) {
        final Intent intent = new Intent(this, RecentlyRated.class);
        intent.putExtra(USER_NAME, username);
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

    private class GetLoginAndMajor extends AsyncTask<String, Long, String> {
        /**
         * Do request in background
         * @param urls The url to request from
         * @return Response
         */
        protected String doInBackground(String... urls) {
            try {

                final HttpRequest request = HttpRequest.get(urls[0]);
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
         * The progress of request
         * @param progress The progress of request
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * The response execution
         * @param file The response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                try {
                    final JSONArray arr = new JSONArray(file);
                    final JSONObject obj = arr.getJSONObject(0);
                    username = obj.getString("username");
                    major = obj.getString("major");
                } catch (JSONException j) {
                    Log.d("MUST HAVE", "MUST HAVE");
                }
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    private class Logout extends AsyncTask<String, Long, String> {
        /**
         * the request
         * @param urls The url
         * @return The response
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
         *
         * @param progress The progress of the request
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * The response
         * @param file The response file
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                final Intent intent = new Intent(Home.this, WelcomeScreen.class);
                startActivity(intent);
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
