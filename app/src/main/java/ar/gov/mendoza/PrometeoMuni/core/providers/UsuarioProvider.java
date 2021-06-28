package ar.gov.mendoza.PrometeoMuni.core.providers;

import android.net.Uri;

public class UsuarioProvider extends BaseProvider {
		
	public static final Uri CONTENT_URI = Uri.parse("content://ar.gov.mendoza.deviceactas.core.providers.UsuarioProvider");
	
	/*
	 * TABLA
	 */
	public static final String TABLE = "T_USUARIO";

	/*
	 * CAMPOS COLUMNAS
	 */
	public static final String ID_USUARIO = "_id";//"ID_ACTA_CONSTATACION";
	public static final String LOGIN = "LOGIN";
	public static final String BLOQUEADO = "BLOQUEADO";
	public static final String FECHA_DESDE = "FECHA_DESDE";
	public static final String FECHA_HASTA = "FECHA_HASTA";
	public static final String ID_PERSONA = "ID_PERSONA";
	public static final String ID_ENTIDAD = "ID_ENTIDAD";
	public static final String ID_FUNCIONARIO = "ID_FUNCIONARIO";
	public static final String USER_NAME = "UserName";
	public static final String PASSWORD = "PassWord";
	public static final String ID_USU_BASE = "ID_USU_BASE";
	public static final String APELLIDO_NOMBRE = "ApellidoNombre";
	
	
	
	public static final String SQLCreateTable = "CREATE TABLE "
    + TABLE + "(" + ID_USUARIO + " INTEGER PRIMARY KEY," 
								           + LOGIN + " TEXT ,"
								           + BLOQUEADO + " TEXT,"
								           + FECHA_DESDE + " DATETIME,"
								           + FECHA_HASTA + " DATETIME,"
								           + ID_PERSONA + " INTEGER,"
								           + ID_ENTIDAD + " INTEGER,"
								           + ID_FUNCIONARIO + " INTEGER,"
								           + USER_NAME + " TEXT,"
								           + PASSWORD + " TEXT,"
								           + ID_USU_BASE + " INTEGER,"
								           + APELLIDO_NOMBRE + " TEXT"
								           + ")";
	
	public static final String SQLDropTable ="DROP TABLE IF EXISTS " + TABLE;
    
	
	@Override
	public boolean onCreate() {
		table = TABLE;
		return super.onCreate();
	}
	
	public void setSecuenceValue(long pValue)
	{
		// no hay implementacion de actualizacion de secuencias
		return;		
		
	}
}
