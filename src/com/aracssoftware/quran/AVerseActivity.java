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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.QuranItem;

public class AVerseActivity extends Activity {

	Context context;
	int lang, trans, surahId, verseId;
	boolean hasMark, hasNote, fromSurah;
	Intent verseIntent;
	DatabaseHelper databaseH;
	String verseText, surahName;
	String[][] strings = {{},{}};
	QuranItem verse, surah;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		databaseH.close();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_verse);
		verseIntent = getIntent();
		context = this;

		try {

			databaseH = new DatabaseHelper(this);

			Bundle b = getIntent().getExtras();
			surahId = b.getInt("surahId");
			verseId = b.getInt("verseId");
			fromSurah = b.getBoolean("fromSurah");
			lang = b.getInt("lang");
			trans = b.getInt("trans");
			//hasMark = 
			//hasNote = 
			
			verse = databaseH.getItem(trans, surahId, verseId);
			surah = databaseH.getItem(trans, surahId, 0);
			
			Typeface fontMonotype = Typeface.createFromAsset(context.getAssets(), "Monotype.ttf");
			Typeface fontEvo = Typeface.createFromAsset(context.getAssets(), "Evo.otf");

			TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
			txtTitle.setTypeface(fontEvo);
			TextView txtAztext = (TextView) findViewById(R.id.txtAztext);
			txtAztext.setTypeface(fontMonotype);
			Button btnPrev = (Button) findViewById(R.id.btnPrev);
			btnPrev.setTypeface(fontEvo);
			Button btnNext = (Button) findViewById(R.id.btnNext);
			btnNext.setTypeface(fontEvo);


			
			//surahName = 

			//verseText =

			//title = b.getString("title");

			
			txtTitle.setText(surah.text+", "+String.valueOf(verse.verseId));
			txtAztext.setText(verse.text);

			btnPrev.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					verseIntent.putExtra("command", "nav");
					verseIntent.putExtra("to", verseId - 1);
					setResult(RESULT_OK, verseIntent);
					finish();
				}
			});

			btnNext.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					verseIntent.putExtra("command", "nav");
					verseIntent.putExtra("to", verseId + 1);
					setResult(RESULT_OK, verseIntent);
					finish();
				}
			});

			if (!fromSurah) {
				try {
					View navBar = (View) findViewById(R.id.relativeNav);
					((ViewGroup) navBar.getParent()).removeView(navBar);
					LinearLayout linearContent = (LinearLayout) findViewById(R.id.linearContent);
					MarginLayoutParams lp = (MarginLayoutParams) linearContent
							.getLayoutParams();
					lp.setMargins(0, 0, 0, 0);
					linearContent.setLayoutParams(lp);
				} catch (Exception e) {
					Toast.makeText(context, e.toString(), 1).show();
				}

			}

		} catch (Exception e) {
			Toast.makeText(this, e.toString(), 1).show();
		}

	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, hasMark ? "Nisani gotur" : "Nisanla");
		menu.add(0, 2, 1, "Qeyd yaz");
		menu.add(0, 3, 2, "Paylaş");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			if (hasMark) {
				//databaseH.removeMark(surahId, verseId);
			} else {
				//databaseH.addMark(surahId, verseId);
			}
			hasMark = hasMark ? false : true;
			databaseH.setMark(surahId, verseId, hasMark);
			item.setTitle(hasMark ? "Nisani gotur" : "Nisanla");
			return true;
		case 2:
			Intent noteIntent = new Intent(context, NoteActivity.class);
			noteIntent.putExtra("surahId", surahId);
			noteIntent.putExtra("lang", lang);
			noteIntent.putExtra("verseId", verseId);
			noteIntent.putExtra("trans", trans);
			noteIntent.putExtra("hasNote", verse.hasNote);
			noteIntent.putExtra("fromVerse", true);
			startActivityForResult(noteIntent, 2);
			return true;
		case 3:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, "Qurani Kerim, " + surahName + " : "+ String.valueOf(verseId)
					+ " – \"" + verseText + "\"");
			startActivity(Intent.createChooser(intent, "Paylaşım vasitəsi"));
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		verseIntent.putExtra("command", "scroll");
		verseIntent.putExtra("to", verseId - 1);
		setResult(RESULT_CANCELED, verseIntent);
		super.onBackPressed();
	}

}
