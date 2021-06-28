package ar.gov.mendoza.PrometeoMuni.sync.dto;

//import ar.gov.mendoza.deviceactas.core.domain.Uti;
import com.google.gson.annotations.SerializedName;

public class DtoUsuarioValidado {

	@SerializedName("IdUsuario")
	public String idUsuario;// antes era int
	
	@SerializedName("Login")
	public String login ;
	
	@SerializedName("Clave")
	public String clave;
	
	@SerializedName("Habilitado")
	public String habilitado;
	
	@SerializedName("PasswordIngresadoEncrip")
	public String passIngresadoEncrip;
	
	@SerializedName("ApellidoNombre")
	public String apellidoNombre;
	
	
	{
	idUsuario = "";	
	login ="";
	clave = "";
	habilitado="";
	passIngresadoEncrip="";
	apellidoNombre ="";
	}
}
