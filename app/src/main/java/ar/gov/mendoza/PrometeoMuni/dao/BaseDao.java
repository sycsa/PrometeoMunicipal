package ar.gov.mendoza.PrometeoMuni.dao;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;

/**
 * Define the basic method to work with database: insert, update, delete, get...
 * 
 * @author huyletran84@gmail.com
 */


public abstract class BaseDao<T> implements IBaseDao<T> {

	//antes era privado
	protected Class<T> tClass;
	/**
	 * The application context.
	 */
	protected Context mContext;
	/**
	 * Keep the static connection to database.
	 */
	protected SQLiteDatabase mDB;
	/**
	 * The default constructor.
	 */
	public BaseDao(Class<T> tClass) {
		mContext = GlobalStateApp.mContext;
		if (GlobalStateApp.mDatabaseConnection != null) {
			mDB = GlobalStateApp.mDatabaseConnection.getDatabase();
		}
		this.tClass = tClass;
	}

	public void createTable(SQLiteDatabase db) {
		if (getTableName(tClass) == null) {
			return;
		}
		StringBuffer sql = new StringBuffer("CREATE TABLE ");
		sql.append(getTableName(tClass));
		sql.append(" ( ");//_id INTEGER PRIMARY KEY AUTOINCREMENT
		boolean _firstColumn = true;
		for (Field field : tClass.getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				if (_firstColumn==true)
				{	
					_firstColumn = false;
				}
				else
				{
					sql.append(", ");
				}
				sql.append(fieldEntityAnnotation.name());
				sql.append(" ");
				sql.append(fieldEntityAnnotation.type());
				sql.append(" ");
				if(fieldEntityAnnotation.isPrimaryKey())
					sql.append("PRIMARY KEY ");
				
				if(fieldEntityAnnotation.isAutoincrement())
					sql.append("AUTOINCREMENT ");
				
				if(!fieldEntityAnnotation.defaultValue().equals("NULL"))
					sql.append("default('").append(fieldEntityAnnotation.defaultValue()).append("') ");
				
			}
		}
		sql.append(");");
		db.execSQL(sql.toString());
	}
	/**
	 * Get record by specified id.
	 * 
	 * @param id
	 *            specified id
	 * @return the cursor
	 */
	public Cursor get(String id) {
		Cursor cursor = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from " + getTableName(tClass));
		sql.append(" where _id = ?");
		String[] args={id};
		cursor = mDB.rawQuery(sql.toString(), args);
		return cursor;
	}

	public long getNextId()
	{
		long itemToReturn = -1;
		
		  mDB.beginTransaction();
		try {
			
			String FieldIdName = getDescriptionColumnName();// "NUMERO_ACTA";
			ContentValues values = new ContentValues();
			values.put(FieldIdName, "-1");
			itemToReturn = mDB.insert(getTableName(tClass), null, values);
		} catch (Exception e) {
		}
		finally{
			mDB.endTransaction();
		}
		return itemToReturn;
	}
	public ArrayList<T> getByFilter(ContentValues pContentValues,String... pSort)
	{

		if (mDB == null) {
			return null;
		}
		
		ArrayList<T> items = new ArrayList<T>();
		Cursor cursor = null;
		
		synchronized (mDB) {
			cursor = getAllByCursor(pContentValues,pSort);
		}
		// convert cursor to list items.
		if (cursor != null && cursor.getCount() > 0) {
		//	for (int i = 0; i < cursor.getCount(); i++) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) 
			{
				//cursor.moveToPosition(i);
				T newTObject;
				try {
					newTObject = tClass.newInstance();
					bindObject(newTObject, cursor);
					items.add(newTObject);
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (NoSuchFieldException e) {
				}
			}
			cursor.close();
			cursor = null;
		}
		return items;
		
	}
	
	public ArrayList<T> getByFilterLikeOr(ContentValues pContentValues,String... pSort)
	{

		if (mDB == null) {
			return null;
		}
		
		ArrayList<T> items = new ArrayList<T>();
		Cursor cursor = null;
		
		synchronized (mDB) {
			cursor = getAllByCursorLikeOr(pContentValues,pSort);
		}
		// convert cursor to list items.
		if (cursor != null && cursor.getCount() > 0) {
		//	for (int i = 0; i < cursor.getCount(); i++) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) 
			{
				//cursor.moveToPosition(i);
				T newTObject;
				try {
					newTObject = tClass.newInstance();
					bindObject(newTObject, cursor);
					items.add(newTObject);
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (NoSuchFieldException e) {
				}
			}
			cursor.close();
			cursor = null;
		}
		return items;
		
	}
	
	public ArrayList<T> getByFilterLike(ContentValues pContentValues,String... pSort)
	{

		if (mDB == null) {
			return null;
		}
		
		ArrayList<T> items = new ArrayList<T>();
		Cursor cursor = null;
		
		synchronized (mDB) {
			cursor = getAllByCursorLike(pContentValues,pSort);
		}
		// convert cursor to list items.
		if (cursor != null && cursor.getCount() > 0) {
		//	for (int i = 0; i < cursor.getCount(); i++) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) 
			{
				//cursor.moveToPosition(i);
				T newTObject;
				try {
					newTObject = tClass.newInstance();
					bindObject(newTObject, cursor);
					items.add(newTObject);
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (NoSuchFieldException e) {
				}
			}
			cursor.close();
			cursor = null;
		}
		return items;
		
	}
	/**
	 * Get all data in a specified table.
	 * 
	 * @return array list keeps all data of table
	 */
	public ArrayList<T> getAll(String... pSort) {

		if (mDB == null) {
			return null;
		}

		ArrayList<T> items = new ArrayList<T>();
		Cursor cursor = null;

		synchronized (mDB) {
			cursor = getAllByCursor(pSort);
		}
		// convert cursor to list items.
		if (cursor != null && cursor.getCount() > 0) 
		{
		
			//for (int i = 0; i < cursor.getCount(); i++)
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) 
			{
				//cursor.moveToPosition(i);
				T newTObject;
				try {
					newTObject = tClass.newInstance();
					bindObject(newTObject, cursor);
					items.add(newTObject);
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (NoSuchFieldException e) {
				}
			}
			cursor.close();
			cursor = null;
		}
		return items;
	}

	/**
	 * Get all data of specified table and return in a cursor.
	 * 
	 * @return the cursor keeps all data of table
	 */
	public Cursor getAllByCursor(String... pSort) {
		
		String sSort = null;
		for (String sort: pSort) {  
			sSort  = sort + ",";
		}
		sSort = sSort !=null?sSort.substring(0,sSort.length()-1) :null;		
		
		return mDB.query(getTableName(tClass), null, null, null, null, null, sSort);
	}

	/**
	 * Get all data of specified table and return in a cursor.
	 * 
	 * @return the cursor keeps all data of table
	 */
	public Cursor getAllByCursor(ContentValues pContentValues,String... pSort) {
		
		String sSort = null;
		for (String sort: pSort) {  
			sSort  = sort + ",";
		}
		sSort = sSort !=null?sSort.substring(0,sSort.length()-1) :null;		
		
		
		String[] args = new String[pContentValues.size()]; 
		String sFilter ="";
		 Set<Entry<String, Object>> s=pContentValues.valueSet();
		   Iterator<Entry<String, Object>> itr = s.iterator();
		   int i=0;
		   while(itr.hasNext())
		   {
		        Entry<String,Object> me = itr.next();
		        String key = me.getKey();
		        Object value =  me.getValue();
		        sFilter = sFilter + " " + key + " = ? and";  
		        args[i] = value.toString();
		        i++;
		        //Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
		   }
		
		   if(sFilter.length()>0)
			   sFilter = sFilter.substring(0, sFilter.length()-3);
		return mDB.query(getTableName(tClass), null, sFilter, args, null, null,sSort);
	}

public Cursor getAllByCursorLikeOr(ContentValues pContentValues,String... pSort) {
		
		String sSort = null;
		for (String sort: pSort) {  
			sSort  = sort + ",";
		}
		sSort = sSort !=null?sSort.substring(0,sSort.length()-1) :null;		
		
		
		String[] args = new String[pContentValues.size()]; 
		String sFilter ="";
		 Set<Entry<String, Object>> s=pContentValues.valueSet();
		   Iterator<Entry<String, Object>> itr = s.iterator();
		   int i=0;
		   while(itr.hasNext())
		   {
		        Entry<String,Object> me = itr.next();
		        String key = me.getKey();
		        Object value =  me.getValue();
		        sFilter = sFilter + " " + key + " LIKE ? OR";  
		        args[i] = value.toString();
		        i++;
		        //Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
		   }
		
		   if(sFilter.length()>0)
			   sFilter = sFilter.substring(0, sFilter.length()-2);
		return mDB.query(getTableName(tClass), null, sFilter, args, null, null,sSort);
	}
	
	public Cursor getAllByCursorLike(ContentValues pContentValues,String... pSort) {
		
		String sSort = null;
		for (String sort: pSort) {  
			sSort  = sort + ",";
		}
		sSort = sSort !=null?sSort.substring(0,sSort.length()-1) :null;		
		
		
		String[] args = new String[pContentValues.size()]; 
		String sFilter ="";
		 Set<Entry<String, Object>> s=pContentValues.valueSet();
		   Iterator<Entry<String, Object>> itr = s.iterator();
		   int i=0;
		   while(itr.hasNext())
		   {
		        Entry<String,Object> me = itr.next();
		        String key = me.getKey();
		        Object value =  me.getValue();
		        sFilter = sFilter + " " + key + " LIKE ? and";  
		        args[i] = value.toString();
		        i++;
		        //Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
		   }
		
		   if(sFilter.length()>0)
			   sFilter = sFilter.substring(0, sFilter.length()-3);
		return mDB.query(getTableName(tClass), null, sFilter, args, null, null,sSort);
	}
	/**
	 * Check whether a specified record exist or not
	 * 
	 * @param id
	 *            specified id of record wants to check.
	 * @return return true if specified data exist.
	 */
	public boolean isExistence(final String id) {
		Cursor cursor = get(id);
		if (cursor.getCount() > 0) {
			// Close cursor after using
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}

	/**
	 * Delete specified record by id.
	 * 
	 * @param id
	 *            specified id of record wants to delete.
	 * @return true if delete successfully
	 */
	public int delete(final String id) {
		StringBuffer whereClause = new StringBuffer(getPrimaryKeyColumnName());
		whereClause.append("='");
		whereClause.append(id);
		whereClause.append("'");
		return mDB.delete(getTableName(tClass), whereClause.toString(), null);
	}

	private String getDescriptionColumnName() {
		for (Field field : tClass.getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				String columnName = getColumnName(field);
				if (columnName != null) {
					Column annotationColumn = field.getAnnotation(Column.class);
					if (annotationColumn.isDescription()) {
						return columnName;
					}
				}
			}
		}
		return "_id";
	}
	private String getPrimaryKeyColumnName() {
		for (Field field : tClass.getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				String columnName = getColumnName(field);
				if (columnName != null) {
					Column annotationColumn = field.getAnnotation(Column.class);
					if (annotationColumn.isPrimaryKey()) {
						return columnName;
					}
				}
			}
		}
		return "_id";
	}

	/**
	 * Insert a object to corresponding table.
	 * 
	 * @param object
	 *            data wants to insert
	 */
	public long insert(T item) {
		if (item != null) {
			try {
				ContentValues value = getFilledContentValues(item);
				// 
				long id = mDB.insert(getTableName(tClass), null, value);
				return id;
			} 
			catch (IllegalAccessException e) {
			}
			catch (Exception ex)
			{
				String sMensaje = ex.getMessage();
				Tools.Log(Log.ERROR, "Insert Into " + getTableName(tClass), sMensaje);
			}
		}
		return -1;
	}

	/**
	 * Delete all data of table.
	 */
	public final void deleteAll() {
		mDB.delete(getTableName(tClass), null, null);
	}

	/**
	 * Update data of specified item.
	 * 
	 * @param object
	 *            object wants to update
	 */
	public int update(String pId,ContentValues contentValues) {
		int itemsAffected  = -1;
		if (pId != null && !pId.equals("")) {
			try {
				
				if (contentValues.containsKey("_id"))
					contentValues.remove("_id");
				
				String whereClause = " _id = ? ";
				String[] whereArgs = new String[] {pId};
				
				mDB.beginTransaction();
				try
				{
					 itemsAffected =  mDB.update(getTableName(tClass), contentValues, whereClause, whereArgs);
					 mDB.setTransactionSuccessful();
			 	} finally
			 	{
			 		mDB.endTransaction();
			 	}
				
			} 
			/*catch (IllegalAccessException e) {
			}*/
			catch (Exception ex)
			{
				String sMensaje = ex.getMessage();
				Tools.Log(Log.ERROR, "Update Item " + getTableName(tClass), sMensaje);
			}
		}
		return itemsAffected;
		
	}

	/**
	 * Update data of specified item.
	 * 
	 * @param object
	 *            object wants to update
	 */
	public void update(T object, String id) {
	}

	/**
	 * Update data of specified item with _id value.
	 * 
	 * @param object
	 *            object wants to update
	 */
	public int update(T item) {
	
		int itemsAffected = -1;
		if (item != null) {
			try {
				ContentValues value = getFilledContentValues(item);
				if (!value.containsKey("_id"))
					return -1;
				
				String vId = value.getAsString("_id");
				String whereClause = " _id = ? ";
				String[] whereArgs = new String[] {vId};

				value.remove("_id");
				mDB.beginTransaction();
				try
				{
					 itemsAffected = mDB.update(getTableName(tClass), value, whereClause, whereArgs);
					mDB.setTransactionSuccessful();
					
				} finally
				{
					mDB.endTransaction();
				}
				
			} 
			catch (IllegalAccessException e) {
			}
			catch (Exception ex)
			{
				String sMensaje = ex.getMessage();
				Tools.Log(Log.ERROR, "Update Item " + getTableName(tClass), sMensaje);
			}
		}
		return itemsAffected;
	}
	
	/**
	 * Get record by specified id.
	 * 
	 * @param id
	 *            specified id
	 */
	public T getItem(String id) {
		Cursor cursor = get(id);
		// convert cursor to list items.
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			T newTObject = null;
			try {
				newTObject = tClass.newInstance();
				bindObject(newTObject, cursor);
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (NoSuchFieldException e) {
			}
			cursor.close();
			cursor = null;
			return newTObject;
		}
		return null;
	}

	private void bindObject(T newTObject, Cursor cursor)
			throws NoSuchFieldException, IllegalAccessException {
		for (Field field : tClass.getDeclaredFields()) {
			if (!field.isAccessible())
				field.setAccessible(true); // for private variables
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {

				field.set(newTObject, getValueFromCursor(cursor, field));
			}
		}
	}

	public void configSecuence(long pValue)
	{	
		if (pValue<=0)return; // si viene cero o menos  no se actualiza la secuencia

		String strQueryExists =	"SELECT * FROM SQLITE_SEQUENCE WHERE upper(name) = '" +  getTableName(tClass) + "'";
		Cursor cursor = mDB.rawQuery(strQueryExists, null);
		long seqvalue = 0;
		if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
	    {   /// si no hay secuencias.. no deberia haber registros en actas.  
			 seqvalue = cursor.getLong(cursor.getColumnIndex("seq"));
			//hay registros de secuencia ... se puede hacer update
			 if (pValue > seqvalue) 
			 {	mDB.beginTransaction();
			 	try
			 	{
			 		ContentValues values = new ContentValues();
					 values.put("seq", pValue);
				//db.rawQuery(strQueryUpdateSecuencia, null);
			 	  mDB.update("SQLITE_SEQUENCE", values, "name=?", new String[]{getTableName(tClass)});
			 	  
				mDB.setTransactionSuccessful();
			 	} finally
			 	{
			 		mDB.endTransaction();
			 	}
			 }
	    }
		else //el registro de la secuenca de la tabla no existia... por lo que lo vamos agenerar nosotros con un insert
		{
			mDB.beginTransaction();
			try
			{
				
			//db.rawQuery(strQueryInsertSecuencia, null);
				ContentValues values = new ContentValues();
				values.put("name", getTableName(tClass));
				values.put("seq", pValue);
			//db.rawQuery(strQueryUpdateSecuencia, null);
		 	  mDB.insert("SQLITE_SEQUENCE",null, values);
			
			mDB.setTransactionSuccessful();
			}
			finally
			{
			mDB.endTransaction();
			}
		}
		
		
	}
	
	// Get content from specific types
	private Object getValueFromCursor(Cursor cursor, Field field)
			throws IllegalAccessException {
		Class<?> fieldType = field.getType();
		Object value = null;
		
		String columnName = getColumnName(field);
		int columnIndex = cursor.getColumnIndex(columnName);
		
		if (fieldType.isAssignableFrom(Long.class)
				|| fieldType.isAssignableFrom(long.class)) {
			value = cursor.getLong(columnIndex);
		} else if (fieldType.isAssignableFrom(String.class)) {
			value = cursor.getString(columnIndex);
		} else if ((fieldType.isAssignableFrom(Integer.class) || fieldType
				.isAssignableFrom(int.class))) {
			value = cursor.getInt(columnIndex);
		} else if ((fieldType.isAssignableFrom(Byte[].class) || fieldType
				.isAssignableFrom(byte[].class))) {
			value = cursor.getBlob(columnIndex);
		} else if ((fieldType.isAssignableFrom(Double.class) || fieldType
				.isAssignableFrom(double.class))) {
			value = cursor.getDouble(columnIndex);
		} else if ((fieldType.isAssignableFrom(Float.class) || fieldType
				.isAssignableFrom(float.class))) {
			value = cursor.getFloat(columnIndex);
		} else if ((fieldType.isAssignableFrom(Short.class) || fieldType
				.isAssignableFrom(short.class))) {
			value = cursor.getShort(columnIndex);
		} else if (fieldType.isAssignableFrom(Byte.class)
				|| fieldType.isAssignableFrom(byte.class)) {
			value = (byte) cursor.getShort(columnIndex);
		} else if (fieldType.isAssignableFrom(Boolean.class)
				|| fieldType.isAssignableFrom(boolean.class)) {
			int booleanInteger = cursor.getInt(columnIndex);
			value = booleanInteger == 1;
		}else if (fieldType.isAssignableFrom(Date.class)) {
			Long dateLong = cursor.getLong(columnIndex);
			Date date = Tools.ConvertLongToDate(dateLong);
			 //Date date = new Date(cursor.getLong(columnIndex)*1000);
			/*String sDate = cursor.getString(columnIndex);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.getDefault());
			Calendar now = Calendar.getInstance();
			java.util.Date dt = now.getTime();
			try {
				dt = sdf.parse(sDate);
			} catch (ParseException e) {
				//e.printStackTrace();
			}
			value = dt.getTime();
			*/
			 value = date;
		}
		
		return value;
	}

	public void putInContentValues(ContentValues contentValues, Field field,
			Object object) throws IllegalAccessException {
		if (!field.isAccessible())
			field.setAccessible(true); // for private variables
		Object fieldValue = field.get(object);
		String key = getColumnName(field);
		if (fieldValue instanceof Long) {
			contentValues.put(key, Long.valueOf(fieldValue.toString()));
		} else if (fieldValue instanceof String) {
			contentValues.put(key, fieldValue.toString());
		} else if (fieldValue instanceof Integer) {
			contentValues.put(key, Integer.valueOf(fieldValue.toString()));
		} else if (fieldValue instanceof Float) {
			contentValues.put(key, Float.valueOf(fieldValue.toString()));
		} else if (fieldValue instanceof Byte) {
			contentValues.put(key, Byte.valueOf(fieldValue.toString()));
		} else if (fieldValue instanceof Short) {
			contentValues.put(key, Short.valueOf(fieldValue.toString()));
		} else if (fieldValue instanceof Boolean) {
			contentValues.put(key, Boolean.parseBoolean(fieldValue.toString()));
		} else if (fieldValue instanceof Double) {
			contentValues.put(key, Double.valueOf(fieldValue.toString()));
		} else if (fieldValue instanceof Date) { // agregado para el manejo de fechas
			//contentValues.put(key, Tools.DateValueOf((Date)fieldValue));
			contentValues.put(key, Tools.ConvertDateToLong((Date)fieldValue));
		} else if (fieldValue instanceof Byte[] || fieldValue instanceof byte[]) {
			try {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						outputStream);
				objectOutputStream.writeObject(fieldValue);
				contentValues.put(key, outputStream.toByteArray());
				objectOutputStream.flush();
				objectOutputStream.close();
				outputStream.flush();
				outputStream.close();
			} catch (Exception e) {
			}
		}
	}

	private static String getColumnName(Field field) {
		Column annotationColumn = field.getAnnotation(Column.class);
		String column = null;
		if (annotationColumn != null) {
			if (annotationColumn.name().equals("")) {
				column = field.getName();
			} else {
				column = annotationColumn.name();
			}
		}
		return column;
	}

	private ContentValues getFilledContentValues(Object object)
			throws IllegalAccessException {
		ContentValues contentValues = new ContentValues();
		for (Field field : object.getClass().getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				if (!fieldEntityAnnotation.isAutoincrement()) {
					putInContentValues(contentValues, field, object);
				}
			}
		}
		return contentValues;
	}

	private String[] getColumns() {
		boolean isHaveAnyKey = false;
		List<String> columnsList = new ArrayList<String>();
		for (Field field : tClass.getDeclaredFields()) {
			Column fieldEntityAnnotation = field.getAnnotation(Column.class);
			if (fieldEntityAnnotation != null) {
				String columnName = getColumnName(field);
				if (columnName != null)
					columnsList.add(columnName);
				if (fieldEntityAnnotation.isPrimaryKey()) {
					isHaveAnyKey = true;
				}
			}
		}
		if (!isHaveAnyKey) {
			columnsList.add("_id");
		}
		String[] columnsArray = new String[columnsList.size()];
		return columnsList.toArray(columnsArray);
	}

	@SuppressWarnings("unused")
	private Cursor selectCursorFromTable(SQLiteDatabase db, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		try {
			String table = getTableName(tClass);
			String[] columns = getColumns();
			Cursor cursor = db.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
			cursor.moveToFirst();
			return cursor;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getTableName(Class<?> tClass) {
		Table annotationTable = tClass.getAnnotation(Table.class);
		String table = tClass.getSimpleName();
		if (annotationTable != null) {
			if (!annotationTable.name().equals("")) {
				table = annotationTable.name();
			}
		}
		return table;
	}
	public T getNewObject()
	{
		T newTObject;
		try {
			newTObject = tClass.newInstance();
			return newTObject;
		}
		catch(Exception e)
		{
			return null;
		}
		
		
	}
}
