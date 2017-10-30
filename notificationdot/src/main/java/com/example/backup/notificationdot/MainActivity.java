package com.example.backup.notificationdot;

import android.app.Notification;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int notification_one = 101;
    private static final int notification_two = 102;
    private MainUi mainUI;

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationHelper = new NotificationHelper(this);
        mainUI = new MainUi(findViewById(R.id.activity_main));
    }

//Post the notifications//

    public void postNotification(int id, String title) {
        Notification.Builder notificationBuilder = null;
        switch (id) {
            case notification_one:
                notificationBuilder = notificationHelper.getNotification1(title,
                        getString(R.string.channel_one_body));
                break;

            case notification_two:
                notificationBuilder = notificationHelper.getNotification2(title,
                        getString(R.string.channel_two_body));
                break;
        }

        if (notificationBuilder != null) {
            notificationHelper.notify(id, notificationBuilder);
        }
    }

//Load the settings screen for the selected notification channel//

    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }

//Implement our onClickListeners//

    class MainUi implements View.OnClickListener {
        final EditText editTextOne;
        final EditText editTextTwo;

        private MainUi(View root) {
            editTextOne = (EditText) root.findViewById(R.id.channel_one_text);
            ((Button) root.findViewById(R.id.post_to_channel_one)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.channel_one_settings)).setOnClickListener(this);

            editTextTwo = (EditText) root.findViewById(R.id.channel_two_text);
            ((Button) root.findViewById(R.id.post_to_channel_two)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.channel_two_settings)).setOnClickListener(this);

        }

//Retrieve the contents of each EditText//

        private String getChannelOneText() {
            if (editTextOne != null) {
                return editTextOne.getText().toString();
            }
            return "";
        }

        private String getChannelTwoText() {
            if (editTextOne != null) {
                return editTextTwo.getText().toString();
            }
            return "";
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.post_to_channel_one:
                    postNotification(notification_one, getChannelOneText());
                    break;

                case R.id.channel_one_settings:
                    goToNotificationSettings(NotificationHelper.CHANNEL_ONE_ID);
                    break;

                case R.id.post_to_channel_two:
                    postNotification(notification_two, getChannelTwoText());
                    break;

                case R.id.channel_two_settings:
                    goToNotificationSettings(NotificationHelper.CHANNEL_TWO_ID);
                    break;


            }
        }
    }
}
