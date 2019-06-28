package com.cs.basicappandthread190628;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView resultView;
    ImageView resultitemview;

    Button phonebtn, contactbtn, camerabtn, mapbtn, chromebtn;

    // Image의 크기를 저장할 변수
    int requestWidth, requestHeight;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = (TextView)findViewById(R.id.resultview);
        resultitemview = (ImageView)findViewById(R.id.resultitemview);

        phonebtn = (Button)findViewById(R.id.phonebtn);
        contactbtn = (Button)findViewById(R.id.contactbtn);
        camerabtn = (Button)findViewById(R.id.camerabtn);
        mapbtn = (Button)findViewById(R.id.mapbtn);
        chromebtn = (Button)findViewById(R.id.chromebtn);

        // 크기 가져오기
        requestWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        requestHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);

        // 연락처 App 호출
        contactbtn.setOnClickListener(view->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

            // 다음 Activity를 화면에 출력하고, Data를 돌려 받을 수는 없습니다.
            startActivity(intent);

            // Data를 넘겨 받는 형태로 다음 Activity 출력 - 구분하는 번호를 만들어주어야 합니다.
            startActivityForResult(intent, 100);
        });

        // Camera App 호출
        camerabtn.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 200);
        });

        // 지도 App 호출
        mapbtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662, 126.9779"));
            startActivity(intent);
        });

        // Chrome App 호출
        chromebtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apple.com"));
            startActivity(intent);
        });

        // 전화 App 호출 - 실시간으로 권한을 확인
        // 권한이 없으면 권한을 확인하는 대화상자 출력
        phonebtn.setOnClickListener(view -> {
            if(ContextCompat.checkSelfPermission(
                    MainActivity.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3539-3379"));
                startActivity(intent);
            }else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        });

    }

    // startActiviryForResult로 다음 Activity를 출력하였을 때 하위 Activity가 사라지면 호출 되는 Method
    @Override
    public void  onActivityResult(int requestCode, int responseCode, Intent intent){
        if(requestCode == 100){

            // 선택한 전화번호의 문자열을 출력, 마지막 부분이 id 입니다.
            // 이 id를 이용하여 CotentPeovider를 이용하면 자세한 정보를 찾아올 수 있습니다.
            String result = intent.getDataString();
            resultView.setText(result);
        }else if(requestCode == 200){

            // Camera App에서 넘겨 받은 Image를 Image View에 출력
            Bitmap bitmap = (Bitmap)intent.getExtras().get("data");
            resultitemview.setImageBitmap(bitmap);
        }
    }

    // Activity가 제거되기 직전에 호출되는 Method
    // 저장할 Data가 있으면 이 Method의 Bundle에 저장
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        //Data 저장
        bundle.putString(
                "data", resultView.getText().toString());
        // Android에서는 직렬화하는 Interface 2개
        // Java에서 사용하는 Serializable 	// Android에서 추가된 Parclable
        if(bitmap != null){
            bundle.putParcelable("image", bitmap);
        }
    }

    //화면을 복원하기 위한 Method
    @Override
    public void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        // Data 가져오기
        String data = bundle.getString("data");
        // Data를 다시 출력
        resultView.setText(data);

        Object obj = bundle.getParcelable("image");
        if(obj != null){
            bitmap = (Bitmap)bundle.getParcelable("image");
            resultitemview.setImageBitmap(bitmap);
        }
    }
}