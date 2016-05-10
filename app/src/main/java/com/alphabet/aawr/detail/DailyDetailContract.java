package com.alphabet.aawr.detail;

import com.alphabet.lib.mvp.BasePresenter;
import com.alphabet.lib.mvp.BaseView;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-05-05
 * @time: 10:29
 */
public class DailyDetailContract {

    interface Presenter extends BasePresenter {
        void loadDailyDetail();
    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean indicator);

    }
}
