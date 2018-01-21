package com.example.user.spartahack4_receipts;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    TextView infoText;
    TextView dateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        dateText = (TextView)findViewById(R.id.DateText);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        dateText.setText((CharSequence) currentDateTimeString);
        if (nfcAdapter == null){
            infoText = (TextView) findViewById(R.id.TestText);
            infoText.setText("NFC not available.");
        }

        else{
            infoText = (TextView) findViewById(R.id.TestText);
            infoText.setText("NFC available!");

        }
//        nfcAdapter.setNdefPushMessageCallback(this, this);
//        nfcAdapter.setOnNdefPushCompleteCallback(this, this);
    }
}
