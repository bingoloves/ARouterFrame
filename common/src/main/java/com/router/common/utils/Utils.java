package com.router.common.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import java.lang.reflect.Method;

public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of UtilsFileProvider.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (app == null) {
            Log.e("Utils", "app is null.");
            return;
        }
        if (sApp == null) {
            sApp = app;
            return;
        }
        if (sApp.equals(app)) return;
    }
    /**
     * 获取当前应用的Application
     * 先使用ActivityThread里获取Application的方法，如果没有获取到，
     * 再使用AppGlobals里面的获取Application的方法
     * @return
     */
    private static Application getCurApplication(){
        Application application = null;
        try{
            Class atClass = Class.forName("android.app.ActivityThread");
            Method currentApplicationMethod = atClass.getDeclaredMethod("currentApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(application != null) return application;
        try{
            Class atClass = Class.forName("android.app.AppGlobals");
            Method currentApplicationMethod = atClass.getDeclaredMethod("getInitialApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return application;
    }

    /**
     * Return the Application object.
     * and other process get app by reflect.</p>
     *
     * @return the Application object
     */
    public static Application getApp() {
        if (sApp != null) return sApp;
        sApp = getCurApplication();
        if (sApp != null) return sApp;
        if (sApp == null) throw new NullPointerException("reflect failed.");
        return sApp;
    }
}
