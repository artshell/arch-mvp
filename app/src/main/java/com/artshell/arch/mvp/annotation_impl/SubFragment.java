package com.artshell.arch.mvp.annotation_impl;

import android.util.Log;

import com.arch.mvp.BaseAnnotatedV4Fragment;
import com.arch.mvp.Viewable;
import com.artshell.arch.mvp.R;

/**
 * @author artshell on 2018/5/1
 */
@Viewable(presenter = SubPresenter.class, layout = R.layout.fragment_annotation_sub)
public class SubFragment extends BaseAnnotatedV4Fragment<SubFragmentContract.View, SubFragmentContract.Presenter>
        implements SubFragmentContract.View {

    @Override
    public void showSomething() {
        Log.e("showSomething ", "SomethingShowed");
    }
}
