package ar.gov.mendoza.PrometeoMuni.sync;

import java.util.HashMap;

import android.content.Context;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.data.DaoUti;

//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;


public class SyncUsuario extends SyncBase {	  
		
	public SyncUsuario(Context context) {
		 super(context, "SyncUsuario");
	}


	public String validateLogin(String usuario, String clave, int control,double latitud,double longitud) throws SecurityException, NoSuchMethodException {
		
		setServiceMethodName("validarUsuarioEnRepat");
		
		HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("usuario", usuario);
		
		return "";
	}
		
	}
