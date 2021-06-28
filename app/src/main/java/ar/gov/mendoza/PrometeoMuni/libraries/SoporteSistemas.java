package ar.gov.mendoza.PrometeoMuni.libraries;

import android.content.Context;
import android.telephony.TelephonyManager;
import ar.gov.mendoza.PrometeoMuni.core.security.Encriptacion;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

public class SoporteSistemas {

	
	private Encriptacion encriptacion;
	
	public SoporteSistemas()
	{
		String _sCryptoKey = GlobalVar.getInstance().getSuportTable().getCriptoKey(); 
		String _sCryptoVector = GlobalVar.getInstance().getSuportTable().getCriptoVector(); 
		//this.encriptacion = new Encriptacion(_sCryptoKey);
		this.encriptacion = new Encriptacion(_sCryptoKey, _sCryptoVector);
	}

	public String DesProteger(String Texto)
	{	
		String texto = "";
		try
		{
			texto = encriptacion.Desencripta(Texto);//uncrypt(Texto,_bCryptoVector);
		} 
		catch(Exception pex)
		{
			String valor = pex.getMessage();
			valor ="";
		}
		return texto;
	}
	public String Proteger(String Texto)
	{
		String texto = "";
		try
		{
			texto = encriptacion.Encripta(Texto);//crypt(Texto, _bCryptoVector);
		} 
		catch(Exception pex)
		{
			String valor = pex.getMessage();
			valor ="";
		}
		return texto;
	}
	
	
	
	
	
	
	/*
	 * STUFF
	 */
	public String getTitle()
	{
		return "Policia Caminera";
		
	}
	
	public String getImei(Context mContext)
	{
		String identifier = null; 
 		TelephonyManager tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
 		if (tm != null)
				  identifier = tm.getDeviceId();

		return identifier;
	}
}
