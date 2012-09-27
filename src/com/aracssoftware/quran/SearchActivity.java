package com.aracssoftware.quran;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.MarkedAdapter;

public class SearchActivity extends Activity {

	Cursor c;
	ListView listSearch;
	MarkedAdapter adapter;
	DatabaseHelper dbHelper;
	Context context;
	int surahId, trans, lang;
	String query;
	String[][] strings = { { "Axtarış: " }, { "Поиск: " } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marknote);

		this.context = this;
		dbHelper = new DatabaseHelper(context);
		surahId = getIntent().getIntExtra("surahId", 0);
		query = getIntent().getStringExtra("query");
		trans = getIntent().getIntExtra("trans", -1);
		lang = getIntent().getIntExtra("lang", -1);

		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(strings[lang][0] + query);

		if (surahId == 0) {
			c = dbHelper.searchQuran(trans, query);
		} else {
			c = dbHelper.searchSurah(trans, surahId, query);
		}

		adapter = new MarkedAdapter(context, c, lang, trans);
		listSearch = (ListView) findViewById(R.id.listItems);
		listSearch.setAdapter(adapter);

	}

}
