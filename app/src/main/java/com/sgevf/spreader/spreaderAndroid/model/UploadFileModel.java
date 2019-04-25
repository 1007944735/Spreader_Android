package com.sgevf.spreader.spreaderAndroid.model;

import org.json.JSONObject;

public class UploadFileModel {
    public String filePath;

    public UploadFileModel(JSONObject obj){
        filePath=obj.optString("filePath");
    }
}
