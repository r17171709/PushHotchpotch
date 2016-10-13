package mipush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.renyu.push.PushApplication;
import com.renyu.push.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Clevo on 2016/10/9.
 */

public class MiFunctionActivity extends AppCompatActivity {

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
                ((PushApplication) getApplication()).miUtils.setAlias("alias");
                break;
            case R.id.button_unset_alias:
                ((PushApplication) getApplication()).miUtils.unsetAlias("alias");
                break;
            case R.id.button_set_account:
                ((PushApplication) getApplication()).miUtils.setAccount("account");
                break;
            case R.id.button_unset_account:
                ((PushApplication) getApplication()).miUtils.unsetUserAccount("account");
                break;
            case R.id.button_subscribe_topic:
                ((PushApplication) getApplication()).miUtils.subscribe("topic");
                break;
            case R.id.button_unsubscribe_topic:
                ((PushApplication) getApplication()).miUtils.unsubscribe("topic");
                break;
            case R.id.button_set_accept_time:
                ((PushApplication) getApplication()).miUtils.setAcceptTime(0, 0, 23, 59);
                break;
            case R.id.button_pause_push:
                ((PushApplication) getApplication()).miUtils.pausePush();
                break;
            case R.id.button_resume_push:
                ((PushApplication) getApplication()).miUtils.resumePush();
                break;
        }
    }
}
