package com.alphabet.aawr.fuli;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alphabet.aawr.R;
import com.alphabet.lib.utils.ActivityUtils;

/**
 * Created by alphabet on 5/2/16.
 */
public class FuliActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli);

        FuliFragment fuliFragment = (FuliFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fuliFragment == null) {
            fuliFragment = FuliFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fuliFragment, R.id.fragment_container);
        }

        new FuliPresenter(fuliFragment);
    }
}
