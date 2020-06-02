package com.example.chatter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentAlice fragmentAlice;
    private FragmentBob fragmentBob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentAlice = (FragmentAlice) getSupportFragmentManager().findFragmentById(R.id.fragmentAlice);
        fragmentBob = (FragmentBob) getSupportFragmentManager().findFragmentById(R.id.fragmentBob);
    }


}
