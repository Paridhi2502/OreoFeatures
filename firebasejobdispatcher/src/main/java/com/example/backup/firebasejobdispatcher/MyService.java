package com.example.backup.firebasejobdispatcher;


import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


/**
 * Created by Back UP on 10/28/2017.
 */

public class MyService extends JobService {
    BackgroundTask backgroundTask;

    // true -> running in seperate thread
    @Override
    public boolean onStartJob(final JobParameters job) {

        backgroundTask = new BackgroundTask() {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), "Message from background task :"
                        + s, Toast.LENGTH_SHORT).show();
                // if you create seperate thread then must call this method otherwise system think it is continue to running in background
                jobFinished(job, false); //if you want to reschedule this job then return true otherwise false
            }
        };
        backgroundTask.execute();

        return true;
    }

    // if job is interrupted before completion the system will call this method
    // so if you want to reschedule the fail the job you can return true
    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    public static class BackgroundTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return "Hello from background Job";
        }
    }
}
