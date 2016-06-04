package com.example.baidumapmotiontrack.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.example.baidumapmotiontrack.model.Chat;
import com.example.baidumapmotiontrack.model.Comment;
import com.example.baidumapmotiontrack.model.Moment;
import com.example.baidumapmotiontrack.model.Praise;
import com.example.baidumapmotiontrack.model.Run;
import com.example.baidumapmotiontrack.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MTDatabase {

	private static final String DB_NAME = "mt";

	private static final int VERSION = 2;

	private MTOpenHelper dbHelper;

	private SQLiteDatabase db;

	private static MTDatabase myMTDatabase;

	private MTDatabase(Context context) {
		dbHelper = new MTOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public static MTDatabase getInstance(Context context) {
		if (myMTDatabase == null) {
			myMTDatabase = new MTDatabase(context);
		}
		return myMTDatabase;
	}

	public void saveLatLng(LatLng ll) {
		if (ll != null) {
			ContentValues values = new ContentValues();
			values.put("lat", ll.latitude);
			values.put("lng", ll.longitude);
			db.insert("latlng", null, values);
			Log.d("Database", "saved");
		}
	}

	public List<LatLng> loadAllLatLngs() {
		List<LatLng> list = new ArrayList<LatLng>();
		Cursor cursor = db.query("latlng", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				LatLng ll = new LatLng(cursor.getDouble(cursor
						.getColumnIndex("lat")), cursor.getDouble(cursor
						.getColumnIndex("lng")));
				list.add(ll);
			} while (cursor.moveToNext());
		}
		Log.d("Database", "load all");
		clearLatLng();
		return list;
	}

	/*
	 * public List<LatLng> loadLatLngs(int lastId) {// 加载所有Id大于等于lastId的latlng
	 * List<LatLng> list = new ArrayList<LatLng>(); Cursor cursor =
	 * db.query("latlng", null, "id>?", new String[] { String.valueOf(lastId -
	 * 1) }, null, null, null); if (cursor.moveToFirst()) { do { LatLng ll = new
	 * LatLng(cursor.getDouble(cursor .getColumnIndex("lat")),
	 * cursor.getDouble(cursor .getColumnIndex("lng"))); list.add(ll); } while
	 * (cursor.moveToNext()); Log.d("Database", "load special"); } return list;
	 * }
	 */

	//
	/*
	 * public LatLng loadOneLatLng(int Id) { LatLng ll = null; Cursor cursor =
	 * db.query("latlng", null, "id=?", new String[] { String.valueOf(Id) },
	 * null, null, null); if (cursor.moveToFirst()) { do { ll = new
	 * LatLng(cursor.getDouble(cursor.getColumnIndex("lat")),
	 * cursor.getDouble(cursor.getColumnIndex("lng"))); } while
	 * (cursor.moveToNext()); } return ll; }
	 */

	public void clearLatLng() {// 清空latlng表
		db.delete("latlng", null, null);
	}

	/*
	 * // 删除主键小于id的所有数据 public void clear(int id) { db.delete("latlng", "id<?",
	 * new String[] { String.valueOf(id) }); }
	 */

	// 增加用户
	public void saveUser(User user) {
		if (user != null) {
			ContentValues values = new ContentValues();
			values.put("username", user.getUsername());
			values.put("password", user.getPassword());
			values.put("phone", user.getPhone());
			values.put("total_time", user.getTotalTime());
			values.put("total_distance", user.getTotalDistance());
			values.put("total_calorie", user.getTotalCalorie());
			values.put("total_count", user.getTotalCount());
			values.put("location", user.getLocation());
			db.insert("user", null, values);
		}
	}

	// 根据id查找用户
	public User findUser(int id) {
		User user = null;
		Cursor cursor = db.query("user", null, "id=?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.moveToFirst()) {
			user = new User();
			user.setId(id);
			user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			user.setTotalCalorie(cursor.getDouble(cursor
					.getColumnIndex("total_calorie")));
			user.setTotalCount(cursor.getInt(cursor
					.getColumnIndex("total_count")));
			user.setTotalDistance(cursor.getDouble(cursor
					.getColumnIndex("total_distance")));
			user.setTotalTime(cursor.getDouble(cursor
					.getColumnIndex("total_time")));
			user.setLocation(cursor.getString(cursor.getColumnIndex("location")));
		}
		return user;
	}

	public int findUserId(String username) {
		Cursor c = db.query("user", null, "username=?",
				new String[] { String.valueOf(username) }, null, null, null);
		if (c.moveToFirst())
			return c.getInt(c.getColumnIndex("id"));
		else
			return -1;
	}

	// 根据指定的Id号查找用户
	public Cursor getUser(long rowId) throws SQLException {
		Cursor mcursor = db.query("user", new String[] { "id", "username",
				"phone" }, "id" + "=" + rowId, null, null, null, null, null);
		if (mcursor != null) {
			mcursor.moveToFirst();
		}
		return mcursor;
	}

	// 根据用户名查找用户
	public User findUser(String username) {
		User user = null;
		Cursor cursor = db.query("user", null, "username=?",
				new String[] { String.valueOf(username) }, null, null, null);
		if (cursor.moveToFirst()) {
			user = new User();
			user.setId(cursor.getInt(cursor.getColumnIndex("id")));
			user.setUsername(username);
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			user.setTotalCalorie(cursor.getDouble(cursor
					.getColumnIndex("total_calorie")));
			user.setTotalCount(cursor.getInt(cursor
					.getColumnIndex("total_count")));
			user.setTotalDistance(cursor.getDouble(cursor
					.getColumnIndex("total_distance")));
			user.setTotalTime(cursor.getDouble(cursor
					.getColumnIndex("total_time")));
			user.setLocation(cursor.getString(cursor.getColumnIndex("location")));
		}
		return user;
	}

	// 添加跑步记录,同时更新相应的用户数据
	public void saveRun(Run run) {
		if (run != null) {
			ContentValues values = new ContentValues();
			values.put("user_id", run.getUserId());
			values.put("date", run.getDate());
			values.put("time", run.getTime());
			values.put("distance", run.getDistance());
			values.put("calorie", run.getCalorie());

			User user = findUser(run.getUserId());
			ContentValues values2 = new ContentValues();
			values2.put("total_time", user.getTotalTime() + run.getTime());
			values2.put("total_distance",
					user.getTotalDistance() + run.getDistance());
			values2.put("total_calorie",
					user.getTotalCalorie() + run.getCalorie());
			values2.put("total_count", user.getTotalCount() + 1);

			db.insert("run", null, values);
			db.update("user", values2, "id=?",
					new String[] { String.valueOf(run.getUserId()) });
		}
	}

	// 根据id获得用户名
	public String getUserName(int id) {
		Cursor c = db
				.rawQuery("SELECT username FROM user WHERE id=" + id, null);
		if (c.moveToFirst())
			return c.getString(c.getColumnIndex("username"));
		else
			return null;

	}

	// 根据用户id加载所有运动记录
	public List<Run> loadRuns(int userId) {
		Cursor cursor = db.query("run", null, "user_id=?",
				new String[] { String.valueOf(userId) }, null, null, "date");
		List<Run> list = new ArrayList<Run>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (cursor.moveToFirst()) {
			do {
				Run run = new Run();
				run.setId(cursor.getInt(cursor.getColumnIndex("id")));

				Date date = null;
				try {
					date = sdf.parse(cursor.getString(cursor
							.getColumnIndex("date")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				run.setDate(date);
				run.setDistance(cursor.getDouble(cursor
						.getColumnIndex("distance")));
				run.setCalorie(cursor.getDouble(cursor
						.getColumnIndex("calorie")));
				run.setTime(cursor.getDouble(cursor.getColumnIndex("time")));
				run.setUserId(userId);
				list.add(run);
			} while (cursor.moveToNext());
		}
		return list;
	}

	// 添加说说
	public void saveMoment(Moment moment) {
		if (moment != null) {
			db.beginTransaction(); // 开始事务
			try {
				ContentValues values = new ContentValues();
				values.put("user_id", moment.user_id);
				values.put("content", moment.content);
				db.insert("moment", null, values);
				db.setTransactionSuccessful(); // 设置事务成功完成
			} finally {
				db.endTransaction(); // 结束事务
				Log.v("tran", "done2");
			}
		}
	}

	// 根据用户id加载对应的说说（自己的及好友的）
	public List<Moment> queryAllMoment(int id) {
		List<Moment> moments = new ArrayList<Moment>();
		List<Integer> userIds = new ArrayList<Integer>();
		Cursor s1 = db.rawQuery("SELECT user_id1 from friend where user_id2="
				+ id, null);
		while (s1.moveToNext()) {
			int m = 0;
			m = s1.getInt(s1.getColumnIndex("user_id1"));
			userIds.add(m);
		}
		s1.close();
		/*
		 * Cursor s2 = db.rawQuery("SELECT user_id2 from friend where user_id1="
		 * + id, null); while (s2.moveToNext()) { int m = 0; m =
		 * s2.getInt(s2.getColumnIndex("user_id2")); userIds.add(m); }
		 * s2.close();
		 */
		userIds.add(id);
		for (int i = 0; i < userIds.size(); ++i) {
			Log.d("ID", String.valueOf(userIds.get(i)));
			Cursor c = db.rawQuery(
					"SELECT user_id,content,datetime(time,'localtime') FROM moment where user_id="
							+ userIds.get(i) + " order by time asc", null);
			while (c.moveToNext()) {
				Moment moment = new Moment();
				// 获取动态的主人名字
				int a = c.getInt(c.getColumnIndex("user_id"));
				moment.user_id = a;
				moment.username = getUserName(a);
				// 获取动态内容
				moment.content = c.getString(c.getColumnIndex("content"));
				// 获取发布动态的时间

				String str = c.getString(c
						.getColumnIndex("datetime(time,'localtime')"));
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				try {
					moment.time = format.parse(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				moments.add(moment);
			}

			c.close();
		}

		return moments;
	}

	// 模糊查找相关联系人
		public List<User> getRelevantContacts(String s,int id) {
			List<User> list = new ArrayList<User>();
			Cursor cursor = db.query("user", null, "username like?",
					new String[] { '%' + s + '%' }, null, null, "alpha asc");
			// Log.d("DB","cursor size:"+cursor.getCount());
			if (cursor.moveToFirst()) {
				do {
					User user = new User();
					user.setUsername(cursor.getString(cursor
							.getColumnIndex("username")));
					user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
					int pid=cursor.getInt(cursor.getColumnIndex("id"));
					if(isFriend(id,pid)==1)
						list.add(user);
					//list.add(user);
				} while (cursor.moveToNext());
			}
			return list;
		}

	public List<User> getAllFri1(int rowId) {
		Cursor c = getFriend(rowId);
		List<User> list = new ArrayList<User>();
		// List<Integer> l=new ArrayList<Integer>();
		String ins = "#";
		if (c.moveToFirst()) {
			do {
				int pid = c.getInt(c.getColumnIndex("user_id2"));
				ins += pid + "#";
			} while (c.moveToNext());
		}
		Cursor cursor = db.query("user", null, null, null, null, null,
				"alpha asc");
		if (cursor.moveToFirst()) {
			do {
				int pid1 = cursor.getInt(cursor.getColumnIndex("id"));

				if (ins.contains("#" + pid1 + "#")) {
					User user = new User();
					user.setUsername(cursor.getString(cursor
							.getColumnIndex("username")));
					user.setPhone(cursor.getString(cursor
							.getColumnIndex("phone")));
					list.add(user);
				}
			} while (cursor.moveToNext());
		}
		return list;
	}

	// 向好友表中添加数据
	public void addfriend(long rowId1, long rowId2) {
		ContentValues values = new ContentValues();
		values.put("user_id1", rowId1);
		values.put("user_id2", rowId2);
		db.insert("friend", null, values);
		ContentValues v = new ContentValues();
		v.put("user_id1", rowId2);
		v.put("user_id2", rowId1);
		db.insert("friend", null, v);
	}

	// 查询所有的好友
	public Cursor getAllFriend() {
		return db.query("friend",
				new String[] { "id", "user_id1", "user_id2" }, null, null,
				null, null, null, null);
	}

	// 根据Id获得对应的好友
	public Cursor getFriend(long rowId) throws SQLException {
		Cursor mcursor = db.query("friend", new String[] { "id", "user_id1",
				"user_id2" }, "user_id1" + "=" + rowId, null, null, null, null,
				null);
		if (mcursor != null) {
			mcursor.moveToFirst();
		}
		return mcursor;
	}

	// 删除好友表中好友
	public void deleteFriend(long rowId) {
		db.delete("friend", "id" + "=" + rowId, null);
	}

	public void deleteFriend1(long rowId1, long rowId2) {
		String s = "DELETE FROM friend WHERE user_id1='rowId1' AND user_id2='rowId2'";
		db.execSQL(s);
		String s1 = "DELETE FROM friend WHERE user_id1='rowId2' AND user_id2='rowId1'";
		db.execSQL(s1);
	}

	// 查询所有的请求
	public Cursor getAllRequest() {
		return db.query("add_friend", new String[] { "id", "user_id1",
				"user_id2" }, null, null, null, null, null, null);
	}

	// 根据Id获得对应的请求
	public Cursor getRequest(long rowId) throws SQLException {
		Cursor mcursor = db.query("add_friend", new String[] { "id",
				"user_id1", "user_id2","content" }, "user_id2" + "=" + rowId, null, null,
				null, null, null);
		if (mcursor != null) {
			mcursor.moveToFirst();
			return mcursor;
		}
		//return mcursor;
		else
			return null;
	}

	// 删除好友
	public void deleteRequest(long rowId) {
		db.delete("add_friend", "id" + "=" + rowId, null);
	}

	public void addP(List<Praise> praises) {
		db.beginTransaction(); // 开始事务
		try {
			for (Praise p : praises) {
				ContentValues values = new ContentValues();
				values.put("username", p.getU_name());
				values.put("id", p.getId());
				values.put("u_id", p.getU_id());
				db.insert("praise", null, values);
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	public void addC(List<Comment> comments) {
		db.beginTransaction();// 开始事务
		try {
			for (Comment c : comments) {
				ContentValues values = new ContentValues();
				values.put("id", c.getId());
				values.put("comment_content", c.getComment_content());
				values.put("username", c.getU_name());
				values.put("u_id", c.getU_id());
				db.insert("comments", null, values);
			}
			db.setTransactionSuccessful();// 设置事务成功完成
		} finally {
			db.endTransaction();// 事务结束
		}

	}

	// 添加聊天
	public void saveChat(Chat chat) {
		if (chat != null) {
			db.beginTransaction(); // 开始事务
			try {
				ContentValues values = new ContentValues();
				values.put("user_id1", chat.user_id1);
				values.put("user_id2", chat.user_id2);
				values.put("content", chat.content);
				// values.put("flag", chat.isComMeg);
				db.insert("chat", null, values);
				db.setTransactionSuccessful(); // 设置事务成功完成
			} finally {
				db.endTransaction(); // 结束事务
				Log.v("tran", "done2");
			}
		}
	}

	public List<Chat> getChat(int id1, int id2) {
		List<Chat> chats = new ArrayList<Chat>();
		List<Integer> keyId = new ArrayList<Integer>();// 记录主键id
		Cursor c2 = db.rawQuery("SELECT id from chat where (user_id1=" + id1
				+ " and user_id2=" + id2 + ") or (user_id1=" + id2
				+ " and user_id2=" + id1 + ") order by time asc", null);
		while (c2.moveToNext()) {
			int key = c2.getInt(c2.getColumnIndex("id"));
			keyId.add(key);
		}
		for (int i = 0; i < keyId.size(); ++i) {
			Log.d("ID", String.valueOf(keyId.get(i)));
			Cursor c0 = db
					.rawQuery(
							"SELECT user_id1,user_id2,content,datetime(time,'localtime') FROM chat where id="
									+ keyId.get(i) + " order by time asc", null);
			while (c0.moveToNext()) {
				Chat chat = new Chat();
				int a = c0.getInt(c0.getColumnIndex("user_id1"));
				chat.user_id1 = a;
				int b = c0.getInt(c0.getColumnIndex("user_id1"));
				chat.user_id2 = b;
				if (a == id1)
					chat.setMsgType(false);
				else if (b == id1)
					chat.setMsgType(true);
				chat.username = getUserName(a);
				chat.content = c0.getString(c0.getColumnIndex("content"));
				// 获取发布动态的时间
				String str = c0.getString(c0
						.getColumnIndex("datetime(time,'localtime')"));
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				try {
					chat.time = format.parse(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				chats.add(chat);
			}
			c0.close();
		}
		return chats;
	}

	// 查询和所有人的聊天记录
	public List<Chat> getAllChat(int id) {
		List<Chat> chats = new ArrayList<Chat>();
		// List<Integer> keyId=new ArrayList<Integer>();//记录主键id
		String ins = "#";
		Cursor c2 = db.rawQuery("SELECT id,user_id2 from chat where user_id1="
				+ id, null);
		while (c2.moveToNext()) {
			int b = c2.getInt(c2.getColumnIndex("user_id2"));
			if (ins.contains("#" + b + "#")) {
				continue;
			} else {
				ins += b + "#";
				Chat chat = new Chat();
				chat.user_id1 = id;
				chat.user_id2 = b;
				chat.username = getUserName(b);
				chats.add(chat);
			}

		}
		Cursor c1 = db.rawQuery("SELECT id,user_id1 from chat where user_id2="
				+ id, null);
		while (c1.moveToNext()) {
			// int key1=c1.getInt(c1.getColumnIndex("id"));
			int m = c1.getInt(c1.getColumnIndex("user_id1"));
			if (ins.contains("#" + m + "#")) {
				continue;
			} else {
				Chat chat = new Chat();
				chat.user_id2 = id;
				chat.user_id1 = m;
				chat.username = getUserName(m);
				chats.add(chat);
			}
		}
		return chats;
	}

	// 请求好友的个数
	public int numRequest(long rowId) throws SQLException {
		int num = 0;
		Cursor mcursor = db.query("add_friend", new String[] { "id",
				"user_id1", "user_id2", "content" }, "user_id2" + "=" + rowId,
				null, null, null, null, null);
		if (mcursor != null) {
			while (mcursor.moveToNext()) {
				num = num + 1;
			}
			return num;
		} else
			return 0;
	}

	// 向请表中添加数据
		public void addRequest(long rowId1, long rowId2,String s) {
			ContentValues values = new ContentValues();
			values.put("user_id1", rowId1);
			values.put("user_id2", rowId2);
			values.put("content", s);
			db.insert("add_friend", null, values);
		}
		
		//判断是否为好友
		public int isFriend(int id1,int id2){
			int flag=0;
			/*Cursor c1=db.rawQuery("SELECT id from friend where user_id1="+id1 +" and user_id2="+id2,null);
			if (c1 != null) {
				return true;
			}*/
			//Cursor c=getFriend(id1);
			Cursor c = db.query("friend", new String[] { "id", "user_id1",
			"user_id2" }, "user_id1" + "=" + id1, null, null, null, null,
			null);
			while(c.moveToNext()){
				int key = c.getInt(c.getColumnIndex("user_id2")); 
				if(key==id2){
					flag=1;
				}
				else if(id2==id1){
					flag=2;
				}
			}
			return flag;
		}
		
		//查找好友列表主键
		public int findFriId(int id1,int id2){
			int key=0;
			Cursor c2=db.rawQuery("SELECT id from friend where user_id1="+id1 +" and user_id2="+id2,null);
			while(c2.moveToNext()){
				key=c2.getInt(c2.getColumnIndex("id"));
			}
			
			return key;
		}
		
		//查找好友聊天的主键
		public int findChaId(int id1,int id2){
			int key=0;
			Cursor c2=db.rawQuery("SELECT id from chat where user_id1="+id1 +" and user_id2="+id2,null);
			while(c2.moveToNext()){
				key=c2.getInt(c2.getColumnIndex("id"));
			}
			
			return key;
		}
		
		//删除聊天记录
		public void deleteChat(long rowId) {
			db.delete("chat", "id" + "=" + rowId, null);
		}

}
