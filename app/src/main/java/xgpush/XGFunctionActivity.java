package xgpush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.renyu.push.BuildConfig;
import com.renyu.push.PushApplication;
import com.renyu.push.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Clevo on 2016/10/10.
 */

public class XGFunctionActivity extends AppCompatActivity {

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
                ((PushApplication) getApplication()).xgUtils.registWithAccount(Long.parseLong(BuildConfig.ACCESSID), BuildConfig.ACCESSKEY, "alias");
                break;
            case R.id.button_unset_alias:
                ((PushApplication) getApplication()).xgUtils.unBindAccount();
                break;
            case R.id.button_set_account:
                ((PushApplication) getApplication()).xgUtils.registWithAccount(Long.parseLong(BuildConfig.ACCESSID), BuildConfig.ACCESSKEY, "alias");
                break;
            case R.id.button_unset_account:
                ((PushApplication) getApplication()).xgUtils.unBindAccount();
                break;
            case R.id.button_subscribe_topic:
                ((PushApplication) getApplication()).xgUtils.setTag("topic");
                break;
            case R.id.button_unsubscribe_topic:
                ((PushApplication) getApplication()).xgUtils.deleteTag("topic");
                break;
            case R.id.button_set_accept_time:
                Toast.makeText(this, "信鸽暂无此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_pause_push:
                ((PushApplication) getApplication()).xgUtils.stopPush();
                break;
            case R.id.button_resume_push:
                Toast.makeText(this, "信鸽暂无此功能，重新注册即可", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
