package jpush;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.renyu.push.PushApplication;
import com.renyu.push.R;

import java.util.LinkedHashSet;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Clevo on 2016/10/12.
 */

public class JFunctionActivity extends AppCompatActivity {

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
                ((PushApplication) getApplication()).jUtils.setAlias("alias");
                break;
            case R.id.button_unset_alias:
                ((PushApplication) getApplication()).jUtils.removeAlias();
                break;
            case R.id.button_set_account:
                Toast.makeText(this, "极光暂无此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_unset_account:
                Toast.makeText(this, "极光暂无此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_subscribe_topic:
                LinkedHashSet<String> strings=new LinkedHashSet<>();
                strings.add("topic");
                ((PushApplication) getApplication()).jUtils.setTags(strings);
                break;
            case R.id.button_unsubscribe_topic:
                ((PushApplication) getApplication()).jUtils.removeTags();
                break;
            case R.id.button_set_accept_time:
                LinkedHashSet<Integer> ints=new LinkedHashSet<>();
                ints.add(0);
                ints.add(1);
                ints.add(2);
                ints.add(3);
                ints.add(4);
                ints.add(5);
                ints.add(6);
                ((PushApplication) getApplication()).jUtils.setPushTime(ints, 0, 23);
                break;
            case R.id.button_pause_push:
                ((PushApplication) getApplication()).jUtils.stopPush();
                break;
            case R.id.button_resume_push:
                ((PushApplication) getApplication()).jUtils.resumePush();
                break;
        }
    }
}
