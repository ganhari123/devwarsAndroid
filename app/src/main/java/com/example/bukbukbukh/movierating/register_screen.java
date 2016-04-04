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

public class Register_screen extends AppCompatActivity {

    private static final String EMPTY = "";

    /**
     * declaring variables that can be used all throughout the program
     */
    private String name;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        //mDb = MainDatabase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_screen, menu);
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
     * Registers a user by making a http call to the server and adding to the database
     * @param view
     */
    public void registerUser(View view) {
        final EditText ed1 = (EditText) findViewById(R.id.first_name);
        final EditText ed2 = (EditText) findViewById(R.id.last_name);
        final EditText ed3 = (EditText) findViewById(R.id.user_n);
        final EditText ed4 = (EditText) findViewById(R.id.pass);
        final EditText ed5 = (EditText) findViewById(R.id.conf_pass);
        name = ed1.getText().toString() + " " + ed2.getText().toString();
        username = ed3.getText().toString();
        password = ed4.getText().toString();
        final User globUser = new User(ed1.getText().toString(), ed1.getText().toString(), username, password);
        Log.d("ID", Integer.toString(globUser.getNumOfUsers()));
        if (ed5.getText().toString().equals(ed4.getText().toString())) {
            new RegisterTask().execute("https://pandango.herokuapp.com/userRegistration");
        } else {
            final LoginStatus checkRegisterStatus = LoginStatus.newInstance(R.string.register_status1);
            checkRegisterStatus.show(getFragmentManager(), "dialog");
        }

    }


    /**
     * Cancels the registration and returns to the Welcome screen
     * @param view
     */
    public void cancelButtonReg(View view) {
        final Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }

    /**
     * An async subclass that completes the http post request for registering a user
     */
    private class RegisterTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("name", name);
                keyValuePairs.put("username", username);
                keyValuePairs.put("password", password);
                final HttpRequest request = HttpRequest.post(urls[0]).form(keyValuePairs);
                String result = null;
                if (request.ok()) {
                    result = request.body();
                }
                return result;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(String file) {
            if (file != null) {
                if ("User already added".equals(file)) {
                    final LoginStatus login = LoginStatus.newInstance(R.string.register_status2);
                    login.show(getFragmentManager(), "dialog");
                } else if ("user added sucessfully!".equals(file)) {
                    final LoginStatus login = LoginStatus.newInstance(R.string.register_status3);
                    login.show(getFragmentManager(), "dialog");
                }
                final EditText ed1 = (EditText) findViewById(R.id.first_name);
                final EditText ed2 = (EditText) findViewById(R.id.last_name);
                final EditText ed3 = (EditText) findViewById(R.id.user_n);
                final EditText ed4 = (EditText) findViewById(R.id.pass);
                final EditText ed5 = (EditText) findViewById(R.id.conf_pass);
                ed1.setText(EMPTY);
                ed2.setText(EMPTY);
                ed3.setText(EMPTY);
                ed4.setText(EMPTY);
                ed5.setText(EMPTY);

            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
