package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_DOMINIOS")
public class DominioPadron implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3827819147171754856L;

	@SerializedName("Dominio")
	@Column(name="DOMINIO" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String dominio;

	@SerializedName("IdTipoVehiculo")
	@Column(name="ID_TIPO_VEHICULO")
	private String idTipoVehiculo;
	
	
	@SerializedName("Modelo")
	@Column(name="MODELO")
	private String modelo;

	@SerializedName("IdMarca")
	@Column(name="ID_MARCA")
	private String idMarca;

	@SerializedName("Marca")
	@Column(name="MARCA")
	private String marca;
	
	@SerializedName("IdColor")
	@Column(name="ID_COLOR")
	private String idColor;
	
	@SerializedName("Color")
	@Column(name="COLOR")
	private String color;
	
	@SerializedName("IdTipoDocumento")
	@Column(name="ID_TIPO_DOCUMENTO")
	private String idTipoDocumento;
	
	@SerializedName("NumeroDocumento")
	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;
	
	@SerializedName("Anio")
	@Column(name="ANIO")
	private String anio;

	
	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	
	/* ISeleccionable methods */
	public String getItemId() {
		return dominio;
	}

	public String getItemName() {
		return dominio + " ["+ idTipoVehiculo + "]";
	}
	
	public String getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(String idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(String idMarca) {
		this.idMarca = idMarca;
	}

	public String getIdColor() {
		return idColor;
	}

	public void setIdColor(String idColor) {
		this.idColor = idColor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public DominioPadron(){}
	
	public DominioPadron(String dominio, String idTipoVechiculo){
		super();
		this.dominio = dominio;
		this.idTipoVehiculo = idTipoVechiculo;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
    }

	@Override
	public void setItemId(String item) {
		// TODO Auto-generated method stub
		this.dominio = item;
		
	}

	@Override
	public void setItemName(String item) {
		// TODO Auto-generated method stub
		this.dominio = item;
		this.idTipoVehiculo  = "";
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
