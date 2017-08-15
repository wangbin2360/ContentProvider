package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Switch;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class MyProvider extends ContentProvider {
    private static final int BOOK_DIR = 0;
    private static final int BOOK_ITEM = 1 ;
    private static final int CATEGORY_DIR = 2;
    private static final int CATEGORY_ITEM = 3;

    private static final String AUTHORITY = "com.example.contentprovider";
    private static UriMatcher uriMatcher ;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);//1权限，2一般写成数据库名，3匹配返回值
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
    }
    @Override
    public boolean onCreate() {
        Connector.getDatabase();//创建数据库
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase sqLiteDatabase = Connector.getWritableDatabase();//获得数据库连接
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){//判断uri类型，并返回匹配值
            case BOOK_DIR:
                cursor = sqLiteDatabase.query("Book",strings,s,strings1,null,null,s1);
                break;
            case BOOK_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = sqLiteDatabase.query("Book",strings,"id=?",new String[]{id},null,null,s1);//按id查询
                break;
            case  CATEGORY_DIR:
                break;
            case  CATEGORY_ITEM:
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
      switch(uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+".book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd."+AUTHORITY+".book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+".category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd."+AUTHORITY+".category";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                Book book = new Book();
                book.setAuthorName((String)contentValues.get("authorName"));
                book.setPrice((double)contentValues.get("price"));
                book.setBookName((String)contentValues.get("bookName"));
                book.setNumber((int)contentValues.get("number"));
                book.save();
                uriReturn = Uri.parse("content://"+AUTHORITY+"/book/"+book.getId());
                break;
            case CATEGORY_DIR:
                break;
            case CATEGORY_ITEM:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = Connector.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                sqLiteDatabase.delete("Book",s,strings);
                break;
            case BOOK_ITEM:
                String id = uri.getPathSegments().get(1);
                sqLiteDatabase.delete("Book","id=?",new String[]{id});
                break;
            case CATEGORY_DIR:
                break;
            case CATEGORY_ITEM:
                break;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = Connector.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                sqLiteDatabase.update("Book",contentValues,s,strings);
                break;
            case BOOK_ITEM:
                String id = uri.getPathSegments().get(1);
                sqLiteDatabase.update("Book",contentValues,"id=?",new String[]{id});
                break;
            case CATEGORY_DIR:
                break;
            case CATEGORY_ITEM:
                break;
        }
        return 0;
    }

}
