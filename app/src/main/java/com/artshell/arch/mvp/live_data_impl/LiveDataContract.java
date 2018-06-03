package com.artshell.arch.mvp.live_data_impl;

import android.arch.lifecycle.LiveData;

import com.arch.mvp.BaseContract;

import java.util.List;

/**
 * @author artshell on 2018/5/1
 */
public interface LiveDataContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<View> {
        LiveData<List<String>> getList();
    }
}
