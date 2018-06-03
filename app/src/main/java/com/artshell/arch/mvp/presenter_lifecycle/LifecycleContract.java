package com.artshell.arch.mvp.presenter_lifecycle;

import com.arch.mvp.BaseContract;

public interface LifecycleContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter<LifecycleContract.View> {
    }
}
