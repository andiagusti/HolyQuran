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

package com.aracssoftware.quran.lib;

import java.util.ArrayList;

import com.aracssoftware.quran.AVerseActivity;
import com.aracssoftware.quran.NoteActivity;
import com.aracssoftware.quran.R;
import com.aracssoftware.quran.VersesActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesAdapter extends CursorAdapter {
	
	private Context context;
	Cursor c;
	int lang, trans;
	Typeface fontEvo;
	LayoutInflater mInflater;

	public NotesAdapter(Context context, Cursor c, int lang, int trans) {
		super(context, c);
		this.lang = lang;
		this.trans = trans;
		this.context = context;
		fontEvo = Typeface.createFromAsset(context.getAssets(), "Evo.otf");
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		TextView txtId = (TextView) view.findViewById(R.id.txtId);
		TextView txtNote = (TextView) view.findViewById(R.id.txtNameOrText);
		
		final int verseId = cursor.getInt(cursor.getColumnIndex("verseId"));
		final int surahId = cursor.getInt(cursor.getColumnIndex("surahId"));
		String note = cursor.getString(cursor.getColumnIndex("note"));
		
		if (verseId == 0) {
			txtId.setText(String.valueOf(surahId));
			txtNote.setText(note);
		} else {
			txtId.setText(String.valueOf(surahId)+", "+String.valueOf(verseId));
			txtNote.setText(note);
		}
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(context, NoteActivity.class);
				in.putExtra("lang", lang);
				in.putExtra("trans", trans);
				in.putExtra("surahId", surahId);
				in.putExtra("verseId", verseId);
				in.putExtra("fromSurah", false);
				context.startActivity(in);
			}
		});
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.single_item, parent, false);
	}
	
}
