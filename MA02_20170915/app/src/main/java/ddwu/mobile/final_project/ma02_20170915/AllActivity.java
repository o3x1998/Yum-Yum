package ddwu.mobile.final_project.ma02_20170915;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AllActivity extends Activity{
    ListView list_Info = null;
    ReviewDBHelper helper;
    Cursor cursor;
    MyCursorAdapter myCursorAdapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        list_Info = (ListView)findViewById(R.id.lvReview);
        editText = (EditText)findViewById(R.id.et_name_search);

        helper = new ReviewDBHelper(this);

        myCursorAdapter = new MyCursorAdapter(this, R.layout.listview_layout, null);
        list_Info.setAdapter(myCursorAdapter);

        //수정
        list_Info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(AllActivity.this, UpdateActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        //삭제
        list_Info.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, final long id) {
//                삭제 기능 작성 - 삭제 확인 대화상자 출력
                final int position = pos;

                SQLiteDatabase db = helper.getWritableDatabase();
                cursor = db.rawQuery("SELECT * FROM "
                        + helper.TABLE_NAME + " WHERE _id='" + id + "';", null);

                while (cursor.moveToNext()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AllActivity.this);
                    builder.setTitle("정보 삭제");
                    builder.setMessage(cursor.getString(cursor.getColumnIndex(helper.COL_TITLE)) + "의 정보를 삭제하시겠습니까?");
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        DB 삭제 수행
                            SQLiteDatabase db = helper.getWritableDatabase();
                            String whereClause = helper.COL_ID + "=?";
                            String[] whereArgs = new String[]{String.valueOf(id)};
                            db.delete(helper.TABLE_NAME, whereClause, whereArgs);
                            helper.close();

//                        새로운 DB 내용으로 리스트뷰 갱신
                            readAllContacts();

                            Toast.makeText(AllActivity.this, "정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("취소", null);
                    builder.show();
                }
                return true;
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_name_search:
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    String result = "";

                    SQLiteDatabase db = helper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM "
                            + ReviewDBHelper.TABLE_NAME + " WHERE title LIKE '%" + editText.getText().toString() + "%';", null);

                    while (cursor.moveToNext()) {
                        String title = cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_TITLE));
                        String location = cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_LOCATION));
                        String date = cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_DATE));
                        String gpa = cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_GPA));

                        result += "리뷰 제목 : " + title + "\n위치 : " + location + "\n날짜 : " + date + "\n평점 : " + gpa + "\n\n";
                    }

                    if(result.equals("")) {
                        Toast.makeText(this, "해당 리뷰를 찾을 수 없습니다!", Toast.LENGTH_SHORT).show();
                        helper.close();
                        cursor.close();
                        break;
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AllActivity.this);
                        builder.setTitle("검색 결과");
                        builder.setMessage(result);

                        builder.show();
                        helper.close();
                        cursor.close();
                        break;
                    }
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        readAllContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }


    private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + ReviewDBHelper.TABLE_NAME, null);

        myCursorAdapter.changeCursor(cursor);
        helper.close();
    }
}
