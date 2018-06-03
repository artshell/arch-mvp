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
 * @author artshell on 2018/6/1
 */

@Viewable(presenter = LazyIndexPresenter.class, layout = R.layout.fragment_lazy_index)
public class LazyIndexFragment extends BaseAnnotatedV4Fragment<LazyContract.View, LazyContract.Presenter>
        implements LazyContract.View {

    private static final String TAG = "LazyIndexFragment";

    private TextView result;
    private List<String> items = new ArrayList<>();

    public static LazyIndexFragment newInstance() {
        return new LazyIndexFragment();
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
    protected boolean isSupportLazyLoad() {
        return true;
    }

    /**
     * @see #onNext(List)
     */
    @Override
    protected void onLazyLoad() {
        Log.i(TAG, "onLazyLoad: ");
        if (items.isEmpty()) {
            presenter.request();
        } else {
            bindData(items);
        }
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
        items.clear();
        items.addAll(result);
        bindData(result);
    }

    @Override
    public void failTip() {

    }

    private void bindData(List<String> items) {
        result.setText(items.toString());
    }
}
