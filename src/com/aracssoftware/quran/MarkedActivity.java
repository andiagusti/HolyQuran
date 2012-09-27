package com.aracssoftware.quran;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.ItemModel;
import com.aracssoftware.quran.lib.MarkedAdapter;

public class MarkedActivity extends Activity {
	ListView listItems;
	DatabaseHelper databaseH;
	Context context;
	MarkedAdapter markedAdapter;
	int lang, trans;
	Cursor c;
	String[] surahNames = new String[114];
	String[][] strings = { {}, {} };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marknote);

		lang = getIntent().getIntExtra("lang", -1);
		trans = getIntent().getIntExtra("trans", -1);
		this.context = this;

		databaseH = new DatabaseHelper(this);
		Typeface fontEvo = Typeface.createFromAsset(context.getAssets(),
				"Evo.otf");

		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setTypeface(fontEvo);
		txtTitle.setText("Isarelenmisler");

		listItems = (ListView) findViewById(R.id.listItems);
		c = databaseH.getMarkeds(trans);
		markedAdapter = new MarkedAdapter(context, c, lang, trans);
		listItems.setAdapter(markedAdapter);
		
		/*
		listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				ItemModel item = itemList.get(pos);

				if (item.verseId == 0) {
					Intent VersesIntent = new Intent(context,
							VersesActivity.class);
					VersesIntent.putExtra("lang", lang);
					VersesIntent.putExtra("surahId", item.surahId);
					VersesIntent.putExtra("surahName", item.surahName);
					startActivityForResult(VersesIntent, 1);
				} else {
					Intent aVerseIntent = new Intent(context,
							AVerseActivity.class);
					aVerseIntent.putExtra("surahId", item.surahId);
					aVerseIntent.putExtra("lang", lang);
					aVerseIntent.putExtra("fromSurah", false);
					aVerseIntent.putExtra("verseId", item.verseId);
					aVerseIntent.putExtra("title", item.surahName + ", "
							+ String.valueOf(item.verseId));
					startActivityForResult(aVerseIntent, 1);
				}

			}
		});*/

	}

	/*
	 * @Override protected void onDestroy() { super.onDestroy();
	 * databaseH.close(); }
	 */

}
