package ddwu.mobile.final_project.ma02_20170915;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class ReviewDBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "review_db";
    public final static String TABLE_NAME = "review_table";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_LOCATION = "location";
    public final static String COL_DATE = "date";
    public final static String COL_GPA = "gpa";
    public final static String COL_CONTENTS = "contents";
    public final static String COL_IMG = "img";

    public ReviewDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
//                COL_TITLE + " TEXT, " + COL_LOCATION + " TEXT, " + COL_DATE + " TEXT, " + COL_GPA + " TEXT, "
//                + COL_CONTENTS + " TEXT)";
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_TITLE + " TEXT, " + COL_LOCATION + " TEXT, " + COL_DATE + " TEXT, " + COL_GPA + " TEXT, "
                + COL_CONTENTS + " TEXT, " + COL_IMG + " integer)";

        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '동대돈부리', '서울특별시 성북구 월곡2 장월로1길 동', '190506', '4', '친구가 연어덮밥만 먹는다.', " + null + " );");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '돌냄비열우동', '서울특별시 성북구 종암동 회기로3길 2', '190814', '3.5', '매운데 맛있다. 근데 너무 뜨거웠다..돌냄비라...', " + null + " );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
