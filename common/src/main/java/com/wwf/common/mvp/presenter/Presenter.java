package com.wwf.common.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.wwf.common.mvp.view.IView;
import com.wwf.common.net.retrofit.RetrofitUtil;
import com.wwf.common.util.NetworkUtil;
import com.wwf.common.util.ThreadUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by wzp on 2018/5/31.
 */

public abstract class Presenter<V extends IView> implements IPresenter {

    protected String TAG = this.getClass().getName();
    protected Context mContext;
    protected V mView;
//    protected Handler mHandler;
    private List<Disposable> mDisposables;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        mHandler = new Handler();
        mDisposables = new ArrayList<>();
    }

    //fragment中的生命周期方法
    public void onCreateView(Bundle savedInstanceState) {
        mDisposables = new ArrayList<>();
    }

    //fragment中的生命周期方法
    @Override
    public void onViewCreated() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    //fragment中的生命周期方法
    @Override
    public void onDestroyView() {
        release();
    }

    /**
     * 可以做Presenter自己的回收资源的操作
     */
    @Override
    public void onDestroy() {
        release();
    }

    //释放资源
    public void release() {
        if (mContext != null || mView != null) {
            if (mDisposables.size() > 0) {
                for (Disposable disposable : mDisposables) {
                    disposable.dispose();
                }
                //取消订阅后, 集合置空
                mDisposables = null;
            }
            //延迟一会儿, 释放资源, 防止mView调用时, 为空, (有一种情况, Retrofit调用成功后, 再取消请求, 成功时, 还是会回调数据的)
            ThreadUtil.postDelayed(() -> {
                mContext = null;
                mView = null;
            }, 1200);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
    //fragment中使用
    @Override
    public void onNewBundle(Bundle bundle) {

    }

    /**
     * 当为Activity指定Presenter时，为Presenter注入该Activity的Context引用
     *
     * @param context
     */
    public void setView(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }


    /**
     * post请求
     *
     * @param path    请求地址, 非全路径
     * @param params  请求参数
     * @param urlflag 每个地址对应一个唯一标记, 用来区分不同的请求
     */
    public void post(String path, Map<String, Object> params, int urlflag) {
        if (NetworkUtil.isNetworkAvailable(mContext)) {
            Disposable disposable = RetrofitUtil.getInstance()
                    .getApiService()
                    .post(path, params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(json -> {//成功
                        if (TextUtils.isEmpty(json)) {
                            onFailure("网络异常", urlflag);
                        } else {
                            onSuccess(json, urlflag);
                        }
                    }, throwable -> {//异常
                        onFailure(throwable.getMessage(), urlflag);
                    });
            mDisposables.add(disposable);
        } else {
            onNotNetwork(urlflag);
        }
    }

    //上传图片, 可以上传多张图片
    public void uploadImg(String path, List<String> localImgPaths, Map<String, Object> params, int urlflag) {
        if (localImgPaths == null) {
//            Toasty.warning(mContext, "图片地址为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (NetworkUtil.isNetworkAvailable(mContext)) {
            Map<String, RequestBody> bodys = new HashMap<>();
            for (String localImgPath : localImgPaths) {
                File file = new File(localImgPath);
                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                bodys.put("AttachmentKey\"; filename=\"" + file.getName(), fileBody);
            }
            Disposable disposable = RetrofitUtil.getInstance().getApiService()
                    .uploadMuti(path, bodys)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(json -> {
                        if (TextUtils.isEmpty(json)) {
                            onFailure("网络异常", urlflag);
                        } else {
                            onSuccess(json, urlflag);
                        }
                    }, throwable -> {
                        onFailure(throwable.getMessage(), urlflag);
                    });
            mDisposables.add(disposable);
        } else {
            onNotNetwork(urlflag);
        }
    }

    //成功
    protected void onSuccess(String json, int urlflag) {

    }

    //失败
    protected void onFailure(String msg, int urlflag) {
//        Toasty.info(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    //没有网络
    protected void onNotNetwork(int urlflag) {
    }

    //上传图片
    public void postImg() {

    }

}
