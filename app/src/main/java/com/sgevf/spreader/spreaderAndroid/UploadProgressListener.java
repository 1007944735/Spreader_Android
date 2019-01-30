package com.sgevf.spreader.spreaderAndroid;

public interface UploadProgressListener {
    void progress(long currentBytesCount,long totalBytesCount);
}
