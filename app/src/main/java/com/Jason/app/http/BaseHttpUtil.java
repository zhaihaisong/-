package com.Jason.app.http;

import com.Jason.app.Constants;
import com.Jason.app.util.LogUtils;
import com.Jason.app.util.Sign;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Progress;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: Jason
 * @desc: 通用HTTP数据请求代理工具
 * <p>
 * -1       服务端返回的错误信息
 * -101     接口封装异常
 * -102     接口调用失败
 * -103     JSON转换异常
 * -104     服务端返回数据为空
 * -105     未知异常
 */
public class BaseHttpUtil {

    /**
     * 执行普通的get请求
     *
     * @param url       接口请求地址
     * @param mCallBack 回调结果
     */
    public void doGet1(String url, String Cookie, Map params, final HttpStringCallBack mCallBack) {
        try {
            OkGo.<String>get(url).tag(this).params(params).headers("Cookie", Cookie).execute(new StringCallback() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                    String body = response.body();
                    mCallBack.onSuccess(body);

                }

                @Override
                public void onError(com.lzy.okgo.model.Response<String> response) {
                    super.onError(response);
                    mCallBack.onFailure(-102, "接口调用失败");
                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(-101, "接口封装异常");
            e.printStackTrace();
        }
    }

    public void doPost_sys(String url, String Cookie, Map params, final HttpPostCallBack mCallBack) {


        OkGo.<String>post(url).tag(this).params(params).headers("Cookie", Cookie).execute(new StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                String body = response.body();
                mCallBack.onSuccess(body);

            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                mCallBack.onFailure(1, "接口异常");
            }
        });


    }


    /**
     * 执行普通的pos请求
     *
     * @param url       接口请求地址
     * @param params    请求参数
     * @param mCallBack 回调结果
     */

    public void doPost(String url, Map params, final HttpPostCallBack mCallBack) {


        params.put("resTime", "");
        OkGo.<String>post(url).tag(this).params(params).headers("Cookie", "").execute(new StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                String body = response.body();
                mCallBack.onSuccess(body);
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                mCallBack.onFailure(1, "接口异常");
            }
        });


    }

    public void doPost_renlian(String content_type, Object imgA,Object imgB, String url, final HttpPostCallBack mCallBack) {


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                //可以根据自己的接口需求在这里添加上传的参数
                .addFormDataPart("appid", Constants.TC_APPID)
                .addFormDataPart("content-type", content_type)

               ;
        if (content_type.equals("application/json")) {
            builder.addFormDataPart("urlA", (String) imgA)
                    .addFormDataPart("urlB", (String)imgB);
        } else {

            RequestBody fileA = RequestBody.create(MediaType.parse("image/png"), (File)imgA);
            RequestBody fileB = RequestBody.create(MediaType.parse("image/png"), (File)imgB);
            builder .addFormDataPart("imageA", "renlian", fileA)
                    .addFormDataPart("imageB", "renlian", fileB);
        }
        RequestBody requestBody = builder.build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("host", "recognition.image.myqcloud.com");
        httpHeaders.put("authorization", Sign.appSign());

        OkGo.<String>post(url).isMultipart(true).upRequestBody(requestBody).tag(this).headers(httpHeaders).execute(new StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                String body = response.body();
                mCallBack.onSuccess(body);
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                mCallBack.onFailure(1, "接口异常");
            }
        });


    }

    /**
     * 执行普通的文件下载请求
     *
     * @param path      文件保存路径
     * @param name      文件名称
     * @param url       文件下载地址
     * @param mCallBack 回调结果
     */
    public void doDownloadFile(String path, String name,
                               String url, final HttpFileCallBack mCallBack) {
        try {
            // 已经在HttpFileTranslate中封装过了
            // File dir = new File(path);
            // if (!dir.exists()) dir.mkdirs();
            // File file = new File(dir, name);
            // if (file.exists()) file.delete();

//            LogUtils.e("接口路径: " + url + "下载文件保存地址: " + path);
            OkGo.<File>get(url).tag(this).execute(new FileCallback(path,name) {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                    File body = response.body();
                    mCallBack.onSuccess(body);
                }

                @Override
                public void downloadProgress(Progress progress) {

                    /**
                     *@param fraction       下载的进度，0-1
                     *@param totalSize      总字节长度, byte
                     *@param currentSize    本次下载的大小, byte
                     *@param speed;         网速，byte/s
                     */
                    mCallBack.inProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
                }

                @Override
                public void onError(com.lzy.okgo.model.Response<File> response) {
                    super.onError(response);

                }
            });
        } catch (Exception e) {
            mCallBack.onError(e);
            e.printStackTrace();
        }
    }

  /*  *//**
     * 支持断点下载的功能
     * DownloadManager、DownloadInfo、DownloadListener的参与
     * 1.DownloadService.getDownloadManager() 获取DownloadManager
     * 2.downloadManager.getDownloadInfo("Tag"); 获取指定的DownloadInfo
     * 3.downloadInfo.setListener(downListener); 如果任务存在，把任务的监听换成当前页面需要的监听
     * 4.refreshUi(downloadInfo) 需要第一次手动刷一次
     * 5.downloadManager.getDownloadInfo("Tag") 每次点击的时候，需要更新当前对象
     * 6.downloadManager.addTask("Tag", OkHttpUtils.get(mUrl), downListener); 添加下载任务
     * 7.downloadManager.pauseTask("Tag") 暂停下载任务
     * 8.downloadManager.removeTask("Tag") 删除下载任务
     * 9.downloadManager.restartTask("Tag") 重启下载任务
     * 10.onResume()生命周期中执行“4”操作refreshUi(downloadInfo)
     * 11.onDestroy()生命周期中执行downloadInfo.removeListener()
     * 12.	downloadInfo.getState()、 downloadInfo.getDownloadLength()
     * 		downloadInfo.getTotalLength()、downloadInfo.getNetworkSpeed()
     * 		(Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%"
     *//*

    *//**
     * 上传某个用户的头像信息
     *
     * @param url       上传接口地址
     * @param uid       用户id
     * @param uic       用户头像
     * @param mCallBack 回调结果
     *//*
    public void doPostUic(String url, String uid, File uic, final HttpUlCallBack mCallBack) {
        try {
            LogUtils.e("接口路径: " + url);
            OkGo.post(url).tag(this)
                    .params("Uid", uid)
                    .params("Uic", uic)
                    .execute(new StringCallback() {

                        @Override
                        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            mCallBack.upProgress(currentSize, totalSize, progress, networkSpeed);
                        }

                        @Override
                        public void onSuccess(String t, Call call, Response response) {
                            mCallBack.onSuccess();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mCallBack.onFailure();
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            mCallBack.onFailure();
            e.printStackTrace();
        }
    }

      *//**
     * 执行文件上传请求
     *
     * @param url       上传接口地址
     * @param params    上传的参数键值对
     * @param key       上传文件的标识
     * @param files     上传的文件集合
     * @param mCallBack 回调结果
     *//*
    public void doPostMore(String url, Map<String, String> params,
                           String key, ArrayList<File> files, final HttpUlCallBack mCallBack) {
        try {
            OkGo.post(url).tag(this)
                    .params(params)
                    .addFileParams(key, files)
                    .execute(new StringCallback() {

                        @Override
                        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            mCallBack.upProgress(currentSize, totalSize, progress, networkSpeed);
                        }

                        @Override
                        public void onSuccess(String t, Call call, Response response) {
                            mCallBack.onSuccess();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mCallBack.onFailure();
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            mCallBack.onFailure();
            e.printStackTrace();
        }
    }*/

    /**
     * 关闭网络请求
     */
    public void closeHttp() {
        try {
            OkGo.getInstance().cancelTag(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
