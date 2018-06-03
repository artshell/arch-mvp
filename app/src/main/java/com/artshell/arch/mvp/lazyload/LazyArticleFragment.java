package com.artshell.arch.mvp.lazyload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arch.mvp.BaseAnnotatedV4Fragment;
import com.arch.mvp.Viewable;
import com.artshell.arch.mvp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author artshell on 2018/6/2
 */
@Viewable(presenter = LazyArticlePresenter.class, layout = R.layout.fragment_lazy_article)
public class LazyArticleFragment extends BaseAnnotatedV4Fragment<LazyContract.View, LazyContract.Presenter>
        implements LazyContract.View {

    private static final String TAG = "LazyArticleFragment";

    private List<String> mItems = new ArrayList<>();
    private TextView result;

    public static LazyArticleFragment newInstance() {
        return new LazyArticleFragment();
    }

    @Override
    protected boolean isSupportLazyLoad() {
        return true;
    }

    @Override
    protected void onLazyLoad() {
        Log.i(TAG, "onLazyLoad: ");
        if (mItems.isEmpty()) {
            presenter.request();
        } else {
            bindData(mItems);
        } 
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        super.onActivityCreated(savedState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        result = view.findViewById(R.id.tv_result);
    }

    @Override
    public void showDialog() {
        RotationDialog.showDialog(getChildFragmentManager());
    }

    @Override
    public void dismissDialog() {
        RotationDialog.dismissDialog(getChildFragmentManager());
    }

    @Override
    public void onNext(List<String> result) {
        mItems.clear();
        mItems.addAll(result);
        bindData(result);
    }

    @Override
    public void failTip() {

    }
    
    private void bindData(List<String> items) {
        result.setText(items.toString());
    }
}
