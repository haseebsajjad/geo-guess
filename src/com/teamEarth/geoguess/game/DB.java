package com.teamEarth.geoguess.game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

	public DB(Context context){
		super(context,"imagedb",null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table questions(id integer primary key autoincrement, image text, url text,ans text, level text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists questions;");
		onCreate(db);
	}

}
