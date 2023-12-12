package com.example.zakat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the title to an empty string
        getSupportActionBar().setTitle("");

        // Disable the system back button
        // Comment out the following line if you want to keep the system back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    // Handle back button click
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
