package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements GenerateListener {
    private static int currentFragment = 0;
    private static int previous = 0;
    static int min = -1;
    static int max = -1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(previous);
    }

    void openFirstFragment(int previousNumber) {
        currentFragment = 1;
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    void openSecondFragment(int min, int max) {
        currentFragment = 2;
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if(currentFragment == 2 ) openFirstFragment(previous);
        else super.onBackPressed();
    }

    @Override
    public void minMaxSaver(int minSave, int maxSave) {
         min = minSave;
         max = maxSave;

    }
    @Override
    public void onGenerateButtonClick(int previousResult) {
        previous = previousResult;
    }
}

