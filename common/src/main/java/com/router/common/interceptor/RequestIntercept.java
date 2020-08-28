package com.router.common.interceptor;


import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.router.common.http.BaseResponse;
import com.router.common.utils.ZipHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by jess on 7/1/16.
 */
public class RequestIntercept implements Interceptor {
    /**
     * 设置缓存 防止同一请求多次提交
     */
    private Map<String,String> cache = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        }
        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();
        //响应时间
        XLog.d("Received response  in %.1fms%n%s", (t2 - t1) / 1e6d);
        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        //获取content的压缩类型
        String encoding = originalResponse.headers().get("Content-Encoding");
        Buffer clone = buffer.clone();
        String bodyString;
        //解析response content
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            clone.writeTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            bodyString = ZipHelper.decompressForGzip(bytes);//解压
            outputStream.close();
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            clone.writeTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            bodyString = ZipHelper.decompressToStringForZlib(bytes);//解压
            outputStream.close();
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }
        XLog.json(bodyString);
        return originalResponse;
    }
}
