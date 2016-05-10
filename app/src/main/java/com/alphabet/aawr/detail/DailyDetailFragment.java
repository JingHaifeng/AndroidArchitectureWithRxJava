package com.alphabet.aawr.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-05-10
 * @time: 10:31
 */
public class DailyDetailFragment extends Fragment implements DailyDetailContract.View{

    private DailyDetailContract.Presenter mPresenter;

    public DailyDetailFragment() {
    }

    public static DailyDetailFragment newInstance() {
        return new DailyDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void setLoadingIndicator(boolean indicator) {

    }

    @Override
    public void setPresenter(DailyDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
