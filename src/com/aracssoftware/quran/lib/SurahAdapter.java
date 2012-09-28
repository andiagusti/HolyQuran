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

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aracssoftware.quran.R;

public class SurahAdapter extends CursorAdapter {

	private final LayoutInflater mInflater;

	public SurahAdapter(Context context, Cursor cursor) {
		super(context, cursor, true);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {

		TextView txtSurahName = (TextView) view.findViewById(R.id.txtSurahName);
		ImageView imgMark = (ImageView) view.findViewById(R.id.imgMark);
		ImageView imgNote = (ImageView) view.findViewById(R.id.imgNote);

		txtSurahName.setText(cursor.getString(cursor.getColumnIndex("text")));

		boolean isMarked = cursor.getInt(cursor.getColumnIndex("marked")) == 1 ? true
				: false;
		// TODO note ile bagli duzelis et
		boolean hasNote = cursor.getInt(cursor.getColumnIndex("hasNote")) == 0 ? false
				: true;

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
		return mInflater.inflate(R.layout.surah_list_item, parent, false);
	}

}
