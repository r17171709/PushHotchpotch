package com.renyu.pushmodule.umpush;

import android.app.Application;
import android.util.Log;

import com.umeng.common.inter.ITagManager;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.tag.TagManager;

import java.util.List;

/**
 * Created by Clevo on 2016/10/12.
 */

public class UmengUtils {

    private static UmengUtils utils;
    private static Application application;
    private PushAgent mPushAgent;

    private UmengUtils() {
        mPushAgent = PushAgent.getInstance(application);
    }

    public static UmengUtils getInstance(Application app) {
        synchronized (UmengUtils.class) {
            if (utils==null) {
                synchronized (UmengUtils.class) {
                    application=app;
                    utils=new UmengUtils();
                }
            }
        }
        return utils;
    }

    public void setDebugMode(boolean flag) {
        mPushAgent.setDebugMode(flag);
    }

    public void setMessageHandler(UmengMessageHandler umengMessageHandler) {
        mPushAgent.setMessageHandler(umengMessageHandler);
    }

    public void register(IUmengRegisterCallback callback) {
        mPushAgent.register(callback);
    }

    public void setNotificationClickHandler(UmengNotificationClickHandler notificationClickHandler) {
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    //开启推送
    public void enable(IUmengCallback iUmengCallback) {
        mPushAgent.enable(iUmengCallback);
    }

    //关闭推送
    public void disable(IUmengCallback iUmengCallback) {
        mPushAgent.disable(iUmengCallback);
    }

    /**
     * 设置用户标签
     * @param tag 目前每个用户tag限制在1024个， 每个tag 最大256个字符。tag名称请不要加入URL Encode等变换处理，请使用原生字符串。
     */
    public void addTags(String... tag) {
        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    Log.d("UmengUtils", "add: Add Tag:\n" + result);
                } else {
                    Log.d("UmengUtils", "add: Add Tag:\n" + "加入tag失败");
                }
            }
        }, tag);
    }

    /**
     * 将之前添加的标签中的一个或多个删除。
     * @param tag
     */
    public void deleteTags(String... tag) {
        mPushAgent.getTagManager().delete(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    Log.d("UmengUtils", "delete: Delete Tag:\n" + result);
                } else {
                    Log.d("UmengUtils", "delete: Delete Tag:\n" + "删除tag失败");
                }
            }
        }, tag);
    }

    /**
     * 删除之前添加的所有标签。
     */
    public void resetTags() {
        mPushAgent.getTagManager().reset(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    Log.d("UmengUtils", "update: Update Tag:\n" + result);
                } else {
                    Log.d("UmengUtils", "update: Update Tag:\n" + "重置tag失败");
                }
            }
        });
    }

    /**
     * 删除之前添加的所有标签，重置为新标签。
     */
    public void updateTags(String tag) {
        mPushAgent.getTagManager().update(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                if (isSuccess) {
                    Log.d("UmengUtils", "update: Update Tag:\n" + result);
                } else {
                    Log.d("UmengUtils", "update: Update Tag:\n" + "更新tag失败");
                }
            }
        }, tag);
    }

    public void listTags() {
        mPushAgent.getTagManager().list(new TagManager.TagListCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final List<String> result) {
                if (isSuccess) {
                    if (result != null) {
                        StringBuilder info = new StringBuilder();
                        for (int i = 0; i < result.size(); i++) {
                            String tag = result.get(i);
                            info.append(tag + "\n");
                        }
                        Log.d("UmengUtils", "list:" + info);
                    }
                }
                else {
                    Log.d("UmengUtils", "list: Tags获取失败");
                }
            }
        });
    }

    /**
     * 设置用户别名
     * 设置用户id和device_token的一对多的映射关系
     * @param alias alias名称请不要使用URLEncode等变换处理，请使用原生字符串。
     * @param aliasType 每个类型的alias同时只能存在一个，同一个类型的新alias会覆盖旧alias。
     */
    public void addAlias(String alias, String aliasType) {
        mPushAgent.addAlias(alias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.d("UmengUtils", "addAlias: isSuccess:" + isSuccess + "," + message);
            }
        });
    }

    /**
     * 设置用户别名
     * 设置用户id和device_token的一一映射关系，确保同一个alias只对应一台设备：
     * @param exclusiveAlias
     * @param aliasType
     */
    public void addExclusiveAlias(String exclusiveAlias, String aliasType) {
        mPushAgent.addExclusiveAlias(exclusiveAlias, aliasType, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.d("UmengUtils", "addAlias: isSuccess:" + isSuccess + "," + message);
            }
        });
    }

    /**
     * 删除别名
     * 若是要移除用户id
     * @param alias
     * @param aliasType
     */
    public void removeAlias(String alias, String aliasType) {
        mPushAgent.removeAlias(alias, aliasType, new UTrack.ICallBack(){
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Log.d("UmengUtils", "addAlias: isSuccess:" + isSuccess + "," + message);
            }
        });
    }

    /**
     * 免打扰模式
     * 注:
     * 为免过度打扰用户，SDK默认在“23:00”到“7:00”之间收到通知消息时不响铃，不振动，不闪灯。如果需要改变默认的静音时间
     * @param startHour
     * @param startMinute
     * @param endHour
     * @param endMinute
     */
    public void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute) {
        mPushAgent.setNoDisturbMode(startHour, startMinute, endHour, endMinute);
    }

    /**
     * 应用在前台时否显示通知
     * 如果应用在前台的时候，开发者可以自定义配置是否显示通知，默认情况下，应用在前台是显示通知的。 开发者更改前台通知显示设置后，会根据更改生效。
     * @param flag
     */
    public void setNotificaitonOnForeground(boolean flag) {
        mPushAgent.setNotificaitonOnForeground(flag);
    }
}
