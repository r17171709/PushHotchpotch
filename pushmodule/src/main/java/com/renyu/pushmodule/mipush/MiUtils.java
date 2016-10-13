package com.renyu.pushmodule.mipush;

import android.app.Application;
import android.util.Log;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Created by Clevo on 2016/10/11.
 */

public class MiUtils {

    private static MiUtils utils;
    private static Application application;

    private MiUtils() {

    }

    public static MiUtils getInstance(Application app) {
        synchronized (MiUtils.class) {
            if (utils==null) {
                synchronized (MiUtils.class) {
                    utils=new MiUtils();
                    application=app;
                }
            }
        }
        return utils;
    }

    /**
     * 注册MiPush推送服务，建议在app启动的时候调用。
     * @param MI_APP_ID 在开发者网站上注册时生成的，MiPush推送服务颁发给app的唯一认证标识
     * @param MI_APP_KEY 在开发者网站上注册时生成的，与appID相对应，用于验证appID是否合法
     */
    public void registerPush(String MI_APP_ID, String MI_APP_KEY) {
        MiPushClient.registerPush(application, MI_APP_ID, MI_APP_KEY);
    }

    /**
     * 关闭MiPush推送服务，当用户希望不再使用MiPush推送服务的时候调用，调用成功之后，app将不会接收到任何MiPush服务推送的数据，直到下一次调用registerPush()。
     */
    public void unregisterPush() {
        MiPushClient.unregisterPush(application);
    }

    /**
     * debug模式
     */
    public void enableDebug() {
        Logger.setLogger(application, new LoggerInterface() {
            @Override
            public void setTag(String s) {
                Log.d("PushApplication  setTag", s);
            }

            @Override
            public void log(String s) {
                Log.d("PushApplication  log1", s);
            }

            @Override
            public void log(String s, Throwable throwable) {
                Log.d("PushApplication  log2", s);
                Log.d("PushApplication  log2", throwable.getMessage());
            }
        });
    }

    /**
     * 开发者可以为指定用户设置别名，然后给这个别名推送消息，效果等同于给RegId推送消息。
     * 注：
     * 一个RegId可以被设置多个别名，如果设置的别名已经存在，会覆盖掉之前的别名。
     * @param alias 为指定用户设置别名
     */
    public void setAlias(String alias) {
        MiPushClient.setAlias(application, alias, null);
    }

    /**
     * 开发者可以取消指定用户的某个别名，服务器就不会给这个别名推送消息了。
     * @param alias 为指定用户取消别名
     */
    public void unsetAlias(String alias) {
        MiPushClient.unsetAlias(application, alias, null);
    }

    /**
     * 开发者可以为指定用户设置userAccount。
     * @param account 为指定用户设置userAccount
     */
    public void setAccount(String account) {
        MiPushClient.setUserAccount(application, account, null);
    }

    /**
     * 开发者可以取消指定用户的某个userAccount，服务器就不会给这个userAccount推送消息了。
     * @param account 为指定用户取消userAccount
     */
    public void unsetUserAccount(String account) {
        MiPushClient.unsetUserAccount(application, account, null);
    }

    /**
     * 为某个用户设置订阅主题；根据用户订阅的不同主题，开发者可以根据订阅的主题实现分组群发。
     * @param topic 某个用户设置订阅的主题
     */
    public void subscribe(String topic) {
        MiPushClient.subscribe(application, topic, null);
    }

    /**
     * 为某个用户取消某个订阅主题。
     * @param topic 某个用户取消订阅的主题
     */
    public void unsubscribe(String topic) {
        MiPushClient.unsubscribe(application, topic, null);
    }

    /**
     * 设置接收MiPush服务推送的时段，不在该时段的推送消息会被缓存起来，到了合适的时段再向app推送原先被缓存的消息
     * 这里采用24小时制
     * 注：
     * 1. 这里使用与regId相关联的alias和topic推送消息，也会受到限制
     * 2. 如果时间设置为0:00-0:00，就是暂停push推送服务，也可以直接调用pausePush()方法，其本质相同
     * 3. 如果时间设置为0:00-23:59，就是恢复push推送服务，即全天接收push推送消息，也可以直接调用resumePush()方法，其本质相同
     * @param startHour 接收时段开始时间的小时
     * @param startMin 接收时段开始时间的分钟
     * @param endHour 接收时段结束时间的小时
     * @param endMin 接收时段结束时间的分钟
     */
    public void setAcceptTime(int startHour, int startMin, int endHour, int endMin) {
        MiPushClient.setAcceptTime(application, startHour, startMin, endHour, endMin, null);
    }

    /**
     * 暂停接收MiPush服务推送的消息，app在恢复MiPush推送服务之前，不接收任何推送消息
     * 注：
     * 这里使用与RegId相关联的alias和topic推送消息，也是被暂停的
     */
    public void pausePush() {
        MiPushClient.pausePush(application, null);
    }

    /**
     * 恢复接收MiPush服务推送的消息
     * 注：
     * 这里使用与RegId相关联的alias和topic推送消息，也是被恢复的；这时服务器会把暂停时期的推送消息重新推送过来
     */
    public void resumePush() {
        MiPushClient.resumePush(application, null);
    }

    /**
     * 清除小米推送弹出的某一个notifyId通知。
     * @param notifyId 调用server api设置通知消息的notifyId。
     */
    public void clearNotification(int notifyId) {
        MiPushClient.clearNotification(application, notifyId);
    }

    /**
     * 清除小米推送弹出的所有通知。
     */
    public void clearNotification() {
        MiPushClient.clearNotification(application);
    }

    /**
     * 获取客户端的RegId。
     * @return
     */
    public String getRegId() {
        return MiPushClient.getRegId(application);
    }
}
