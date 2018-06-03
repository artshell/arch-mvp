package com.artshell.arch.mvp.lazyload;

import com.arch.mvp.BaseContract;

import java.util.List;

/**
 * @author artshell on 2018/6/1
 */
public interface LazyContract {

    interface View extends BaseContract.View {
        void showDialog();

        void dismissDialog();

        void onNext(List<String> result);

        void failTip();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void request();
    }
}
