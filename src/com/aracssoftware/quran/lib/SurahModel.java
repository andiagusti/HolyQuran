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
