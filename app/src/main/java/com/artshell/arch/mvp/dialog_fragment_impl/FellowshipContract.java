package com.artshell.arch.mvp.dialog_fragment_impl;

import com.arch.mvp.BaseContract;

public interface FellowshipContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter<FellowshipContract.View> {

    }
}
