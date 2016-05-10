package com.alphabet.aawr.daily;

import com.alphabet.aawr.daily.data.BaseData;
import com.alphabet.aawr.daily.data.DailyData;
import com.alphabet.lib.mvp.BaseView;
import com.alphabet.lib.mvp.BasePresenter;

import java.util.List;

/**
 * Created by alphabet on 5/2/16.
 */
public interface DailyContract {

    interface Presenter extends BasePresenter {

        void loadPage(int page);

        void loadNextPage();

    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean indicator);

        void setDataList(List<DailyData> baseDataList);

        void allCompleted(boolean completed);

        void showMessage(String error);
    }
}
