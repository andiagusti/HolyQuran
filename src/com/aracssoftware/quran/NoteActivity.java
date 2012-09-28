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
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.QuranItem;

public class NoteActivity extends Activity {
	DatabaseHelper databaseH;
	Context context;
	QuranItem item;
	int surahId, verseId, lang, trans;
	boolean fromVerse;
	String[][] strings = {
			{ "← Surəyə qayıt – ", "← Ayəyə qayıt – ", "Saxla", "Ləğv et" },
			{ "← rus: Surəyə qayıt", "← rus: Ayəyə qayıt", "rus: Saxla",
					"rus: Ləğv et" } };

	@Override
	protected void onDestroy() {
		super.onDestroy();
		databaseH.close();
	}

	boolean hadNote = true;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);
		context = this;
		databaseH = new DatabaseHelper(this);

		try {
			intent = getIntent();
			Bundle b = intent.getExtras();
			surahId = b.getInt("surahId");
			verseId = b.getInt("verseId");
			lang = b.getInt("lang");
			trans = b.getInt("trans");
			// fromVerse = b.getBoolean("fromVerse");

			item = databaseH.getItem(trans, surahId, verseId);

			Typeface fontEvo = Typeface.createFromAsset(getAssets(), "Evo.otf");

			final EditText editNote = (EditText) findViewById(R.id.editNote);
			editNote.setTypeface(fontEvo);
			Button btnSave = (Button) findViewById(R.id.btnSave);
			btnSave.setTypeface(fontEvo);
			Button btnDiscard = (Button) findViewById(R.id.btnDiscard);
			btnDiscard.setTypeface(fontEvo);
			Button btnBack = (Button) findViewById(R.id.btnBack);
			btnBack.setTypeface(fontEvo);

			if (verseId == 0) {
				btnBack.setText(strings[lang][0] + " : "
						+ String.valueOf(surahId));
				btnBack.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent SurahIntent = new Intent(context,
								VersesActivity.class);
						SurahIntent.putExtra("lang", lang);
						SurahIntent.putExtra("trans", trans);
						SurahIntent.putExtra("surahId", surahId);
						startActivity(SurahIntent);
						finish();
					}
				});
			} else {
				btnBack.setText(strings[lang][1] + String.valueOf(surahId)
						+ " : " + String.valueOf(verseId));
				btnBack.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent VerseIntent = new Intent(context,
								AVerseActivity.class);
						VerseIntent.putExtra("lang", lang);
						VerseIntent.putExtra("trans", trans);
						VerseIntent.putExtra("surahId", surahId);
						VerseIntent.putExtra("verseId", verseId);
						VerseIntent.putExtra("fromSurah", false);
						startActivity(VerseIntent);
						finish();
					}
				});
			}

			if (!item.hasNote) {
				hadNote = false;
				// Toast.makeText(context, "qeyd yoxdur", 1).show();
			} else {
				editNote.setText(item.note);
				// Toast.makeText(context, "qeyd var: "+item.note, 1).show();
				// //item.note
			}

			btnSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					databaseH.setNote(surahId, verseId, true, editNote
							.getText().toString());
					Toast.makeText(context,
							"saxlanildi: " + editNote.getText().toString(), 1)
							.show();
					intent.putExtra("newHasNote", true);
					setResult(RESULT_OK, intent);
					finish();
				}
			});

			btnDiscard.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (hadNote) { // qeyd varsa sil
						databaseH.setNote(surahId, verseId, false, "");
						// Toast.makeText(context, "silindi", 1).show();
					} // yoxdursa geri qayit
					intent.putExtra("newHasNote", false);
					setResult(RESULT_OK, intent);
					finish();
				}
			});

		} catch (Exception e) {
			Toast.makeText(context, e.toString(), 1).show();
		}

	}

}
