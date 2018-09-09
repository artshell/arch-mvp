/*
 * Copyright 2017 armcha. https://github.com/armcha/
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


import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

/**
 * If you use <b>Java 7 Language</b>, Lifecycle events are observed using annotations.
 * Once Java 8 Language becomes mainstream on Android, annotations will be deprecated, so between
 * {@link DefaultLifecycleObserver} and annotations, you must always prefer {@code DefaultLifecycleObserver}.
 *
 * If you use <b>Java 8 Language</b>, then observe events with {@link DefaultLifecycleObserver}.
 * To include it you should add {@code "android.arch.lifecycle:common-java8:<version>"} to your
 * build.gradle file.
 *
 * Created by Chatikyan on 20.05.2017.
 * Modified by artshell
 */
public class BasePresenter<V extends BaseContract.View>
        implements BaseContract.Presenter<V>, DefaultLifecycleObserver {

    private Context context;
    private Bundle stateBundle;
    private V view;

    @Override
    public final void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public final void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    @Override
    public final void attachView(V view) {
        this.view = view;
    }

    @Override
    public final void detachView() {
        view = null;
    }

    @Override
    public final V getView() {
        return view;
    }

    @Override
    public final boolean isViewAttached() {
        return view != null;
    }

    @Override
    public final Bundle getStateBundle() {
        return stateBundle == null
                ? stateBundle = new Bundle()
                : stateBundle;
    }

    @Override
    public final void onAppContext(@NonNull Context appContext) {
        context = appContext;
    }

    @Override
    public final Context getAppContext() {
        return context;
    }

    @CallSuper
    @Override
    public void onPresenterCreated() {
        //NO-OP
    }

    @CallSuper
    @Override
    public void onPresenterDestroy() {
        if (stateBundle == null || stateBundle.isEmpty()) return;
        stateBundle.clear();
    }

    @CallSuper
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @CallSuper
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }
}
