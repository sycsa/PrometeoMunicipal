package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_LOCALIDAD")
public class Localidad implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdLocalidad")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Localidad")
	@Column(name="NOMBRE")
	private String nombre;
	
	@SerializedName("Provincia")
	@Column(name="ID_PROVINCIA")
	private String idProvincia;

	
	@SerializedName("Departamento")
	@Column(name="ID_DEPARTAMENTO")
	private String idDepartamento;

	@SerializedName("CPA")
	@Column(name="CPA")
	private String cpa;
	
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getCpa() {
		return cpa;
	}

	public void setCpa(String cpa) {
		this.cpa = cpa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return nombre;
	}
	
	public Localidad(){}
	
	public Localidad(Integer id, String nombre, String idDepartamento){
		super();
		this.id = id;
		this.nombre=nombre;
		this.idDepartamento = idDepartamento;
	}
	
	@Override
    public String toString() {
        //return "Localidad [id=" + this.id + ", nombre=" + this.nombre  + ", departamento=" + this.idDepartamento +  "]";
		return this.getItemName();
    }

	public String getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	@Override
	public void setItemId(String item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItemName(String item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
