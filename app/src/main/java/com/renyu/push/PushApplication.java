package com.renyu.push;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.renyu.pushmodule.gtpush.GTUtils;
import com.renyu.pushmodule.jpush.JUtils;
import com.renyu.pushmodule.mipush.MiUtils;
import com.renyu.pushmodule.umpush.UmengUtils;
import com.renyu.pushmodule.xgpush.XGUtils;

import java.util.List;

/**
 * Created by Clevo on 2016/10/9.
 */

public class PushApplication extends Application {

    public XGUtils xgUtils;
    public MiUtils miUtils;
    public JUtils jUtils;
    public GTUtils gtUtils;
    public UmengUtils umengUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        if (shouldInit()) {
            //小米推送
            miUtils=MiUtils.getInstance(this);
            //开启debug模式
            miUtils.enableDebug();
            //注册
            miUtils.registerPush(BuildConfig.MI_APP_ID, BuildConfig.MI_APP_KEY);

            //极光推送
//            jUtils=JUtils.getInstance(this);
//            jUtils.setDebugMode(true);
//            jUtils.init();

            //信鸽推送
//            xgUtils=XGUtils.getInstance(this);
//            //开启debug模式
//            xgUtils.enableDebug(true);
//            //设置拦截
//            xgUtils.setNotifactionCallback(new XGUtils.OnInterceptListener() {
//                @Override
//                public boolean action(XGNotifaction xGNotifaction) {
//                    // 获取标签、内容、自定义内容
//                    String title = xGNotifaction.getTitle();
//                    String content = xGNotifaction.getContent();
//                    String customContent = xGNotifaction.getCustomContent();
//                    return false;
//                }
//            });
//            xgUtils.regist(ParamsUtils.ACCESSID, ParamsUtils.ACCESSKEY);

            //个推推送
//            gtUtils=GTUtils.getInstance(this);
//            gtUtils.initialize();
        }

//        umengUtils=UmengUtils.getInstance(this);
//        umengUtils.setDebugMode(true);
//        umengUtils.setMessageHandler(new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, UMessage uMessage) {
//                NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
//                builder.setTicker(uMessage.ticker);
//                builder.setContentTitle(uMessage.title);
//                builder.setContentText(uMessage.text);
//                builder.setContentIntent(PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
//                builder.setColor(context.getResources().getColor(R.color.colorAccent));
//                builder.setSmallIcon(R.mipmap.ic_launcher);
//                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//                builder.setWhen(System.currentTimeMillis());
//                builder.setAutoCancel(true);
//                builder.setPriority(NotificationCompat.PRIORITY_MAX);
//                builder.setDefaults(Notification.DEFAULT_LIGHTS);
//                builder.setOngoing(true);
//                return builder.build();
//            }
//
//            @Override
//            public void dealWithCustomMessage(Context context, UMessage uMessage) {
//                super.dealWithCustomMessage(context, uMessage);
//                //自定义消息的点击统计
//                UTrack.getInstance(getApplicationContext()).trackMsgClick(uMessage);
//                Log.d("PushApplication", uMessage.custom);
//            }
//        });
//        umengUtils.setNotificationClickHandler(new UmengNotificationClickHandler() {
//            @Override
//            public void dismissNotification(Context context, UMessage uMessage) {
//                super.dismissNotification(context, uMessage);
//            }
//
//            @Override
//            public void autoUpdate(Context context, UMessage uMessage) {
//                super.autoUpdate(context, uMessage);
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage uMessage) {
//                super.openUrl(context, uMessage);
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage uMessage) {
//                super.openActivity(context, uMessage);
//            }
//
//            @Override
//            public void launchApp(Context context, UMessage uMessage) {
//                super.launchApp(context, uMessage);
//            }
//
//            @Override
//            public void dealWithCustomAction(Context context, UMessage uMessage) {
//                super.dealWithCustomAction(context, uMessage);
//            }
//        });
//        umengUtils.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String s) {
//                //注册成功会返回device token
//                Log.d("PushApplication", "注册成功:"+s);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.d("PushApplication", "注册失败:"+s+" "+s1);
//            }
//        });

    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
