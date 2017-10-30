package com.example.backup.jobscheduler;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    /*
   1) JobInfo that holds the constraints of when the job should run
   (e.g. only on WiFi, only when plugged in, periodically, etc.)
   2) JobService that is a service that handles the scheduled requests.
    */
    private static final int JOB_ID = 0x34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scheduleJob = (Button) findViewById(R.id.main_schedule_job_btn);

        // JOB_ID : Unique job id
        // Component Name : Service name
        // setRequiredNetworkType:  job to run only on WiFi connection
        // setRequiresCharging(true) : for the device to be plugged in
        //setOverrideDeadline(long) : job will be executed after a period of time
        final JobInfo job = new JobInfo.Builder(JOB_ID, new ComponentName(this, SyncJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .build();


        final JobScheduler jobScheduler =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        // jobScheduler.schedule(job) :  job will be executed if the criteria are met
        scheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobScheduler.schedule(job);
            }
        });
    }
}
