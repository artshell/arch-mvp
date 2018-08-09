package com.artshell.arch.mvp.reactive_stream_impl;

import com.arch.mvp.BaseLoadingContract;

/**
 * @author artshell on 2018/5/1
 */
public interface ReactiveContract {
    interface View extends BaseLoadingContract.View {
        void onNext(String value);
    }

    interface Presenter extends BaseLoadingContract.Presenter<View> {
        void requestAfterDelay(long delay);
    }
}
