package com.example.myweather;

import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    // Activity code here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //assigning the activities for setting and about tasks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast settings = Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT);
                settings.show();
                Intent settings_activity = new Intent(BaseActivity.this, SettingsActivity.class);
                startActivity(settings_activity);
                return true;

            case R.id.action_about:
                Toast about = Toast.makeText(this, "About selected", Toast.LENGTH_SHORT);
                about.show();
                Intent about_activity = new Intent(BaseActivity.this, About.class);
                startActivity(about_activity);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}