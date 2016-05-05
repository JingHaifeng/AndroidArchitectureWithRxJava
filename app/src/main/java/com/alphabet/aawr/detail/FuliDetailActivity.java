package com.alphabet.aawr.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphabet.aawr.R;
import com.alphabet.aawr.data.FuliData;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-05-05
 * @time: 10:29
 */
public class FuliDetailActivity extends AppCompatActivity {

    public static final String TRANSIT_PIC = "picture";
    public static final String TRANSIT_DATA = "data";

    @BindView(R.id.pic)
    ImageView mPicIv;

    @BindView(R.id.date)
    TextView mDateTv;

    private FuliData mFuliData;

    private Unbinder mUnbinder;

    public static Intent newIntent(Context context, FuliData fuliData) {
        Intent intent = new Intent(context, FuliDetailActivity.class);
        intent.putExtra(TRANSIT_DATA, fuliData);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli_detail);
        parseIntent();
        mUnbinder = ButterKnife.bind(this);

        ViewCompat.setTransitionName(mPicIv, TRANSIT_PIC);
        Glide.with(this)
                .load(mFuliData.url)
                .into(mPicIv);
        mDateTv.setText(mFuliData.desc);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void parseIntent() {
        if (getIntent() != null) {
            mFuliData = getIntent().getParcelableExtra(TRANSIT_DATA);
        } else {
            Logger.e("Show detail Activity without data");
            finish();
        }
    }
}
