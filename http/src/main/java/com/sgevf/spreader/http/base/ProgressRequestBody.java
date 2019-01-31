package com.sgevf.spreader.http.base;

import android.support.annotation.Nullable;

import com.sgevf.spreader.http.base.impl.UploadProgressListener;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {
    private RequestBody requestBody;
    private UploadProgressListener progressListener;
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody requestBody,UploadProgressListener progressListener){
        this.requestBody=requestBody;
        this.progressListener=progressListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if(null==bufferedSink){
            bufferedSink= Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink sink(Sink sink){
        return new ForwardingSink(sink) {

            long writtenBytesCount=0L;
            long totalBytesCount=0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                writtenBytesCount+=byteCount;
                if(totalBytesCount==0){
                    totalBytesCount=contentLength();
                }
                Observable.just(writtenBytesCount).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                progressListener.progress(writtenBytesCount,totalBytesCount);
                            }
                        });
            }
        };
    }


}
