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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_screen extends AppCompatActivity {

    /**
     * Number of login attempts
     */
    private static final int LOGIN_ATTEMPTS = 3;
    /**
     * Number of login attempts
     */
    private int loginAttempt;
    /**
     * The username
     */
    private String username;
    /**
     * the password
     */
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        loginAttempt = LOGIN_ATTEMPTS;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_screen, menu);
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
     * Logs a user in based on the details in the two textboxes
     * If the user has tried to login more than 3 times he is locked out
     * Makes a Http Request which accesses a route on the server which checks the database
     * to see if username exists and password matches
     * @param view The view
     */
    public void checkLoginIn(View view) {
        if (loginAttempt > 0) {
            final EditText ed = (EditText) findViewById(R.id.user_name);
            final EditText ed2 = (EditText) findViewById(R.id.password);
            username = ed.getText().toString();
            password = ed2.getText().toString();
            final String url = "https://pandango.herokuapp.com/getLoginStatus/" + username + "/" + password;
            new LoginTask().execute(url).toString();
        } else {
            final LoginStatus login = LoginStatus.newInstance(R.string.StopLogin);
            login.show(getFragmentManager(), "dialog");
        }
    }

    /**
     * Cancels the login activity and returns to the welcome screen
     * @param view The view
     */
    public void cancelButton(View view) {
        final Intent intent = new Intent(this, WelcomeScreen.class);
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
     * get password
     * @return returns the major
     */
    public String getPassword() {
        return password;
    }


    /**
     * Set the password to anyString
     * @param any anyString
     */
    public void setMajor(String any) {
        password = any;
    }

    /**
     * get password
     * @return returns the major
     */
    public int getLoginAttempts() {
        return loginAttempt;
    }


    /**
     * Set the password to anyString
     * @param any anyString
     */
    public void setLoginAttempt(int any) {
        loginAttempt = any;
    }

    /**
     * Async subclass to manage http request of logging in
     * Throws exception if http request did not go through
     */
    private class LoginTask extends AsyncTask<String, Long, String> {
        /**
         * does this in another thread
         * @param urls The url
         * @return The response to request
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
         * While request is taking place
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * what happens after request is done
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                if (loginAttempt > 0) {
                    if (!"0".equals(file)) {
                        try {
                            final JSONArray arr = new JSONArray(file);
                            final JSONObject obj = arr.getJSONObject(0);
                            if ("admin".equals(obj.getString("userType"))) {
                                Log.d("userType", obj.getString("userType"));
                                final Intent intent = new Intent(Login_screen.this, AdminHome.class);
                                intent.putExtra("USER_NAME", username);
                                intent.putExtra("MAJOR", obj.getString("major"));
                                startActivity(intent);
                            } else {
                                final Intent intent = new Intent(Login_screen.this, Home.class);
                                intent.putExtra("USER_NAME", username);
                                intent.putExtra("MAJOR", obj.getString("major"));
                                startActivity(intent);
                            }

                        } catch(JSONException j) {
                            Log.d("MUST HAVE", "MUST HAVE");
                        }
                    } else {
                        final LoginStatus login = LoginStatus.newInstance(R.string.loginFailure);
                        login.show(getFragmentManager(), "dialog");
                        loginAttempt--;
                    }
                }
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
