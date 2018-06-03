package com.artshell.arch.mvp.annotation_impl;

import com.arch.mvp.BaseAnnotatedActivity;
import com.arch.mvp.Viewable;
import com.artshell.arch.mvp.R;

/**
 * @author artshell on 2018/5/1
 */
@Viewable(presenter = MasterPresenter.class, layout = R.layout.activity_annotation_master)
public class MasterActivity extends BaseAnnotatedActivity<MasterContract.View, MasterContract.Presenter>
        implements MasterContract.View {


    @Override
    public void openSubFragment() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
