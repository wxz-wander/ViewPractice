package com.example.viewpractice.pack;

/**
 * 作者：wxz11 on 2019/3/16 15:37
 * Email : wxzwander@gmail.com
 */
public class JniOpts {
    static {
        System.loadLibrary("jniPack");
    }
    /**
     * 获取应用签名
     * */
    public native String getSignature(Object o);
    /**
     * 获取应用包名
     * */
    public native String getPackageName(Object o);
    /**
     * A native method that is implemented by the 'jniPack' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
