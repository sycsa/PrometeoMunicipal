package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_PERSONAS")
public class PersonaPadron implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3827819147171754856L;

	@SerializedName("IdTipoDocumento")
	@Column(name="ID_TIPO_DOCUMENTO") 
	private String idTipoDocumento;

	
	@SerializedName("NumeroDocumento")
	@Column(name="NUMERO_DOCUMENTO" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String numeroDocumento;

	@SerializedName("Apellido")
	@Column(name="APELLIDO") 
	private String apellido;
	
	@SerializedName("Nombre")
	@Column(name="NOMBRE") 
	private String nombre;

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@SerializedName("Calle")
	@Column(name="CALLE") 
	private String calle;

	@SerializedName("Altura")
	@Column(name="ALTURA") 
	private String altura;

	@SerializedName("Piso")
	@Column(name="PISO") 
	private String piso;
	
	@SerializedName("Depto")
	@Column(name="DEPTO") 
	private String depto;
	public String getDepto() {
		return depto;
	}
	
	@SerializedName("Barrio")
	@Column(name="BARRIO") 
	private String barrio;


	@SerializedName("IdLocalidad")
	@Column(name="ID_LOCALIDAD") 
	private String idLocalidad;

	@SerializedName("Pais")
	@Column(name="PAIS") 
	private String pais;
	
	@SerializedName("Provincia")
	@Column(name="PROVINCIA") 
	private String provincia;

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(String idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	@SerializedName("Departamento")
	@Column(name="DEPARTAMENTO") 
	private String departamento;
	
	@SerializedName("Localidad")
	@Column(name="LOCALIDAD") 
	private String localidad;




	
	

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
		return numeroDocumento;
	}

	public String getItemName() {
		return apellido + ", "+ nombre;
	}
	
	public PersonaPadron(){}
	
	public PersonaPadron(String numeroDocumento, String nombre){
		super();
		this.numeroDocumento = numeroDocumento;
		this.nombre=nombre;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
    }

	@Override
	public void setItemId(String item) {
		// TODO Auto-generated method stub
		this.numeroDocumento = item;
		
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
