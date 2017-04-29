package randomforest.com.profilemanagerwcp;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class HelloIntentService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public HelloIntentService() {
        super("HelloIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Toast.makeText(this, "Service onHandler invoke "+Math.random(), Toast.LENGTH_SHORT).show();

    }
}