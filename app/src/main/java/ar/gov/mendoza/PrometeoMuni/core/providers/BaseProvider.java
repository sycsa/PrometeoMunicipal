package ar.gov.mendoza.PrometeoMuni.core.providers;

//import com.cids.siga.core.data.DatabaseHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Locale;

import ar.gov.mendoza.PrometeoMuni.core.data.DatabaseHelper;

public class BaseProvider extends ContentProvider
{

	
	protected String table;
	protected DatabaseHelper dbHelper;
	
	protected static final int ROWS =1; 
	protected static final int ROW_ID =2;
	static
	{/*
		ROWS = 1;
		ROW_ID= 2;
		*/
	}
	
	protected UriMatcher baseUriMatcher;
	protected Uri BASE_CONTENT_URI;
	
	@Override
	public boolean onCreate() {
		this.dbHelper = new DatabaseHelper(getContext());
		return false;
	}

	public void configSecuence(long pValue)
	{	
		if (pValue<=0)return; // si viene cero o menos  no se actualiza la secuencia
	
		//
		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		String strQueryUpdateSecuencia = "UPDATE SQLITE_SEQUENCE SET seq = " + pValue + " WHERE upper(name) = '" +  table + "'";
//		String strQueryInsertSecuencia = "INSERT INTO SQLITE_SEQUENCE (seq,name) values (" + pValue + ", '" +  table + "')";
		String strQueryExists =	"SELECT * FROM SQLITE_SEQUENCE WHERE upper(name) = '" +  table + "'";
		Cursor cursor = db.rawQuery(strQueryExists, null);
		long seqvalue = 0;
		if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
	    {   /// si no hay secuencias.. no deberia haber registros en actas.  
			 seqvalue = cursor.getLong(cursor.getColumnIndex("seq"));
			//hay registros de secuencia ... se puede hacer update
			 if (pValue > seqvalue) 
			 {	db.beginTransaction();
			 	try
			 	{
			 		ContentValues values = new ContentValues();
					 values.put("seq", pValue);
				//db.rawQuery(strQueryUpdateSecuencia, null);
			 	  db.update("SQLITE_SEQUENCE", values, "name=?", new String[]{table});
			 	  
				db.setTransactionSuccessful();
			 	} finally
			 	{
			 		db.endTransaction();
			 	}
			 }
	    }
		else //el registro de la secuenca de la tabla no existia... por lo que lo vamos agenerar nosotros con un insert
		{
			db.beginTransaction();
			try
			{
				
			//db.rawQuery(strQueryInsertSecuencia, null);
				ContentValues values = new ContentValues();
				values.put("name", table);
				values.put("seq", pValue);
			//db.rawQuery(strQueryUpdateSecuencia, null);
		 	  db.insert("SQLITE_SEQUENCE",null, values);
			
			db.setTransactionSuccessful();
			}
			finally
			{
			db.endTransaction();
			}
		}
		
		
	}
	
	public long nextId( ContentValues values) {
	    long rowId = -1;

	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    db.beginTransaction();
	    try {
	        // fill values ...
	        // insert a valid row into your table
	        rowId = db.insert(table, null, values);

	        // NOTE: we don't call  db.setTransactionSuccessful()
	        // so as to rollback and cancel the last changes
	    } finally {
	        db.endTransaction();
	    }
	    return rowId;
	}
	public Cursor consulta(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		 //columnas    whereClause ?    parametrosWhere?[]  groupby[]  	filtros para el group
		Cursor query = db.query(table, projection, selection      , selectionArgs     , null      , null                  ,    sortOrder);
		
		//db.close();
		
		return query;
	}
	
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
								//columnas             whereClause ?     parametrosWhere?[]     groupby[]  	filtros para el group
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String where = selection;
		int uriType = baseUriMatcher.match(uri);
	    if( uriType == ROW_ID)
	    {
	                where = "_id=" + uri.getLastPathSegment();
	    } else if(uriType != ROWS)
	    {
	    	 throw new IllegalArgumentException("URI Desconocido");
	    }
	    
	    /*
	    if (sortOrder==null || sortOrder=="") 
	    	sortOrder = TITLE;
	    */
	    	
		Cursor query = db.query(table, projection, where      , selectionArgs     , null      , null                  ,    sortOrder);
		

		query.setNotificationUri(getContext().getContentResolver(), uri);
		return query;
	}
	
	

	static final String SINGLE_RECORD_MIME_TYPE_GENERICO = "vnd.android.cursor.item/vnd.gov.cba.deviceactas.core.providers";
	static final String MULTIPLE_RECORDS_MIME_TYPE_GENERICO = "vnd.android.cursor.dir/vnd.gov.cba.deviceactas.core.providers";

	@Override
	public String getType(Uri uri) {
	 
	    int match = baseUriMatcher.match(uri);
	 
	    switch (match)
	    {
	        case ROWS:
	            return MULTIPLE_RECORDS_MIME_TYPE_GENERICO  + "." + table.toLowerCase(Locale.getDefault());//"vnd.android.cursor.dir/vnd.sgoliver.cliente";
	        case ROW_ID:
	            return SINGLE_RECORD_MIME_TYPE_GENERICO + "." + table.toLowerCase(Locale.getDefault());//"vnd.android.cursor.item/vnd.sgoliver.cliente";
	        default:
	            //return null;
	        	throw new IllegalArgumentException("URI: No soportado " + uri);
	    }
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insert(table, null, values);

		if (id>0)
		{
			Uri newUri = ContentUris.withAppendedId(BASE_CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(newUri, null);
			return newUri;//Uri.parse(String.valueOf(id));
		}
		throw new SQLException("Failed to insert row into " + uri);
		
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = baseUriMatcher.match(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String where ="";
		 
		    if( uriType == ROW_ID)
		    {
    				where = "_id=" + uri.getLastPathSegment();
		    		if (!TextUtils.isEmpty(selection))
		    			    where = selection + " and  " +  where;
		    }
		    else if (uriType != ROWS)
		    {
		    	throw new IllegalArgumentException("URI Desconocido o Invalido " + uri);
		    }
		    else
		    {
		    	where = selection;
		    }
		    	
		int rowsAffected = 0;     
		rowsAffected = db.delete(table, where, selectionArgs);
	
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = baseUriMatcher.match(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String where = "";
		    if(uriType == ROW_ID)
		    {
		            where = "_id=" + uri.getLastPathSegment();
		            if (!TextUtils.isEmpty(selection))
	    			    where = selection + " and  " +  where;
		    }
		    else if(uriType != ROWS)
		    {
		    	throw new IllegalArgumentException("URI Desconocido o Invalido " + uri);
		    }
		    else
		    {
		    	where = selection;
		    }
		    
		int rowsAffected = 0;    	
		rowsAffected = db.update(table, values, where, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}
}
