package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_RUTA")
public class Ruta implements ISeleccionable<String> , Serializable {
	
	@SerializedName("IdRuta")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("Ruta")
	@Column(name="NOMBRE") 
	private String nombre;
	
	@SerializedName("ID_TIPO_RUTA")
	@Column(name="ID_TIPO_RUTA") 
	private String idTipoRuta;
	
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
	
	public String getIdTipoRuta() {
		return idTipoRuta;
	}

	public void setIdTipoRuta(String idTipoRuta) {
		this.idTipoRuta= idTipoRuta;
	}
	/* ISeleccionable methods */
	public String getItemId() {
		return id;
	}

	public String getItemName() {
		return nombre;
	}
	
	public Ruta(){}
	
	public Ruta(String id, String nombre, String idTipoRuta){
		super();
		this.id = id;
		this.nombre=nombre;
		this.idTipoRuta= idTipoRuta;
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
