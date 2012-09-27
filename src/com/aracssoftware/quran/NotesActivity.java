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
