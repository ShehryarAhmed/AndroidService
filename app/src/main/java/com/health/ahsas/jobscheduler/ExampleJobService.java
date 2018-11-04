package com.health.ahsas.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by Admin on 11/4/2018.
 */

public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d(TAG, "job Started");
        doBackgroundWork(jobParameters);
        return true;
    }

    private void doBackgroundWork(final JobParameters jobParameters) {
     new Thread(new Runnable() {
         @Override
         public void run() {
            for (int i = 0; i< 10; i++){
                Log.d(TAG, "run: "+i);
                if(jobCancelled){
                    return;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

             Log.d(TAG, "Job Finished");
             jobFinished(jobParameters, false);
         }
     }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
