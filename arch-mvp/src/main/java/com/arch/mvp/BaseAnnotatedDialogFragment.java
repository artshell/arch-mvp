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

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @see Viewable
 *
 * @author artshell on 2018/5/17
 */
public class BaseAnnotatedDialogFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends BaseDialogFragment<V, P> {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedState) {
        return super.onCreateDialog(savedState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedState) {
        int layoutResId = getClass().getAnnotation(Viewable.class).layout();
        if (layoutResId != Viewable.LAYOUT_NOT_DEFINED) {
            return inflater.inflate(layoutResId, container, false);
        }
        return super.onCreateView(inflater, container, savedState);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final P initPresenter() {
        return (P) AnnotationHelper.createPresenter(getClass());
    }
}
