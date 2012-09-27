package com.aracssoftware.quran;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.SurahAdapter;
import com.aracssoftware.quran.lib.SurahModel;

public class SurahsActivity extends Activity {

	ListView listSurahs;
	DatabaseHelper databaseH;
	String[][] strings = {
			{ "Qurani-Kərim surələri", "Axtarış", "Axtar", "İmtina" },
			{ "Суры Священного Корана", "Поиск", "Найти",
					"Отмена" } };

	@Override
	protected void onDestroy() {
		super.onDestroy();
		databaseH.close();
	}

	Context context;
	Cursor cSurahs;
	SurahAdapter surahsAdapter;
	int lang, trans;
	ArrayList<SurahModel> surahList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surahs);
		databaseH = new DatabaseHelper(this);
		context = this;
		lang = getIntent().getIntExtra("lang", -1);
		trans = getIntent().getIntExtra("trans", -1);

		final Typeface fontEvo = Typeface.createFromAsset(getAssets(),
				"Evo.otf");
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(strings[lang][0]);
		txtTitle.setTypeface(fontEvo);

		listSurahs = (ListView) findViewById(R.id.listSurahs);
		listSurahs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				Intent VersesIntent = new Intent(context, VersesActivity.class);
				VersesIntent.putExtra("lang", lang);
				VersesIntent.putExtra("trans", trans);
				VersesIntent.putExtra("surahId", pos + 1);
				startActivityForResult(VersesIntent, 1);

			}
		});

		Button btnSearchQuran = (Button) findViewById(R.id.btnSearchQuran);
		btnSearchQuran.setText(strings[lang][1]);
		btnSearchQuran.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				doQuranSearch();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		cSurahs = databaseH.getSurahs(trans);
		surahsAdapter = new SurahAdapter(context, cSurahs);
		listSurahs.setAdapter(surahsAdapter);
	}

	@Override
	public boolean onSearchRequested() {
		doQuranSearch();
		return true;
	}

	private void doQuranSearch() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(strings[lang][1]);
		final EditText editQuery = new EditText(context);
		builder.setView(editQuery);

		builder.setPositiveButton(strings[lang][2],
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent searchIntent = new Intent(context,
								SearchActivity.class);
						searchIntent.putExtra("lang", lang);
						searchIntent.putExtra("trans", trans);
						searchIntent.putExtra("surahId", 0);
						searchIntent.putExtra("query", editQuery.getText()
								.toString());
						startActivity(searchIntent);
					}
				});

		builder.setNegativeButton(strings[lang][3],
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.show();
	}

}