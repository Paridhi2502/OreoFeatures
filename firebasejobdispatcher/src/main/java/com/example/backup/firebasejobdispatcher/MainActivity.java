package com.example.backup.firebasejobdispatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity {

    private static final String JOB_TAG = "my_job_tag";
    private FirebaseJobDispatcher jobDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
    }

    public void startJob(View view) {
        Job job = jobDispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MyService.class)
                .setLifetime(Lifetime.FOREVER)
                // one-off job
                .setRecurring(true)
                // uniquely identifies the job
                .setTag(JOB_TAG)
                // start between 10 and 15 seconds from now
                .setTrigger(Trigger.executionWindow(5, 10))
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // Job need network connection then set this variable
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        jobDispatcher.mustSchedule(job);
        Toast.makeText(getApplicationContext(), "Job Scheduled...", Toast.LENGTH_SHORT).show();

    }

    public void stopJob(View view) {
        jobDispatcher.cancel(JOB_TAG);
        Toast.makeText(getApplicationContext(), "Job Cancelled...", Toast.LENGTH_SHORT).show();
        // jobDispatcher.cancelAll();
    }
}
