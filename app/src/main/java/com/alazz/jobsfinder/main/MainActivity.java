package com.alazz.jobsfinder.main;

import android.os.Bundle;

import com.alazz.jobsfinder.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);

        mNavController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupActionBarWithNavController(this,mNavController);


    }

    @Override
    public boolean onSupportNavigateUp() {
        mNavController.navigate(R.id.action_jobDetailsFragment_to_homeFragment);

        return false;
    }





}
