package com.renyu.pushmodule.xgpush;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by Clevo on 2016/10/10.
 */

public class XGPushReceiver extends XGPushBaseReceiver {

    //注册结果
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
        String text;
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = xgPushRegisterResult + "注册成功";
            // 在这里拿token
            String token = xgPushRegisterResult.getToken();
            Log.d("XGPushReceiver", "token:"+token);
        }
        else {
            text = xgPushRegisterResult + "注册失败，错误码：" + i;
        }
        Log.d("XGPushReceiver", "onRegisterResult:"+text);
    }

    //反注册结果
    @Override
    public void onUnregisterResult(Context context, int i) {
        String text;
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        }
        else {
            text = "反注册失败" + i;
        }
        Log.d("XGPushReceiver", "onUnregisterResult:"+text);
    }

    //设置标签结果
    @Override
    public void onSetTagResult(Context context, int i, String s) {
        String text;
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = "标签"+s+"设置成功";
        } else {
            text = "标签"+s+"设置失败,错误码：" + i;
        }
        Log.d("XGPushReceiver", "onSetTagResult:"+text);
    }

    //删除标签结果
    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
        String text;
        if (i == XGPushBaseReceiver.SUCCESS) {
            text = "标签"+s+"删除成功";
        } else {
            text = "标签"+s+"删除失败,错误码：" + i;
        }
        Log.d("XGPushReceiver", "onDeleteTagResult:"+text);
    }

    //透传消息
    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        String text = "收到消息:" + xgPushTextMessage.toString();
        Log.d("XGPushReceiver", "onTextMessage:"+text);
        // 获取自定义key-value
        String customContent = xgPushTextMessage.getCustomContent();
        if (!TextUtils.isEmpty(customContent)) {
            Log.d("XGPushReceiver", "customContent:"+customContent);
        }
        // APP自主处理消息的过程...
    }

    //通知被点击触发的结果
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        String text="";
        if (xgPushClickedResult.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + xgPushClickedResult;
        }
        else if (xgPushClickedResult.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + xgPushClickedResult;
        }
        Log.d("XGPushReceiver", "onNotifactionClickedResult:"+text);
        // 获取自定义key-value
        String customContent = xgPushClickedResult.getCustomContent();
        if (!TextUtils.isEmpty(customContent)) {
            Log.d("XGPushReceiver", "customContent:"+customContent);
        }
        // APP自主处理的过程。。。
    }

    //通知被展示触发的结果，可以在此保存APP收到的通知
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        Log.i("XGPushReceiver", "onNotifactionShowedResult:"+"收到信鸽通知：" + xgPushShowedResult);
    }
}
