package com.aracssoftware.quran.lib;

public class VerseModel {
	public int ID;
	public String VerseText;
	public boolean hasMark;
	public boolean hasNote;

	public VerseModel(int id, String VerseText) {
		ID = id;
		this.VerseText = VerseText;
		this.hasMark = false;
		this.hasNote = false;
	}

}
