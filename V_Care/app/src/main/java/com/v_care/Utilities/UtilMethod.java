package com.v_care.Utilities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.v_care.R;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class UtilMethod {

	public static ProgressDialog showLoading(ProgressDialog progress, Context context) {
		try {
			if (progress == null) {
				progress = new ProgressDialog(context);
				progress.setMessage("Please wait..");
				progress.setCancelable(true);
			}
			progress.show();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return progress;
	}



	public static void hideLoading(ProgressDialog progress) {
		try {
			if (progress != null) {

				if (progress.isShowing()) {
					progress.dismiss();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDownloading(ProgressBar progress) {
		if (progress != null) {
			progress.setVisibility(View.VISIBLE);
		}
	}

	public static void hideDownloading(ProgressBar progress) {
		if (progress != null) {
			progress.setVisibility(View.GONE);
		}
	}

	public static boolean isNetworkAvailable(Context context) {

		NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity"))
				.getActiveNetworkInfo();
		return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
	}

	public static boolean isStringNullOrBlank(String str) {
		if (str == null) {
			return true;
		} else if (str.equals("null") || str.equals("")) {
			return true;
		}
		return false;
	}

	public static void showToast(String message, Context ctx) {
		try {
			if (ctx != null)
				Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	public static void printToLog(String message) {
		Log.i("Emper App Log ", message);
	}

	public static void hideKeyBoard(Context ct, EditText ed) {
		InputMethodManager imm = (InputMethodManager) ct.getSystemService(Service.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);
	}


}