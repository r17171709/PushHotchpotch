package com.renyu.pushmodule.mipush;

import android.content.Context;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * Created by Clevo on 2016/10/9.
 */

public class MiPushReceiver extends PushMessageReceiver {

    //用来接收服务器向客户端发送的透传消息
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
        showMiPushMessageLog("onReceivePassThroughMessage", miPushMessage);
    }

    //用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
        showMiPushMessageLog("onNotificationMessageClicked", miPushMessage);
    }

    //用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        showMiPushMessageLog("onNotificationMessageArrived", miPushMessage);
    }

    //用来接收服务器推送的消息
    @Override
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceiveMessage(context, miPushMessage);
    }

    //用来接收客户端向服务器发送注册命令后的响应结果
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
        showMiPushCommandMessageLog("onReceiveRegisterResult", miPushCommandMessage);
    }

    //用来接收客户端向服务器发送命令后的响应结果
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
        showMiPushCommandMessageLog("onCommandResult", miPushCommandMessage);
    }

    private void showMiPushMessageLog(String from, MiPushMessage miPushMessage) {
        Log.d("MiPushReceiver", from);
        Log.d("MiPushReceiver", miPushMessage.toString());
        Log.d("MiPushReceiver", "Alias:"+miPushMessage.getAlias());
        Log.d("MiPushReceiver", "Topic:"+miPushMessage.getTopic());
        Log.d("MiPushReceiver", "Extra:"+miPushMessage.getExtra().toString());
    }

    private void showMiPushCommandMessageLog(String from, MiPushCommandMessage miPushCommandMessage) {
        Log.d("MiPushReceiver", from);
        Log.d("MiPushReceiver", miPushCommandMessage.toString());
        Log.d("MiPushReceiver", "Command:"+miPushCommandMessage.getCommand());
        if (miPushCommandMessage.getCommandArguments()!=null) {
            for (String s : miPushCommandMessage.getCommandArguments()) {
                Log.d("MiPushReceiver", "CommandArguments:"+s);
            }
        }
        Log.d("MiPushReceiver", "SUCCESS:" + (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS));
        if (miPushCommandMessage.getResultCode() != ErrorCode.SUCCESS) {
            Log.d("MiPushReceiver", "ResultCode:" + miPushCommandMessage.getResultCode());
            Log.d("MiPushReceiver", "Reason:"+miPushCommandMessage.getReason());
        }
    }
}
