package com.prac;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created @2017/6/3 17:46
 */
public class FileUtil {

    public static void copyAsync(String path, String dest, ProgressListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                copy(path, dest, listener);
            }
        }).start();
    }

    public static void copy(String path, String dest, ProgressListener listener) {
        listener.onStart();
        try {
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream(dest);
            int totalByte = fis.available();
            int read = 0;
            int n = 0;
            byte[] buffer = new byte[4096];
            while ((n = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
                fos.flush();
                read += n;
                float per = (float) read / (float) totalByte;
                listener.onProgressUpdate((int) (per * 100));
            }

            fos.close();
            fis.close();

            listener.onComplete();
        } catch (Exception e) {
            listener.onError();
        }
    }
}
