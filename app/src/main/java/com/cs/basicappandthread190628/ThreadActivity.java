package com.cs.basicappandthread190628;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {

    TextView display;
    Button inc;
    int idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        display = (TextView)findViewById(R.id.display);
        inc = (Button)findViewById(R.id.inc);


        inc.setOnClickListener(view -> {

            /*
            for(int i=0 ; i<10 ; i=i+1){
                try{
                    Thread.sleep(1000);
                    display.setText("idx" + idx);
                    idx = idx + 1;
                }catch (Exception e){}
                */

            /*
            // Thread Class를 상속 받은 Class를 이용해서 Thread로 작업 : 예외 발생 후 Crach 됩니다.
            Thread th = new Thread(){
                public void run(){
                    for(int i=0 ; i<10 ; i=i+1){
                        try{
                            Thread.sleep(1000);
                            display.setText("idx : " + idx);
                            idx = idx + 1;
                        }catch (Exception e){}
                    }
                }
            };
            th.start();
            */

            // Runnable Interface 이용
            Runnable r = new Runnable() {
                @Override
                public void run(){
                    for(int i=0 ; i<10 ; i=i+1){
                        try{
                            Thread.sleep(1000);
                            display.setText("idx : " + idx);
                            idx = idx + 1;
                        }catch (Exception e){}
                    }
                }
            };
            Thread th = new Thread();
            th.start();
        });
    }
}
