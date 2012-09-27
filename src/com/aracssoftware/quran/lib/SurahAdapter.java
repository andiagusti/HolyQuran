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
