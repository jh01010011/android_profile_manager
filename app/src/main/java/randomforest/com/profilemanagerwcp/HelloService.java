package randomforest.com.profilemanagerwcp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by blueb on 4/21/2017.
 */

public class HelloService extends Service{

    Thread thread;
    MyThread myThread;

    private SensorManager sensorManager;
    //AudioManager audioManager;




    final class MyThread implements Runnable,SensorEventListener{

        final int DELAY=5000000;


        private Sensor light_sensor;
        private Sensor gyroscope;
        private Sensor accelerometer;
        private Sensor proximity;

        private long lastUpdate;
        private long currentUpdate;

        public float l_value;
        public float p_value;
        public float[] gyro_value;
        public float[] acc_value;

        int service_id;
        MyThread(int service_id){

            this.service_id=service_id;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {


            if(event.sensor==sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)){

                currentUpdate=event.timestamp;
                l_value=event.values[0];

                modeChange();

                //Toast.makeText(this, event.sensor.getName()+" "+event.values[0], Toast.LENGTH_SHORT).show();
            }
            if(event.sensor==sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)){

                currentUpdate=event.timestamp;
                p_value=event.values[0];
                modeChange();

                //Toast.makeText(this, event.sensor.getName()+" X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2], Toast.LENGTH_SHORT).show();
            }

            if(event.sensor==sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)){

                currentUpdate=event.timestamp;
                acc_value[0]=event.values[1];
                acc_value[1]=event.values[1];
                acc_value[2]=event.values[2];

                modeChange();

                //Toast.makeText(this, event.sensor.getName()+" X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2], Toast.LENGTH_SHORT).show();
            }

            if(event.sensor==sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)){


                currentUpdate=event.timestamp;
                gyro_value[0]=event.values[1];
                gyro_value[1]=event.values[1];
                gyro_value[2]=event.values[2];
                modeChange();

                //Toast.makeText(this, event.sensor.getName()+" X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2], Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void run() {


            sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);

            lastUpdate= System.currentTimeMillis();
            gyro_value=new float[3];
            acc_value=new float[3];

            if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!=null){
                light_sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            }

            if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!=null){
                gyroscope=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            }

            if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
                accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
            if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!=null){
                proximity=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            }



           // sensorManager.registerListener(this,light_sensor,SensorManager.SENSOR_DELAY_NORMAL,100000);
            sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL,3000000);
            sensorManager.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL,3000000);
            sensorManager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL,3000000);
            sensorManager.registerListener(this,light_sensor,SensorManager.SENSOR_DELAY_NORMAL,3000000);

        }


        private void modeChange(){


            AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);



            if(currentUpdate-lastUpdate<7000){
                return;
            }
            lastUpdate=currentUpdate;



            if(p_value >= 0.0 && gyro_value[0]==0.0 && gyro_value[1]==0.0 && gyro_value[2] == 0.0){

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                //Toast.makeText(this," L/P-Y , Gy-0, Ga>8 Mode-V", Toast.LENGTH_SHORT).show();

            }

            else if(p_value == 0.0 && gyro_value[0]==0.0 && gyro_value[1]==0.0 && gyro_value[2] == 0.0 && acc_value[2]<=(-8.0)){


                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                //Toast.makeText(this," L/P-N , Gy-0, Ga<-8 Mode-S", Toast.LENGTH_SHORT).show();

            }

            else if(p_value != 0.0 && (gyro_value[0]!=0.0 || gyro_value[1]!=0.0 || gyro_value[2] != 0.0)){

                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                //Toast.makeText(this," L/P-Y , Gy-!0 Mode-V", Toast.LENGTH_SHORT).show();


            }
            else if(p_value == 0.0 && (gyro_value[0]!=0.0 || gyro_value[1]!=0.0 || gyro_value[2] != 0.0)){

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                //Toast.makeText(this," L/P-N , Gy-0, Mode-N", Toast.LENGTH_SHORT).show();


            }




        }

        public void  unregister_lisntener(){

            sensorManager.unregisterListener(this);
        }

    }




//
//    private SensorEventListener light_eListener=new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//            float lux_1=event.values[0];
//
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };
//
//    private SensorEventListener gyro_eListener=new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };
//
//    private SensorEventListener acc_eListener=new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };
//
//    private SensorEventListener prox_eListener=new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {





        Toast.makeText(this, "Service Started "+Math.random(), Toast.LENGTH_SHORT).show();
        myThread=new MyThread(startId);
        thread =new Thread(myThread);
        thread.start();


        return START_STICKY;
    }




    @Override
    public void onDestroy() {
        myThread.unregister_lisntener();
       // thread.interrupt();



        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

        super.onDestroy();
    }


}
