package ar.gov.mendoza.PrometeoMuni.singleton;

import com.zebra.android.zebrautilitiesmza.ZebraUtilitiesApplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ar.gov.mendoza.PrometeoMuni.BuildConfig;
import ar.gov.mendoza.PrometeoMuni.core.domain.SuportTable;

public class GlobalVar {
	
	private static GlobalVar instance;
	   
	
	//private SuportTable supportTable;
	private SuportTable suportTable;
	
	private String imei;
	private String imsi;
	
	private Integer idMovil;
	private String numeroSerie;
	private String letraSerie;
	  
	private String idUsuario;//Integer idUsuario;
	private String login;
	
	private Double latitud;
	private Double longitud;
	private String provider;
	private String restModoDebug ="N";
	
	public boolean isBeingDebugged()
	{
		
		boolean bReturn = false;
		bReturn = BuildConfig.DEBUG;
		bReturn = android.os.Debug.isDebuggerConnected();
		
		return bReturn;
	}
	public String getImpresoraConfigurada() {
		String impresoraConfigurada="";
		try
		{
	        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
	        impresoraConfigurada  = sharedPref.getString("CURRENTLY_SELECTED_PRINTER", "(Ninguna)");
		}
		catch(Exception ex)
		{
			
		}
		
		return impresoraConfigurada;
	}
	
	private String versionSoftwarePDA;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi==null?"(sin chip)":imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	public SuportTable getSuportTable() {
		return suportTable;
	}
	public void setSuportTable(SuportTable suportTable) {
		this.suportTable = suportTable;
	}
	public static void InitInstance()
	  {
		  if(instance==null)
			  instance = new GlobalVar();
	  }
	  public static GlobalVar getInstance()
	  {
		 InitInstance();
	     return instance;
	  }
	   
	  private GlobalVar()
	  {
	    // Constructor hidden because this is a singleton
	  }
	public Integer getIdMovil() {
		return idMovil;
	}
	public void setIdMovil(Integer idMovil) {
		this.idMovil = idMovil;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {//(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
    public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Double getLatitud() {
		return latitud==null?0.0f:latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public String getRestModoDebug() {
		return restModoDebug==null?"N":restModoDebug;
	}

	public void setRestModoDebug(String restModoDebug) {
		this.restModoDebug = restModoDebug;
	}

	public Double getLongitud() {
		return longitud==null?0.0f:longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getLetraSerie() {
		return letraSerie;
	}
	public void setLetraSerie(String letraSerie) {
		this.letraSerie = letraSerie;
	}
	public String getProvider() {
		return provider==null?"":provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getVersionSoftwarePDA() {
		return versionSoftwarePDA;
	}
	public void setVersionSoftwarePDA(String versionSoftwarePDA) {
		this.versionSoftwarePDA = versionSoftwarePDA;
	}
	   

}
