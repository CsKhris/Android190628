package com.cs.basicappandthread190628;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class HandlerActivity extends AppCompatActivity {

    TextView handlerdisplay;
    Button handlerbtn;
    int idx;
    int [] colors = {Color.RED, Color.BLUE, Color.GREEN};

    // 넘겨받은 obj를 Text View에 출력하는 Handler
    Handler hdr = new Handler(){
      @Override
      public void handleMessage(Message msg){
          Integer data = (Integer)msg.obj;
          handlerdisplay.setTextColor(colors[idx%3]);
          handlerdisplay.setText("data" + data);
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        handlerdisplay = (TextView)findViewById(R.id.handlerdisplay);
        handlerbtn = (Button)findViewById(R.id.handlerbtn);

        /*
        handlerbtn.setOnClickListener(view -> {
            Thread th = new Thread(){
                @Override
                public void run(){
                    for(int i=0 ; i<10 ; i=i+1){
                        try{
                            Thread.sleep(1000);

                            // Message 생성
                            Message msg = new Message();

                            // Data 삽입 - 어떤 속성의 값을 바로 대입
                           msg.obj = idx;
                            idx = idx + 1;

                            // Handler에게 Message 전송
                            hdr.sendMessage(msg);

                        }catch (Exception e){

                        }
                    }
                }
            };
            th.start();

        });
        */

        handlerbtn.setOnClickListener(view -> {
            // Handler를 post를 이용하여 호출
            Thread th = new Thread(){
                // 비어있는 Handler 작성
                Handler handler = new Handler();
                public void run(){
                    // 반복문
                    for(int i=0 ; i<10 ; i=i+1){
                        try{

                            // 내일 아침 9시 30분
                            Calendar cs = new GregorianCalendar(
                                    2019, 5, 28, 16, 55, 00);
                            long nextDay = cs.getTimeInMillis();

                            Thread.sleep(1000);
                            handler.postAtTime(new Runnable() {
                                public void run(){
                                    handlerdisplay.setText(idx + "");
                                }
                            }, nextDay);
                            idx = idx + 1;
                        }catch (Exception e){}
                    }
                }
            };
            th.start();
        });
    }
}
