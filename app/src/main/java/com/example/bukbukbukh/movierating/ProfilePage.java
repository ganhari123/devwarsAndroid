package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {
    /**
     * Storing the username and other details to use in other methods within this class
     */
    private String username;
    /**
     * the password
     */
    private String password;
    /**
     * the bio
     */
    private String bio;
    /**
     * The major
     */
    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        final Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        major = intent.getStringExtra("MAJOR");
        Log.d("user", username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_page, menu);
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
     * Allows you to change password and edit bio and major
     * Makes a http call to the database
     * @param view the view
     */
    public void changePassword(View view) {
        final EditText password1 = (EditText) findViewById(R.id.change_password);
        final EditText password2 = (EditText) findViewById(R.id.change_password2);
        final EditText bioT = (EditText) findViewById(R.id.bio);
        final EditText majorT = (EditText) findViewById(R.id.major);
        password = password1.getText().toString();
        bio = bioT.getText().toString();
        major = majorT.getText().toString();
        if (password.equals(password2.getText().toString())) {
            new ChangeProfileTask().execute("https://pandango.herokuapp.com/editProfile/" + username + "/" + password);
        } else {
            Log.d("MUST HAVE", "MUST HAVE");
        }
        /*profileChange login = profileChange.newInstance(R.string.changepassword, username);
        login.show(getFragmentManager(), "dialog");*/
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

    /**
     * get password
     * @return returns the password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Set the password to anyString
     * @param any anyString
     */
    public void setPassword(String any) {
        password = any;
    }

    /**
     * get bio
     * @return returns the major
     */
    public String getBio() {
        return bio;
    }


    /**
     * Set the bio to anyString
     * @param any anyString
     */
    public void setBio(String any) {
        bio = any;
    }

    /**
     * An async call that helps in changing password, bio and major
     */
    private class ChangeProfileTask extends AsyncTask<String, Long, String> {
        /**
         * the request done on another thread
         * @param urls The url
         * @return The response
         */
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> map = new HashMap<String, String>();
                map.put("bio", bio);
                map.put("major", major);
                final HttpRequest request = HttpRequest.post(urls[0]).form(map);
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
         * The progress
         * @param progress The progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * after request takes place
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                final Intent intent = new Intent(ProfilePage.this, Home.class);
                intent.putExtra("USER_NAME", username);
                intent.putExtra("MAJOR", major);
                startActivity(intent);
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
