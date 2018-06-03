package com.artshell.arch.mvp.lazyload;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;

import com.arch.mvp.BaseCyclePresenter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author artshell on 2018/6/1
 */
public class LazyIndexPresenter extends BaseCyclePresenter<LazyContract.View>
        implements LazyContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void request() {
        Observable.just(Arrays.asList("gradle", "java", "rust"))
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .compose(bindUntilEvent(Lifecycle.Event.ON_PAUSE))
                .filter(list -> isViewAttached())
                .doOnSubscribe(disposable -> getView().showDialog())
                .doFinally(() -> getView().dismissDialog())
                .subscribe(items -> getView().onNext(items), thr -> getView().failTip());

    }
}
