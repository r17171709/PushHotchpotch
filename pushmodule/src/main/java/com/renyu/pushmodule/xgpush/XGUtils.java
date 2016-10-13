package com.renyu.pushmodule.xgpush;

import android.app.Application;

import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;

/**
 * Created by Clevo on 2016/10/11.
 */

public class XGUtils {

    private static XGUtils utils;
    private static Application application;

    private XGUtils() {

    }

    public static XGUtils getInstance(Application app) {
        synchronized (XGUtils.class) {
            if (utils==null) {
                synchronized (XGUtils.class) {
                    utils=new XGUtils();
                    application=app;
                }
            }
        }
        return utils;
    }

    /**
     * 普通注册只注册当前设备，后台能够针对不同的设备token发送推送消息
     * @param accessId
     * @param accessKey
     */
    public void regist(long accessId, String accessKey) {
        //信鸽推送
        XGPushConfig.setAccessId(application, accessId);
        XGPushConfig.setAccessKey(application, accessKey);
        // 注册接口
        XGPushManager.registerPush(application);
    }

    /**
     * 绑定账号注册指的是，在绑定设备注册的基础上，使用指定的账号（一个账号可能在多个设备登陆）注册APP，这样可以通过后台向指定的账号发送推送消息
     * @param accessId
     * @param accessKey
     * @param account
     */
    public void registWithAccount(long accessId, String accessKey, String account) {
        //信鸽推送
        XGPushConfig.setAccessId(application, accessId);
        XGPushConfig.setAccessKey(application, accessKey);
        // 注册接口
        XGPushManager.registerPush(application.getApplicationContext(), account);
    }

    /**
     * 若APP调用registerPush(context, account)等绑定account账号，需要解绑（如用户退出）
     * 注：
     * 账号解绑只是解除token与APP账号的关联，若使用全量/标签/token推送仍然能收到通知/消息。
     */
    public void unBindAccount() {
        XGPushManager.registerPush(application, "*");
    }

    /**
     * debug模式是否开启
     * 注：
     * 为保证数据的安全性，请在发布时确保已关闭debug模式
     * @param flag
     */
    public void enableDebug(boolean flag) {
        XGPushConfig.enableDebug(application, flag);
    }

    /**
     * 拦截默认推送通知
     * @param listener
     */
    public void setNotifactionCallback(final OnInterceptListener listener) {
        XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {

            @Override
            public void handleNotify(XGNotifaction xGNotifaction) {
                if (!listener.action(xGNotifaction)) {
                    // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                    xGNotifaction.doNotify();
                }
            }
        });
    }

    /**
     * 当用户已退出或APP被关闭，不再需要接收推送时，可以取消注册APP
     * 注：
     * 反注册操作切勿过于频繁，可能会造成后台同步延时。切换帐号无需反注册，多次注册自动会以最后一次为准。
     */
    public void stopPush() {
        XGPushManager.unregisterPush(application);
    }

    /**
     * 获取设备Token
     * 注：
     * 当设备一旦注册成功后，便会将token存储在本地，之后可通过XGPushConfig.getToken(context)接口获取。
     * @return
     */
    public String getToken() {
        return XGPushConfig.getToken(application);
    }

    /**
     * 开发者可以针对不同的用户设置标签，然后在前台根据标签名群发通知。
     * 注：
     * 一个应用最多有10000个tag， 每个token在一个应用下最多100个tag， tag中不准包含空格。
     * @param tag
     */
    public void setTag(String tag) {
        XGPushManager.setTag(application, tag);
    }

    /**
     * 开发者删除用户标签数据。
     * @param tag
     */
    public void deleteTag(String tag) {
        XGPushManager.deleteTag(application, tag);
    }

    public interface OnInterceptListener {
        /**
         * 返回false代表不拦截
         * @param xGNotifaction
         * @return
         */
        boolean action(XGNotifaction xGNotifaction);
    }
}
