package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

//import org.simpleframework.xml.Element;

import java.io.Serializable;
import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;

@Table(name ="T_ACTUALIZACION_WEBSERVICE")
public class ActualizacionWebService implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -991457763706443641L;

	@SerializedName("IdActualizacionWebService")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=true,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("IdActualizacionRePAT")
	@Column(name="ID_ACTUALIZACION_REPAT", defaultValue="0") 
	private String idActualizacionRepat;
	
	@SerializedName("Tipo")
	@Column(name="TIPO", defaultValue="SQL") 
	private String tipo;

	@SerializedName("Comando")
	@Column(name="COMANDO") 
	private String comando;
	
	@SerializedName("VersionPDA")
	@Column(name="VERSION_PDA") 
	private String versionPDA;

	//@Element
	@SerializedName("FechaRegistro")
	@Column(name="FECHA_REGISTRO",type ="INTEGER")// ANTES DATETIME
	private Date fechaRegistro;

	//@Element
	@SerializedName("FechaEjecucion")
	@Column(name="FECHA_EJECUCION",type ="INTEGER")// ANTES DATETIME
	private Date fechaEjecucion;

	@SerializedName("Realizado")
	@Column(name="REALIZADO", defaultValue="N") 
	private String realizado;
	
	@SerializedName("Observacion")
	@Column(name="OBSERVACION", defaultValue="") 
	private String observacion;

	@SerializedName("Confirmado")
	@Column(name="CONFIRMADO", defaultValue="") 
	private String confirmado;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String nombre) {
		this.tipo = nombre;
	}
	
	
	
	public String getIdActualizacionRepat() {
		return idActualizacionRepat;
	}

	public void setIdActualizacionRepat(String idActualizacionRepat) {
		this.idActualizacionRepat = idActualizacionRepat;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public String getVersionPDA() {
		return versionPDA;
	}

	public void setVersionPDA(String versionPDA) {
		this.versionPDA = versionPDA;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getRealizado() {
		return realizado;
	}

	public void setRealizado(String realizado) {
		this.realizado = realizado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(String confirmado) {
		this.confirmado = confirmado;
	}

	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return comando;
	}
	
	public ActualizacionWebService(){}
	
	public ActualizacionWebService(Integer id, String tipo, String comando, String versionPDA){
		super();
		this.id = id;
		this.tipo=tipo;
		this.comando=comando;
		this.realizado ="N";
		this.versionPDA = versionPDA;
		try
		{
		this.fechaRegistro = Tools.Today();
		}
		catch(Exception ex)
		{
			
		}
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
