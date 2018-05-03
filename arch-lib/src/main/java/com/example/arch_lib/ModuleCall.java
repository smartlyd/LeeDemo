package com.example.arch_lib;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lee on 2018/5/2.
 * <p>
 * module 异步方法调用返回值。
 * 调用enqueue 方法才真正出发任务。
 */

public class ModuleCall<T> {

    private Object mObservable;
    private ModuleCallBack<T> mModuleCallBack;
    private Object mCancelHandler;
    private volatile boolean mDone = false;
    private volatile boolean mCanceled = false;
    private boolean mExcuted = false;

    public void setObservable(Object observable) {
        this.mObservable = observable;
    }


    public void cancel() {
        mCanceled = true;
        if (mCancelHandler instanceof Disposable) {
            ((Disposable) mCancelHandler).dispose();
            ;
        } else if (mCancelHandler instanceof Subscription) {
            ((Subscription) mCancelHandler).cancel();
        }
    }

    public boolean isDone() {
        return mDone || mCanceled;
    }

    public boolean isCanceled() {
        return mCanceled;
    }
    public void enqueue(final ModuleCallBack<T> callback) {
        synchronized (this) {
            if (mExcuted) {
                throw new IllegalStateException("每个ModuleCall只能enqueue一次");
            }
            mExcuted = true;
        }
        if (mCanceled || mDone) {
            return;
        }
        mModuleCallBack = callback;

        if (mObservable instanceof Observable) {
            subscribeObservable((Observable<T>) mObservable);
        } else if (mObservable instanceof Single) {
            subscribeSingle((Single<T>) mObservable);
        } else if (mObservable instanceof Flowable) {
            subscribeFlowable((Flowable<T>) mObservable);
        } else {
            subscribeMaybe((Maybe<T>) mObservable);
        }
    }

    private void subscribeObservable(Observable<T> observable) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mCancelHandler = d;
            }

            @Override
            public void onNext(@NonNull T t) {
                ModuleResult<T> result = new ModuleResult<>(t, null);
                doCallback(result);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                ModuleResult<T> result = (ModuleResult<T>) new ModuleResult<>(null, e);
                doCallback(result);
                mDone = true;
            }

            @Override
            public void onComplete() {
                mDone = true;
            }
        });
    }

    private void subscribeSingle(Single<T> single) {
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mCancelHandler = d;
            }

            @Override
            public void onSuccess(@NonNull T t) {
                ModuleResult<T> result = new ModuleResult<>(t, null);
                doCallback(result);
                mDone = true;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                ModuleResult<T> result = (ModuleResult<T>) new ModuleResult<>(null, e);
                doCallback(result);
                mDone = true;
            }
        });
    }

    private void subscribeFlowable(Flowable<T> flowable) {
        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new FlowableSubscriber<T>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                mCancelHandler = s;
            }

            @Override
            public void onNext(T t) {
                ModuleResult<T> result = new ModuleResult<>(t, null);
                doCallback(result);
            }

            @Override
            public void onError(Throwable t) {
                ModuleResult<T> result = (ModuleResult<T>) new ModuleResult<>(null, t);
                doCallback(result);
                mDone = true;
            }

            @Override
            public void onComplete() {
                mDone = true;
            }
        });
    }

    private void subscribeMaybe(Maybe<T> maybe) {
        maybe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MaybeObserver<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mCancelHandler = d;
            }

            @Override
            public void onSuccess(@NonNull T t) {
                ModuleResult<T> result = new ModuleResult<>(t, null);
                doCallback(result);
                mDone = true;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                ModuleResult<T> result = (ModuleResult<T>) new ModuleResult<>(null, e);
                doCallback(result);
                mDone = true;
            }

            @Override
            public void onComplete() {
                mDone = true;
            }
        });
    }

    private void doCallback(ModuleResult<T> result) {
        if (mModuleCallBack == null || mCanceled) {
            return;
        }
        mModuleCallBack.onModuleCallBack(result);
    }

}