package com.fpoly.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fpoly.object.English;

public class Mydatabase extends SQLiteOpenHelper {

	// khai bao
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_PATH = "/data/data/com.fpoly.englishfunforkid/databases/";
	// database name
	private static final String DATABASE_NAME = "Db_EnglishFunForKid.sqlite";
	// table database
	private static final String TABLE_ENGLISH = "english";

	private SQLiteDatabase db;

	// English table colums names
	private static final String KEY_ID = "id";
	private static final String KEY_NAMEIMAGE = "name_image";
	private static final String KEY_NAMEAUDIO = "name_audio";
	private static final String KEY_DECRIPTION = "decription";

	Context ctx;

	public Mydatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.ctx = context;
		Log.d(null,
				" create db--------------------------------------------------------------------");
		// TODO Auto-generated constructor stub
	}

	/*
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 */

	// get all in sqlite
	public ArrayList<English> GetAllInEnglish() {
		ArrayList<English> listEnglish = new ArrayList<English>();
		String sSQL = "SELECT id, name_image,name_audio,description from english";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(sSQL, null);
		if (cur.moveToFirst()) {
			do {
				English eng = new English();
				eng.setId(cur.getInt(0));
				eng.setImage(cur.getString(1));
				eng.setAudio(cur.getString(2));
				eng.setDecription(cur.getString(3));
				// add to list
				listEnglish.add(eng);
			} while (cur.moveToNext());

		}

		return listEnglish;
	}

//	// truy van theo id
//	public Cursor getId(int id) {
//		Cursor cursor = db.rawQuery("SELECT * FROM TABLE_ENGLISH WHERE id = '"
//				+ id + "' ;", null);
//		if (cursor != null) {
//
//			cursor.moveToFirst();
//
//		}
//		return cursor;
//	}
//
//	// truy van theo ten image
//	public Cursor getImage(String img) {
//		Cursor cursor = db.rawQuery(
//				"SELECT * FROM TABLE_ENGLISH WHERE name_image = '" + img
//						+ "' ;", null);
//		if (cursor != null) {
//
//			cursor.moveToFirst();
//
//		}
//		return cursor;
//
//	}

	// truy van
	public void CopyDatabaseFromAsset() throws IOException {
		InputStream in = ctx.getAssets().open(DATABASE_NAME);
		Log.d("sample", "Start copy===============================");
		String outputFileName = DATABASE_PATH + DATABASE_NAME;

		OutputStream out = new FileOutputStream(outputFileName);

		byte[] buffer = new byte[1024];
		int length;

		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		Log.e("sample", "Completed=====================================");
		out.flush();
		out.close();
		in.close();
	}

	public void openDatabase() throws SQLException {
		String Mypath = DATABASE_PATH + DATABASE_NAME;
		db = SQLiteDatabase.openDatabase(Mypath, null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS
						| SQLiteDatabase.CREATE_IF_NECESSARY);

	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	public void createDatabase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {

		} else {
			this.getReadableDatabase();
			try {
				CopyDatabaseFromAsset();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}

		}
	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
