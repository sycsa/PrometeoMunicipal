package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_TIPO_RUTA")
public class TipoRuta implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdTipoRuta")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("TipoRuta")
	@Column(name="NOMBRE") 
	private String nombre;

	@SerializedName("EsCiudad")
	@Column(name="ES_CIUDAD") 
	private String esCiudad;
	
	public String getId() {
		return id;
	}

	public String getEsCiudad() {
		return esCiudad;
	}

	public void setEsCiudad(String esCiudad) {
		this.esCiudad = esCiudad;
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
		return id;
	}

	public String getItemName() {
		return nombre;
	}
	
	public TipoRuta(){}
	
	public TipoRuta(String id, String nombre){
		super();
		this.id = id;
		this.nombre=nombre;
	}
	
	@Override
    public String toString() {
        //return "Pais [id=" + this.id + ", nombre=" + this.nombre  + "]";
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
