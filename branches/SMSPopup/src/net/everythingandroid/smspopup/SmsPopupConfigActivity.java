package net.everythingandroid.smspopup;

import net.everythingandroid.smspopup.preferences.AppEnabledCheckBoxPreference;
import net.everythingandroid.smspopup.preferences.ButtonListPreference;
import net.everythingandroid.smspopup.preferences.QuickReplyCheckBoxPreference;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.cxl.smspopup.R;
import com.cxl.smspopup.R.string;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class SmsPopupConfigActivity extends PreferenceActivity implements UpdatePointsNotifier {
	private static final int DIALOG_DONATE = Menu.FIRST;
	private Preference donateDialogPref = null;
	private QuickReplyCheckBoxPreference quickReplyPref;
	private ButtonListPreference button1;
	private ButtonListPreference button2;
	private ButtonListPreference button3;

	Preference pointsTextView;
	Preference offersButton;
	Preference ownsButton;
	String displayText;
	boolean update_text = false;
	int currentPointTotal = 0;//当前积分
	public static final int Pref_Additional_Require_Point = 80;
	public static final int QuickMessage_Require_Point = 150;
	private static boolean Has_Pref_Additional_Point = false;//是否达到积分
	private static boolean Has_QuickMessage_Require_Point = false;//是否达到积分

	PreferenceScreen prefAdditional;
	PreferenceScreen quickmessages;


	final Handler mHandler = new Handler();

	@Override
	protected void onDestroy() {
		AppConnect.getInstance(this).finalize();
		super.onDestroy();
	}

	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				if (update_text) {
					//					pointsTextView.setText(displayText);
					pointsTextView.setTitle(displayText);
					update_text = false;
				}
			}
		}
	};

	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 *
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {
		currentPointTotal = pointTotal;
		if (currentPointTotal >= Pref_Additional_Require_Point) {
			Has_Pref_Additional_Point = true;
		}
			
		if (currentPointTotal >= QuickMessage_Require_Point) {
			Has_QuickMessage_Require_Point = true;
		}
		update_text = true;
		displayText = currencyName + ": " + pointTotal;
		mHandler.post(mUpdateResults);
	}

	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 *
	 * @param error
	 *            请求失败的错误信息
	 */

	public void getUpdatePointsFailed(String error) {
		currentPointTotal = 0;
		update_text = true;
		displayText = error;
		mHandler.post(mUpdateResults);
	}

	private void showMyDialog(int requirePoint) {
		String settingString = requirePoint==Pref_Additional_Require_Point?"【更多设置】":"【预设信息】";
		new AlertDialog.Builder(SmsPopupConfigActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + currentPointTotal)
				.setMessage(
						"【温馨提示:】只要积分满足" + requirePoint + "，本程序的"+settingString+"功能就可以永久使用！！ 您当前的积分不足" + requirePoint
								+ "，无法使用"+settingString+"功能。\n【免费获得积分方法:】请点击确认键进入推荐下载列表 , 下载并安装软件获得相应积分。")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(SmsPopupConfigActivity.this).showOffers(SmsPopupConfigActivity.this);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						//						finish();
					}
				}).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		// 连接服务器. 应用启动时调用(为了统计准确性，此句必须填写).
		AppConnect.getInstance(this);
		pointsTextView = findPreference(getString(R.string.PointsTextView_key));
		offersButton = findPreference(getString(R.string.OffersButton_key));
		offersButton.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference arg0) {
				// 显示推荐安装程序（Offer）.
				AppConnect.getInstance(SmsPopupConfigActivity.this).showOffers(SmsPopupConfigActivity.this);
				return false;
			}
		});
		ownsButton = findPreference(getString(R.string.OwnsButton_key));
		ownsButton.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference arg0) {
				// 显示自家应用列表.
				AppConnect.getInstance(SmsPopupConfigActivity.this).showMore(SmsPopupConfigActivity.this);
				return false;
			}
		});
//		AppConnect.getInstance(SmsPopupConfigActivity.this).getPoints(SmsPopupConfigActivity.this);

		//更多设置
		prefAdditional = (PreferenceScreen) findPreference("pref_additional_key");
		//预设信息
		quickmessages = (PreferenceScreen) findPreference(getString(R.string.quickmessages_key));
		//	    prefAdditional.setEnabled(false);

		//		prefAdditional.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
		//			
		//			public boolean onPreferenceChange(Preference preference, Object newValue) {
		//				if (!Has_Pref_Additional_Point) {
		//					// 先获取当前积分
		//					AppConnect.getInstance(SmsPopupConfigActivity.this).getPoints(SmsPopupConfigActivity.this);
		//					if (currentPointTotal < Pref_Additional_Require_Point) {
		//						showMyDialog(Pref_Additional_Require_Point);
		//						return true;
		//					} else {
		//						return false;
		//					}
		//				}
		//				return false;
		//			}
		//		});
		
//		prefAdditional.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//
//			public boolean onPreferenceClick(Preference preference) {
//				prefAdditional.setDefaultValue("dddd");
//				if (!Has_Pref_Additional_Point) {
//					// 先获取当前积分
//					AppConnect.getInstance(SmsPopupConfigActivity.this).getPoints(SmsPopupConfigActivity.this);
//					if (currentPointTotal < Pref_Additional_Require_Point) {
//						showMyDialog(Pref_Additional_Require_Point);
//						return true;
//					} else {
//						return true;
//					}
//				}
//				return true;
//			}
//		});

		//Try and find app version number
		String version;
		PackageManager pm = this.getPackageManager();
		try {
			//Get version number, not sure if there is a better way to do this
			version = " v" + pm.getPackageInfo(SmsPopupConfigActivity.class.getPackage().getName(), 0).versionName;
		} catch (NameNotFoundException e) {
			version = "";
		}

		//    // Set the version number in the about dialog preference
		//    final DialogPreference aboutPref =
		//      (DialogPreference) findPreference(getString(R.string.pref_about_key));
		//    aboutPref.setDialogTitle(getString(R.string.app_name) + version);
		//    aboutPref.setDialogLayoutResource(R.layout.about);
		//
		//    // Set the version number in the email preference dialog
		//    final EmailDialogPreference emailPref =
		//      (EmailDialogPreference) findPreference(getString(R.string.pref_sendemail_key));
		//    emailPref.setVersion(version);

		// Set intent for contact notification option
		final PreferenceScreen contactsPS = (PreferenceScreen) findPreference(getString(R.string.contacts_key));
		contactsPS.setIntent(new Intent(this, net.everythingandroid.smspopup.ConfigContactsActivity.class));

		// Set intent for quick message option
		final PreferenceScreen quickMessagePS = (PreferenceScreen) findPreference(getString(R.string.quickmessages_key));
		quickMessagePS.setIntent(new Intent(this, net.everythingandroid.smspopup.ConfigPresetMessagesActivity.class));

		// Button 1 preference
		button1 = (ButtonListPreference) findPreference(getString(R.string.pref_button1_key));
		button1.refreshSummary();
		button1.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				quickReplyPref.setChecked(isQuickReplyActive((String) newValue, button2.getValue(), button3.getValue()));
				updateReplyTypePref((String) newValue, button2.getValue(), button3.getValue());
				return true;
			}
		});

		// Button 2 preference
		button2 = (ButtonListPreference) findPreference(getString(R.string.pref_button2_key));
		button2.refreshSummary();
		button2.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				quickReplyPref.setChecked(isQuickReplyActive((String) newValue, button1.getValue(), button3.getValue()));
				updateReplyTypePref((String) newValue, button1.getValue(), button3.getValue());
				return true;
			}
		});

		// Button 3 preference
		button3 = (ButtonListPreference) findPreference(getString(R.string.pref_button3_key));
		button3.refreshSummary();
		button3.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				quickReplyPref.setChecked(isQuickReplyActive((String) newValue, button1.getValue(), button2.getValue()));
				updateReplyTypePref((String) newValue, button1.getValue(), button2.getValue());
				return true;
			}
		});

		// Quick Reply checkbox preference
		quickReplyPref = (QuickReplyCheckBoxPreference) findPreference(getString(R.string.pref_quickreply_key));

		quickReplyPref.setChecked(isQuickReplyActive(button1.getValue(), button2.getValue(), button3.getValue()));

		// Refresh reply type pref
		updateReplyTypePref(button1.getValue(), button2.getValue(), button3.getValue());

		/*
		 * This is a really manual way of dealing with this, but I didn't think it was worth
		 * spending the time to make it more generic.  This will basically look through the active
		 * buttons and switch any Reply buttons to Quick Reply buttons when enabling and the opposite
		 * when disabling.
		 */
		quickReplyPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				int button1val = Integer.valueOf(button1.getValue());
				int button2val = Integer.valueOf(button2.getValue());
				int button3val = Integer.valueOf(button3.getValue());

				int count = 0;

				if (button1.isReplyButton())
					count++;
				if (button2.isReplyButton())
					count++;
				if (button3.isReplyButton())
					count++;

				if (count > 1) {
					Toast.makeText(SmsPopupConfigActivity.this, R.string.pref_quickreply_bothreplybuttons,
							Toast.LENGTH_LONG).show();
					return false;
				} else if (count == 0) {
					Toast.makeText(SmsPopupConfigActivity.this, R.string.pref_quickreply_noreplybuttons,
							Toast.LENGTH_LONG).show();
					return false;
				}

				if (Boolean.FALSE == newValue) {

					// Quick Reply should be turned off
					if (button1val == ButtonListPreference.BUTTON_QUICKREPLY) {
						button1.setValue(String.valueOf(ButtonListPreference.BUTTON_REPLY));
					} else if (button2val == ButtonListPreference.BUTTON_QUICKREPLY) {
						button2.setValue(String.valueOf(ButtonListPreference.BUTTON_REPLY));
					} else if (button3val == ButtonListPreference.BUTTON_QUICKREPLY) {
						button3.setValue(String.valueOf(ButtonListPreference.BUTTON_REPLY));
					}
					button1.refreshSummary();
					button2.refreshSummary();
					button3.refreshSummary();

					return true;
				} else if (Boolean.TRUE == newValue) {

					// Quick Reply should be turned on
					if (button1val == ButtonListPreference.BUTTON_REPLY) {
						button1.setValue(String.valueOf(ButtonListPreference.BUTTON_QUICKREPLY));
					} else if (button2val == ButtonListPreference.BUTTON_REPLY) {
						button2.setValue(String.valueOf(ButtonListPreference.BUTTON_QUICKREPLY));
					} else if (button3val == ButtonListPreference.BUTTON_REPLY) {
						button3.setValue(String.valueOf(ButtonListPreference.BUTTON_QUICKREPLY));

					}
					button1.refreshSummary();
					button2.refreshSummary();
					button3.refreshSummary();

					return true;
				}

				return false;
			}
		});

		// Donate dialog preference
		//    donateDialogPref = findPreference(getString(R.string.pref_donate_key));
		//    if (donateDialogPref != null) {
		//      donateDialogPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
		//        public boolean onPreferenceClick(Preference preference) {
		//          SmsPopupConfigActivity.this.showDialog(DIALOG_DONATE);
		//          return true;
		//        }
		//      });
		//    }

		// Split long messages preference (for some CDMA carriers like Verizon)
		CheckBoxPreference splitLongMessagesPref = (CheckBoxPreference) findPreference(getString(R.string.pref_split_message_key));

		// This pref is only shown for CDMA phones
		if (splitLongMessagesPref != null) {
			TelephonyManager mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			if (mTM.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
				PreferenceCategory quickreplyPrefCategory = (PreferenceCategory) findPreference(getString(R.string.pref_quickreply_cat_key));
				quickreplyPrefCategory.removePreference(splitLongMessagesPref);
				splitLongMessagesPref = null;
			}
		}

		// Opening and closing the database will trigger the update or create
		// TODO: this should be done on a separate thread to prevent "not responding" messages
		SmsPopupDbAdapter mDbAdapter = new SmsPopupDbAdapter(this);
		mDbAdapter.open(true); // Open database read-only
		mDbAdapter.close();

		// Eula.show(this);

		//    for (int i=0; i<1000; i++) {
		//      new SmsMessageSender(this, new String[] {"12345"}, "message " + i, 10).sendMessage();
		//    }
	}

	@Override
	protected void onResume() {
		AppConnect.getInstance(SmsPopupConfigActivity.this).getPoints(SmsPopupConfigActivity.this);
		super.onResume();

		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		// Donate Dialog
		if (donateDialogPref != null) {
			boolean donated = myPrefs.getBoolean(this.getString(R.string.pref_donated_key), false);
			// boolean donated = true;
			if (donated) {
				PreferenceCategory otherPrefCategory = (PreferenceCategory) findPreference(getString(R.string.pref_other_key));
				otherPrefCategory.removePreference(donateDialogPref);
				donateDialogPref = null;
			}
		}

		/*
		 * This is quite hacky - in case the app was enabled or disabled externally (by
		 * ExternalEventReceiver) this will refresh the checkbox that is visible to the user
		 */
		AppEnabledCheckBoxPreference mEnabledPreference = (AppEnabledCheckBoxPreference) findPreference(getString(R.string.pref_enabled_key));

		boolean enabled = myPrefs.getBoolean(getString(R.string.pref_enabled_key), true);
		mEnabledPreference.setChecked(enabled);

		// If enabled, send a broadcast to disable other SMS Popup apps
		if (enabled) {
			SmsPopupUtils.disableOtherSMSPopup(this);
		}
		prefAdditional.setEnabled(true);
		prefAdditional.setEnabled(true);
		if (currentPointTotal < Pref_Additional_Require_Point) {
			prefAdditional.setEnabled(false);
			showMyDialog(Pref_Additional_Require_Point);
		}else if (currentPointTotal < QuickMessage_Require_Point) {
//			quickmessages.setEnabled(false);
//			showMyDialog(QuickMessage_Require_Point);
		}
			
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case DIALOG_DONATE:
			LayoutInflater factory = getLayoutInflater();
			final View donateView = factory.inflate(R.layout.donate, null);

			Button donateMarketButton = (Button) donateView.findViewById(R.id.DonateMarketButton);
			donateMarketButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(SmsPopupUtils.DONATE_MARKET_URI);
					SmsPopupConfigActivity.this.startActivity(Intent.createChooser(i,
							getString(R.string.pref_donate_title)));
				}
			});

			Button donatePaypalButton = (Button) donateView.findViewById(R.id.DonatePaypalButton);
			donatePaypalButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(SmsPopupUtils.DONATE_PAYPAL_URI);
					SmsPopupConfigActivity.this.startActivity(i);
				}
			});

			return new AlertDialog.Builder(this).setIcon(R.drawable.smspopup_icon).setTitle(R.string.pref_donate_title)
					.setView(donateView).setPositiveButton(android.R.string.ok, null).create();
		}
		return super.onCreateDialog(id);
	}

	/*
	 * Quick method to work out if Quick Reply is active or not (to toggle the pref)
	 */
	private boolean isQuickReplyActive(String val1, String val2, String val3) {
		if (Integer.valueOf(val1) == ButtonListPreference.BUTTON_QUICKREPLY
				|| Integer.valueOf(val2) == ButtonListPreference.BUTTON_QUICKREPLY
				|| Integer.valueOf(val3) == ButtonListPreference.BUTTON_QUICKREPLY) {
			return true;
		}
		return false;
	}

	/*
	 * Updates reply-type preference based on the value passed
	 */
	private void updateReplyTypePref(String val1, String val2, String val3) {
		SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor settings = mPrefs.edit();

		if (Integer.valueOf(val1) == ButtonListPreference.BUTTON_REPLY_BY_ADDRESS
				|| Integer.valueOf(val2) == ButtonListPreference.BUTTON_REPLY_BY_ADDRESS
				|| Integer.valueOf(val3) == ButtonListPreference.BUTTON_REPLY_BY_ADDRESS) {
			settings.putBoolean(getString(R.string.pref_reply_to_thread_key), false);
			//      if (Log.DEBUG) Log.v("Reply to address set");
		} else {
			settings.putBoolean(getString(R.string.pref_reply_to_thread_key), true);
			//      if (Log.DEBUG) Log.v("Reply to threadId set");
		}

		settings.commit();
	}

}