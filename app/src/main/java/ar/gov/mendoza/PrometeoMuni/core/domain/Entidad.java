package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_ENTIDAD")
public class Entidad implements ISeleccionable<String>, Serializable {
	
	@SerializedName("IdEntidad")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Entidad")
	@Column(name="NOMBRE") 
	private String nombre;
	
	
	@SerializedName("IdDependencia")
	@Column(name="ID_DEPENDENCIA") 
	private String idDependencia;
	
	@SerializedName("NumeroIdentificacion")
	@Column(name="NUMERO_IDENTIFICACION") 
	private String numeroIdentificacion;
	
	
	@SerializedName("Descripcion")
	@Column(name="DESCRIPCION") 
	private String descripcion;
	
	@SerializedName("IdDomicilio")
	@Column(name="ID_DOMICILIO") 
	private String idDomicilio;
	
	@SerializedName("Estado")
	@Column(name="ESTADO") 
	private String estado;
	
	@SerializedName("IdTipoEntidad")
	@Column(name="ID_TIPO_ENTIDAD") 
	private String idTipoEntidad;
	
	@SerializedName("DomicilioCalle")
	@Column(name="ENTIDADDOMCALLE") 
	private String domicilioCalle;
	
	@SerializedName("DomicilioNumero")
	@Column(name="ENTIDADDOMNUMERO")	 
	private String domicilioNumero;

	
	@SerializedName("DomicilioLocalidad")
	@Column(name="ENTIDADDOMLOCALIDAD")	 
	private String domicilioLocalidad;
	
	@SerializedName("DomicilioCodigoPostal")
	@Column(name="ENTIDADDOMCP")	 
	private String domicilioCodigoPostal;
	
	@SerializedName("EsPolicial")
	@Column(name="POLICIAL")	 
	private String esPolicial;

	@SerializedName("Telefono")
	@Column(name="TELEFONO",type ="TEXT")
	private String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@SerializedName("Email")
	@Column(name="EMAIL",type ="TEXT")
	private String email;

	
	public Integer getId() {
		return id;
	}

	public String getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(String idDependencia) {
		this.idDependencia = idDependencia;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(String idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdTipoEntidad() {
		return idTipoEntidad;
	}

	public void setIdTipoEntidad(String idTipoEntidad) {
		this.idTipoEntidad = idTipoEntidad;
	}

	public String getDomicilioCalle() {
		return domicilioCalle;
	}

	public void setDomicilioCalle(String domicilioCalle) {
		this.domicilioCalle = domicilioCalle;
	}

	public String getDomicilioNumero() {
		return domicilioNumero;
	}

	public void setDomicilioNumero(String domicilioNumero) {
		this.domicilioNumero = domicilioNumero;
	}

	public String getDomicilioLocalidad() {
		return domicilioLocalidad;
	}

	public void setDomicilioLocalidad(String domicilioLocalidad) {
		this.domicilioLocalidad = domicilioLocalidad;
	}

	public String getDomicilioCodigoPostal() {
		return domicilioCodigoPostal;
	}

	public void setDomicilioCodigoPostal(String domicilioCodigoPostal) {
		this.domicilioCodigoPostal = domicilioCodigoPostal;
	}

	public String getEsPolicial() {
		return esPolicial;
	}

	public void setEsPolicial(String esPolicial) {
		this.esPolicial = esPolicial;
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
	
	public Entidad(){}
	
	public Entidad(Integer id, String nombre){
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
