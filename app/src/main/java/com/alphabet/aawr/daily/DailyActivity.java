package com.alphabet.aawr.daily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alphabet.aawr.R;
import com.alphabet.lib.utils.ActivityUtils;

/**
 * Created by alphabet on 5/2/16.
 */
public class DailyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli);

        DailyFragment dailyFragment = (DailyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (dailyFragment == null) {
            dailyFragment = DailyFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),
                    dailyFragment, R.id.fragment_container);
        }

        new DailyPresenter(dailyFragment);
    }
}
