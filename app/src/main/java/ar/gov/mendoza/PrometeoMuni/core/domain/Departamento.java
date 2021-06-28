package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_DEPARTAMENTO")
public class Departamento implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdDepartamento")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Departamento")
	@Column(name="NOMBRE")
	private String nombre;
	
	@SerializedName("Provincia")
	@Column(name="ID_PROVINCIA")
	private String idProvincia;

	@SerializedName("Juzgado")
	@Column(name = "JUZGADO")
	private Integer juzgado;

	public Integer getJuzgado() { return juzgado; }

	public void setJuzgado(Integer juzgado) { this.juzgado = juzgado; }
	
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
	
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return nombre;
	}
	
	public Departamento(){}
	
	public Departamento(Integer id, String nombre, String idProvincia){
		super();
		this.id = id;
		this.nombre=nombre;
		this.idProvincia = idProvincia;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
        //return "Departamento [id=" + this.id + ", nombre=" + this.nombre  + ", idProvincia=" + this.idProvincia + "]";
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
