package com.health.ahsas.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button StartService, StopService;


    //forground Service
    EditText userInput;
    Button mStartService, mStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        foreground Service
        setContentView(R.layout.activity_foreground);

//        StartService = (Button) findViewById(R.id.startService);
//        StopService = (Button) findViewById(R.id.stopService);
//
//
//        StartService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startService();
//            }
//        });
//
//
//
//        StopService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopService();
//            }
//        });

        userInput = (EditText) findViewById(R.id.edit_text_input);
        mStartService = (Button) findViewById(R.id.BtnStartService);
        mStopService = (Button) findViewById(R.id.BtnStopService);

        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = userInput.getText().toString();

                Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                serviceIntent.putExtra("inputExtra",serviceIntent);

                startService(serviceIntent);
            }
        });

        mStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                stopService(serviceIntent);
            }
        });
    }

    public void startService() {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);

        JobInfo info = new JobInfo.Builder(123, componentName)
//                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job Schedule");
        }else{
            Log.d(TAG, "job scheduling failed");
        }
    }

    public void stopService() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "job cancelled");
    }
}
