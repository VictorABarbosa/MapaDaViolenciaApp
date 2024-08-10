package br.ufc.dc.sd4mp.cactacea;

/**
 * Created by Lucas on 24/05/2015.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CityDAO extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "CitiesCeara.db";
    public static final int DATABASE_VERSION = 1;


    public CityDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public CityDAO(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table cities (");
        sql.append("id integer primary key autoincrement,");
        sql.append("nameCity text,");
        sql.append("UF text,");
        sql.append("population Double,");
        sql.append("rateHomicides Double,");
        sql.append("nacionalPosition Integer,");
        sql.append("lat Double,");
        sql.append("lng Double)");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cities");
        onCreate(db);
    }

    public void create(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameCity", city.getNameCity());
        contentValues.put("UF", city.getUF());
        contentValues.put("population", city.getPopulation());
        contentValues.put("rateHomicides", city.getRateHomicides());
        contentValues.put("nacionalPosition", city.getNacionalPosition());
        contentValues.put("lat", city.getLat());
        contentValues.put("lng", city.getLng());

        long id = db.insert("cities", null, contentValues);
        Log.v("SQLite", "create id = " + id);

    }

    public City retrieve(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from cities where id = ?", new String[]{Integer.toString(id)});
        City city = null;
        result.moveToFirst();
        if (result != null && result.getCount() > 0) {
            city = new City();
            city.setId(id);
            city.setNameCity( result.getString(1) );
            city.setUF(result.getString(2));
            city.setPopulation((int)result.getDouble(3));
            city.setRateHomicides(result.getDouble(4));
            city.setNacionalPosition(result.getInt(5));
            city.setLat(result.getDouble(6));
            city.setLng(result.getDouble(7));
        }
        return city;
    }

    public void update(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nameCity", city.getNameCity());
        contentValues.put("UF", city.getUF());
        contentValues.put("population", city.getPopulation());
        contentValues.put("rateHomicides", city.getRateHomicides());
        contentValues.put("nacionalPosition", city.getNacionalPosition());
        contentValues.put("lat", city.getLat());
        contentValues.put("lng", city.getLng());

        db.update("cities", contentValues, " id = ? ", new String[]{Integer.toString(city.getId())});
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cities", " id = ? ", new String[]{Integer.toString(id)});
    }

    public List<City> list() {
        List<City> cities = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from cities order by id", null);//order by desc cration_time
        if (result != null && result.getCount() > 0) {
            cities = new ArrayList<City>();
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                City city = new City();
                city.setId(result.getInt(0));
                city.setNameCity(result.getString(1));
                city.setUF(result.getString(2));
                city.setPopulation((int)result.getDouble(3));
                city.setRateHomicides(result.getDouble(4));
                city.setNacionalPosition(result.getInt(5));
                city.setLat(result.getDouble(6));
                city.setLng(result.getDouble(7));
                cities.add(city);
                result.moveToNext();
            }
        }
        if (cities != null) Log.v("SQLite List", "size list = " + cities.size());
        return cities;
    }

    public City queryName(String nameCity) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from cities where nameCity like '" + nameCity + "'", null );
        City city = null;
        result.moveToFirst();
        if (result != null && result.getCount() > 0) {
            city = new City();
            city.setId(result.getInt(0));
            city.setNameCity( result.getString( 1 ) );
            city.setUF(result.getString(2));
            city.setPopulation((int)result.getDouble(3));
            city.setRateHomicides(result.getDouble(4));
            city.setNacionalPosition(result.getInt(5));
            city.setLat(result.getDouble(6));
            city.setLng(result.getDouble(7));
        }
        return city;
    }


}
