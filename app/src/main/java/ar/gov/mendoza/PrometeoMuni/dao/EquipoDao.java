package ar.gov.mendoza.PrometeoMuni.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;

public class EquipoDao extends BaseDao<Equipo>
{

	public EquipoDao() {
		//Class<Equipo> class1
		super(Equipo.class);
	}
	public void Initialize(SQLiteDatabase db)
	{  
		 Resources res = mContext.getResources();
	     XmlResourceParser _xml = res.getXml(R.xml.equipos_records);
	     
	     try
	        {
	            //Check for end of document
	            int eventType = _xml.getEventType();
	            while (eventType != XmlPullParser.END_DOCUMENT) {
	                //Search for record tags
	                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("record"))){
	                    //Record tag found, now get values and insert record
	                    String _id = _xml.getAttributeValue(null, "id");
	                    String _imei= _xml.getAttributeValue(null, "imei");
	                    String _numeroSerie = _xml.getAttributeValue(null, "numero_serie");
	                    
	                    /*
	                     values.put(LocalidadProvider.ID_LOCALIDAD, _id);
	                     values.put(LocalidadProvider.NOMBRE, _name);
	                     values.put(LocalidadProvider.ID_DEPARTAMENTO, _iddepartamento);
	                     */
	                    //db.insert(LocalidadProvider.TABLE, null,values);
	                    /*
	                    Equipo equipo = new Equipo();
	                    equipo.setIdEquipo(Integer.parseInt(_id));
	                    equipo.setImei(_imei);
	                    equipo.setNumeroSerie(_numeroSerie);
	                    */
	                     ContentValues values = new ContentValues();
	                     values.put("_id", _id);
	                     values.put("IMEI", _imei);
	                     values.put("NUMERO_SERIE", _numeroSerie);
	                   long retorno = db.insert(getTableName(Equipo.class),null,values);
	                   retorno = 2;
	                  
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
	     	catch(Exception e)
	     	{
	     		Log.e("Carga Item", e.getMessage(),e);
	     	}
	        finally
	        {           
	            //Close the xml file
	            _xml.close();
	        }
	}
	public Equipo getByImei(String imei) {
		/*
		Cursor cursor = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from " + getTableName(Equipo.class));
		sql.append(" where IMEI = ?");
		
		String[] args = new String[] {imei};
		
		cursor = mDB.rawQuery(sql.toString(), args);
		*/
		Equipo equipo = new Equipo();
		
		/*
		if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())
		{
		  equipo.setIdEquipo(cursor.getInt(cursor.getColumnIndex("_id")));
		  equipo.setImei(cursor.getString(cursor.getColumnIndex("IMEI")));
		  equipo.setNumeroSerie(cursor.getString(cursor.getColumnIndex("NUMERO_SERIE")));
		}
		*/
		
		List<Equipo> lstEquipos= new ArrayList<Equipo>();
		EquipoDao equipoDao = new EquipoDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("IMEI", imei);
		
		lstEquipos  = equipoDao.getByFilter(cvalues);
		
		if (lstEquipos.size()>0)
			equipo = lstEquipos.get(0);
			
		return equipo;
	}
	
}
