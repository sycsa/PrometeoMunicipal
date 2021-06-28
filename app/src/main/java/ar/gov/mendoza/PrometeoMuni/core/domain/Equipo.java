package ar.gov.mendoza.PrometeoMuni.core.domain;


import com.google.gson.annotations.SerializedName;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_EQUIPO_IMEI")
public class Equipo {

	@SerializedName("Id_Equipo")
	@Column(name="_id" ,type="INTEGER",isPrimaryKey=true)
	private int idEquipo;
		
	@SerializedName("NumeroSerie")
	@Column(name="NUMERODESERIE" ,type="TEXT")
	private String numeroSerie;
	
	@SerializedName("LetraSerie")
	@Column(name="LETRADESERIE" ,type="TEXT")
	private String letraSerie;

	@SerializedName("Imei")
	@Column(name="IMEI" ,type="TEXT")
	private String imei;

	@SerializedName("Habilitado")
	@Column(name="HABILITADO" ,type="TEXT")
	private String habilitado;
	
	@SerializedName("PrimerUso")
	@Column(name="PRIMERUSO" ,type="TEXT")
	private String primerUso;

	@SerializedName("Id_Departamento")
	@Column(name = "ID_DEPARTAMENTO", type = "INTEGER")
	private int idDepartamento;

	public int getIdDepartamento(){  return idDepartamento;  }

	public void setIdDepartamento(int idDepartamento) {  this.idDepartamento = idDepartamento;  }
	
	public int getIdEquipo() {
		return idEquipo;
	}

	public String getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}

	public String getPrimerUso() {
		return primerUso;
	}

	public void setPrimerUso(String primerUso) {
		this.primerUso = primerUso;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	
	
	
}
