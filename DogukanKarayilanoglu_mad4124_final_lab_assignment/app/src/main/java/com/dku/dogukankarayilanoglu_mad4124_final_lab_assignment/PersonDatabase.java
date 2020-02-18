package com.dku.dogukankarayilanoglu_mad4124_final_lab_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PersonDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "persons";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "person";
    public static final String ROW_ID = "personId";

    public static final String ROW_NAME = "name";
    public static final String ROW_LAST_NAME = "lastName";
    public static final String ROW_ADDRESS = "address";
    public static final String ROW_PHONE_NUMBER = "phoneNumber";

    public PersonDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROW_NAME + " TEXT NOT NULL, " + ROW_LAST_NAME + " TEXT NOT NULL, " + ROW_ADDRESS + " TEXT, " +  ROW_PHONE_NUMBER + " TEXT NOT NULL);";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public ArrayList<Person> loadPersons() {

        ArrayList personList = new ArrayList();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            Person person = new Person();

            person.setPersonId(cursor.getInt(0));
            person.setFirstName(cursor.getString(1));
            person.setLastName(cursor.getString(2));
            person.setAddress(cursor.getString(3));
            person.setPhoneNumber(cursor.getString(4));


            personList.add(person);

        }
        cursor.close();
        db.close();
        return  personList;
    }


    public void addPerson(Person person){

        ContentValues values = new ContentValues();

        values.put(ROW_NAME,person.getFirstName());
        values.put(ROW_LAST_NAME,person.getLastName());
        values.put(ROW_ADDRESS,person.getAddress());
        values.put(ROW_PHONE_NUMBER,person.getPhoneNumber());


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();


    }

    public Person getPerson(int id){
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ROW_ID +"="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        Person person = new Person();

        while (cursor.moveToNext()){

            person.setPersonId(cursor.getInt(0));
            person.setFirstName(cursor.getString(1));
            person.setLastName(cursor.getString(2));
            person.setAddress(cursor.getString(3));
            person.setPhoneNumber(cursor.getString(4));

        }

        return  person;
    }

    public  void deletePerson(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String where = ROW_ID + "=" + id;
        db.delete(TABLE_NAME,where,null);
        db.close();

    }

    public  void updatePerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        String where = ROW_ID + " = " + person.getPersonId();

        ContentValues values = new ContentValues();

        values.put(ROW_NAME,person.getFirstName());
        values.put(ROW_LAST_NAME,person.getLastName());
        values.put(ROW_ADDRESS,person.getAddress());
        values.put(ROW_PHONE_NUMBER,person.getPhoneNumber());

        db.update(TABLE_NAME,values,where,null);
        db.close();

    }

}
