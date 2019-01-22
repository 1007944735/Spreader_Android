package com.sgevf.spreader.http.entity;

public class BaseResult<T> {
    public int code;
    public String msg;
    public T data;
}
