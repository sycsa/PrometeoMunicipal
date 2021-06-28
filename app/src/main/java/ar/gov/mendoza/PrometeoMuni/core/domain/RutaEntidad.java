package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_RUTA_ENTIDAD")
public class RutaEntidad   {
	
	@SerializedName("IdRutaEntidad")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	/*
	@SerializedName("Entidad")
	@Column(name="NOMBRE") 
	private String nombre;
	*/
	
	@SerializedName("IdRuta")
	@Column(name="ID_RUTA") 
	private String idRuta;
	
	@SerializedName("IdEntidad")
	@Column(name="ID_ENTIDAD") 
	private String idEntidad;
	
	
	@SerializedName("KmIni")
	@Column(name="KM_INI") 
	private String kmIni;
	
	@SerializedName("KmFin")
	@Column(name="KM_FIN") 
	private String kmFin;
	
	@SerializedName("Paridad")
	@Column(name="PARIDAD") 
	private String paridad;
	
	@SerializedName("Meses")
	@Column(name="MESES") 
	private String meses;
	
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
/*
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	*/
	/* ISeleccionable methods */
	/*
	public String getItemId() {
		return id;
	}

	public String getItemName() {
		return nombre;
	}
	*/
	public RutaEntidad(){}

	public String getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getKmIni() {
		return kmIni;
	}

	public void setKmIni(String kmIni) {
		this.kmIni = kmIni;
	}

	public String getKmFin() {
		return kmFin;
	}

	public void setKmFin(String kmFin) {
		this.kmFin = kmFin;
	}

	public String getParidad() {
		return paridad;
	}

	public void setParidad(String paridad) {
		this.paridad = paridad;
	}

	public String getMeses() {
		return meses;
	}

	public void setMeses(String meses) {
		this.meses = meses;
	}
	
	/*
	public RutaEntidad(String id,String nombre){
		super();
		this.id = id;
		this.nombre=nombre;
	}
	*/
	
	/*
	@Override
    public String toString() {
       		return this.getItemName();
    }
	*/

}
