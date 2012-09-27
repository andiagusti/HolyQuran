package com.aracssoftware.quran.lib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String DB_PATH = Environment.getExternalStorageDirectory()
			+ "/Android/data/com.aracssoftware.quran/";
	private static String DB_NAME = "quran.db";

	public SQLiteDatabase myDataBase;
	private final Context myContext;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;

		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Helper methods

	public Cursor getTranslations() {
		return myDataBase.rawQuery("SELECT * FROM translations", null);
	}

	public Cursor getSurahs(int trans) {
		return myDataBase.rawQuery("SELECT * FROM quran WHERE trans = "
				+ String.valueOf(trans) + " AND verseId = 0", null);
	}

	public QuranItem getItem(int trans, int surahId, int verseId) {
		Cursor c = myDataBase.rawQuery(
				"SELECT * FROM quran WHERE trans = " + String.valueOf(trans)
						+ " AND ( surahId = " + String.valueOf(surahId)
						+ " AND verseId = " + String.valueOf(verseId) + " )",
				null);
		c.moveToFirst();
		QuranItem item = new QuranItem();
		item.id = c.getInt(c.getColumnIndex("_id"));
		item.surahId = c.getInt(c.getColumnIndex("surahId"));
		item.verseId = c.getInt(c.getColumnIndex("verseId"));
		item.text = c.getString(c.getColumnIndex("text"));
		item.hasMark = c.getInt(c.getColumnIndex("marked")) == 1;
		item.hasNote = c.getInt(c.getColumnIndex("hasNote")) == 1;
		item.verseId = verseId;
		item.surahId = surahId;
		if (item.hasNote)
			item.note = c.getString(c.getColumnIndex("note"));
		return item;
	}

	public Cursor getVerses(int trans, int surahId) {
		return myDataBase.rawQuery(
				"SELECT * FROM quran WHERE trans = " + String.valueOf(trans)
						+ " AND surahId = " + String.valueOf(surahId)
						+ " AND verseId <> 0", null);
	}

	public void setMark(int surahId, int verseId, boolean mark) {
		myDataBase.execSQL("UPDATE quran SET marked = "
				+ String.valueOf(mark ? 1 : 0) + " WHERE surahId = "
				+ String.valueOf(surahId) + " AND verseId = "
				+ String.valueOf(verseId));
	}

	public void setNote(int surahId, int verseId, boolean hasNote, String text) {
		myDataBase.execSQL("UPDATE quran SET hasNote="
				+ String.valueOf(hasNote ? 1 : 0) + ", note=\"" + text
				+ "\" WHERE surahId=" + String.valueOf(surahId)
				+ " AND verseId=" + String.valueOf(verseId));
	}
	 
	public Cursor getMarkeds(int trans) {
		return myDataBase.rawQuery("SELECT * FROM quran WHERE marked = 1 ORDER BY surahId, verseId", null);
	}
	
	public Cursor getNoteds(int trans) {
		return myDataBase.rawQuery("SELECT * FROM quran WHERE hasNote = 1", null);
	}
	
	public Cursor searchQuran(int trans, String query) {
		return myDataBase.rawQuery("SELECT * FROM quran WHERE trans="+String.valueOf(trans)+" AND surahId<>0 AND text MATCH '"+query+"'", null);
	}
	
	public Cursor searchSurah(int trans, int surahId, String query) {
		return myDataBase.rawQuery("SELECT * FROM quran WHERE trans="+String.valueOf(trans)+" AND surahId="+String.valueOf(surahId)+" AND text MATCH '"+query+"'", null);
	}
	

}
