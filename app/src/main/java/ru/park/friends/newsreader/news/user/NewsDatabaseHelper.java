package ru.park.friends.newsreader.news.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import ru.park.friends.newsreader.news.NewsAPI;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "saved_news";
    private static final String COLUMN_SOURCE_ID = "source_id";
    private static final String COLUMN_SOURCE_NAME = "source_name";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_URL_TO_IMAGE = "url_to_image";
    private static final String COLUMN_PUBLISHED_AT = "published_at";
    private static final String COLUMN_CONTENT = "content";

    public NewsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_SOURCE_ID + " INTEGER, " +
                COLUMN_SOURCE_NAME + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_URL_TO_IMAGE + " TEXT, " +
                COLUMN_PUBLISHED_AT + " TEXT, " +
                COLUMN_CONTENT + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveArticle(NewsAPI.Article article){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SOURCE_ID, article.source.id);
        cv.put(COLUMN_SOURCE_NAME, article.source.name);
        cv.put(COLUMN_TITLE, article.title);
        cv.put(COLUMN_AUTHOR, article.author);
        cv.put(COLUMN_DESCRIPTION, article.description);
        cv.put(COLUMN_URL, article.url);
        cv.put(COLUMN_URL_TO_IMAGE, article.urlToImage);
        cv.put(COLUMN_PUBLISHED_AT, article.publishedAt.toString());
        cv.put(COLUMN_CONTENT, article.content);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Log.d("MY_APP", "Couldn't insert article");
        }else {
            Log.d("MY_APP", "Article inserted");
        }
    }

    Cursor getAllSavedArticles(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
