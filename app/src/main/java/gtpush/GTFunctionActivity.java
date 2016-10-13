package gtpush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.renyu.push.PushApplication;
import com.renyu.push.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Clevo on 2016/10/12.
 */

public class GTFunctionActivity extends AppCompatActivity {

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
                ((PushApplication) getApplication()).gtUtils.bindAlias("alias");
                break;
            case R.id.button_unset_alias:
                ((PushApplication) getApplication()).gtUtils.unBindAlias("alias", true);
                break;
            case R.id.button_set_account:
                Toast.makeText(this, "个推暂无此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_unset_account:
                Toast.makeText(this, "个推暂无此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_subscribe_topic:
                ((PushApplication) getApplication()).gtUtils.setTag(new String[]{"topic"});
                break;
            case R.id.button_unsubscribe_topic:
                Toast.makeText(this, "个推暂无此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_set_accept_time:
                Toast.makeText(this, "个推暂无此功能，个推只有设置静默时间", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_pause_push:
                ((PushApplication) getApplication()).gtUtils.stopService();
                break;
            case R.id.button_resume_push:
                ((PushApplication) getApplication()).gtUtils.initialize();
                break;
        }
    }
}
