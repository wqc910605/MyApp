package com.wwf.component.ui.fragment.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.wwf.common.util.GlideUtil;
import com.wwf.common.util.LogUtil;
import com.wwf.common.util.ThreadUtil;
import com.wwf.common.util.ToastUtil;
import com.wwf.component.R;
import com.wwf.component.base.BaseFragment;
import com.wwf.component.presenter.SplashPresenter;
import com.wwf.component.iview.splash.ISplashView;
import com.wwf.component.ui.fragment.main.MainFragment;

import butterknife.BindView;

//闪屏界面
public class SplashFragment extends BaseFragment<SplashPresenter> implements ISplashView {

    //自定义权限请求吗
    private static final int PERMISSION_REQUEST_CODE = 100;
    @BindView(R.id.imageview)
    ImageView mImageView;
    //创建一个实例
    public static SplashFragment newInstance(Bundle bundle) {
        SplashFragment splashFragment = new SplashFragment();
        splashFragment.setArguments(bundle);
        return splashFragment;
    }

    @Override
    protected void initPresenter() {
        setPresenter(this, new SplashPresenter());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initData() {
        //检查权限
        checkPermission();
        //请求权限
        String url = "http://a-ssl.duitang.com/uploads/item/201509/02/20150902151103_J4E3c.jpeg";
        GlideUtil.setRectangleImage(getActivity(), url, mImageView);

    }

    private void startPage() {
        ThreadUtil.postDelayed(() -> {
            start(new MainFragment(), SINGLETASK);
        }, 4000);
    }

    private void checkPermission() {
        if ((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            //申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA
                    },
                    PERMISSION_REQUEST_CODE);//自定义的code
        } else {
            //跳转界面
            startPage();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onPermissionResult(int requestCode, int[] grantResults) {
        super.onPermissionResult(requestCode, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE ) {
            if (grantResults != null && grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.show("请给与相应权限, 否则某些功能无法使用");
                        break;
                    }
                }
            }
            startPage();
        }
    }
}
