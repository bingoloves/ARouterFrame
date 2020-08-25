package com.router.common.rx.rxerrorhandler;


import org.reactivestreams.Subscriber;

/**
 * @description :通过继承该观察者，实现错误交给RxErrorHandler进行处理。
 * @autor :  V.Wenju.Tian
 * @date : 2016/12/5 15:08
 */
public abstract class RxErrorHandlerSubscriber<T> implements Subscriber<T> {
    private RxErrorHandler rxErrorHandler;

    public RxErrorHandlerSubscriber(RxErrorHandler rxErrorHandler) {
        this.rxErrorHandler = rxErrorHandler;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        rxErrorHandler.handleError(e);
    }
}
