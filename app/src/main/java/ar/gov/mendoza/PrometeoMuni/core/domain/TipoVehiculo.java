package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_TIPO_VEHICULO")
public class TipoVehiculo implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7596582981600347411L;

	@SerializedName("IdTipoVehiculo")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("TipoVehiculo")
	@Column(name="NOMBRE") 
	private String nombre;

	@SerializedName("RequiereDominio")
	@Column(name="REQUIERE_DOMINIO") 
	private String requiereDominio;
	
	public String getId() {
		return id;
	}

	public String getRequiereDominio() {
		return requiereDominio;
	}

	public void setRequiereDominio(String requiereDominio) {
		this.requiereDominio = requiereDominio;
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
	
	public TipoVehiculo(){}
	
	public TipoVehiculo(String id, String nombre){
		super();
		this.id = id;
		this.nombre=nombre;
		
	}
	public TipoVehiculo(String id, String nombre, String requiereDominio){
		super();
		this.id = id;
		this.nombre=nombre;
		this.requiereDominio= requiereDominio;
		
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
