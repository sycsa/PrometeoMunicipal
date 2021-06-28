package ar.gov.mendoza.PrometeoMuni.core.data;

//import com.sistemas.sismap.core.providers.ContratoProvider;
//import com.sistemas.sismap.core.providers.DetallePenalidadProvider;
//import com.sistemas.sismap.core.providers.NovedadProvider;
//import com.sistemas.sismap.core.providers.ParametroGeneralProvider;
//import com.sistemas.sismap.core.providers.TipoDefectoProvider;
//import com.sistemas.sismap.core.providers.UtiProvider;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.DepartamentoProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.LocalidadProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.PaisProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.ProvinciaProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.UsuarioProvider;
import ar.gov.mendoza.PrometeoMuni.core.providers.VersionProvider;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Actas2011.2.28.1";
    public static final int DATABASE_VERSION = 1;
    private final Context myContext;
    
    //The Android's default system path of your application database.
    /*private static String DB_PATH = "/data/data/ar.gov.cba.deviceactas/databases/";
    private static String DB_NAME = "Actas2011.2.28.1";
    private SQLiteDatabase myDataBase; 
    
    */
    
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		
	}
    /*
	public void createDataBase() throws IOException {
		//	
		boolean dbExist = checkDataBase();
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
    		//By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
         		throw new Error("Error copiando base de datos Template");
         	}
    	}
	}
    */
	/**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
	/*
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
     	if(checkDB != null){
     		checkDB.close();
     	}
     	return checkDB != null ? true : false;
    }
    */
    
	@Override
	public void onCreate(SQLiteDatabase db) {
    	db.execSQL(VersionProvider.SQLCreateTable);
    	db.execSQL(UsuarioProvider.SQLCreateTable);
		db.execSQL(ActaConstatacionProvider.SQLCreateTable);
		db.execSQL(PaisProvider.SQLCreateTable);
		db.execSQL(ProvinciaProvider.SQLCreateTable);
		db.execSQL(DepartamentoProvider.SQLCreateTable);
		db.execSQL(LocalidadProvider.SQLCreateTable);
		//String sDatabasName = this.getDatabaseName().toString();
		//Toast.makeText(this.myContext, sDatabasName, Toast.LENGTH_LONG).show();
		// carga de tablas base
		//Initialize(db);
	}
    
    private void Initialize(SQLiteDatabase db)
    {
    	
    InitializePaises(db);
    InitializeProvincias(db);
    InitializeDepartamentos(db);
    InitializeLocalidades(db);
	     /*
		 PaisProvider prov = new PaisProvider();
		 values.put(PaisProvider.ID_PAIS,"ARG");
		 db.insert(PaisProvider.TABLE,null,values);
		 values.clear(); 
		 */
    }
    private void InitializeLocalidades(SQLiteDatabase db)
    {
    	
   	 ContentValues values = new ContentValues();
	 
	 Resources res = myContext.getResources();
	 
     XmlResourceParser _xml = res.getXml(R.xml.localidades_records);
     
     try
        {
            //Check for end of document
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Search for record tags
                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("record"))){
                    //Record tag found, now get values and insert record
                    String _id = _xml.getAttributeValue(null, "id");
                    String _name = _xml.getAttributeValue(null, "name");
                    String _iddepartamento = _xml.getAttributeValue(null, "iddepartamento");
                    
                    values.put(LocalidadProvider.ID_LOCALIDAD, _id);
                    values.put(LocalidadProvider.NOMBRE, _name);
                    values.put(LocalidadProvider.ID_DEPARTAMENTO, _iddepartamento);
                    
                    
                    
                    db.insert(LocalidadProvider.TABLE, null,values);
                }
                eventType = _xml.next();
            }
        }
        //Catch errors
        catch (XmlPullParserException e)
        {       
            Log.e("Initialize Localidades", e.getMessage(), e);
        }
        catch (IOException e)
        {
            Log.e("Initialize Localidades", e.getMessage(), e);
             
        }           
        finally
        {           
            //Close the xml file
            _xml.close();
        }
    }
    private void InitializeDepartamentos(SQLiteDatabase db)
    {
    	
   	 ContentValues values = new ContentValues();
	 
	 Resources res = myContext.getResources();
     XmlResourceParser _xml = res.getXml(R.xml.departamentos_records);
     
     try
        {
            //Check for end of document
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Search for record tags
                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("record"))){
                    //Record tag found, now get values and insert record
                    String _id = _xml.getAttributeValue(null, "id");
                    String _name = _xml.getAttributeValue(null, "name");
                    String _idprovincia = _xml.getAttributeValue(null, "idprovincia");
                    values.put(DepartamentoProvider.ID_DEPARTAMENTO, _id);
                    values.put(DepartamentoProvider.NOMBRE, _name);
                    values.put(DepartamentoProvider.ID_PROVINCIA, _idprovincia);
                    
                    db.insert(DepartamentoProvider.TABLE, null,values);              
                }
                eventType = _xml.next();
            }
        }
        //Catch errors
        catch (XmlPullParserException e)
        {       
            Log.e("Initialize Departamentos", e.getMessage(), e);
        }
        catch (IOException e)
        {
            Log.e("Initialize Departamentos", e.getMessage(), e);
             
        }           
        finally
        {           
            //Close the xml file
            _xml.close();
        }
    }
    private void InitializeProvincias(SQLiteDatabase db)
    {
    	
   	 ContentValues values = new ContentValues();
	 
	 Resources res = myContext.getResources();
     XmlResourceParser _xml = res.getXml(R.xml.provincias_records);
     
     try
        {
            //Check for end of document
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Search for record tags
                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("record"))){
                    //Record tag found, now get values and insert record
                    String _id = _xml.getAttributeValue(null, "id");
                    String _name = _xml.getAttributeValue(null, "name");
                    String _idpais = _xml.getAttributeValue(null, "idpais");
                    values.put(ProvinciaProvider.ID_PROVINCIA, _id);
                    values.put(ProvinciaProvider.NOMBRE, _name);
                    values.put(ProvinciaProvider.ID_PAIS, _idpais);
                    db.insert(ProvinciaProvider.TABLE, null,values);
                }
                eventType = _xml.next();
            }
        }
        //Catch errors
        catch (XmlPullParserException e)
        {       
            Log.e("Initialize Provincias", e.getMessage(), e);
        }
        catch (IOException e)
        {
            Log.e("Initialize Provincias", e.getMessage(), e);
             
        }           
        finally
        {           
            //Close the xml file
            _xml.close();
        }
    }
    private void InitializePaises(SQLiteDatabase db)
    {
   	 ContentValues values = new ContentValues();
	 
	 Resources res = myContext.getResources();
     XmlResourceParser _xml = res.getXml(R.xml.paises_records);
     
     try
        {
            //Check for end of document
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Search for record tags
                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("record"))){
                    //Record tag found, now get values and insert record
                    String _id = _xml.getAttributeValue(null, "id");
                    String _name = _xml.getAttributeValue(null, "name");
                    values.put(PaisProvider.ID_PAIS, _id);
                    values.put(PaisProvider.NOMBRE, _name);
                    db.insert(PaisProvider.TABLE, null,values);              
                }
                eventType = _xml.next();
            }
        }
        //Catch errors
        catch (XmlPullParserException e)
        {       
            Log.e("Initialize Paises", e.getMessage(), e);
        }
        catch (IOException e)
        {
            Log.e("Initialize Paises", e.getMessage(), e);
             
        }           
        finally
        {           
            //Close the xml file
            _xml.close();
        }
    }
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// eliminar las tablas anteriores para volverlas a crear?
    	// TODO ver de hacer las actualizaciones pertinentes segun la version
    	db.execSQL(ActaConstatacionProvider.SQLDropTable );
    	db.execSQL(PaisProvider.SQLDropTable );
    	db.execSQL(ProvinciaProvider.SQLDropTable );
    	db.execSQL(DepartamentoProvider.SQLDropTable );
    	db.execSQL(LocalidadProvider.SQLDropTable );
		onCreate(db);
		
	}
	
	 /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
/*   
 private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 */
 /*
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 */
    /*
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    
    	    super.close();
	}*/
 
	
 
	
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
}
