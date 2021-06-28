package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_INFORME_ACTAS")
public class InformeActa implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1574268003599575207L;

	String formatoFechaHora = "dd/MM/yyyy HH:mm:ss";
	String formatoFecha = "dd/MM/yyyy";
	
	@SerializedName("IdInforme")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Usuario")
	@Column(name="USUARIO") 
	private String usuario;
	
	@SerializedName("FechaYHora")
	@Column(name="FECHA_Y_HORA" ,type="INTEGER") 
	private Date fechayHora;
	
	private String fechayHoraString;
	
	@SerializedName("NumeroReferencia")
	@Column(name="NUMERO_REFERENCIA") 
	private String numeroReferencia;
	
	@SerializedName("Estado")
	@Column(name="ESTADO") 
	private String estado;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		
		if(this.id!=null && this.id!=-1)
			return "Numero de Referencia :" + this.numeroReferencia  ;
			else
			return	this.numeroReferencia;
	}
	
	public InformeActa(){}
	
	public InformeActa(Integer id, String numeroReferencia){
		super();
		this.id = id;
		this.numeroReferencia=numeroReferencia;
	}
	
	@Override
    public String toString() {
		return this.getItemName();
    }

	@Override
	public void setItemId(String item) {
		// TODO Auto-generated method stub
		this.id = Integer.parseInt(item);
	}

	@Override
	public void setItemName(String item) {
		// TODO Auto-generated method stub
		this.numeroReferencia= item;
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		if(this.id!=null && this.id!=-1)
			return  "Usuario : " + this.usuario +  "\nReferencia " + this.numeroReferencia + " Fecha Registro :" + this.fechayHoraString + "\nSincronizada : " + this.estado;
			else
			return "";
		
	}

	public Date getFechayHora() {
		return fechayHora;
	}

	public void setFechayHora(Date fechayHora) {
		this.fechayHora = fechayHora;
		if (fechayHora!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechayHoraString = formatter.format(fechayHora);
		}

	}

	public String getNumeroReferencia() {
		return numeroReferencia;
	}

	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechayHoraString() {
		return fechayHoraString;
	}

	public void setFechayHoraString(String fechayHoraString) {
		this.fechayHoraString = fechayHoraString;
	}

}
