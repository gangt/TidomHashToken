package com.yunqukuailian.app.update;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.yunqukuailian.app.R;
import com.yunqukuailian.app.utils.DisplayUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateVersionController {


    private Context context;

    //更新文件的实例
    private AppUpdateInfo info;
    //当前版本号
    private int versionCode;
    //提示用户更新的dialog
    private Dialog dialog;
    //下载进度条
    private ProgressDialog pd;

    public static UpdateVersionController getInstance(Context context) {
        return new UpdateVersionController(context);
    }

    public UpdateVersionController(Context context) {
        this.context = context;
    }

    public void normalCheckUpdateInfo(int code, String des, String url) {
        //获取版本号：这里的版本号在项目的build.gradle中是可以看到的，看复制过来的参数
        /**
         defaultConfig {
         applicationId "com.zhh.test"
         minSdkVersion 16
         targetSdkVersion 23
         versionCode 1
         versionName "1.0"
         }
         */
        versionCode = getVerCode(context);//等于19
        checkVersionTask(code, des, url);

    }

    public void forceCheckUpdateInfo(int code, String des, String url) {//强制更新一般不用
        versionCode = getVerCode(context);//等于19
        info = new AppUpdateInfo();
        info.setUrl(url);
        info.setVercode(code);//每次更新都靠它
        info.setVername("2.0");//版本名字
        info.setApkname("com.hellotext.1309171635.apk");
        info.setAppname("Hello");
        info.setForceUpp("yes");
        info.setUppcontent(des);//更新内容
        updateApp(1);
    }

    /**
     * 步骤一：获取版本信息
     */
    private void checkVersionTask(int code, String des, String url) {
        //网络加载获取app新版版本信息
        //这里不做请求直接赋值
        info = new AppUpdateInfo();
        info.setUrl(url);
        info.setVercode(code);//每次更新都靠它
        info.setVername("2.0");//版本名字
        info.setApkname("com.hellotext.1309171635.apk");
        info.setAppname("Hello");
        info.setForceUpp("no");
        info.setUppcontent(des);//更新内容
        updateApp(2);
    }

    private void updateApp( int type) {
        if (null != info && info.getVercode() > versionCode) {//20>19可更新
            showUpdataDialog( type);
        } else {
//            Toast.makeText(context, "已经是最新版本啦~", Toast.LENGTH_SHORT).show();
        }
    }

    private Button cancelBtn;

    /**
     * 步骤二：弹出对话框提示用户更新
     */
    protected void showUpdataDialog( int type) {
        dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.uploaddialog, null);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height =  DisplayUtil.dip2px(context,150); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width =  DisplayUtil.dip2px(context,300); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView sure = (TextView) view.findViewById(R.id.sure);
        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(info.getUppcontent());
        if (type == 1) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downLoadApk();

            }
        });


    }

    /**
     * 步骤三：下载文件
     */
    private void downLoadApk() {
        // 进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("下载中...");
        pd.setCanceledOnTouchOutside(false);
//        pd.setProgressNumberFormat("%1d M/%2d M");
        pd.setCancelable(false);
        // 监听返回键--防止下载的时候点击返回
        pd.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(context, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Sdcard不可用
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡不可用~", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            //下载的子线程
            new Thread() {
                @Override
                public void run() {
                    try {
                        // 在子线程中下载APK文件
                        File file = getFileFromServer(info.getUrl(), pd);
//                        sleep(1000);
                        // 安装APK文件
//                        installApk(file);
                        openFile(file.getAbsolutePath());
                        pd.dismiss(); // 结束掉进度条对话框
                    } catch (Exception e) {
//                        Toast.makeText(context, "文件下载失败了~", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }

            }.start();
        }
    }

    /**
     * 从服务器下载apk
     */
    public File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength() / 1024/1024);
            InputStream is = conn.getInputStream();

            File file = new File(Environment.getExternalStorageDirectory().getPath()
                    + "/qianke", "qianke.apk");
            //判断文件夹是否被创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }else {
                file.delete();
            }

            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[50*1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total / 1024/1024);

                float all = conn.getContentLength() / 1024/1024;
                float percent = total/1024/1024;
                pd.setProgressNumberFormat(String.format("%.1fM/%.1fM", percent, all));
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    public void openFile(String path) {
        //之前有部分手机不能安装
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
//        } else {
//            intent.setDataAndType(Uri.fromFile(file), getMIMEType(file));
//        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri fileUri = FileProvider.getUriForFile(context, getFileProviderAuthority(context), new File(path));
            Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(path));
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取版本名
     */
    public static String getVerName(Context context) {

        String verName = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);

            verName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取版本号
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);

            verCode = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取FileProvider的auth
     */
    private static String getFileProviderAuthority(Context context) {
        try {
            for (ProviderInfo provider : context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PROVIDERS).providers) {
                if (FileProvider.class.getName().equals(provider.name) && provider.authority.endsWith(".update_app.file_provider")) {
                    return provider.authority;
                }
            }
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return null;
    }


}
