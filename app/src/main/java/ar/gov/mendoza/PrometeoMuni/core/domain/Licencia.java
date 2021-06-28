package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_LICENCIA")
public class Licencia implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4561450153721610851L;

	@SerializedName("NumeroLicencia")
	@Column(name="_id" ,type="TEXT",isAutoincrement=false,isPrimaryKey=true)
	private String numeroLicencia;

	@SerializedName("FechaEmision")
	@Column(name="FECHA_EMISION")
	private String fechaEmision;
	
	@SerializedName("FechaVencimiento")
	@Column(name="FECHA_VENCIMIENTO")
	private String fechaVencimiento;

	@SerializedName("FechaAnulacion")
	@Column(name="FECHA_ANULACION")
	private String fechaAnulacion;
	
	
	@SerializedName("IdClaseLicencia")
	@Column(name="ID_CLASE_LICENCIA")
	private String idClaseLicencia;
	
	
	@SerializedName("NumeroDocumentoConductor")
	@Column(name="CONDUCTOR")
	private String numeroDocumentoConductor;

	@SerializedName("Aprendiz")
	@Column(name="APRENDIZ")
	private String aprendiz;
	

	@SerializedName("IdEntidad")
	@Column(name="ID_ENTIDAD")
	private String idEntidad;

	
	
	@SerializedName("LicenciaUnicaProvincial")
	@Column(name="LICENCA_UNICA_PROVINCIAL")
	private String licenciaUnicaProvincial;

	/* ISeleccionable methods */
	public String getItemId() {
		return numeroLicencia;
	}

	public String getItemName() {
		return "Tipo ";
	}
	
	public Licencia(){}
	
	public Licencia(String numeroLicencia, String claseLicencia){
		super();
		this.numeroLicencia = numeroLicencia;
		this.idClaseLicencia=claseLicencia;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
    }

	@Override
	public void setItemId(String item) {
		this.numeroLicencia = item;
		
	}

	@Override
	public void setItemName(String item) {
		this.idClaseLicencia = item;
		
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
