package com.aracssoftware.quran.lib;

public class ItemModel {
	public int surahId, verseId;
	public String surahName, surahLink, note;
	
	public ItemModel (int surahId, int verseId,String surahName) {
		this.surahId = surahId;
		this.verseId = verseId;
		this.surahName = surahName;
		this.surahLink = "Chapter" + String.format("%03d", surahId) + ".xml";
	}

}
