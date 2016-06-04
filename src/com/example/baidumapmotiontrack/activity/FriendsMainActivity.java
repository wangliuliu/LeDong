/*
package a.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ledong.com.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;


public class Main extends Activity {
	private MTDatabase db;
	public static final String TAG="MainActivity";
	private ListView sortListView;
	private SideBar sideBar;
	private SortPeopleAdapter adapter;
	private SimpleSearch mSearchEditText;
	private LinearLayout firstLayout;
	private TextView txtdialog,first,txtTip;
	private Button btnAdd;
	private RelativeLayout rl_addFriend,rl_new;
	//�ϴε�һ���ɼ�Ԫ�أ����ڹ���ʱ��¼��ʶ��
	private int lastFirstVisibleItem=-1;
	//����ת����ƴ������
	private CharacterParser characterParser;
	private List<People> PeopleList;
	//����ƴ��������ListView�����������
	private PinyinComparator pinyinComparator;
	// ������ϵ�˲˵�
	public static final int MENU_CONTACTS_DELETE = Menu.FIRST;//ɾ����ϵ��
	public static final int MENU_CONTACTS_MODIFY = Menu.FIRST + 1;//�༭��ϵ��
	
	int userId;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnAdd=(Button)findViewById(R.id.btnAdd);
		rl_addFriend=(RelativeLayout)findViewById(R.id.rl_addFriend);
		rl_new=(RelativeLayout)findViewById(R.id.rl_new);
		//btnAdd.setOnClickListener(myListener);
		rl_addFriend.setOnClickListener(addListener);
		rl_new.setOnClickListener(addListener);

		db=MTDatabase.getInstance(this);
		User user=new User();
		user.setUsername("����");
		user.setPhone("123");
		db.saveUser(user);
		db.addfriend(1, 2);
		db.addfriend(1, 3);
		db.addfriend(1, 4);
		db.addfriend(5, 2);
		db.addfriend(5, 3);
		db.addfriend(5, 4);
		
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		userId=bundle.getInt("user_id");
		init();
	}
	
	private RelativeLayout.OnClickListener addListener=new
			RelativeLayout.OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.rl_addFriend:
			{
				Intent intent = new Intent();
				intent.setClass(Main.this,AddFriend.class);
				Bundle bundle=new Bundle();
				bundle.putInt("user_id1", userId);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			}
			case R.id.rl_new:
			{
				Intent intent = new Intent();
				intent.setClass(Main.this,RequestNew.class);
				Bundle bundle=new Bundle();
				bundle.putInt("user_id1", userId);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			}
			}
		}
	};
	
	
	//���������Ĳ˵�
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
	    Log.v(TAG, "populate context menu");
	    // set context menu title
	    menu.setHeaderTitle("��ϵ�˲���");
	    // add context menu item
	    menu.add(0, MENU_CONTACTS_DELETE, 0, "ɾ����ϵ��");
		menu.add(0, MENU_CONTACTS_MODIFY, 0, "�༭��ϵ��");
	}
	
	//ʵ�������Ĳ˵��ﶨ��Ĳ˵�
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo acmenuinfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final int mListPos = acmenuinfo.position;
		final String ss=String.valueOf(mListPos);
		final String pname=((People)adapter.getItem(acmenuinfo.position)).getName();
		final String pphone=((People)adapter.getItem(acmenuinfo.position)).getPhone();
		//final String paddress=((People)adapter.getItem(acmenuinfo.position)).getAddress();
		final int pid=((People)adapter.getItem(acmenuinfo.position)).getId();
		Cursor c1=db.getFriend(userId);
	    int pid1;
		while(c1.moveToNext()){
			final int pid2=Integer.parseInt(c1.getString(c1.getColumnIndex("id")));
			int pid3=Integer.parseInt(c1.getString(c1.getColumnIndex("user_id2")));
			if(pid3==pid){
				pid1=pid2;
				break;
			}				
		}		
		switch (item.getItemId()){
		case MENU_CONTACTS_DELETE: {//ɾ����ϵ��
			delete(pid);
			onCreate(null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("ȷ��Ҫɾ����ϵ����");
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							 //db.deleteUser(pid);
							 db.deleteFriend1(userId,pid);
							 db.deleteFriend1(pid,userId);
							 onCreate(null);
							Toast.makeText(getApplication(),"ɾ���ɹ�",Toast.LENGTH_SHORT).show();
						}
					});
			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {

							dialog.dismiss();
						}
					});
			builder.show();
			break;
		}
		case MENU_CONTACTS_MODIFY: {//�޸���ϵ��
			Intent intent = new Intent();
			intent.setClass(MainActivity.this,ModifyMember.class);
			Bundle bundle=new Bundle();
			bundle.putInt("PID", pid);
			bundle.putString("NAME", pname);
			bundle.putString("PHONE", pphone);
			bundle.putString("ADDRESS", paddress);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
		}
		return super.onContextItemSelected(item);
	}
	
	//�����ϵ��
	private Button.OnClickListener myListener=new
			Button.OnClickListener(){
		public void onClick(View v){
			Intent intent=new Intent();
			intent.setClass(MainActivity.this,AddUser.class);
			startActivity(intent);
		}
	};
	
	private void init(){
		firstLayout=(LinearLayout) findViewById(R.id.ll_first);
		first=(TextView) this.findViewById(R.id.txtFirst);
		txtTip=(TextView) this.findViewById(R.id.txtTip);
		// ʵ��������תƴ����
		characterParser=CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		txtdialog = (TextView) findViewById(R.id.txtdialog);
		sideBar.setTextView(txtdialog);
		
		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new ledong.com.SideBar.OnTouchingLetterChangedListener(){
			public void onTouchingLetterChanged(String s){
				// ����ĸ�״γ��ֵ�λ��
				int position=adapter.getPositionForSection(s.charAt(0));
				if (position!=-1){
					sortListView.setSelection(position);
				}
			}
		});

		sortListView=(ListView)findViewById(R.id.listMember);
		sortListView.setOnCreateContextMenuListener(this);
		//�鿴��ϵ������
		sortListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>parent,View view,
					int position,long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
				String str=((People)adapter.getItem(position)).getName();
				Intent intent=new Intent();
				intent.setClass(Main.this, UserInfo.class);
				String name=((People)adapter.getItem(position)).getName();
				String phone=((People)adapter.getItem(position)).getPhone();
				int uId=((People)adapter.getItem(position)).getId();
				Bundle bundle=new Bundle();
				bundle.putString("NAME", name);
				bundle.putString("PHONE", phone);
				bundle.putInt("uId", uId);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		PeopleList = filledData();
		// ����a-z��������Դ����
		Collections.sort(PeopleList, pinyinComparator);
		adapter=new SortPeopleAdapter(this, PeopleList);
		sortListView.setAdapter(adapter);
		sortListView.setOnScrollListener(new OnScrollListener(){
			@Override
			public void onScrollStateChanged(AbsListView view,int scrollState){
			}

			@Override
			public void onScroll(AbsListView view,int firstVisibleItem,
					int visibleItemCount,int totalItemCount){
				//System.out.println(firstVisibleItem+2);
				int section=getSectionForPosition(firstVisibleItem);
				int nextSection=getSectionForPosition(firstVisibleItem+1);
				int nextSecPosition=getPositionForSection(+nextSection);
				if (firstVisibleItem!=lastFirstVisibleItem+1){
					MarginLayoutParams params=(MarginLayoutParams)firstLayout
							.getLayoutParams();
					params.topMargin=0;
					firstLayout.setLayoutParams(params);
					first.setText(PeopleList.get(
							getPositionForSection(section)).getFirstLetter());
					if(section==0)
						first.setText("");
					else
						first.setText(PeopleList.get(
							getPositionForSection(section)).getFirstLetter());
				}
				if (nextSecPosition==firstVisibleItem + 1){
					View childView=view.getChildAt(0);
					if (childView!=null){
						int titleHeight=firstLayout.getHeight();
						int bottom=childView.getBottom();
						MarginLayoutParams params=(MarginLayoutParams)firstLayout
								.getLayoutParams();
						if (bottom < titleHeight){
							float pushedDistance=bottom - titleHeight;
							params.topMargin=(int) pushedDistance;
							firstLayout.setLayoutParams(params);
						} else {
							if (params.topMargin!=0){
								params.topMargin=0;
								firstLayout.setLayoutParams(params);
							}
						}
					}
				}
				lastFirstVisibleItem=firstVisibleItem;
			}
		});
		mSearchEditText=(SimpleSearch)findViewById(R.id.filter_edit);

		// �������������ֵ�ĸı�����������
		mSearchEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void onTextChanged(CharSequence s,int start,int before,
					int count){
				// ���ʱ����Ҫ��ѹЧ�� �Ͱ������ص�
				firstLayout.setVisibility(View.GONE);
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s,int start,int count,
					int after){
			}
			@Override
			public void afterTextChanged(Editable s){
			}
		});
	}
	
	//�������
	private List<People> filledData(){
		List<People> mSortList=new ArrayList<People>();
		System.out.println(userId);
		Cursor cursor=db.getAllUser();
		//Cursor cursor=db.getFriend();
		while(cursor.moveToNext()){
			//�����ݿ�������ݵ���People��
			//int pid=Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id1")));
			People p=new People();
			p.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
			p.setName(cursor.getString(cursor.getColumnIndex("username")));
			p.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			int pid=Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
			System.out.println(pid);
			// ����ת����ƴ��
			String pinyin=characterParser.getSelling(cursor.getString(cursor.getColumnIndex("username")));
			String sortString=pinyin.substring(0,1).toUpperCase();
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")){
				p.setFirstLetter(sortString.toUpperCase());
				}
			else{
				p.setFirstLetter("#");
				}
			mSortList.add(p);
		}
		return mSortList;
	}
	private List<People> filledData(){
		List<People> mSortList=new ArrayList<People>();
		//Cursor cursor=db.getAllUser();
		//Cursor cur=db.getFriend(4);
		Cursor cur=db.getFriend(userId);
		System.out.println(userId);
		while(cur.moveToNext()){
			//�����ݿ�������ݵ���People��
			int pid=Integer.parseInt(cur.getString(cur.getColumnIndex("user_id2")));
			Cursor c=db.getUser(pid);
			People p=new People();
			p.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
			p.setName(c.getString(c.getColumnIndex("username")));
			p.setPhone(c.getString(c.getColumnIndex("phone")));
			//int pid=Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
			//System.out.println(pid);
			// ����ת����ƴ��
			String pinyin=characterParser.getSelling(c.getString(c.getColumnIndex("username")));
			String sortString=pinyin.substring(0,1).toUpperCase();
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")){
				p.setFirstLetter(sortString.toUpperCase());
				}
			else{
				p.setFirstLetter("#");
				}
			mSortList.add(p);
		}
		return mSortList;
	}

	//����������е�ֵ���������ݲ�����ListView
	private void filterData(String filterStr){
		List<People> filterDateList = new ArrayList<People>();
		if (TextUtils.isEmpty(filterStr)){
			filterDateList = PeopleList;
			txtTip.setVisibility(View.GONE);
			} 
		else 
		{
			filterDateList.clear();
			for (People sortModel:PeopleList) 
			{
				String name=sortModel.getName();
				if (name.indexOf(filterStr.toString())!=-1
						||characterParser.getSelling(name).startsWith(
								filterStr.toString())) 
				{
					filterDateList.add(sortModel);
				}
			}
		}
		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
		if (filterDateList.size()==0)
		{
			txtTip.setVisibility(View.VISIBLE);
		}
	}

	public Object[] getSections()
	{
		return null;
	}

	//����ListView�ĵ�ǰλ�û�ȡ���������ĸ��ֵ
	public int getSectionForPosition(int position)
	{
		if(PeopleList==null)
			return 0;
		else
			return PeopleList.get(position).getFirstLetter().charAt(0);
		return PeopleList.get(position).getFirstLetter().charAt(0);
	}
	
	 //���ݷ��������ĸ��ֵ��ȡ���һ�γ��ָ�����ĸ��λ��	 
	public int getPositionForSection(int section)
	{
		for (int i=0;i<PeopleList.size();i++) 
		{
			String sortStr=PeopleList.get(i).getFirstLetter();
			char firstChar=sortStr.toUpperCase().charAt(0);
			if (firstChar==section)
			{
				return i;
			}
		}
		return -1;
	}
	
	//�����ݿ���ɾ������
	public void delete(long rowId){
		db.delete("table02","_id"+"="+rowId,null);
	}
	
	protected void onDestroy(){
		super.onDestroy();
		db.close();
	}
	
	//ˢ�½���
	protected void onResume() {
		super.onResume();
		onCreate(null);	
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

*/
package com.example.baidumapmotiontrack.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.baidumapmotiontrack.R;
import com.example.baidumapmotiontrack.Utility.MyActivityManager;
import com.example.baidumapmotiontrack.application.MyApp;
import com.example.baidumapmotiontrack.db.MTDatabase;
import com.example.baidumapmotiontrack.model.User;
import com.example.baidumapmotiontrack.view.AlphaView;
import com.example.baidumapmotiontrack.view.AlphaView.OnAlphaChangedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsMainActivity extends Activity implements OnAlphaChangedListener {

	// ���ݿ�
	private MTDatabase contactsDB;
	
	private MyApp myApp;

	// ������ť�ļ�����
	private MyOnClickListener listener;
	// ���
	private EditText searchField;
	private Button searchButton;
	private ListView contactsList;
	private AlphaView alphaView;
	private TextView overlay;
	private ImageView addContact;
	
	private RelativeLayout rl_addFriend,rl_new;

	private WindowManager windowManager;
	private Handler handler;
	private OverlayThread overlayThread;

	// ��ϵ�˶���
	private List<User> list;
	// �洢����ĸA-Z��һ�γ��ֵ�λ��
	private HashMap<String, Integer> alphaIndexer;
	// �Զ���������
	private ListAdapter adapter;
	
	private int userId;
	public static final String TAG="FriendsMainActivity";
	// ������ϵ�˲˵�
	public static final int MENU_CONTACTS_DELETE = Menu.FIRST;//ɾ����ϵ��
	public static final int MENU_CONTACTS_MODIFY = Menu.FIRST + 1;//�༭��ϵ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friend_main);

		// ��ȡ���ݿ�ʵ��
		contactsDB = MTDatabase.getInstance(this);

		myApp=(MyApp)getApplication();
		
		listener = new MyOnClickListener();
		// ��ʼ�����
		searchField = (EditText) findViewById(R.id.search_field);
		searchButton = (Button) findViewById(R.id.search_button);
		contactsList = (ListView) findViewById(R.id.list_view);
		addContact = (ImageView) findViewById(R.id.add_contact);
		addContact.setOnClickListener(listener);
		alphaView = (AlphaView) findViewById(R.id.alphaView);
		
		rl_addFriend=(RelativeLayout)findViewById(R.id.rl_addFriend);
		rl_new=(RelativeLayout)findViewById(R.id.rl_new);
		//btnAdd.setOnClickListener(myListener);
		rl_addFriend.setOnClickListener(addListener);
		rl_new.setOnClickListener(addListener);

		alphaView.setOnAlphaChangedListener(this);
		searchButton.setOnClickListener(listener);
		overlay = (TextView) findViewById(R.id.empty_text);
		
		//��ȡ��¼�û�Id
		userId=myApp.getUserId();

		handler = new Handler();
		overlayThread = new OverlayThread();

		// ��ʼ����ϵ�˶���
		list = new ArrayList<User>();
		// ��ʼ������ĸmap
		alphaIndexer = new HashMap<String, Integer>();

		// �����ݿ��ж�ȡ������ϵ�˱��浽��ϵ�˶�����
		list = contactsDB.getAllFri1(userId);
		setAdapter();
		//������
		contactsList.setOnCreateContextMenuListener(this);

		initOverlay();
		// listView�ĵ���¼�
		contactsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				/*User user = list.get(position);
				Toast.makeText(getApplicationContext(), user.getPhone(),
						Toast.LENGTH_SHORT).show();
				;*/
				String str=((User)adapter.getItem(position)).getUsername();
				Intent intent=new Intent();
				intent.setClass(FriendsMainActivity.this, UserInfoActivity.class);
				String name=((User)adapter.getItem(position)).getUsername();
				String phone=((User)adapter.getItem(position)).getPhone();
				//int uId=((User)adapter.getItem(position)).getId();
				int uId=contactsDB.findUserId(name);
				System.out.println("id��"+uId);
				Bundle bundle=new Bundle();
				bundle.putString("NAME", name);
				bundle.putString("PHONE", phone);
				bundle.putInt("uId", uId);
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}
	
	//���������Ĳ˵�
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		Log.v(TAG, "populate context menu");
	    // set context menu title
		menu.setHeaderTitle("��ϵ�˲���");
		// add context menu item
		menu.add(0, MENU_CONTACTS_DELETE, 0, "ɾ����ϵ��");
		menu.add(0, MENU_CONTACTS_MODIFY, 0, "�༭��ϵ��");
		}
	
	//ʵ�������Ĳ˵��ﶨ��Ĳ˵�
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo acmenuinfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final int mListPos = acmenuinfo.position;
		final String ss=String.valueOf(mListPos);
		final String pname=((User)adapter.getItem(acmenuinfo.position)).getUsername();
		final String pphone=((User)adapter.getItem(acmenuinfo.position)).getPhone();
		//final String paddress=((People)adapter.getItem(acmenuinfo.position)).getAddress();
		//final int pid=((User)adapter.getItem(acmenuinfo.position)).getId();
		final int pid=contactsDB.findUserId(pname);
		
		switch (item.getItemId()){
		case MENU_CONTACTS_DELETE: {//ɾ����ϵ��
			/*delete(pid);
			onCreate(null);*/
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("ȷ��Ҫɾ����ϵ����");
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							int a1=contactsDB.findFriId(userId,pid);
							contactsDB.deleteFriend(a1);
							int a2=contactsDB.findFriId(pid,userId);
							contactsDB.deleteFriend(a2);
							int b1=contactsDB.findChaId(userId,pid);
							contactsDB.deleteChat(b1);
							int b2=contactsDB.findChaId(pid,userId);
							contactsDB.deleteChat(b2);
							//onCreate(null);
							onRestart();
							Toast.makeText(getApplication(),"ɾ���ɹ�",Toast.LENGTH_SHORT).show();
						}
					});

			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {

							dialog.dismiss();
						}
					});
			builder.show();
			break;
		}
		case MENU_CONTACTS_MODIFY: {//�޸���ϵ��
			/*Intent intent = new Intent();
			intent.setClass(FriendsMainActivity.this,ModifyMember.class);
			Bundle bundle=new Bundle();
			bundle.putInt("PID", pid);
			bundle.putString("NAME", pname);
			bundle.putString("PHONE", pphone);
			//bundle.putString("ADDRESS", paddress);
			intent.putExtras(bundle);
			startActivity(intent);*/
			break;
		}
		}
		return super.onContextItemSelected(item);
	}
		
	private RelativeLayout.OnClickListener addListener=new
			RelativeLayout.OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.rl_addFriend:
			{
				Intent intent = new Intent();
				intent.setClass(FriendsMainActivity.this,AddFriendActivity.class);
				/*Bundle bundle=new Bundle();
				bundle.putInt("user_id1", userId);
				intent.putExtras(bundle);*/
				startActivity(intent);
				break;
			}
			case R.id.rl_new:
			{
				int num=contactsDB.numRequest(userId);
				if(num==0){
					Intent intent5 = new Intent(FriendsMainActivity.this, NoInformActivity.class);
					startActivity(intent5);
				}
				else{
					Intent intent5 = new Intent(FriendsMainActivity.this, RequestNewActivity.class);
					startActivity(intent5);
				}
				//Intent intent = new Intent();
				//intent.setClass(FriendsMainActivity.this,RequestNewActivity.class);
				/*Bundle bundle=new Bundle();
				bundle.putInt("user_id1", userId);
				intent.putExtras(bundle);*/
				//startActivity(intent);
				break;
			}
			}
		}
	};

	@Override
	public void onRestart() {
		super.onRestart();
		list=contactsDB.getAllFri1(userId);
		setAdapter();
	}
	
	public void back(View view) {

        finish();
    }

	@Override
	protected void onStop() {
		try {
			windowManager.removeViewImmediate(overlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		MyActivityManager.removeActivity(this);
	}

	public class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_button:
				String s = searchField.getText().toString();
				list = contactsDB.getRelevantContacts(s,userId);
				setAdapter();
				break;
			case R.id.add_contact:
				Log.d("MainActivity", "+ clicked");
				Intent intent = new Intent();
				intent.setClass(FriendsMainActivity.this,AddFriendActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("user_id1", userId);
				intent.putExtras(bundle);
				startActivity(intent);
				/*Intent intent = new Intent(Main.this, AddFriend.class);
				startActivity(intent);*/
				break;
			default:
				break;
			}
		}

	}


	private void setAdapter() {
		if (adapter == null) {
			adapter = new ListAdapter(this);
			contactsList.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void OnAlphaChanged(String s, int index) {
		// TODO Auto-generated method stub
		if (s != null && s.trim().length() > 0) {
			overlay.setText(s);
			overlay.setVisibility(View.VISIBLE);
			handler.removeCallbacks(overlayThread);
			handler.postDelayed(overlayThread, 700);
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				contactsList.setSelection(position);
			}
		}
	}

	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);// ��ʼ��Ϊ���ɼ�
		// ����overlay������:�������
		// WindowManager.LayoutParams(int w, int h, int _type, int _flags, int
		// _format)
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			overlay.setVisibility(View.GONE);
		}

	}

	private class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public ListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
			for (int i = 0; i < list.size(); i++) {
				// ��õ�ǰ��ϵ�˵�����ĸ
				String currentAlpha = list.get(i).getAlpha();
				// ���ǰһ����ϵ�˵�����ĸ
				String previewAlpha = (i - 1) >= 0 ? list.get(i - 1).getAlpha()
						: " ";
				// �����ǰ��ϵ������ĸ����һ����ϵ������ĸ��ͬ����������ĸ����ǰ��ϵ�����б��е�λ�ô���map��
				if (!previewAlpha.equals(currentAlpha)) {
					String alpha = list.get(i).getAlpha();
					alphaIndexer.put(alpha, i);
				}
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			User item = list.get(position);
			holder.name.setText(item.getUsername());
			holder.phone.setText(item.getPhone());

			String currentAlpha = list.get(position).getAlpha();
			String previewAlpha = (position - 1) >= 0 ? list.get(position - 1)
					.getAlpha() : " ";
			if (!previewAlpha.equals(currentAlpha)) {
				// ͷ��ĸ���б��е�һ�γ���
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentAlpha);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}

	}

	private final class ViewHolder {
		TextView alpha;
		TextView name;
		TextView phone;

		public ViewHolder(View v) {
			alpha = (TextView) v.findViewById(R.id.alpha_text);
			name = (TextView) v.findViewById(R.id.name);
			phone = (TextView) v.findViewById(R.id.phone);
		}
	}
}
