package com.alphabet.aawr.detail;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-05-10
 * @time: 10:32
 */
public class DailyDetailPresenter implements DailyDetailContract.Presenter {

    private DailyDetailContract.View mView;

    public DailyDetailPresenter(DailyDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadDailyDetail() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
