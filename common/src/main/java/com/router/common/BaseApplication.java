package com.router.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.router.common.utils.AppConfig;
import com.router.common.utils.Utils;

/**
 * Created by bingo on 2020/8/25 0025.
 */

public abstract class BaseApplication extends Application {

    public abstract boolean isDebug();

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppConfig.getInstance().init(this);
        initARouter();
        initXLog();
    }

    /**
     * 初始化日志
     */
    private void initXLog(){
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(isDebug()? LogLevel.ALL : LogLevel.NONE)     // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                .tag("bingo")                                          // 指定 TAG，默认为 "X-LOG"
                .t()                                                   // 允许打印线程信息，默认禁止
                .st(2)                                                 // 允许打印深度为2的调用栈信息，默认禁止
                .b()                                                   // 允许打印日志边框，默认禁止
//                .jsonFormatter(new MyJsonFormatter())                  // 指定 JSON 格式化器，默认为 DefaultJsonFormatter
//                .xmlFormatter(new MyXmlFormatter())                    // 指定 XML 格式化器，默认为 DefaultXmlFormatter
//                .throwableFormatter(new MyThrowableFormatter())        // 指定可抛出异常格式化器，默认为 DefaultThrowableFormatter
//                .threadFormatter(new MyThreadFormatter())              // 指定线程信息格式化器，默认为 DefaultThreadFormatter
//                .stackTraceFormatter(new MyStackTraceFormatter())      // 指定调用栈信息格式化器，默认为 DefaultStackTraceFormatter
//                .borderFormatter(new MyBoardFormatter())               // 指定边框格式化器，默认为 DefaultBorderFormatter
//                .addObjectFormatter(AnyClass.class,                    // 为指定类添加格式化器
//                        new AnyClassObjectFormatter())                 // 默认使用 Object.toString()
//                .addInterceptor(new BlacklistTagsFilterInterceptor(    // 添加黑名单 TAG 过滤器
//                        "blacklist1", "blacklist2", "blacklist3"))
//                .addInterceptor(new MyInterceptor())                   // 添加一个日志拦截器
                .build();

        Printer androidPrinter = new AndroidPrinter();                  // 通过 android.util.Log 打印日志的打印器
        Printer consolePrinter = new ConsolePrinter();                  // 通过 System.out 打印日志到控制台的打印器
        Printer filePrinter = new FilePrinter                           // 打印日志到文件的打印器
                .Builder(getCacheDir().getAbsolutePath()+"/xlog/")// 指定保存日志文件的路径
                .fileNameGenerator(new DateFileNameGenerator())         // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())              // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
//                .cleanStrategy(new FileLastModifiedCleanStrategy(MAX_TIME))// 指定日志文件清除策略，默认为 NeverCleanStrategy()
//                .flattener(new MyFlattener())                           // 指定日志平铺器，默认为 DefaultFlattener
                .build();
        XLog.init(                                                     // 初始化 XLog
                config,                                                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                androidPrinter,                                        // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
//                consolePrinter,
                filePrinter);
    }

    /**
     * 路由初始化
     */
    private void initARouter() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
