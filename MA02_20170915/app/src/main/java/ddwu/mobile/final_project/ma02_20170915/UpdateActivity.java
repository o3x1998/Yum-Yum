package ddwu.mobile.final_project.ma02_20170915;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.LocationTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;
//import com.kakao.kakaolink.KakaoLink;
//import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends Activity {
    final static String TAG = "UpdateActivity";
    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    EditText etTitle;
    EditText etLocation;
    EditText etDate;
    EditText etGpa;
    EditText etContents;
    ImageView imageView;
    Bitmap myImg;

    String save; // 수정 페이지에서 이미지는 수정 안되니까 초기 값 넣기 위해서.

    ReviewDBHelper helper;
    long id;

    private static final int REQUEST_TAKE_PHOTO = 200;  // 원본 이미지 사용 요청
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etTitle = findViewById(R.id.et_myTitle);
        etLocation = findViewById(R.id.et_myLocation);
        etDate = findViewById(R.id.et_myDate);
        etGpa = findViewById(R.id.et_myGpa);
        etContents = findViewById(R.id.et_myContents);
        imageView = findViewById(R.id.image_update);

        helper = new ReviewDBHelper(this);

        id = getIntent().getLongExtra("id", 0);
    }

    protected  void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();
        helper = new ReviewDBHelper(this);

        id = getIntent().getLongExtra("id", 0);
        //select * from contact_table where _id = id

        Cursor cursor = db.rawQuery( "select * from " + ReviewDBHelper.TABLE_NAME + " where " + ReviewDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            etTitle.setText( cursor.getString( cursor.getColumnIndex(ReviewDBHelper.COL_TITLE) ) );
            etLocation.setText( cursor.getString( cursor.getColumnIndex(ReviewDBHelper.COL_LOCATION) ) );
            etDate.setText( cursor.getString( cursor.getColumnIndex(ReviewDBHelper.COL_DATE) ) );
            etGpa.setText( cursor.getString( cursor.getColumnIndex(ReviewDBHelper.COL_GPA) ) );
            etContents.setText( cursor.getString( cursor.getColumnIndex(ReviewDBHelper.COL_CONTENTS) ) );

            try {
                File imgFile = new File(cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_IMG)));
                save = cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_IMG));

                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    myImg = myBitmap;
                    imageView.setImageBitmap(myBitmap);
                }
            }
            catch (Exception ex) {
                imageView.setImageResource(R.mipmap.food);
            }
        }
        cursor.close();
        helper.close();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_share:
                if (etTitle.getText().toString().equals("") || etGpa.getText().toString().equals("")
                        || etLocation.getText().toString().equals("") || etContents.getText().toString().equals("")) {
                    Toast.makeText(this, "입력이 안 된 항목이 있습니다!", Toast.LENGTH_SHORT).show();
                    break;
                }
                shareKakao();
                break;
            case R.id.btn_update:
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues row = new ContentValues();

                row.put(ReviewDBHelper.COL_TITLE, etTitle.getText().toString());
                row.put(ReviewDBHelper.COL_LOCATION, etLocation.getText().toString());
                row.put(ReviewDBHelper.COL_DATE, etDate.getText().toString());
                row.put(ReviewDBHelper.COL_GPA, etGpa.getText().toString());
                row.put(ReviewDBHelper.COL_CONTENTS, etContents.getText().toString());
                row.put(ReviewDBHelper.COL_IMG, save);

                String whereClause = ReviewDBHelper.COL_ID + "=?";
                String[] whereArgs = new String[] { String.valueOf(id) };

                int result = db.update(ReviewDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                helper.close();

                String msg = result > 0 ? "수정되었습니다!" : "수정실패!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                finish();
                break;
            case R.id.btn_cancel2:
//                DB 데이터 업데이트 작업 취소
                finish();
                break;
        }
    }


    public void shareKakao() {
        LocationTemplate params = LocationTemplate.newBuilder(etLocation.getText().toString(),
                ContentObject.newBuilder("<yum-yum 음식점 공유> : " + etTitle.getText().toString(),
                        "http://www.kakaocorp.com/images/logo/og_daumkakao_151001.png",
                        LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .build())
                        .setDescrption("평점 : " + etGpa.getText().toString() + "\n세부내용 : " + etContents.getText().toString())
                        .build())
                .setAddressTitle("음식점 위치 찾기")
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        KakaoLinkService.getInstance().sendDefault(this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
            }
        });
    }
}