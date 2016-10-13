package umpush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.renyu.push.PushApplication;
import com.renyu.push.R;
import com.umeng.message.IUmengCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Clevo on 2016/10/13.
 */

public class UMFunctionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_set_alias, R.id.button_unset_alias, R.id.button_set_account, R.id.button_unset_account, R.id.button_subscribe_topic, R.id.button_unsubscribe_topic, R.id.button_set_accept_time, R.id.button_pause_push, R.id.button_resume_push})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_set_alias:
                ((PushApplication) getApplication()).umengUtils.addAlias("alias", "default");
                break;
            case R.id.button_unset_alias:
                ((PushApplication) getApplication()).umengUtils.removeAlias("alias", "default");
                break;
            case R.id.button_set_account:
                ((PushApplication) getApplication()).umengUtils.addExclusiveAlias("account", "default");
                break;
            case R.id.button_unset_account:
                ((PushApplication) getApplication()).umengUtils.removeAlias("account", "default");
                break;
            case R.id.button_subscribe_topic:
                ((PushApplication) getApplication()).umengUtils.addTags("topic", "topic1");
                break;
            case R.id.button_unsubscribe_topic:
                ((PushApplication) getApplication()).umengUtils.deleteTags("topic", "topic1");
                break;
            case R.id.button_set_accept_time:
                Toast.makeText(this, "友盟暂无此功能，个推只有设置静默时间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_pause_push:
                ((PushApplication) getApplication()).umengUtils.disable(new IUmengCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("PushApplication", "disable: onSuccess");
                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        Log.d("PushApplication", "disable: fail "+s+" "+s1);
                    }
                });
                break;
            case R.id.button_resume_push:
                ((PushApplication) getApplication()).umengUtils.enable(new IUmengCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("PushApplication", "enable: onSuccess");
                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        Log.d("PushApplication", "enable: fail "+s+" "+s1);
                    }
                });
                break;
        }
    }
}
