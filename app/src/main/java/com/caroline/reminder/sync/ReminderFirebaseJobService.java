package com.caroline.reminder.sync;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public class ReminderFirebaseJobService extends JobService {

        private AsyncTask mBackgroundTask;
        @Override
        public boolean onStartJob(final JobParameters jobParameters) {
            mBackgroundTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {

                    Context context = ReminderFirebaseJobService.this;
                    ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                    return null;
                }
                @Override
                protected void onPostExecute(Object o) {


                    jobFinished(jobParameters, false);
                }
            };
            mBackgroundTask.execute();

            return true;

        }

        @Override
        public boolean onStopJob(JobParameters params) {
            if (mBackgroundTask != null) mBackgroundTask.cancel(true);
            return true;
        }
}
