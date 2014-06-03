/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.data;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseAdapter {

	static final String dbName="iaeste12_mobile";
	
	static final String phoneTable="app_phone_numbers";
	static final String delegateTable="app_delegates";
	
	static final String colCountry = "country";
	static final String colLcName = "lcName";
	static final String colFullName = "delegateName";
	
	static final String colID="id";
	static final String colVisible="visible";
	static final String colName="name";
	static final String colNumber="number";
	
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static Context mContext;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, dbName, null, 2);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

	    	db.execSQL("CREATE TABLE "  + delegateTable +
				  "(" + colCountry + " varchar(200), " + colLcName + " varchar(200), " + colFullName + " varchar(255) );");
	    	
        
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		db.execSQL("DROP TABLE "+phoneTable);
    		db.execSQL("DROP TABLE "+delegateTable);
    		onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public DatabaseAdapter(Context ctx) {
        DatabaseAdapter.mContext = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public DatabaseAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
	public void updatePhoneNumber(String id, String visible, String name, String number){

	   ContentValues cv=new ContentValues();
	   cv.put(colVisible, visible);
	   cv.put(colName, name);
	   cv.put(colNumber, number);
	   mDb.update(phoneTable, cv, colID+"=?", new String []{id});  
	    
	}
	
	public void updateDelegates (List<Delegate> delegates)
	{
		   mDb.execSQL("DELETE FROM "+delegateTable);
		   
		   ContentValues cv=new ContentValues();

		   for (Delegate delegate:delegates)
		   {
			   cv.put(colCountry, delegate.getCountry().getName());
			   cv.put(colFullName, delegate.getFullName());
			   cv.put(colLcName, delegate.getLc());
			   
			   mDb.insert(delegateTable, null, cv);
		   }
	}
	
	
	public Cursor getAllPhoneNumbers(){

		return mDb.rawQuery("SELECT * FROM "+ phoneTable, new String [] {});
	    
	}
	
	public List<String> getAllCountries ()
	{
		  
		Cursor cur = mDb.rawQuery("SELECT DISTINCT " + colCountry + " FROM "+ delegateTable, new String [] {});
		List<String> output = new LinkedList<String>();
		for (int i = 0; i < cur.getCount(); i++)
		{
			cur.moveToPosition(i);
			output.add(cur.getString(0));
		}
		cur.close();
		return output;
	}

	public List<String> getLCs (ECountry country)
	{
		Cursor cur = mDb.rawQuery("SELECT DISTINCT " + colLcName + " FROM "+ delegateTable + 
								  " WHERE " + colCountry + " = '" + country.getName() + "' AND " + colLcName + " <>  '';",
								  new String [] {});
		List<String> output = new LinkedList<String>();
		for (int i = 0; i < cur.getCount(); i++)
		{
			cur.moveToPosition(i);
			output.add(cur.getString(0));
		}
		cur.close();
		return output;
	}	
	
	public List<String> getDelegateList (ECountry country, String lc)
	{
		Cursor cur = mDb.rawQuery("SELECT " + colFullName + " FROM "+ delegateTable + " WHERE " + colCountry + " = '" + country.getName() + "' AND " + colLcName + " = '" + lc + "';",
								  new String [] {});
		List<String> output = new LinkedList<String>();
		for (int i = 0; i < cur.getCount(); i++)
		{
			cur.moveToPosition(i);
			output.add(cur.getString(0));
		}
		cur.close();
		return output;
	}	
	
}
