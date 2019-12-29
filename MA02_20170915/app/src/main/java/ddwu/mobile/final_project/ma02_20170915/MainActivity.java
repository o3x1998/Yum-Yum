package ddwu.mobile.final_project.ma02_20170915;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppKeyHash();
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key은 이것 입니다 : ", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }


    public void onClick (View v) {
        Intent intent = null;

        switch (v.getId()) {
            //전체 리스트를 보여준다.
            case R.id.btn_all:
                intent = new Intent(this, AllActivity.class);
                break;
            //새로 추가한다.
            case R.id.btn_add:
                intent = new Intent(this, AddActivity.class);
                break;
        }

        if (intent != null)
            startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    public void onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            //blog 검색 액티비티로 넘어간다.
            case R.id.search_blog:
                Intent bIntent = new Intent(this, B_Activity.class);
                startActivity(bIntent);
                break;
            //local 검색 액태비티로 넘어간다.
            case  R.id.search_logical:
                Intent lIntent = new Intent(this, L_Activity.class);
                startActivity(lIntent);
                break;
            //주변 음식점 검색 액티비티로 넘어간다.
            case R.id.search_around:
                Intent aIntent = new Intent(this, AroundActivity.class);
                startActivity(aIntent);
                break;
        }
    }
}
