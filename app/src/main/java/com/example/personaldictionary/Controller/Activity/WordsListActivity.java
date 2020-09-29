package com.example.personaldictionary.Controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.personaldictionary.Controller.Fragment.AddDialogFragment;
import com.example.personaldictionary.Controller.Fragment.WordsListFragment;
import com.example.personaldictionary.R;

public class WordsListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.single_fragment_activity);
        if(fragment == null){
            fragmentManager.beginTransaction()
                    .add(R.id.single_fragment_activity, WordsListFragment.newInstance())
                    .commit();
        }
    }
}