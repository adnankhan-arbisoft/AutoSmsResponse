package com.semester.seecs.autoresponse;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyPhoneStateListener extends PhoneStateListener {

    private static String TAG = MyPhoneStateListener.class.getSimpleName();

    private static Boolean phoneRinging = false;

    private Context context;

    public MyPhoneStateListener(Context context){
        this.context = context;
    }

    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "IDLE");
                if(phoneRinging){
                    List<Model> models = getActiveModelList();
                    for (Model model : models){
                        Log.d(TAG, model.toString());
                        sendSMS(incomingNumber, model.getMessage());
                        showNotification(incomingNumber, model.getMessage());
                    }

                }
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                phoneRinging = false;
                break;

            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "RINGING");
                phoneRinging = true;

                break;
        }
    }


    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public List<Model> getActiveModelList(){

        List<Model> modelList = DataHolder.getInstance(context).getModels();
        Iterator<Model> iterator = modelList.iterator();
        while (iterator.hasNext()) {
            Model model = iterator.next();
            Log.d(TAG, model.toString());
            if (!model.isActive()) {
                iterator.remove();
            }else {
                if(!isWithinRange(model)){
                    iterator.remove();
                    Log.d(TAG, "Not within range....Removed");
                }
            }
        }
        return modelList;
    }

    private Date getDate(Model.Time time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, time.hourOfDay);
        calendar.set(Calendar.MINUTE, time.minute);
        return calendar.getTime();
    }

    boolean isWithinRange(Model model) {
        Date startDate = getDate(model.getStartTime());
        Date endDate = getDate(model.getEndTime());

        Date currentDate = Calendar.getInstance().getTime();

        return !(currentDate.before(startDate) || currentDate.after(endDate));
    }

    public void showNotification(String number, String message){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_my_launcher)
                        .setContentTitle(number)
                        .setContentText(message);


        int mNotificationId = (int) (Math.random() *100);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, builder.build());
    }

}
