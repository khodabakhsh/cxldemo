package com.cxl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMSActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		btnSend = (Button) findViewById(R.id.btnSend);
		edtPhoneNo = (EditText) findViewById(R.id.edtPhoneNo);
		edtContent = (EditText) findViewById(R.id.edtContent);

		btnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String phoneNo = edtPhoneNo.getText().toString();
				String message = edtContent.getText().toString();
				if (phoneNo.length() > 0 && message.length() > 0) {
					// call sendSMS to send message to phoneNo
					sendSMS(phoneNo, message);
				} else
					Toast.makeText(getBaseContext(), "Please enter both phone number and message.", Toast.LENGTH_SHORT)
							.show();
			}
		});

		btnContact = (Button) findViewById(R.id.btnContact);

		btnContact.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});

	}

	@Override
	public void onActivityResult(int reqCode, int resCode, Intent data) {

		super.onActivityResult(reqCode, resCode, data);

		switch (reqCode) {

		case (PICK_CONTACT): {

			if (resCode == Activity.RESULT_OK) {

				String name;

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				c.moveToFirst();

				name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				TextView tv;

				tv = (TextView) findViewById(R.id.edtPhoneNo);

				tv.setText(name);

			}

			break;

		}

		}

	}

	private static final int PICK_CONTACT = 500;//自定义数值？
	private Button btnContact;
	private Button btnSend;
	private EditText edtPhoneNo;
	private EditText edtContent;

	private void sendSMS(String phoneNumber, String message) {

		// ---sends an SMS message to another device---
		SmsManager sms = SmsManager.getDefault();
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

		// create the sentIntent parameter
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);

		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

		// register the Broadcast Receivers
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent success actions", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "SMS generic failure actions", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "SMS radio off failure actions", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "SMS null PDU failure actions", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT_SMS_ACTION));
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				Toast.makeText(getBaseContext(), "SMS delivered actions", Toast.LENGTH_SHORT).show();
			}
		}, new IntentFilter(DELIVERED_SMS_ACTION));

		// if message's length more than 70 ,
		// then call divideMessage to dive message into several part ,and call
		// sendTextMessage()
		// else direct call sendTextMessage()
		if (message.length() > 70) {
			ArrayList<String> msgs = sms.divideMessage(message);
			for (String msg : msgs) {
				sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);
			}
		} else {
			sms.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
		}
	}

}
