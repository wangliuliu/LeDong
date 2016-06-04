package com.example.baidumapmotiontrack.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MTOpenHelper extends SQLiteOpenHelper {

	public static final String CREATE_POINTS = "create table latlng("
			+ "id integer primary key autoincrement," + "lat real,"
			+ "lng real)";

	public static final String CREATE_USER = "create table user("
			+ "id integer primary key autoincrement," + "username text,"
			+ "password text," + "phone text," + "total_time real,"// 总时间, 小时
			+ "total_distance real," + "total_calorie real,"// 大卡
			+ "total_count integer,"// 次数
			+ "photo_id integer," + "pinyin Text," // 拼音
			+ "alpha Text,"// 首字母
			+ "location text)";

	public static final String CREATE_RUN = "create table run("
			+ "id integer primary key autoincrement," + "user_id integer,"
			+ "date text," + "time real,"// 分钟
			+ "distance real," + "calorie real)";

	public static final String CREATE_FRIEND = "create table friend("
			+ "id integer primary key autoincrement," + "user_id1 integer,"
			+ "user_id2 integer)";

	public static final String CREATE_ADD_FRIEND = "create table add_friend("
			+ "id integer primary key autoincrement," + "user_id1 integer,"// 申请人
			+ "user_id2 integer,"// 被申请人
			+ "content text)";

	public static final String CREATE_MOMENT = "CREATE TABLE  moment ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "user_id integer,content text,"
			+ "time DATETIME DEFAULT CURRENT_TIMESTAMP)";

	public static final String CREATE_COMMENTS = "CREATE TABLE IF NOT EXISTS comments"
			+ "(c_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "id integer,"
			+ "comment_content text," + "u_id integer," + "username text)";

	public static final String CREATE_PRAISE = "CREATE TABLE IF NOT EXISTS praise"
			+ "(p_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "id integer,"
			+ "u_id integer," + "username text)";

	public static final String CREATE_CHAT = "CREATE TABLE  chat ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "user_id1 integer,user_id2 integer,content text,"
			+ "time DATETIME DEFAULT CURRENT_TIMESTAMP)";
	
	public MTOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_POINTS);
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_RUN);
		db.execSQL(CREATE_FRIEND);
		db.execSQL(CREATE_ADD_FRIEND);
		db.execSQL(CREATE_MOMENT);
		Log.d("Database", "onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// db.execSQL("drop table if exists latlng");
		// onCreate(db);
		db.execSQL("drop table if exists latlng");
		db.execSQL("drop table if exists user");
		db.execSQL("drop table if exists run");
		db.execSQL("drop table if exists friend");
		db.execSQL("drop table if exists add_friend");
		db.execSQL("drop table if exists moment");
		db.execSQL("drop table if exists comments");
		db.execSQL("drop table if exists praise");
		//db.execSQL("drop table if exists chat");
		db.execSQL(CREATE_POINTS);
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_RUN);
		db.execSQL(CREATE_FRIEND);
		db.execSQL(CREATE_ADD_FRIEND);
		db.execSQL(CREATE_MOMENT);
		db.execSQL(CREATE_COMMENTS);
		db.execSQL(CREATE_PRAISE);
		db.execSQL(CREATE_CHAT);
		Log.d("Database", "onUpgrade");
	}

}
