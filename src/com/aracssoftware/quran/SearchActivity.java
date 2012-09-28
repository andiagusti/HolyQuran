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

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.aracssoftware.quran.lib.DatabaseHelper;
import com.aracssoftware.quran.lib.MarkedAdapter;

public class SearchActivity extends Activity {

	Cursor c;
	ListView listSearch;
	MarkedAdapter adapter;
	DatabaseHelper dbHelper;
	Context context;
	int surahId, trans, lang;
	String query;
	String[][] strings = { { "Axtarış: " }, { "Поиск: " } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marknote);

		this.context = this;
		dbHelper = new DatabaseHelper(context);
		surahId = getIntent().getIntExtra("surahId", 0);
		query = getIntent().getStringExtra("query");
		trans = getIntent().getIntExtra("trans", -1);
		lang = getIntent().getIntExtra("lang", -1);

		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(strings[lang][0] + query);

		if (surahId == 0) {
			c = dbHelper.searchQuran(trans, query);
		} else {
			c = dbHelper.searchSurah(trans, surahId, query);
		}

		adapter = new MarkedAdapter(context, c, lang, trans);
		listSearch = (ListView) findViewById(R.id.listItems);
		listSearch.setAdapter(adapter);

	}

}
