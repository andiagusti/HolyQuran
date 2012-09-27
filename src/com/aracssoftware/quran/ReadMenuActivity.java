package com.aracssoftware.quran;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReadMenuActivity extends Activity {
	Context context;
	int lang, trans;
	String[][] strings = {
			{ "Bütün Surələr", "Nişanlanmışlar", "Ən son oxuduğum","Qurani-Kərim" },
			{ "Все Суры", "Отмеченные", "Последное прочитанное","Священный Коран" } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_menu);
	
		context = this;

		lang = getIntent().getIntExtra("lang", -1);
		trans = getIntent().getIntExtra("trans", -1);
		
		final Typeface fontEvo = Typeface.createFromAsset(getAssets(), "Evo.otf");
		
		Button btnAllSurahs = (Button) findViewById(R.id.btnAllSurahs);
		btnAllSurahs.setTypeface(fontEvo);
		Button btnMarkeds = (Button) findViewById(R.id.btnMarkeds);
		btnMarkeds.setTypeface(fontEvo);
		Button btnLastRead = (Button) findViewById(R.id.btnLastRead);
		btnLastRead.setTypeface(fontEvo);
		TextView txtAppName = (TextView) findViewById(R.id.txtAppName);
		
		if (txtAppName != null) {
			txtAppName.setTypeface(fontEvo);
			txtAppName.setText(strings[lang][3]);
		}
		

		btnAllSurahs.setText(strings[lang][0]);
		btnMarkeds.setText(strings[lang][1]);
		btnLastRead.setText(strings[lang][2]);

		btnAllSurahs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent SurahsIntent = new Intent(context, SurahsActivity.class);
				SurahsIntent.putExtra("lang", lang);
				SurahsIntent.putExtra("trans", trans);
				startActivity(SurahsIntent);
			}
		});
		
		btnMarkeds.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent MarkedsIntent  = new Intent(context, MarkedActivity.class);
				MarkedsIntent.putExtra("lang", lang);
				MarkedsIntent.putExtra("trans", trans);
				startActivity(MarkedsIntent);
			}
		});
		
		btnLastRead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent LastReadIntent = new Intent(context, VersesActivity.class);
				LastReadIntent.putExtra("fromLast", true);
				LastReadIntent.putExtra("lang", lang);
				LastReadIntent.putExtra("trans", trans);
				startActivity(LastReadIntent);
			}
		});

	}

}
