package com.example.momstime;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.momstime.Models.NotesModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Notify extends Worker {

    DatabaseHelper databaseHelper;
    public Notify(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        databaseHelper=new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Result doWork() {

              checkForSchedule();


        return null;
    }

    private void checkForSchedule() {

        Date dateObj= Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("d/M/yyyy");
        String newDateStr = postFormater.format(dateObj);

        ArrayList<NotesModel> list=databaseHelper.fetchSchedulesData();

        for (int i=0;i<list.size();i++){


          if (list.get(i).getDate().equals(newDateStr)&&list.get(i).getStatus().equals("pending")){
              sendNotification(list.get(i).getTitle(),list.get(i).getDesc());
               databaseHelper.updateScheduleStatus(list.get(i).getId());
          }


        }

    }



    void sendNotification(String title, String message) {

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;


        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1",
                    "android",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("WorkManger");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "1")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setAutoCancel(true);// clear notification after click
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(m, mBuilder.build());


    }
}
