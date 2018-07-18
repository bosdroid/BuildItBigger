package com.example.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Activity extends AppCompatActivity {

    public static String EXTRA_JOKE = "extra_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_JOKE, getIntent().getStringExtra(EXTRA_JOKE));

            JokingFragment frag = new JokingFragment();
            frag.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, frag)
                    .commit();
        }
    }
}

