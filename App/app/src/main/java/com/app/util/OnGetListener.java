package com.app.util;

public interface OnGetListener<T> {

    void onSuccess(T data);

    void onFail(String error);

}
