package com.example.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String newId;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.www);
    }

    public void add(View view){
        Uri uri = Uri.parse("content://com.example.contentprovider/book");
        ContentValues contentValues = new ContentValues();
        contentValues.put("authorName","wb");
        contentValues.put("bookName","qsmy");
        contentValues.put("price",80.8);
        contentValues.put("number",60);
        Uri newUri = getContentResolver().insert(uri,contentValues);
        newId = newUri.getPathSegments().get(1);
        textView.setText(newId);
    }

    public void query(View view){
        Uri uri = Uri.parse("content://com.example.contentprovider/book");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String authorName = cursor.getString(cursor.getColumnIndex("authorname"));
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String bookName = cursor.getString(cursor.getColumnIndex("bookname"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                textView.append("[authorName:"+authorName+",id:"+id+",bookName:"+bookName+",price:"+price+",number:"+number+"]\n");
            }
        }
    }


    public void update(View view){
        Uri uri = Uri.parse("content://com.example.contentprovider/book/1");
        ContentValues contentValues = new ContentValues();
        contentValues.put("authorName","wwwbbb");
        getContentResolver().update(uri,contentValues,null,null);
    }
}
