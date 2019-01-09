package com.lx.baselibrary;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lx.baselibrary.http.httputil.OkHttp3Util;
import com.lx.baselibrary.utils.ToastUtil;
import com.lx.baselibrary.views.ReflushLayout;

public class MainActivity extends AppCompatActivity {
    OkHttp3Util http3Util;
    ReflushLayout rl;
    private boolean isFlash = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        http3Util = new OkHttp3Util(mHandler);

        rl = (ReflushLayout)findViewById(R.id.rl);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                http3Util.doGet("http://10.10.19.139:8081/MobileField/api/getCustomerData.action?customerId=1-HTLEA5&opportunitiesId=1-HTLEAI&versionType=0");
            if (!isFlash) {
                rl.startFlash();
                isFlash = true;
            } else {
                rl.completeFlash();
                isFlash = false;
            }
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();

                    ToastUtil.showToast(MainActivity.this, bundle.getString(http3Util.getParser().getMessageName()));
                    break;
                default:
                    break;
            }
        }
    };
}
