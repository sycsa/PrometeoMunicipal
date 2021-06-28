package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_DOCUMENTO")
public class TipoDocumento implements ISeleccionable<String> , Serializable {
	
	@SerializedName("IdDocumento")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String id;
	
	@SerializedName("Nombre")
	@Column(name="NOMBRE") 
	private String nombre;
	
	@SerializedName("Descripcion")
	@Column(name="DESCRIPCION") 
	private String descripcion;

	@SerializedName("CantidadMinimaCaracteres")
	@Column(name="CANT_MIN_CAR") 
	private String cantidadMinimaCaracteres;

	@SerializedName("CantidadMaximaCaracteres")
	@Column(name="CANT_MAX_CAR") 
	private String cantidadMaximaCaracteres;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCantidadMinimaCaracteres() {
		return cantidadMinimaCaracteres;
	}

	public void setCantidadMinimaCaracteres(String cantidadMinimaCaracteres) {
		this.cantidadMinimaCaracteres = cantidadMinimaCaracteres;
	}

	public String getCantidadMaximaCaracteres() {
		return cantidadMaximaCaracteres;
	}

	public void setCantidadMaximaCaracteres(String cantidadMaximaCaracteres) {
		this.cantidadMaximaCaracteres = cantidadMaximaCaracteres;
	}

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
	
	public TipoDocumento(){}
	
	public TipoDocumento(String id, String nombre){
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
