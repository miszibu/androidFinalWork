package com.example.zibu.zibumessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zibu on 2016/12/4.
 */

public class SettingActivity extends AppCompatActivity {

    private Button setting;
    private Button historyMessage;
    private EditText phoneNum;
    private EditText seedMessageNum;
    private EditText aimPhoneNum;
    private EditText messageContent;
    private Button sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        historyMessage = (Button)findViewById(R.id.historyMessage);
        setting = (Button)findViewById(R.id.setting);
        sure = (Button)findViewById(R.id.sure);
        phoneNum = (EditText)findViewById(R.id.phoneNum);
        aimPhoneNum = (EditText)findViewById(R.id.aimPhoneNum);
        messageContent = (EditText)findViewById(R.id.messageContent);
        seedMessageNum = (EditText)findViewById(R.id.seedMessageNum);

        /*获取share0dperference中的数据*/
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int seedMessageNumData = pref.getInt("seedMessageNum", 0);
        String phoneNumData = pref.getString("phoneNum","输入短信发送的手机号码");
        String aimPhoneNumData = pref.getString("aimPhoneNum","10010");
        String messageContentData = pref.getString("messageContent","mm");
        seedMessageNum.setText(String.valueOf(seedMessageNumData)+'条');
        aimPhoneNum.setText(aimPhoneNumData);
        messageContent.setText(messageContentData);
        phoneNum.setText(phoneNumData);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "已经在设置页面了",
                        Toast.LENGTH_SHORT).show();
            }
        });

        historyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*存入SharedPreferences*/
                SharedPreferences.Editor editor = getSharedPreferences("data",
                        MODE_PRIVATE).edit();
               // editor.putInt("seedMessageNum", 0);
                editor.putString("aimPhoneNum", aimPhoneNum.getText().toString());
                editor.putString("messageContent", messageContent.getText().toString());
                editor.putString("phoneNum", phoneNum.getText().toString());
                editor.commit();
                Toast.makeText(SettingActivity.this, "修改设置成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
