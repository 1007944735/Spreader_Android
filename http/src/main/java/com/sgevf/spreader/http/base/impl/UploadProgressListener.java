package com.sgevf.spreader.http.base.impl;

public interface UploadProgressListener {
    void progress(long currentBytesCount, long totalBytesCount,String name);
}
