package ddwu.mobile.final_project.ma02_20170915;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    EditText etName;
    EditText etPhone;
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_detail);

        Intent intent = getIntent();

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);

        etName.setText(intent.getStringExtra("name"));
        etPhone.setText(intent.getStringExtra("phone"));
        etAddress.setText(intent.getStringExtra("address"));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                finish();
                break;
        }
    }
}

