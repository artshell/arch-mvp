/*
 * Copyright 2018 artshell. https://github.com/artshell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arch.mvp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author artshell on 2018/5/17
 */
public abstract class BaseDialogFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AppCompatDialogFragment
        implements BaseContract.View, LifecycleProvider<FragmentEvent> {

    protected P presenter;

    private final Subject<FragmentEvent> lifecycleEvent = PublishSubject.<FragmentEvent>create().toSerialized();

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifecycleEvent.onNext(FragmentEvent.ATTACH);
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        boolean isCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            isCreated = true;
        }
        presenter = viewModel.getPresenter();
        presenter.attachLifecycle(getLifecycle());
        if (isCreated) {
            presenter.onPresenterCreated();
        }
        lifecycleEvent.onNext(FragmentEvent.CREATE);
    }

    protected abstract P initPresenter();

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        super.onActivityCreated(savedState);
    }

    @CallSuper
    @Override
    public void onViewStateRestored(@Nullable Bundle savedState) {
        super.onViewStateRestored(savedState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedState) {
        lifecycleEvent.onNext(FragmentEvent.CREATE_VIEW);
        return super.onCreateView(inflater, container, savedState);
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        presenter.attachView((V) this);
        applyAttributes();
        if (isSupportLazyLoad() && getUserVisibleHint() && !isHidden()) {
            onLazyLoad();
        } else if (getUserVisibleHint() && !isHidden()) {
            onDirectLoad();
        }
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        lifecycleEvent.onNext(FragmentEvent.START);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        lifecycleEvent.onNext(FragmentEvent.RESUME);
    }

    @CallSuper
    @Override
    public void onPause() {
        lifecycleEvent.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    @Override
    public void onStop() {
        lifecycleEvent.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        lifecycleEvent.onNext(FragmentEvent.DESTROY_VIEW);
        presenter.detachView();
        super.onDestroyView();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        lifecycleEvent.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        presenter.detachLifecycle(getLifecycle());
        presenter = null;
    }

    @CallSuper
    @Override
    public void onDetach() {
        lifecycleEvent.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @NonNull
    @Override
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleEvent.hide();
    }

    @Override
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleEvent, event);
    }

    @Override
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleEvent);
    }

    /**
     * 子类可实现此方法给View设置属性
     */
    protected void applyAttributes() {

    }

    /**
     * 是否支持懒加载（直接加载与懒加载二选其一, 默认为直接加载方式）子类可继承修改此返回值
     * 懒加载方式适用于 {@link ViewPager}, {@link FragmentPagerAdapter}, {@link FragmentStatePagerAdapter}
     * @return true支持, false不支持
     * @see #onLazyLoad()
     * @see #onDirectLoad()
     * @see #onViewCreated(View, Bundle)
     */
    protected boolean isSupportLazyLoad() {
        return false;
    }

    /**
     * 执行懒加载（直接加载与懒加载二选其一, 默认为直接加载方式）
     * 随Fragment状态变化, 此方法会被多次执行, 可以根据数据有无状态来决定是否执行实际的数据加载逻辑
     * @see #isSupportLazyLoad()
     * @see #onDirectLoad()
     * @see #onViewCreated(View, Bundle)
     * @see #setUserVisibleHint(boolean)
     * @see #onHiddenChanged(boolean)
     */
    protected void onLazyLoad() {

    }

    /**
     * 直接加载（直接加载与懒加载二选其一, 默认为直接加载方式）此方法只被执行一次
     * @see #onViewCreated(View, Bundle)
     * @see #isSupportLazyLoad()
     * @see #onLazyLoad()
     */
    protected void onDirectLoad() {

    }

    /**
     * 是否可以执行懒加载
     */
    private void tryLazyLoad() {
        if (getUserVisibleHint() && isSupportLazyLoad() && isResumed() && !isHidden()) {
            onLazyLoad();
        }
    }

    @CallSuper
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        tryLazyLoad();
    }

    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        tryLazyLoad();
    }
}
