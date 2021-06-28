package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_GENERO")
public class Genero implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdGenero")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("Genero")
	@Column(name="NOMBRE")
	private String nombre;
	
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
	
	public Genero(){}
	
	public Genero(String id, String nombre){
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
