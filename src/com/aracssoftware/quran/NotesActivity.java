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

package com.aracssoftware.quran;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.ItemModel;
import com.aracssoftware.quran.lib.NotesAdapter;

public class NotesActivity extends Activity {
	ListView listItems;
	DatabaseHelper databaseH;
	Context context;
	Cursor c;
	int lang, trans;
	String[][] strings = { {"Qeydlər"}, {"Заметки"} };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marknote);
		context = this;

		Bundle b = getIntent().getExtras();
		lang = b.getInt("lang");
		trans = b.getInt("trans");

		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		ListView listNotes = (ListView) findViewById(R.id.listItems);
		
		txtTitle.setText(strings[lang][0]);
		
		databaseH = new DatabaseHelper(context);
		c = databaseH.getNoteds(trans);
		startManagingCursor(c);
		NotesAdapter adapter = new NotesAdapter(context, c, lang, trans);
		listNotes.setAdapter(adapter);
		
		

	}

}
