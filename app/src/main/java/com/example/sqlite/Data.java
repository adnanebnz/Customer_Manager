package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Data extends SQLiteOpenHelper {

    //CONSTANTS
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String ID = "ID";

    public Data(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //this is called when the first time a database os accessed
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " +CUSTOMER_TABLE+ " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME + " TEXT, " + CUSTOMER_AGE + " INT , " + ACTIVE_CUSTOMER + " BOOL)";
    db.execSQL(createTable);
    }
    //this is called when the database version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME, customerModel.getName());
        cv.put(CUSTOMER_AGE,customerModel.getAge());
        cv.put(ACTIVE_CUSTOMER,customerModel.isActive());
       long insert = db.insert(CUSTOMER_TABLE,null,cv);
        if(insert==-1){
            return false;
        }
        else {return true;}
    }
    public boolean deleteOne(CustomerModel customerModel){
        //if a customerModel was found in the database return true else false
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+CUSTOMER_TABLE+" WHERE "+ID+ " = "+customerModel.getId();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            
        return true;
        }
else {
    return false;
        }

    }
    public List<CustomerModel> getEveryone(){
        List <CustomerModel> returnList = new ArrayList<>();
        //GET THE DATA FROM THE DATABASE
        String queryString = "SELECT * FROM " +CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(queryString,null,null);
        if(cursor.moveToFirst()){
            //loop through the cursor and create new customer objects. put them in the list.
            do{
            int customerId = cursor.getInt(0);
            String customerName = cursor.getString(1);
            int customerAge = cursor.getInt(2);
            boolean customerIsActive = cursor.getInt(3) == 1 ? true : false;

            CustomerModel newCustomer = new CustomerModel(customerId,customerName,customerAge,customerIsActive);
            returnList.add(newCustomer);

            }
            while(cursor.moveToNext());

        }
        else{
        //failure do not add anything to the list.
        }
        //close both cursor and the database when done
        cursor.close();
        db.close();
        return returnList;
    }

}
