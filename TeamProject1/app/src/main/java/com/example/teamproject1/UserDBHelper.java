package com.example.teamproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class UserDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BTS_DB";
    public static final String TABLE_NAME= "User";
    public static final String COL_1="ID";
    public static final String COL_2="userID";
    public static final String COL_3="userPW";
    public static final String COL_4="name";

    public static final String TABLE_NAME2= "Favorite";
    public static final String COL_1_2="ID";
    public static final String COL_2_2="userSID";
    public static final String COL_3_2="Station";

    public static final String TABLE_NAME3= "RecentlyUsed";
    public static final String COL_1_3="ID";
    public static final String COL_2_3="userSID";
    public static final String COL_3_3="Start";
    public static final String COL_4_3="Way";
    public static final String COL_5_3="End_";
    public static final String COL_6_3="type";

    public static final String TABLE_NAME4= "RecentlySearched";
    public static final String COL_1_4="ID";
    public static final String COL_2_4="userSID";
    public static final String COL_3_4="Station";

    private SQLiteDatabase sDB;

    public UserDBHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,2);

    }

    //실행할 때 테이블 최초 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, "
                + COL_3 + " TEXT, "
                + COL_4 + " TEXT); ");
        db.execSQL("CREATE TABLE " + TABLE_NAME2
                + " (" + COL_1_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_2 + " TEXT, "
                + COL_3_2 + " TEXT); ");
        db.execSQL("CREATE TABLE " + TABLE_NAME3
                + " (" + COL_1_3 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_3 + " TEXT, "
                + COL_3_3 + " TEXT, "
                + COL_4_3 + " TEXT, "
                + COL_5_3 + " TEXT, "
                + COL_6_3 + " INTEGER); ");
        db.execSQL("CREATE TABLE " + TABLE_NAME4
                + " (" + COL_1_4 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2_4 + " TEXT, "
                + COL_3_4 + " TEXT); ");
    }

    //버전 업그레이드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //테이블에 정보 넣기
    public boolean insertDatatoUser(String userID, String userPW, String name){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,userID);
        contentValues.put(COL_3,userPW);
        contentValues.put(COL_4,name);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertDatatoFavorite(String userID, String station){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_2,userID);
        contentValues.put(COL_3_2,station);

        long result = db.insert(TABLE_NAME2,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertDatatoRecentlyUsed(String userID, String start, String way, String end, int type){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        List<Recent> list = getRecentlyUsedData(userID);

        contentValues.put(COL_2_3,userID);
        contentValues.put(COL_3_3,start);
        contentValues.put(COL_4_3,way);
        contentValues.put(COL_5_3,end);
        contentValues.put(COL_6_3,type);

        long result = db.insert(TABLE_NAME3,null,contentValues);

        if(list != null && list.size() > 9){
            for(int i = 0; list.size() != 9;){
                Recent tmp = list.get(i);
                deleteRecData(tmp.getSid());
                list.remove(i);
            }
        }

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertDatatoRecentlySearched(String userID, String station){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        List<Searched> list = getRecentlySearchedData(userID);

        contentValues.put(COL_2_4,userID);
        contentValues.put(COL_3_4,station);

        long result = db.insert(TABLE_NAME4,null,contentValues);

        if(list != null && list.size() > 4){
            for(int i = 0; list.size() != 4;){
                Searched tmp = list.get(i);
                deleteRecData(tmp.getSid());
                list.remove(i);
            }
        }

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //선택한 데이터만 조회
    public User getUserDatabyId(String id){
        sDB = this.getWritableDatabase();
        Cursor res = sDB.rawQuery("select * from "+ TABLE_NAME+ " where userID='"+id+"';",null);
        res.moveToFirst();

        User user = new User();

        user.setSid(res.getString(0));
        user.setId(res.getString(1));
        user.setPw(res.getString(2));
        user.setName(res.getString(3));

        return user;
    }

    //데이터 전체 조회
    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME ,null);
        return res;
    }

    //데이터 수정
    public boolean updateData(String id,String userID,String userPW,String name){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,userID);
        contentValues.put(COL_3,userPW);
        contentValues.put(COL_4,name);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }
    //테이블 삭제
    public int deleteUserData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[] {id});
    }

    public int deleteFavData(String station){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2,"Station=?",new String[] {station});
    }

    public int deleteRecData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME3,"ID=?",new String[] {id});
    }

//    public int deleteRecentData(String station) {
//
//    }

//    public boolean openDataBase() throws SQLException
//    {
//        String mPath = DB_PATH + DATABASE_NAME;
//        //Log.v("mPath", mPath);
//        sDB = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
//        return sDB != null;
//    }
//
//    @Override
//    public synchronized void close()
//    {
//        if(sDB != null)
//            sDB.close();
//        super.close();
//    }

    public List getUserData()
    {
        sDB = this.getReadableDatabase();
        try
        {
            String sql ="SELECT * FROM " + TABLE_NAME;

            // 모델 넣을 리스트 생성
            List userList = new ArrayList();

            // TODO : 모델 선언
            User user = null;

            Cursor mCur = sDB.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    user = new User();

                    // TODO : Record 기술
                    user.setSid(mCur.getString(0));
                    user.setId(mCur.getString(1));
                    user.setPw(mCur.getString(2));
                    user.setName(mCur.getString(3));

                    // 리스트에 넣기
                    userList.add(user);
                }

            }
            return userList;
        }
        catch (SQLException mSQLException)
        {
            Log.e("11", "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public List getFavoriteDatabyUserSid(String userSid)
    {
        sDB = this.getReadableDatabase();
        try
        {
            String sql ="SELECT * FROM " + TABLE_NAME2 + " where userSID='"+userSid+"';";

            List favoriteList = new ArrayList();

            Favorites favorites = null;

            Cursor mCur = sDB.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    favorites = new Favorites();

                    favorites.setSid(mCur.getString(0));
                    favorites.setUserSid(mCur.getString(1));
                    favorites.setStation(mCur.getString(2));

                    // 리스트에 넣기
                    favoriteList.add(favorites);
                }

            }
            return favoriteList;
        }
        catch (SQLException mSQLException)
        {
            Log.e("11", "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public List getRecentlyUsedData(String userSid)
    {
        sDB = this.getReadableDatabase();
        try
        {
            String sql ="SELECT * FROM " + TABLE_NAME3 + " where userSID='"+userSid+"';";

            List recentList = new ArrayList();

            Recent recent = null;

            Cursor mCur = sDB.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    recent = new Recent();

                    recent.setSid(mCur.getString(0));
                    recent.setUserSid(mCur.getString(1));
                    recent.setStart(mCur.getString(2));
                    recent.setWay(mCur.getString(3));
                    recent.setEnd(mCur.getString(4));
                    recent.setType(mCur.getInt(5));

                    // 리스트에 넣기
                    recentList.add(recent);
                }

            }
            return recentList;
        }
        catch (SQLException mSQLException)
        {
            Log.e("11", "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public List getRecentlySearchedData(String userSid)
    {
        sDB = this.getReadableDatabase();
        try
        {
            String sql ="SELECT * FROM " + TABLE_NAME4 + " where userSID='"+userSid+"';";

            List list = new ArrayList();

            Searched res = null;

            Cursor mCur = sDB.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    res = new Searched();

                    res.setSid(mCur.getString(0));
                    res.setUserSid(mCur.getString(1));
                    res.setStation(mCur.getString(2));
                    // 리스트에 넣기
                    list.add(res);
                }

            }
            return list;
        }
        catch (SQLException mSQLException)
        {
            Log.e("11", "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}
