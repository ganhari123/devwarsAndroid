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

public class MainProfilePage extends AppCompatActivity {
    /**
     * The username
     */
    private String username;

    /**
     * The major passed through
     */
    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile_page);
        final Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        Log.d("UserName", username);
        major = intent.getStringExtra("MAJOR");
        final String url = "https://pandango.herokuapp.com/showProfile/" + username;
        new LoginTask().execute(url).toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_profile_page, menu);
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
     * Go to edit profile
     * @param view The view
     */
    public void editProfile(View view) {
        final Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }

    /**
     * Go back to home page
     * @param view The view
     */
    public void goHome(View view) {
        final Intent intent = new Intent(this, Home.class);
        intent.putExtra("USER_NAME", username);
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

    private class LoginTask extends AsyncTask<String, Long, String> {
        /**
         * Http request on another thread
         * @param urls The url
         * @return The response
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
         * while request is taking place
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * the execution after the request takes place
         * @param file The response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                try {
                    final JSONArray jsArray = new JSONArray(file);
                    final JSONObject obj = jsArray.getJSONObject(0);
                    final TextView ed1 = (TextView) findViewById(R.id.name_main);
                    final TextView ed2 = (TextView) findViewById(R.id.major_main);
                    final TextView ed3 = (TextView) findViewById(R.id.bio_main);
                    ed1.setText(obj.getString("name"));
                    ed2.setText(obj.getString("major"));
                    ed3.setText(obj.getString("bio"));
                    major = obj.getString("major");
                } catch(JSONException e) {
                    Log.d("MUST HAVE", "MUST HAVE");
                }
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
