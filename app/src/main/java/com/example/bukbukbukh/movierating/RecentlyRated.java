package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecentlyRated extends AppCompatActivity {
    /**
     * the username
     */
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_rated);
        final Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        new DispRating().execute("https://pandango.herokuapp.com/dispRecentRated/" + username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recently_rated, menu);
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
     * goes back home
     * @param view the view
     */
    public void goBackHome(View view) {
        final Intent intent = new Intent(this, Home.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }

    private class DispRating extends AsyncTask<String, Long, String> {
        /**
         * The request is made
         * @param urls The url
         * @return the response
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
                Log.d("PROBLEM", "PROBLEM");
                return null;
            }
        }

        /**
         * the progress of the request
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * The response to the request
         * @param file The response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                Log.d("RESULT", file);
                final ListView lv = (ListView) findViewById(R.id.list_recently_rated);
                try {
                    final JSONArray jsA = new JSONArray(file);
                    final ArrayList<String> mainMovieList = new ArrayList<String>();
                    JSONObject obj1 = null;
                    for (int i = 0; i < jsA.length(); i++) {
                        try {
                            obj1 = jsA.getJSONObject(i);
                            mainMovieList.add(obj1.getString("movie_name") + ": Rating = " + obj1.getString("rating"));
                        } catch (JSONException e) {
                            Log.d("MUST", "MUST");
                        }
                    }
                    lv.setAdapter(new ArrayAdapter<String>(RecentlyRated.this,
                            R.layout.list_item_movies, R.id.movieName, mainMovieList));
                } catch (JSONException e) {
                    Log.d("NULL", "NULL");
                }

            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

}
