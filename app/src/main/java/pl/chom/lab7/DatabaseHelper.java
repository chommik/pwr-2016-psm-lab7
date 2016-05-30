package pl.chom.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Castle.db";
    public static final String TABLE_NAME = "castle_table";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "CASTLE_NAME";
    public static final String COL_DESC = "CASTLE_DESC";
    public static final String COL_IMAGE_URL = "IMAGE";
    public static final String COL_LATITUDE = "LAT";
    public static final String COL_LONGITUDE = "LONG";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, CASTLE_NAME TEXT, CASTLE_DESC TEXT, IMAGE TEXT, LAT TEXT, LONG TEXT)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES " +
                "(1, \"Zamek 1\", \"Opis zamku 1\", \"https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Panorama_of_Malbork_Castle%2C_part_4.jpg/220px-Panorama_of_Malbork_Castle%2C_part_4.jpg\", 0, 0), " +
                "(2, \"Zamek 2\", \"Opis zamku 2\", \"https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Zamek_w_ch%C4%99cinie_panorama.jpg/220px-Zamek_w_ch%C4%99cinie_panorama.jpg\", 0, 0), " +
                "(3, \"Zamek 3\", \"Opis zamku 3\", \"https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Gola_Castle.jpg/220px-Gola_Castle.jpg\", 0, 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String castleName, String castleDesc, String imageSrc, String lat, String longi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, castleName);
        contentValues.put(COL_DESC, castleDesc);
        contentValues.put(COL_IMAGE_URL, imageSrc);
        contentValues.put(COL_LATITUDE, lat);
        contentValues.put(COL_LONGITUDE, longi);

        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Map<String, String> getElement(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Map<String, String> map = new HashMap<>();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME + " where ID = ?", new String[] {id});
        if(result.getCount() > 0){
            result.moveToFirst();
            map.put("name", result.getString(1));
            map.put("desc", result.getString(2));
            map.put("image", result.getString(3));
            map.put("lat", result.getString(4));
            map.put("long", result.getString(5));
        }
        result.close();
        return map;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    public Integer delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] { id });
    }

    public boolean update(String id, String castleName, String castleDesc, String imageSrc,
                          String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, castleName);
        contentValues.put(COL_DESC, castleDesc);
        contentValues.put(COL_IMAGE_URL, imageSrc);
        contentValues.put(COL_LATITUDE, lat);
        contentValues.put(COL_LONGITUDE, lon);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }
}
