package com.example.bukbukbukh.movierating;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class WelcomeScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
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

    /** Go to Login Screen
     *
     * @param view the view
     */
    public void goLoginScreen(View view) {

        Intent intent = new Intent(this, Login_screen.class);
        startActivity(intent);

    }

    /** Goes to Register screen
     *
     * @param view the view
     */
    public void goRegisterScreen(View view) {
        final Intent intent = new Intent(this, Register_screen.class);
        startActivity(intent);
    }

    public void testNewHome(View view) {
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }
}
