package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_SECCIONAL")
public class Seccional implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdSeccional")
	@Column(name="_id" ,type="String",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("Seccional")
	@Column(name="NOMBRE")
	private String nombre;
	
	
	@SerializedName("Descripcion")
	@Column(name="DESCRIPCION")
	private String descripcion;
	


	
	public String getId() {
		return id;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return nombre;
	}
	
	public Seccional(){}
	
	public Seccional(String id, String nombre){
		super();
		this.id = id;
		this.nombre=nombre;
	}
	
	@Override
    public String toString() {
       		return this.getItemName();
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
