package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_RUTA_CONVENIO")
public class RutaConvenio {
	
	@SerializedName("IdRutaConvenio")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	

	
	@SerializedName("IdRuta")
	@Column(name="ID_RUTA") 
	private String idRuta;
	
	@SerializedName("Convenio")
	@Column(name="CONVENIO") 
	private String convenio;
	
	
	@SerializedName("KmIni"	)
	@Column(name="KM_INI") 
	private String kmIni;
	
	@SerializedName("KmFin")
	@Column(name="KM_FIN") 
	private String kmFin;
	

	
	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	public RutaConvenio(){}

	public String getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
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

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public void setKmFin(String kmFin) {
		this.kmFin = kmFin;
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
