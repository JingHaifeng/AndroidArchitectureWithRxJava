package com.alphabet.aawr.fuli;

import com.alphabet.aawr.data.FuliData;
import com.alphabet.lib.mvp.BaesView;
import com.alphabet.lib.mvp.BasePresenter;

import java.util.List;

/**
 * Created by alphabet on 5/2/16.
 */
public interface FuliContract {

    interface Presenter extends BasePresenter {

        void loadPage(int page);

        void loadNextPage();

    }

    interface View extends BaesView<Presenter> {

        void setLoadingIndicator(boolean indicator);

        void setDataList(List<FuliData> fuliDataList);

        void allCompleted(boolean completed);

        void showMessage(String error);
    }
}
