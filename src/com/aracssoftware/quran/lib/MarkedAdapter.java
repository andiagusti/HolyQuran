package com.aracssoftware.quran.lib;

import com.aracssoftware.quran.AVerseActivity;
import com.aracssoftware.quran.R;
import com.aracssoftware.quran.VersesActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MarkedAdapter extends CursorAdapter {
	
	private Context context;
	Cursor c;
	int lang, trans;
	Typeface fontEvo;
	LayoutInflater mInflater;
	
	public MarkedAdapter(Context context, Cursor c, int lang, int trans) {
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
		TextView txtNameOrText = (TextView) view.findViewById(R.id.txtNameOrText);
		
		final int verseId = cursor.getInt(cursor.getColumnIndex("verseId"));
		final int surahId = cursor.getInt(cursor.getColumnIndex("surahId"));
		String text = cursor.getString(cursor.getColumnIndex("text"));
		
		if (verseId == 0) {
			txtId.setText(String.valueOf(surahId));
			txtNameOrText.setText(text);
		} else {
			txtId.setText(String.valueOf(surahId)+", "+String.valueOf(verseId));
			txtNameOrText.setText(text);
		}
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(context, verseId==0? VersesActivity.class : AVerseActivity.class);
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

	
	/*
	@Override
	public View getView(final int pos, View view, ViewGroup parent) {
		LinearLayout rowLayout;
		final ItemModel item = elements.get(pos);

		rowLayout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.single_item, parent, false);
		
		TextView tv = (TextView) rowLayout.findViewById(R.id.txtName);
		tv.setTypeface(fontEvo);
		tv.setText(item.surahName + (item.verseId == 0 ? "" : (" : "+String.valueOf(item.verseId))));

		return rowLayout;
	}*/


}
