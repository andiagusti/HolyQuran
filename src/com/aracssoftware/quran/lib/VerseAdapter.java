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


