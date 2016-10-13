package com.renyu.pushmodule.gtpush;

import android.app.Application;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

/**
 * Created by Clevo on 2016/10/12.
 */

public class GTUtils {

    private static GTUtils utils;
    private static Application application;

    private GTUtils() {

    }

    public static GTUtils getInstance(Application app) {
        synchronized (GTUtils.class) {
            if (utils==null) {
                synchronized (GTUtils.class) {
                    utils=new GTUtils();
                    application=app;
                }
            }
        }
        return utils;
    }

    //开启推送
    public void initialize() {
        PushManager.getInstance().initialize(application);
    }

    //停止SDK服务
    public void stopService() {
        PushManager.getInstance().stopService(application);
    }

    /**
     * 绑定别名
     * 注:
     * 1. 同一个别名最多绑定10个 ClientID (适用于允许多设备同时登陆的应用)，当已绑定10个 ClientID 时，再次调用此接口会自动解绑最早绑定的记录，
     * 2. 当ClientID已绑定了别名A，若调用此接口绑定别名B，则与别名A的绑定关系会自动解除。
     * 3. 此接口与 unBindAlias 一天内最多调用100次，两次调用的间隔需大于5s。
     * @param alias 长度40字节，支持中、英文（区分大小写）、数字以及下划线
     */
    public void bindAlias(String alias) {
        PushManager.getInstance().bindAlias(application, alias);
    }

    /**
     * 解绑别名
     * @param alias
     * @param flag 是否只对当前 cid 有效，如果是 true，只对当前cid做解绑；如果是 false，对所有绑定该别名的cid列表做解绑
     */
    public void unBindAlias(String alias, boolean flag) {
        PushManager.getInstance().unBindAlias(application, alias, flag);
    }

    /**
     * 设置标签
     * 注:
     * 标签的设定，一定要在获取到 Clientid 之后才可以设定。标签的设定，服务端限制一天只能成功设置一次
     * @param tags
     */
    public void setTag(String[] tags) {
        Tag[] tagParam=new Tag[tags.length];
        for (int i = 0; i < tags.length; i++) {
            Tag tag1=new Tag();
            tag1.setName(tags[i]);
            tagParam[i]=tag1;
        }
        PushManager.getInstance().setTag(application, tagParam, System.currentTimeMillis() + "");
    }

    /**
     * 设置静默时间
     * 注：
     * 设置静默时间，静默期间SDK将不再联网
     * @param beginHour 开始时间，设置范围在0-23小时之间，单位 h
     * @param durationHour 持续时间，设置范围在0-23小时之间。持续时间为0则不静默，单位 h
     */
    public void setSilentTime(int beginHour, int durationHour) {
        PushManager.getInstance().setSilentTime(application, beginHour, durationHour);
    }

    /**
     * 获取Clientid
     * @return
     */
    public String getClientid() {
        return PushManager.getInstance().getClientid(application);
    }

    /**
     * 获取SDK服务状态
     * @return
     */
    public boolean isPushTurnedOn() {
        return PushManager.getInstance().isPushTurnedOn(application);
    }
}
