package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserMod extends AppCompatActivity implements AdapterView.OnItemClickListener{
    /**
     * the user list
     */
    private List<String> userList;
    /**
     * username
     */
    private String username;
    /**
     * major
     */
    private String major;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mod);
        new UserList().execute("https://pandango.herokuapp.com/getUserIds");
        username = getIntent().getStringExtra("USER_NAME");
        major = getIntent().getStringExtra("MAJOR");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_mod, menu);
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
     * the on item click adapter
     * @param parent The parent adapter
     * @param view the view
     * @param position the position of tapped item
     * @param id the id
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        final Intent intent = new Intent(this, UserStatus.class);
        final String cursor = (String) parent.getItemAtPosition(position);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("MAJOR", major);
        intent.putExtra("USER", cursor);
        startActivity(intent);
    }

    /**
     * added for Junit
     * @return a string
     */
    public String lastAdded() {
        final List<User> myList = User.getMyList();
        if (myList.isEmpty()) {
            return null;
        } else {
            final int size = myList.size();
            return myList.get(size).getFirstName();
        }
    }

    /**
     * added for Junit
     * @param user the user
     * @param movie the movie
     * @return the movie number
     */
    public static int hasRated(User user, String movie) {
        if (user.getMovieList().containsKey(movie)) {
            return user.getMovieList().get(movie);
        } else {
            return 0;
        }
    }

    private class UserList extends AsyncTask<String, Long, String> {
        /**
         * the request being made
         * @param urls the url
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
                return null;
            }
        }

        /**
         * while the request is being processed
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * the activities after the request is made
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                try {
                    final JSONArray arr = new JSONArray(file);
                    final ListView lv = (ListView) findViewById(R.id.user_list);
                    userList = new ArrayList<String>();
                    JSONObject obj1 = null;
                    for (int i = 0; i < arr.length(); i++) {
                        try {
                            obj1 = arr.getJSONObject(i);
                            userList.add(obj1.getString("username"));
                        } catch (JSONException e) {
                            Log.d("M", "M");
                        }
                    }
                    lv.setAdapter(new ArrayAdapter<String>(UserMod.this,
                            R.layout.list_item_movies, R.id.movieName, userList));
                    lv.setOnItemClickListener(UserMod.this);
                } catch (JSONException j) {
                    Log.d("MUST", "MUST");
                }
            } else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

}
