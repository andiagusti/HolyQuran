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

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aracssoftware.quran.R;

public class VerseAdapter extends CursorAdapter {

	private Cursor mCursor;
	private Context mContext;
	private final LayoutInflater mInflater;

	public VerseAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		TextView txtVerseText = (TextView) view.findViewById(R.id.txtVerseText);
		TextView txtNum = (TextView) view.findViewById(R.id.txtNum);
		ImageView imgMark = (ImageView) view.findViewById(R.id.imgVerseMark);
		ImageView imgNote = (ImageView) view.findViewById(R.id.imgVerseNote);

		txtVerseText.setText(cursor.getString(cursor.getColumnIndex("text")));
		txtNum.setText(cursor.getString(cursor.getColumnIndex("verseId")));

		boolean isMarked = cursor.getInt(cursor.getColumnIndex("marked")) == 1;
		boolean hasNote = cursor.getInt(cursor.getColumnIndex("hasNote")) == 1;

		if (isMarked) {
			imgMark.setVisibility(View.VISIBLE);
		} else {
			imgMark.setVisibility(View.GONE);
		}


		if (hasNote) {
			imgNote.setVisibility(View.VISIBLE);
		} else {
			imgNote.setVisibility(View.GONE);
		}

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = mInflater.inflate(R.layout.verses_list_item, parent,
				false);
		return view;
	}

}


