package com.example.zibu.zibumessage;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button setting;
    private Button historyMessage;
    private String phoneNumData;
    private String aimPhoneNumData;
    private String messageContentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          /*获取偏好设置*/
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        phoneNumData = pref.getString("phoneNum","输入短信发送的手机号码");
        messageContentData = pref.getString("messageContent","mm");
        aimPhoneNumData = pref.getString("aimPhoneNum","10010");

        historyMessage = (Button)findViewById(R.id.historyMessage);
        setting = (Button)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        historyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "已经在主页面了", Toast.LENGTH_SHORT).show();
            }
        });
        /*发送短信*/
        seedMessageToUser();
    }
    /*接受服务商回复的短信*/
    public void receiveMessage(){

    }

    /*给用户发送信息*/
    public void seedMessageToUser(){
        if(!phoneNumData.equals("输入短信发送的手机号码")){
            SmsManager smsManager = SmsManager.getDefault();
            Intent sentIntent = new Intent("SENT_SMS_ACTION");
            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sentIntent, 0);
            smsManager.sendTextMessage(phoneNumData.toString(), null, messageContentData.toString(), pi, null);
            Toast.makeText(MainActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "短信发送失败", Toast.LENGTH_SHORT).show();
        }

    }

    /*向服务提供商发送信息*/
    public void seedMessageToAim(){
        if(true){
            SmsManager smsManager = SmsManager.getDefault();
            Intent sentIntent = new Intent("SENT_SMS_ACTION");
            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sentIntent, 0);
            smsManager.sendTextMessage(aimPhoneNumData.toString(), null, messageContentData.toString(), pi, null);
            Toast.makeText(MainActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "短信发送失败", Toast.LENGTH_SHORT).show();
        }
    }

    /*广播接收器*/
   /* class SendStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                // 短信发送成功
                Toast.makeText(context, "Send succeeded", Toast.LENGTH_LONG).show();
            } else {
                // 短信发送失败
                Toast.makeText(context, "Send failed", Toast.LENGTH_LONG).show();
            }
        }
    }*/

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus"); // 提取短信消息
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String address = messages[0].getOriginatingAddress(); // 获取发送方号码
            String fullMessage = "";
            for (SmsMessage message : messages) {
                fullMessage += message.getMessageBody(); // 获取短信内容
            }
        }

    }
}
