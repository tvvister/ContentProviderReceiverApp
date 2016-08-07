package tvvister.contentproviderreceiverapp;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listView) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final Uri baseUri = Uri.parse("content://tvvister.contentproviderapplication.component/artists");
        Cursor cursor = getContentResolver().query(baseUri, null, null, null, null);

        String[] from = {"name", "description"};
        int[] to = {R.id.nameTextVeiw, R.id.descTextVeiw};
        final SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter (this, R.layout.item_layout, cursor, from, to);

        listView.setAdapter(simpleCursorAdapter);

        getContentResolver().registerContentObserver(baseUri, true, new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                Cursor newCursor = getContentResolver().query(baseUri, null, null, null, null);
                if (cursor.moveToFirst()) {
                    simpleCursorAdapter.changeCursor(newCursor);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    Toast.makeText(MainActivity.this
                            ,"Artist (id = " + uri.getLastPathSegment() + ") has been changed. New name is " + name
                            , Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}
