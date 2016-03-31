package com.vishnu.notificationmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by VISHNUPRASAD on 30/03/16.
 */
public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogactivity);
        getWindow().setLayout(500,500);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent() != null && getIntent().getData() != null)
        System.out.println(getIntent().getData().getSchemeSpecificPart());
        ((TextView)findViewById(R.id.dialogtext)).setText("You just installed "+getIntent().getData().getSchemeSpecificPart()+". Would you like to block notifications from this app");
    }
}
