package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_PROVINCIA")
public class Provincia implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdProvincia")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("Provincia")
	@Column(name="NOMBRE") 
	private String nombre;
	
	@SerializedName("ID_PAIS")
	@Column(name="ID_PAIS") 
	private String idPais;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	/* ISeleccionable methods */
	public String getItemId() {
		return id;
	}

	public String getItemName() {
		return nombre;
	}
	
	public Provincia(){}
	
	public Provincia(String id, String nombre, String idPais){
		super();
		this.id = id;
		this.nombre=nombre;
		this.idPais = idPais;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
       // return "Provincia [id=" + this.id + ", nombre=" + this.nombre  + ", idPais=" + this.idPais + "]";
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
