package ddwu.mobile.final_project.ma02_20170915;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyCursorAdapter extends CursorAdapter {
    LayoutInflater inflater;
    Cursor cursor;

    public MyCursorAdapter(Context context, int layout, Cursor c) {
        // 가능하면 생성자에서 layout도 전달받아서 사용할 수 있도록 정의하는게 좋다.
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // inflater를 사용하여 ListView 내부에 표시할 view를 inflation
        View listItemLayout = inflater.inflate(R.layout.listview_layout, parent, false);
        return listItemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // ListView 내부에 표시할 뷰에 cursor의 데이터 연결
        // 이 부분은 ViewHolder를 사용하면 더 효율적인 코드가 된다.

        TextView list_title = (TextView)view.findViewById(R.id.tv_title);
        TextView list_location = (TextView)view.findViewById(R.id.tv_location);
        TextView list_gpa = (TextView)view.findViewById(R.id.tv_gpa);
        list_title.setText(cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_TITLE)));
        list_location.setText(cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_LOCATION)));
        list_gpa.setText(cursor.getString(cursor.getColumnIndex(ReviewDBHelper.COL_GPA)));
    }
}
