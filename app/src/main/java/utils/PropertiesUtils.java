package utils;

import android.content.Context;

import com.sgevf.spreader.spreaderAndroid.R;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    public static final String URL = "url";

    public static final String getUrl(Context c) {
        try {
            Properties pro = new Properties();
            pro.load(c.getResources().openRawResource(R.raw.http_config));
            return pro.getProperty(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
