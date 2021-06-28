package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_USUARIO")
public class Usuario {

	@SerializedName("Id_Usuario")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=true,isPrimaryKey=true)
	private int idUsuario;
		
	@SerializedName("Login")
	@Column(name="LOGIN" ,type="TEXT")
	private String login;
	
	@SerializedName("Bloqueado")
	@Column(name="BLOQUEADO" ,type="TEXT")
	private String bloqueado;
	
	@SerializedName("Fecha_Desde")
	@Column(name="FECHA_DESDE" ,type="DATETIME")
	private Date fechaDesde;

	@SerializedName("Fecha_Hasta")
	@Column(name="FECHA_HASTA" ,type="DATETIME")
	private Date fechaHasta;

	@SerializedName("Id_Persona")
	@Column(name="ID_PERSONA" ,type="INTEGER")
	private int idPersona;
	
	@SerializedName("Id_Entidad")
	@Column(name="ID_ENTIDAD" ,type="INTEGER")
	private String idEntidad;

	@SerializedName("Id_Funcionario")
	@Column(name="ID_FUNCIONARIO" ,type="INTEGER")
	private String idFuncionario;
	
	@SerializedName("UserName")
	@Column(name="UserName" ,type="TEXT")
	private String userName;
	
	@SerializedName("PassWord")
	@Column(name="PassWord" ,type="TEXT")
	private String passWord;
	
	@SerializedName("Id_Usu_Base")
	@Column(name="ID_USU_BASE" ,type="TEXT")
	private String idUsuBase;
	
	@SerializedName("ApellidoNombre")
	@Column(name="ApellidoNombre" ,type="TEXT")
	private String apellidoNombre;

	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String nombre) {
		this.login = nombre;
	}
	
	public String getBloqueado() {
		return bloqueado;
	}
	
	public void setBloqueado(String valor) {
		this.bloqueado = valor;
	}


	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getIdUsuBase() {
		return idUsuBase;
	}

	public void setIdUsuBase(String idUsuBase) {
		this.idUsuBase = idUsuBase;
	}

	public String getApellidoNombre() {
		return apellidoNombre;
	}

	public void setApellidoNombre(String apellidoNombre) {
		this.apellidoNombre = apellidoNombre;
	}	
}
