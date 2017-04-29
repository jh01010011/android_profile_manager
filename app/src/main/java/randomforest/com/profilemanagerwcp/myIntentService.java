package randomforest.com.profilemanagerwcp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by blueb on 4/22/2017.
 */

public class myIntentService extends IntentService implements SensorEventListener{

    private SensorManager sensorManager;
    AudioManager audioManager;


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
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public myIntentService() {
        super("myIntentService");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);


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


        sensorManager.registerListener(this,light_sensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_NORMAL);

        // Let it continue running until it is stopped.

        Toast.makeText(this, "Service Started "+Math.random(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {



        if(event.sensor==sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)){

            currentUpdate=event.timestamp;
            l_value=event.values[0];

            modeChange();

            Toast.makeText(this, event.sensor.getName()+" "+event.values[0], Toast.LENGTH_SHORT).show();
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

    private void modeChange(){


        if(currentUpdate-lastUpdate<5000){
            return;
        }
        lastUpdate=currentUpdate;

        if(l_value!=0.0 && p_value != 0.0 && gyro_value[0]==0.0 && gyro_value[1]==0.0 && gyro_value[2] == 0.0 && acc_value[2]>=8.0){

            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

            Toast.makeText(this," L/P-Y , Gy-0, Ga>8 Mode-V", Toast.LENGTH_SHORT).show();

        }

        else if(l_value==0.0 && p_value == 0.0 && gyro_value[0]==0.0 && gyro_value[1]==0.0 && gyro_value[2] == 0.0 && acc_value[2]<=(-7.50)){


            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            Toast.makeText(this," L/P-N , Gy-0, Ga<-8 Mode-S", Toast.LENGTH_SHORT).show();

        }

        else if(l_value!=0.0 && p_value != 0.0 && gyro_value[0]!=0.0 && gyro_value[1]!=0.0 && gyro_value[2] != 0.0){

            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

            Toast.makeText(this," L/P-Y , Gy-!0 Mode-V", Toast.LENGTH_SHORT).show();


        }
        else if(l_value==0.0 && p_value == 0.0 && gyro_value[0]!=0.0 && gyro_value[1]!=0.0 && gyro_value[2] != 0.0){

            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(this," L/P-N , Gy-0, Mode-N", Toast.LENGTH_SHORT).show();


        }
    }
}
