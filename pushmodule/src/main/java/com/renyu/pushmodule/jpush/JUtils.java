package com.renyu.pushmodule.jpush;

import android.app.Application;
import android.util.Log;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Clevo on 2016/10/11.
 */

public class JUtils {

    private static JUtils utils;
    private static Application application;

    private JUtils() {

    }

    public static JUtils getInstance(Application app) {
        synchronized (JUtils.class) {
            if (utils==null) {
                synchronized (JUtils.class) {
                    utils=new JUtils();
                    application=app;
                }
            }
        }
        return utils;
    }

    public void init() {
        JPushInterface.init(application);
    }

    public void setDebugMode(boolean flag) {
        JPushInterface.setDebugMode(true);
    }

    public String getRegistrationID() {
        return JPushInterface.getRegistrationID(application);
    }

    public void setAlias(String alias) {
        JPushInterface.setAliasAndTags(application, alias, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("JUtils", "Set tag and alias success");
                        break;
                    default:
                        Log.d("JUtils", "Set tag and alias fail, "+"errorcode:" + i);
                }
            }
        });
    }

    public void setTags(Set<String> tags) {
        JPushInterface.setAliasAndTags(application, null, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("JUtils", "Set tag and alias success");
                        break;
                    default:
                        Log.d("JUtils", "Set tag and alias fail, "+"errorcode:" + i);
                }
            }
        });
    }

    /**
     * 同时设置别名与标签。
     * 注：
     * 这个接口是覆盖逻辑，而不是增量逻辑。即新的调用会覆盖之前的设置。
     * @param alias null 此次调用不设置此值。（注：不是指的字符串"null"）
     *               "" （空字符串）表示取消之前的设置。
     *               每次调用设置有效的别名，覆盖之前的设置。
     *               有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|￥。
     *               限制：alias 命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
     * @param tags null 此次调用不设置此值。（注：不是指的字符串"null"）
     *              空数组或列表表示取消之前的设置。
     *              每次调用至少设置一个 tag，覆盖之前的设置，不是新增。
     *              有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|￥。
     *              限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，但总长度不得超过7K字节。（判断长度需采用UTF-8编码）
     */
    public void setAliasAndTags(String alias, Set<String> tags) {
        JPushInterface.setAliasAndTags(application, alias, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("JUtils", "Set tag and alias success");
                        break;
                    default:
                        Log.d("JUtils", "Set tag and alias fail, "+"errorcode:" + i);
                }
            }
        });
    }

    public void removeAlias() {
        JPushInterface.setAliasAndTags(application, "", null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("JUtils", "Set tag and alias success");
                        break;
                    default:
                        Log.d("JUtils", "Set tag and alias fail, "+"errorcode:" + i);
                }
            }
        });
    }

    public void removeTags() {
        JPushInterface.setAliasAndTags(application, null, new LinkedHashSet<String>(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("JUtils", "Set tag and alias success");
                        break;
                    default:
                        Log.d("JUtils", "Set tag and alias fail, "+"errorcode:" + i);
                }
            }
        });
    }

    public void removeAllAliasAndTags() {
        JPushInterface.setAliasAndTags(application, "", new LinkedHashSet<String>(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                switch (i) {
                    case 0:
                        Log.d("JUtils", "Set tag and alias success");
                        break;
                    default:
                        Log.d("JUtils", "Set tag and alias fail, "+"errorcode:" + i);
                }
            }
        });
    }

    /**
     * 停止推送服务
     */
    public void stopPush() {
        JPushInterface.stopPush(application);
    }

    /**
     * 恢复推送服务。
     */
    public void resumePush() {
        JPushInterface.resumePush(application);
    }

    /**
     * 用来检查 Push Service 是否已经被停止
     * @return
     */
    public boolean isPushStopped() {
        return JPushInterface.isPushStopped(application);
    }

    /**
     * 清除所有 JPush 展现的通知
     */
    public void clearAllNotifications() {
        JPushInterface.clearAllNotifications(application);
    }

    /**
     * 清除指定某个通知。
     * @param notificationId 此 notificationId 来源于intent参数 JPushInterface.EXTRA_NOTIFICATION_ID，可参考文档 接收推送消息Receiver
     */
    public void clearNotificationById(int notificationId) {
        JPushInterface.clearNotificationById(application, notificationId);
    }

    /**
     * 设置允许推送时间
     * 默认情况下用户在任何时间都允许推送。即任何时候有推送下来，客户端都会收到，并展示。开发者可以调用此 API 来设置允许推送的时间。如果不在该时间段内收到消息，当前的行为是：推送到的通知会被扔掉。
     * @param weekDays 0表示星期天，1表示星期一，以此类推。 （7天制，Set集合里面的int范围为0到6）
     *                  Sdk1.2.9 – 新功能:set的值为null,则任何时间都可以收到消息和通知，set的size为0，则表示任何时间都收不到消息和通知.
     * @param startHour 允许推送的开始时间 （24小时制：startHour的范围为0到23）
     * @param endHour 允许推送的结束时间 （24小时制：endHour的范围为0到23）
     */
    public void setPushTime(Set<Integer> weekDays, int startHour, int endHour) {
        JPushInterface.setPushTime(application, weekDays, startHour, endHour);
    }

    /**
     * 设置通知静默时间
     * 开发者可以调用此 API 来设置静音时段。如果在该时间段内收到消息，则：不会有铃声和震动。
     * @param startHour 静音时段的开始时间 - 小时 （24小时制，范围：0~23 ）
     * @param startMinute 静音时段的开始时间 - 分钟（范围：0~59 ）
     * @param endHour 静音时段的结束时间 - 小时 （24小时制，范围：0~23 ）
     * @param endMinute 静音时段的结束时间 - 分钟（范围：0~59 ）
     */
    public void setSilenceTime(int startHour, int startMinute, int endHour, int endMinute) {
        JPushInterface.setSilenceTime(application, startHour, startMinute, endHour, endMinute);
    }

    /**
     * 设置保留最近通知条数
     * @param maxNum
     */
    public void setLatestNotificationNumber(int maxNum) {
        JPushInterface.setLatestNotificationNumber(application, maxNum);
    }
}
