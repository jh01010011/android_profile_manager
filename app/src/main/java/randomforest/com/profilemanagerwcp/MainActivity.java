package randomforest.com.profilemanagerwcp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Thread thread;
    Button start_Btn;
    Button stop_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_Btn=(Button) findViewById(R.id.start);
        stop_Btn=(Button) findViewById(R.id.stop);
    }

    @Override
    protected void onResume() {
        super.onResume();
        start_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),HelloService.class);


                //Intent intent=new Intent(getBaseContext(),HelloIntentService.class);

                startService(intent);
            }

        });

        stop_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getBaseContext(),HelloService.class);

                stopService(intent);
              //  thread.interrupt();
            }
        });

    }



//    public void startService(View view){
////
////         thread=new Thread(){
////            @Override
////            public void run() {
//                startService(new Intent(getBaseContext(),HelloIntentService.class));
////            }
////        };
////        thread.start();
//
//    }

//    public void stopService(View view){
//        stopService(new Intent(getBaseContext(),HelloIntentService.class));
//    }
}
