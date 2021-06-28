package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.Settings.System;
import android.util.Log;

import java.util.Locale;

public class ActaConstatacionProvider extends BaseProvider {
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_ACTA_CONSTATACION";
	
	static final String TAG = "ActaConstatacionProvider";
	static final String AUTHORITY = "ar.gov.mendoza.deviceactas.core.providers.ActaConstatacionProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE.toLowerCase(Locale.getDefault()));
	
		


	private static final UriMatcher uriMatcher;
	static
	{ 
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, TABLE.toLowerCase(Locale.getDefault()), ROWS);
		uriMatcher.addURI(AUTHORITY, TABLE.toLowerCase(Locale.getDefault()) + "/#", ROW_ID);
	} 

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_ACTA_CONSTATACION = "_id";//"ID_ACTA_CONSTATACION";
	public static final String NUMERO_ACTA = "NUMERO_ACTA";
	public static final String DIGITO_VERIFICADOR = "DIGITO_VERIFICADOR";
	public static final String CODIGO_BARRA = "CODIGO_BARRA";
	public static final String ID_MOVIL = "ID_MOVIL";
	public static final String FECHA_VENCIMIENTO_CUPON = "FECHA_VENCIMIENTO_CUPON";
	public static final String FECHA_VENCIMIENTO_CUPON_STRING = "FECHA_VENCIMIENTO_CUPON_STRING";
	public static final String MONTO_CUPON = "MONTO_CUPON";
	public static final String MONTO_CUPON_STRING = "MONTO_CUPON_STRING";
	
	public static final String FECHA_HORA_CARGA = "FECHA_HORA_CARGA";
	public static final String FECHA_HORA_CARGA_STRING = "FECHA_HORA_CARGA_STRING";
	public static final String FECHA_HORA_LABRADO = "FECHA_HORA_LABRADO";
	public static final String FECHA_HORA_LABRADO_STRING = "FECHA_HORA_LABRADO_STRING";
	public static final String ID_TIPO_RUTA = "ID_TIPO_RUTA";
	public static final String TIPO_RUTA = "TIPO_RUTA";
	public static final String ID_NUMERO_RUTA = "ID_NUMERO_RUTA";
	public static final String NUMERO_RUTA = "NUMERO_RUTA";
	public static final String KM = "KM";
	public static final String PROVEEDOR_POSICION_GPS = "PROVEEDOR_POSICION_GPS";
	
	
	public static final String LATITUD = "LATITUD";
	public static final String LONGITUD = "LONGITUD";
	public static final String LICENCIA_DESDE_CODIGO_QR = "LICENCIA_DESDE_CODIGO_QR";
	public static final String COBERTURA_DESDE_CODIGO_QR = "COBERTURA_DESDE_CODIGO_QR";
	public static final String LICENCIA_RETENIDA = "LICENCIA_RETENIDA";
	public static final String LICENCIA_UNICA_PROVINCIAL = "LICENCIA_UNICA_PROVINCIAL";
	public static final String COBERTURA_DESDE_NUMERO_LICENCIA = "COBERTURA_DESDE_NUMERO_LICENCIA";
	public static final String NUMERO_LICENCIA = "NUMERO_LICENCIA";
	public static final String ID_CLASE_LICENCIA = "ID_CLASE_LICENCIA";
	public static final String CLASE_LICENCIA = "CLASE_LICENCIA";
	public static final String FECHA_VENCIMIENTO_LICENCIA = "FECHA_VENCIMIENTO_LICENCIA";
	public static final String ID_PAIS_LICENCIA = "ID_PAIS_LICENCIA";
	public static final String PAIS_LICENCIA = "PAIS_LICENCIA";
	public static final String ID_PROVINCIA_LICENCIA = "ID_PROVINCIA_LICENCIA";
	public static final String PROVINCIA_LICENCIA = "PROVINCIA_LICENCIA";
	public static final String ID_DEPARTAMENTO_LICENCIA = "ID_DEPARTAMENTO_LICENCIA";
	public static final String DEPARTAMENTO_LICENCIA = "DEPARTAMENTO_LICENCIA";
	public static final String ID_LOCALIDAD_LICENCIA = "ID_LOCALIDAD_LICENCIA";
	public static final String LOCALIDAD_LICENCIA = "LOCALIDAD_LICENCIA";
	
	
	public static final String TIPO_DOCUMENTO = "TIPO_DOCUMENTO";
	public static final String COBERTURA_DESDE_NUMERO_DOCUMENTO = "COBERTURA_DESDE_NUMERO_DOCUMENTO";
	public static final String NUMERO_DOCUMENTO = "NUMERO_DOCUMENTO";
	public static final String ID_SEXO = "ID_SEXO";
	public static final String APELLIDO = "APELLIDO";
	public static final String NOMBRE = "NOMBRE";
	public static final String ES_CALLE_PUBLICA = "ES_CALLE_PUBLICA";
	public static final String CALLE = "CALLE";

	public static final String ES_ALTURA_SIN_NUMERO = "ES_ALTURA_SIN_NUMERO";
	public static final String ALTURA = "ALTURA";
	public static final String PISO = "PISO";
	public static final String DEPARTAMENTO = "DEPARTAMENTO";
	public static final String BARRIO = "BARRIO";
	
	public static final String ID_PAIS_DOMICILIO = "ID_PAIS_DOMICILIO";
	public static final String PAIS_DOMICILIO = "PAIS_DOMICILIO";
	public static final String ID_PROVINCIA_DOMICILIO = "ID_PROVINCIA_DOMICILIO";
	public static final String PROVINCIA_DOMICILIO = "PROVINCIA_DOMICILIO";
	public static final String ID_DEPARTAMENTO_DOMICILIO = "ID_DEPARTAMENTO_DOMICILIO";
	public static final String DEPARTAMENTO_DOMICILIO = "DEPARTAMENTO_DOMICILIO";
	public static final String ID_LOCALIDAD_DOMICILIO = "ID_LOCALIDAD_DOMICILIO";
	public static final String LOCALIDAD_DOMICILIO = "LOCALIDAD_DOMICILIO";
	public static final String CODIGO_POSTAL = "CODIGO_POSTAL";
	public static final String ID_TIPO_VEHICULO = "ID_TIPO_VEHICULO";
	public static final String ID_TIPO_PATENTE = "ID_TIPO_PATENTE";
	
	public static final String COBERTURA_DESDE_DOMINIO = "COBERTURA_DESDE_DOMINIO";
	public static final String DOMINIO = "DOMINIO";
	public static final String ID_MARCA = "ID_MARCA";
	public static final String MARCA = "MARCA";

	public static final String ID_COLOR = "ID_COLOR";
	public static final String COLOR = "COLOR";
	
	public static final String ID_INFRACCION1 = "ID_INFRACCION1";
	public static final String CODIGO_INFRACCION1 = "CODIGO_INFRACCION1";
	public static final String ES_APERCIBIMIENTO_INFRACCION1 = "ES_APERCIBIMIENTO_INFRACCION1";
	public static final String COBERTURA_DESDE_INFRACCION1 = "COBERTURA_DESDE_INFRACCION1";
	
	public static final String ID_INFRACCION2 = "ID_INFRACCION2";
	public static final String CODIGO_INFRACCION2 = "CODIGO_INFRACCION2";
	public static final String ES_APERCIBIMIENTO_INFRACCION2 = "ES_APERCIBIMIENTO_INFRACCION2";
	public static final String COBERTURA_DESDE_INFRACCION2 = "COBERTURA_DESDE_INFRACCION2";

	public static final String FOTOS = "FOTOS";
	public static final String OBSERVACIONES = "OBSERVACIONES";
	public static final String ID_JUZGADO = "ID_JUZGADO";
	public static final String LOCALIDAD_JUZGADO = "LOCALIDAD_JUZGADO";
	public static final String CODIGO_POSTAL_JUZGADO = "CODIGO_POSTAL_JUZGADO";
	public static final String CALLE_JUZGADO = "CALLE_JUZGADO";
	public static final String ALTURA_JUZGADO = "ALTURA_JUZGADO";
	public static final String ID_USUARIO_PDA = "ID_USUARIO_PDA";
	public static final String NIVEL_COBERTURA_MOVIL = "NIVEL_COBERTURA_MOVIL";
	public static final String TIPO_COBERTURA_MOVIL = "TIPO_COBERTURA_MOVIL";
	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_ACTA_CONSTATACION + " INTEGER PRIMARY KEY AUTOINCREMENT," 
								           + NUMERO_ACTA + " TEXT ,"
								           + DIGITO_VERIFICADOR + " INTEGER,"
								           + CODIGO_BARRA + " TEXT,"
								           + ID_MOVIL + " INTEGER,"
								           + FECHA_VENCIMIENTO_CUPON + " DATETIME,"
								           + FECHA_VENCIMIENTO_CUPON_STRING + " TEXT,"
								           + MONTO_CUPON + " REAL,"
								           + FECHA_HORA_CARGA + " DATETIME,"
								           + FECHA_HORA_CARGA_STRING + " TEXT,"
								           + FECHA_HORA_LABRADO + " DATETIME,"
								           + FECHA_HORA_LABRADO_STRING + " TEXT,"		
								           + ID_TIPO_RUTA + " INTEGER,"
								           + TIPO_RUTA + " TEXT,"
								           + ID_NUMERO_RUTA + " INTEGER,"
								           + NUMERO_RUTA + " TEXT,"
								           + KM + " TEXT,"
								           + PROVEEDOR_POSICION_GPS + " TEXT,"
								           + LATITUD + " TEXT,"
								           + LONGITUD + " TEXT,"
								           + LICENCIA_DESDE_CODIGO_QR + " TEXT,"
										   + COBERTURA_DESDE_CODIGO_QR + " TEXT,"
											+ LICENCIA_RETENIDA + " TEXT,"
											+ LICENCIA_UNICA_PROVINCIAL + " TEXT,"
											+ COBERTURA_DESDE_NUMERO_LICENCIA + " TEXT,"
											+ NUMERO_LICENCIA + " TEXT,"
											+ ID_CLASE_LICENCIA + " TEXT,"
											+ CLASE_LICENCIA + " TEXT,"
											+ FECHA_VENCIMIENTO_LICENCIA + " TEXT,"
											+ ID_PAIS_LICENCIA + " TEXT,"
											+ PAIS_LICENCIA + " TEXT,"
											+ ID_PROVINCIA_LICENCIA + " TEXT,"
											+ PROVINCIA_LICENCIA + " TEXT,"
											+ ID_DEPARTAMENTO_LICENCIA + " TEXT,"
											+ DEPARTAMENTO_LICENCIA + " TEXT,"
											+ ID_LOCALIDAD_LICENCIA + " TEXT,"
											+ LOCALIDAD_LICENCIA + " TEXT,"	
											+ TIPO_DOCUMENTO + " TEXT,"
											+ COBERTURA_DESDE_NUMERO_DOCUMENTO + " TEXT,"
											+ NUMERO_DOCUMENTO + " TEXT,"
											+ ID_SEXO + " TEXT,"
											+ APELLIDO + " TEXT,"
											+ NOMBRE + " TEXT,"
											+ ES_CALLE_PUBLICA + " TEXT,"
											+ CALLE + " TEXT,"
											+ ES_ALTURA_SIN_NUMERO + " TEXT,"
											+ ALTURA + " TEXT,"
											+ PISO + " TEXT,"
											+ DEPARTAMENTO + " TEXT,"
											+ BARRIO + " TEXT,"
											+ ID_PAIS_DOMICILIO + " TEXT,"
											+ PAIS_DOMICILIO + " TEXT,"
											+ ID_PROVINCIA_DOMICILIO + " TEXT,"
											+ PROVINCIA_DOMICILIO + " TEXT,"
											+ ID_DEPARTAMENTO_DOMICILIO + " TEXT,"
											+ DEPARTAMENTO_DOMICILIO + " TEXT,"
											+ ID_LOCALIDAD_DOMICILIO + " TEXT,"
											+ LOCALIDAD_DOMICILIO + " TEXT,"		
											+ CODIGO_POSTAL + " TEXT,"
											+ ID_TIPO_VEHICULO + " TEXT,"
											+ ID_TIPO_PATENTE + " TEXT,"
										    + COBERTURA_DESDE_DOMINIO + " TEXT,"
											+ DOMINIO + " TEXT,"
											+ ID_MARCA + " TEXT,"
											+ MARCA + " TEXT,"
											+ ID_COLOR + " TEXT,"
											+ COLOR + " TEXT,"
											+ ID_INFRACCION1 + " TEXT,"
											+ CODIGO_INFRACCION1 + " TEXT,"
											+ ES_APERCIBIMIENTO_INFRACCION1 + " TEXT,"
											+ COBERTURA_DESDE_INFRACCION1 + " TEXT,"
											+ ID_INFRACCION2 + " TEXT,"
											+ CODIGO_INFRACCION2 + " TEXT,"
											+ ES_APERCIBIMIENTO_INFRACCION2 + " TEXT,"
											+ COBERTURA_DESDE_INFRACCION2 + " TEXT,"
											+ FOTOS + " TEXT,"
											+ OBSERVACIONES + " TEXT,"
											+ ID_JUZGADO + " TEXT,"
											+ LOCALIDAD_JUZGADO + " TEXT,"
											+ CODIGO_POSTAL_JUZGADO + " TEXT,"
											+ CALLE_JUZGADO + " TEXT,"
											+ ALTURA_JUZGADO + " TEXT,"
											+ ID_USUARIO_PDA + " TEXT,"
											+ NIVEL_COBERTURA_MOVIL + " TEXT,"
											+ TIPO_COBERTURA_MOVIL + " TEXT"
								           + ")";
	
	public static final String SQLDropTable ="DROP TABLE IF EXISTS " + TABLE;
    
	
	@Override
	public boolean onCreate() {
		table = TABLE;
		baseUriMatcher = ActaConstatacionProvider.uriMatcher;
		BASE_CONTENT_URI = ActaConstatacionProvider.CONTENT_URI;
		return super.onCreate();
	}
	
	// ver que los metohdos query insert update delete estan implementados en el BaseProvider
	
	// ver que esta parte esta en el BaseProvider
	@Override
	public String getType(Uri uri) {
		String ret = getContext().getContentResolver().getType(System.CONTENT_URI);
	    Log.d(TAG, "getType returning: " + ret);
		return null;
	}
	public void setSecuenceValue(long pValue)
	{
		ContentValues values = new ContentValues();
		 values.put(NUMERO_ACTA, "-1");
		 long value = this.nextId(values);
	
		if (value < pValue)
		{
			//el id es 1  quiere decir que nunca se registro nada
			// insertamos un registro ficticio y lo borramos para que se genere la secuencia
			/* restaurar 
			 if (value ==1)
			{
				this.insert(CONTENT_URI, values);
				this.delete(CONTENT_URI, NUMERO_ACTA +"=?", new String[]{"-1"});
				long value2 = this.nextId(values);
			}
			*/
			this.configSecuence(pValue);
		}
		
		// verificamos el valor actual de la secuencia con respecto al valor que viene desde la base externa
		
		
	}
}
