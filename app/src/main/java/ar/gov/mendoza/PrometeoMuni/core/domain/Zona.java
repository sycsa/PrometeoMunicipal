package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_ZONA")
public class Zona implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdZona")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("Zona")
	@Column(name="NOMBRE")
	private String nombre;
	
	@SerializedName("RutasHabilitadas")
	@Column(name="RUTAS_HABILITADAS")
	private String rutasHabilitadas;
	
	
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
	
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id;
	}

	public String getItemName() {
		return nombre;
	}
	
	public Zona(){}
	
	public String getRutasHabilitadas() {
		return rutasHabilitadas;
	}

	public void setRutasHabilitadas(String rutasHabilitadas) {
		this.rutasHabilitadas = rutasHabilitadas;
	}

	public Zona(String id, String nombre, String rutasHabilitadas){
		super();
		this.id = id;
		this.nombre=nombre;
		this.rutasHabilitadas = rutasHabilitadas;
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
