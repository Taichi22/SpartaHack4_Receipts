package com.example.user.spartahack4_receipts;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.text.format.Time;
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

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements CreateNdefMessageCallback{

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
        nfcAdapter.setNdefPushMessageCallback(this, this);
//        nfcAdapter.setOnNdefPushCompleteCallback(, this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String text = "Beam is working...";
        NdefMessage msg = new NdefMessage(new NdefRecord[]{createMimeRecord("ID    Item    Quantity    Price
1    cookies    1    $5.00
2    sandwich bags    2    $3.25
3    wate bottles    5    $4.25
4    kitkat    2    $2.25
5    lolipop    3    $3.00
6    gingerbread    2    $2.50", text.getBytes())});
        return msg;
    }

    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }
    void processIntent(Intent intent) {
        TextView textView = (TextView) findViewById(R.id.TestText);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        textView.setText(new String(msg.getRecords()[0].getPayload()));
    }

}
