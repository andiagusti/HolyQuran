package com.aracssoftware.quran.lib;

public class SurahModel {
	public int ID;
	public String name;
	//public String link;
	public boolean hasMark;
	public boolean hasNote;
	
	public SurahModel(int id, String name) {
		ID = id;
		this.name = name;
		//this.link = link;
		this.hasMark = false;
		this.hasNote = false;
	}

}
