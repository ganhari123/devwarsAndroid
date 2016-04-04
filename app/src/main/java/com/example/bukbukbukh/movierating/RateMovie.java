package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RateMovie extends AppCompatActivity {
    /**
     * A constant called moviename
     */
    private static final String MOVIE_NAME = "moviename";
    /**
     * A constant called rating
     */
    private static final String RATING_M = "rating";
    /**
     * A constant called download failed
     */
    private static final String DOWNLOAD_FAILED = "download failed";
    /**
     * A constant called myapp
     */
    private static final String MY_APP = "MYAPP";

    /**
     * username
     */
    private String username;
    /**
     * moviename
     */
    private String moviename;
    /**
     * ratingM
     */
    private float ratingM;
    /**
     * number of ratings
     */
    private int numOfRates;
    /**
     * newRating for movie
     */
    private double newRating;
    /**
     * major
     */
    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movie);
        final Intent intent = getIntent();
        final Movie movie = (Movie) intent.getSerializableExtra("MOVIE");
        username = intent.getStringExtra("USER_NAME");
        major = intent.getStringExtra("MAJOR");
        new DispCurrentRating().execute("https://pandango.herokuapp.com/getCurrRating");

        final TextView movieName = (TextView) findViewById(R.id.movie_name);

        movieName.setText(movie.getTitle());
        moviename = movie.getTitle();
        addListenerOnRatingBar();
    }

    /**
     * RatingBar listener
     */
    public void addListenerOnRatingBar() {
        final RatingBar rB = (RatingBar) findViewById(R.id.ratingBar);
        //TextView text = (TextView) findViewById(R.id.movie_name);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        rB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratingM = rating;
                new AddRating().execute("https://pandango.herokuapp.com/addRating");

            }
        });
    }

    /*private String getMajor() {
        return major;
    }

    private void setMajor(String str) {
        major = str;
    }

    private double getNewRating() {
        return newRating;
    }

    private void setNewRating(double str) {
        newRating = str;
    }

    private int getNumOfRates() {
        return numOfRates;
    }

    private void setNumOfRates(int str) {
        numOfRates = str;
    }

    private float getRatingM() {
        return ratingM;
    }

    private void setRatingM(float str) {
        ratingM = str;
    }

    private String getMoviename() {
        return moviename;
    }

    private void setMovieName(String str) {
        moviename = str;
    }

    private String getUsername() {
        return username;
    }

    private TextView getText() {
        return text;
    }

    private void setText(TextView tv) {
        text = tv;
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate_movie, menu);
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

    /*public void recMov(View view) {

    }*/

    private class PostNot extends AsyncTask<String, Long, String> {
        /**
         * The request is made on another thread
         * @param urls THe url for request
         * @return the response
         */
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("username", username);
                keyValuePairs.put(MOVIE_NAME, moviename);
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

        /**
         * while the request is being made
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * The response
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                /*Log.d("RESULT", file);
                Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);*/
                new GetCurrentRating().execute("https://pandango.herokuapp.com/getCurrRating");
            } else {
                Log.d(MY_APP, DOWNLOAD_FAILED);
            }
        }
    }

    private class DispCurrentRating extends AsyncTask<String, Long, String> {
        /**
         *
         * @param urls The url
         * @return the response
         */
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put(MOVIE_NAME, moviename);
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

        /**
         * while the request is being made
         * @param progress progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * The response to everything
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                Log.d("RESULT", file);
                try {
                    if ("No data".equals(file)) {
                        final TextView view = (TextView) findViewById(R.id.disp_average_rating);
                        view.setText("Not Rated Yet...");
                    } else {
                        final JSONArray arr = new JSONArray(file);
                        final JSONObject obj = arr.getJSONObject(0);
                        final double rating = obj.getDouble(RATING_M);
                        final TextView view = (TextView) findViewById(R.id.disp_average_rating);
                        view.setText("Rating: " + Double.toString(rating));
                    }
                } catch (JSONException j) {
                    Log.d("MUST HAVE", "MUST HAVE");
                }
            } else {
                Log.d(MY_APP, DOWNLOAD_FAILED);
            }
        }
    }

    private class GetCurrentRating extends AsyncTask<String, Long, String> {
        /**
         * the request
         * @param urls the url
         * @return the response
         */
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put(MOVIE_NAME, moviename);
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

        /**
         * the progress
         * @param progress progress
         */

        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * the response
         * @param file The file
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                Log.d("RESULT", file);
                try {
                    if ("No data".equals(file)) {
                        newRating = ratingM;
                        numOfRates = 1;
                    } else {
                        final JSONArray arr = new JSONArray(file);
                        final JSONObject obj = arr.getJSONObject(0);
                        final double rating = obj.getDouble(RATING_M);
                        numOfRates = obj.getInt("num_of_ratings");
                        final double numSum = numOfRates * rating;
                        newRating = (numSum + ratingM) / (numOfRates + 1);
                        numOfRates = numOfRates + 1;
                        Log.d("RATING", Double.toString(newRating));
                    }
                    new UpdateMovieAverage().execute("https://pandango.herokuapp.com/updateAverage");
                } catch (JSONException j) {
                    Log.d("MUST", "MUST");
                }
            } else {
                Log.d(MY_APP, DOWNLOAD_FAILED);
            }
        }
    }

    private class UpdateMovieAverage extends AsyncTask<String, Long, String> {
        /**
         * the request is made
         * @param urls the url
         * @return the response
         */
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put(MOVIE_NAME, moviename);
                keyValuePairs.put(RATING_M, Double.toString(newRating));
                keyValuePairs.put("numOfRates", Integer.toString(numOfRates));
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

        /**
         * while request is being made
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * after request is done
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                Log.d("RESULT", file);
                final Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                intent.putExtra("MAJOR", major);
                startActivity(intent);

            } else {
                Log.d(MY_APP, DOWNLOAD_FAILED);
            }
        }
    }

    private class AddRating extends AsyncTask<String, Long, String> {
        /**
         * the request is made
         * @param urls the url
         * @return the response
         */
        protected String doInBackground(String... urls) {
            try {
                final Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("username", username);
                keyValuePairs.put(MOVIE_NAME, moviename);
                keyValuePairs.put(RATING_M, Float.toString(ratingM));
                keyValuePairs.put("major", major);
                Log.d("MAJOR", major);
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

        /**
         * while response is being processed
         * @param progress the progress
         */
        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        /**
         * after request is done
         * @param file the response
         */
        protected void onPostExecute(String file) {
            if (file != null) {
                /*Log.d("RESULT", file);
                Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);*/
                new GetCurrentRating().execute("https://pandango.herokuapp.com/getCurrRating");
            } else {
                Log.d(MY_APP, DOWNLOAD_FAILED);
            }
        }
    }
}
