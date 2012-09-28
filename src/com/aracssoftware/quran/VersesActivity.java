/* Copyright (C) 2012  Aracs LLC
 * 
 * This file is part of Holy Quran Android Application.
 * 
 * Holy Quran Android Application is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Holy Quran Android Application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.aracssoftware.quran;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.QuranItem;
import com.aracssoftware.quran.lib.VerseAdapter;

public class VersesActivity extends Activity {

	ListView listVerses;
	DatabaseHelper databaseH;
	Cursor c;
	Context context;
	int surahId, verseCount, lang, trans, lastVerse;
	boolean hasMark, hasNote, fromLast;
	String langS;
	String[][] strings = {
			{ "Nişanla", "Nişanı götür", "Qeyd Yaz", "Axtarış", "Axtar",
					"İmtina" },
			{ "Отметить", "Отменить Отметку", "Написать заметку",
					"Поиск", "Найти", "Отмена" } };

	SharedPreferences settings;
	SharedPreferences.Editor editor;

	String FileName, surahName;
	VerseAdapter verseAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verses);
		databaseH = new DatabaseHelper(this);
		context = this;

		try {
			settings = getSharedPreferences("QuraniKerim", 0);
			editor = settings.edit();

			Bundle b = getIntent().getExtras();
			lang = b.getInt("lang");
			trans = b.getInt("trans");
			fromLast = getIntent().getBooleanExtra("fromLast", false);

			final Typeface fontEvo = Typeface.createFromAsset(getAssets(),
					"Evo.otf");
			TextView txtSurahName = (TextView) findViewById(R.id.txtSurahName);
			txtSurahName.setTypeface(fontEvo);

			if (fromLast) {
				surahId = settings.getInt("surahId", 1);
				lastVerse = settings.getInt("verseId", 0);
			} else {
				surahId = b.getInt("surahId");
			}

			QuranItem surah = databaseH.getItem(trans, surahId, 0);

			hasMark = surah.hasMark;
			hasNote = surah.hasNote;

			txtSurahName.setText(surah.text);

			listVerses = (ListView) findViewById(R.id.listVerses);

			if (fromLast) {
				listVerses.setSelection(lastVerse);
			}

			listVerses.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pos, long id) {
					gotoVerse(pos + 1);
				}
			});

			Button btnSearchSurah = (Button) findViewById(R.id.btnSearchSurah);
			btnSearchSurah.setText(strings[lang][3]);
			btnSearchSurah.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					doSurahSearch();
				}
			});

		} catch (Exception e) {
			Toast.makeText(context, e.toString(), 1).show();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		editor.putInt("surahId", surahId);
		editor.putInt("verseId", listVerses.getFirstVisiblePosition());
		editor.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		databaseH.close();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK) { // verse intent
			String command = data.getStringExtra("command");
			if (command.compareTo("nav") == 0) {
				int to = data.getIntExtra("to", 1);
				if ((to != verseCount + 1) && (to != 0)) {
					try {
						gotoVerse(to);
					} catch (Exception e) {
						Toast.makeText(context, e.toString(), 1).show();
					}
				}

			} // else
		} else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
			String command = data.getStringExtra("command");
			if (command.compareTo("scroll") == 0) {
				int to = data.getIntExtra("to", 1);
				listVerses.setSelection(to);
				// Toast.makeText(context, "geri", 1).show();
			}
		} else if (requestCode == 2 && resultCode == RESULT_OK) { // note intent
			hasNote = data.getBooleanExtra("newHasNote", true);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, hasMark ? strings[lang][1] : strings[lang][0]);
		menu.add(0, 2, 1, strings[lang][2]);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			databaseH.setMark(surahId, 0, !hasMark);
			hasMark = !hasMark;
			item.setTitle(hasMark ? strings[lang][1] : strings[lang][0]);
			return true;
		case 2:
			Intent noteIntent = new Intent(context, NoteActivity.class);
			noteIntent.putExtra("surahId", surahId);
			noteIntent.putExtra("verseId", 0);
			noteIntent.putExtra("lang", lang);
			noteIntent.putExtra("trans", trans);
			startActivityForResult(noteIntent, 2);
			return true;
		}
		return false;
	}

	@Override
	public boolean onSearchRequested() {
		doSurahSearch();
		return true;
	}

	private void doSurahSearch() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(strings[lang][3]);
		final EditText editQuery = new EditText(context);
		builder.setView(editQuery);

		builder.setPositiveButton(strings[lang][4],
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Toast.makeText(context,
						// "axtarish: "+editQuery.getText(), 1).show();
						Intent searchIntent = new Intent(context,
								SearchActivity.class);
						searchIntent.putExtra("lang", lang);
						searchIntent.putExtra("trans", trans);
						searchIntent.putExtra("surahId", surahId);
						searchIntent.putExtra("query", editQuery.getText()
								.toString());
						startActivity(searchIntent);
					}
				});

		builder.setNegativeButton(strings[lang][5],
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.show();
	}

	public void gotoVerse(int verseId) {
		Intent aVerseIntent = new Intent(context, AVerseActivity.class);
		aVerseIntent.putExtra("surahId", surahId);
		aVerseIntent.putExtra("lang", lang);
		aVerseIntent.putExtra("trans", trans);
		aVerseIntent.putExtra("verseId", verseId);
		aVerseIntent.putExtra("fromSurah", true);
		startActivityForResult(aVerseIntent, 1);
	}

	@Override
	protected void onStart() {
		super.onStart();

		c = databaseH.getVerses(trans, surahId);
		verseAdapter = new VerseAdapter(context, c);
		verseCount = c.getCount();
		listVerses.setAdapter(verseAdapter);
	}
}