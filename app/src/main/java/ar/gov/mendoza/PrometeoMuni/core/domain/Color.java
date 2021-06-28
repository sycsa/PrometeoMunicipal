package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_COLOR")
public class Color implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdColor")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Color")
	@Column(name="NOMBRE")
	private String nombre;
	
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
	
	public Color(){}
	
	public Color(Integer id, String nombre){
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
