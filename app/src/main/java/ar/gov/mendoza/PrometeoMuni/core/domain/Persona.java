package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_PERSONAS")
public class Persona implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3827819147171754856L;

	@SerializedName("Matricula")
	@Column(name="MATRICULA" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String matricula;

	@SerializedName("Apellido")
	@Column(name="APELLIDO")
	private String apellido;
	
	@SerializedName("Nombre")
	@Column(name="NOMBRE")
	private String nombre;

	@SerializedName("Sexo")
	@Column(name="SEXO")
	private String sexo;

	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	/* ISeleccionable methods */
	public String getItemId() {
		return matricula;
	}

	public String getItemName() {
		return apellido + ", "+ nombre;
	}
	
	public Persona(){}
	
	public Persona(String matricula, String nombre){
		super();
		this.matricula = matricula;
		this.nombre=nombre;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
    }

	@Override
	public void setItemId(String item) {
		// TODO Auto-generated method stub
		this.matricula = item;
		
	}

	@Override
	public void setItemName(String item) {
		// TODO Auto-generated method stub
		this.apellido = item;
		this.nombre = "";
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
