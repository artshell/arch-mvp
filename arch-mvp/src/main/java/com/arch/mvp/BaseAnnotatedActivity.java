/*
 * Copyright 2017 armcha. https://github.com/armcha/
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

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

/**
 * Created by Chatikyan on 29.06.2017.
 */
public abstract class BaseAnnotatedActivity<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends BaseActivity<V, P> {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        int layoutResId = getClass().getAnnotation(Viewable.class).layout();
        if (layoutResId != Viewable.LAYOUT_NOT_DEFINED) {
            setContentView(layoutResId);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected P initPresenter() {
        return (P) AnnotationHelper.createPresenter(getClass());
    }
}
