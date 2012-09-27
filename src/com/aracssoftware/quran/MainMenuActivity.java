package com.aracssoftware.quran;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
	Context context;
	int lang, trans;
	Button btnReadQuran, btnNotes, btnSettings;
	TextView txtAppName;
	String[][] strings = {
			{ "Quran Oxu", "Qeydlərə bax", "Nizamlar", "Qurani-Kərim" },
			{ "Читайте Коран", "Посмотрите на ноты", "Настройки",
					"Священный Коран" } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		context = this;

		lang = getIntent().getIntExtra("lang", 0);
		trans = getIntent().getIntExtra("trans", 0);

		final Typeface fontEvo = Typeface.createFromAsset(getAssets(),
				"Evo.otf");

		btnReadQuran = (Button) findViewById(R.id.btnReadQuran);
		btnNotes = (Button) findViewById(R.id.btnNotes);
		btnSettings = (Button) findViewById(R.id.btnSettings);
		txtAppName = (TextView) findViewById(R.id.txtAppName);

		btnReadQuran.setText(strings[lang][0]);
		btnReadQuran.setTypeface(fontEvo);
		btnNotes.setText(strings[lang][1]);
		btnNotes.setTypeface(fontEvo);
		btnSettings.setText(strings[lang][2]);
		btnSettings.setTypeface(fontEvo);
		txtAppName.setTypeface(fontEvo);
		txtAppName.setText(strings[lang][3]);

		btnReadQuran.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent ReadMenuIntent = new Intent(context,
						ReadMenuActivity.class);
				ReadMenuIntent.putExtra("lang", lang);
				ReadMenuIntent.putExtra("trans", trans);
				startActivity(ReadMenuIntent);
			}
		});

		btnNotes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent NotesIntent = new Intent(context, NotesActivity.class);
				NotesIntent.putExtra("lang", lang);
				NotesIntent.putExtra("trans", trans);
				startActivity(NotesIntent);
			}
		});

		btnSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent SettingsIntent = new Intent(context,
						SettingsActivity.class);
				SettingsIntent.putExtra("lang", lang);
				startActivity(SettingsIntent);
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		lang = Integer.valueOf(prefs.getString("lang", "0"));
		trans = Integer.valueOf(prefs.getString("trans", "0"));

		btnReadQuran.setText(strings[lang][0]);
		btnNotes.setText(strings[lang][1]);
		btnSettings.setText(strings[lang][2]);
		txtAppName.setText(strings[lang][3]);
	}

}
