package com.aracssoftware.quran;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.widget.TextView;

import com.aracssoftware.quran.MainActivity;
import com.aracssoftware.quran.R;
import com.aracssoftware.quran.R.layout;
public class SplashScreenActivity extends Activity {
	public static final String TAG = "SplashScreenActivity";
	static LoaderAsyncTask loader = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		//TextView text = (TextView) findViewById(R.id.splash_text);
		
		if (loader != null)
			return;
		loader = new LoaderAsyncTask();
		loader.execute();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (loader != null && loader.getStatus() == Status.RUNNING)
			loader.cancel(true);
		loader = null;
	}
	
	class LoaderAsyncTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			// Load data...
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			//
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// add loaded data to storage list
			startMain(null);
		}
	}

	
	void startMain(Bundle savedInstanceState) {
		if (SplashScreenActivity.this != null)
			SplashScreenActivity.this.finish();
		loader = null;
	    startActivity(new Intent(getApplicationContext(), MainActivity.class));
	}
	
	public static String getVersionApp(Context context) {
        String sVersion = "?";
        PackageInfo pInfo = null;
        try {
        	pInfo = context.getPackageManager().getPackageInfo(
        			context.getPackageName(), PackageManager.GET_META_DATA);
        	sVersion = pInfo.versionName;
        } 
        catch (NameNotFoundException e) {
        	e.printStackTrace();
        }    
        return sVersion;
	}
}