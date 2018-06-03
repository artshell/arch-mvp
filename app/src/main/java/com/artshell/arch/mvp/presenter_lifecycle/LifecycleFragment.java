package com.artshell.arch.mvp.presenter_lifecycle;

import com.arch.mvp.BaseAnnotatedV4Fragment;
import com.arch.mvp.Viewable;
import com.artshell.arch.mvp.R;


@Viewable(presenter = FragmentLifecyclePresenter.class, layout = R.layout.fragment_lifecycle)
public class LifecycleFragment extends BaseAnnotatedV4Fragment<LifecycleContract.View, LifecycleContract.Presenter>
        implements LifecycleContract.View {

}
