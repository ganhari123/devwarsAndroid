package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UserStatus extends AppCompatActivity {
    /**
     * the username
     */
    private String username;
    /**
     * the major
     */
    private String major;
    /**
     * the username
     */
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);
        username = getIntent().getStringExtra("USER_NAME");
        major = getIntent().getStringExtra("MAJOR");
        user = getIntent().getStringExtra("USER");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_status, menu);
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
     * ban user
     * @param view the view
     */
    public void ban(View view) {
        new ChangeStatusBan().execute("https://pandango.herokuapp.com/changeUserStatusBan/" + user);
    }

    /**
     * make user active
     * @param view the view
     */
    public void active(View view) {
        new ChangeStatusBan().execute("https://pandango.herokuapp.com/changeUserStatusUnlock/" + user);
    }

    private class ChangeStatusBan extends AsyncTask<String, Long, String> {
        /**
         * The request is made
         * @param urls The url
         * @return the Response
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
         * while request is being processed
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * after the request is made
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                final Intent intent = new Intent(UserStatus.this, AdminHome.class);
                startActivity(intent);
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
