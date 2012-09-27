package com.aracssoftware.quran;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InitActivity extends Activity {
	Context context;
	Handler handler;
	TextView txtProgress;

	String[][] strings = {
			{ "Proqram ilk istifadə üçün hazırlanır. Lütfən bir qədər gözləyin...",
					"Hazırdır!" },
			{ "Программа готовится к первому использованию",
					"Готово!" } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init);
		context = this;

		Typeface fontEvo = Typeface.createFromAsset(getAssets(), "Evo.otf");

		txtProgress = (TextView) findViewById(R.id.txtInitProgress);
		txtProgress.setTypeface(fontEvo);
		txtProgress.setText(strings[0][0] + "\n" + strings[1][0]);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Decompress d = new Decompress();
				d.unzip();
				setResult(RESULT_OK);
				finish();
			}
		}).start();

	}


	public class Decompress {
		private String _location;
		private int progress = 0;

		public Decompress() {
			_location = Environment.getExternalStorageDirectory()
					+ "/Android/data/com.aracssoftware.quran/";
			_dirChecker("");
		}

		public void unzip() {
			try {
				BufferedInputStream bins = new BufferedInputStream(context
						.getAssets().open("qurandb.zip"));
				ZipInputStream zin = new ZipInputStream(bins);
				ZipEntry ze = null;
				while ((ze = zin.getNextEntry()) != null) {

					if (ze.isDirectory()) {
						_dirChecker(ze.getName());
					} else {
						FileOutputStream fout = new FileOutputStream(_location
								+ ze.getName());
						BufferedOutputStream bouts = new BufferedOutputStream(
								fout);
						for (int c = zin.read(); c != -1; c = zin.read()) {
							bouts.write(c);
						}

						zin.closeEntry();
						bouts.close();
					}

					//progress++;
					//updateProgress(progress * 2 / 5);

				}
				zin.close();
			} catch (Exception e) {
			}

		}

		private void _dirChecker(String dir) {
			File f = new File(_location + dir);

			if (!f.isDirectory()) {
				f.mkdirs();
			}
		}
	}

}