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
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;

/**
 * @author artshell on 2018/5/17
 */
public abstract class BaseDialogFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AppCompatDialogFragment implements BaseContract.View {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        boolean isPresenterCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            isPresenterCreated = true;
        }
        presenter = viewModel.getPresenter();
        presenter.attachLifecycle(getLifecycle());
        if (isPresenterCreated) {
            presenter.onPresenterCreated();
        }
    }

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

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        presenter.attachView((V) this);
        if (isSupportLazyLoad() && getUserVisibleHint() && !isHidden()) {
            onLazyLoad();
        }
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachLifecycle(getLifecycle());
        presenter = null;
    }

    protected abstract P initPresenter();

    /**
     * 默认不支持懒加载, 子类可继承修改此返回值
     * @return true支持, false不支持
     */
    protected boolean isSupportLazyLoad() {
        return false;
    }

    /**
     * 是否可以执行懒加载
     */
    private void requireLoad() {
        if (getUserVisibleHint() && isSupportLazyLoad() && isResumed() && !isHidden()) {
            onLazyLoad();
        }
    }

    /**
     * 执行懒加载
     */
    protected void onLazyLoad() {

    }

    @CallSuper
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        requireLoad();
    }

    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        requireLoad();
    }
}
