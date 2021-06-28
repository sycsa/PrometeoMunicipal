package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_INFRACCIONES_NOMENCLADAS")
public class InfraccionNomenclada implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2122520122821027100L;

	@SerializedName("IdInfraccionNomenclada")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=false,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("Codigo")
	@Column(name="CODIGO")
	private String codigo;
	
	
	@SerializedName("Descripcion")
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@SerializedName("Estado")
	@Column(name="ESTADO")
	private String estado;
	
	
	@SerializedName("IdGrupoInfraccion")
	@Column(name="ID_GRUPO_INFRACCION")
	private Integer idGrupoInfraccion;
	
	@SerializedName("CostoAsociado")
	@Column(name="COSTO_ASOCIADO")
	private Integer costoAsociado;
	
	@SerializedName("DescripcionCorta")
	@Column(name="DESCRIPCION_CORTA")
	private String descripcionCorta;
	
	@SerializedName("TipoInfraccionId")
	@Column(name="TIPO_INFRACCION_ID")
	private Integer tipoInfraccionId;
	
	@SerializedName("Puntos")
	@Column(name="PUNTOS")
	private Integer puntos;
	
	@SerializedName("NotasImpresion")
	@Column(name="NOTAS_IMPRESION")
	private String notasImpresion;

	
	@SerializedName("ImprimirPuntos")
	@Column(name="IMPRIMIR_PUNTOS")
	private String imprimirPuntos;

	@SerializedName("Articulo")
	@Column(name="ARTICULO")
	private String articulo;

	@SerializedName("RetieneLicencia")
	@Column(name="RETIENE_LICENCIA")
	private String retieneLicencia;
	
	@SerializedName("SecuestraVehiculo")
	@Column(name="SECUESTRA_VEHICULO")
	private String secuestraVehiculo;

	@SerializedName("TipoFalta")
	@Column(name="TIPO_FALTA")
	private String tipoFalta;

	@SerializedName("OmitirCodBarra")
	@Column(name ="OMITIR_CODBARR")
	private String omitirCodBarra;

	public String getTipoFalta() {
		return tipoFalta;
	}

	public void setTipoFalta(String tipoFalta) {
		this.tipoFalta = tipoFalta;
	}

	public String getRetieneLicencia() {
		return retieneLicencia;
	}


	public void setRetieneLicencia(String retieneLicencia) {
		this.retieneLicencia = retieneLicencia;
	}


	public String getSecuestraVehiculo() {
		return secuestraVehiculo;
	}


	public void setSecuestraVehiculo(String secuestraVehiculo) {
		this.secuestraVehiculo = secuestraVehiculo;
	}


	private String observaciones;
	
	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public String getArticulo() {
		return articulo;
	}


	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}


	public String getInciso() {
		return inciso;
	}


	public void setInciso(String inciso) {
		this.inciso = inciso;
	}


	@SerializedName("Inciso")
	@Column(name="INCISO")
	private String inciso;

	public Integer getId() {
		return id;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}




	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}












	public void setId(Integer id) {
		this.id = id;
	}

		
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		if(this.id!=null && this.id!=-1)
			return "Codigo :" + this.codigo  ;
			else
			return	this.codigo;
		
	}
	
	public InfraccionNomenclada(){}
	
	public InfraccionNomenclada(Integer id, String descripcionCorta){
		super();
		this.id = id;
		this.descripcionCorta=descripcionCorta;
	}
	
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public Integer getIdGrupoInfraccion() {
		return idGrupoInfraccion;
	}


	public void setIdGrupoInfraccion(Integer idGrupoInfraccion) {
		this.idGrupoInfraccion = idGrupoInfraccion;
	}


	public Integer getCostoAsociado() {
		return costoAsociado;
	}


	public void setCostoAsociado(Integer costoAsociado) {
		this.costoAsociado = costoAsociado;
	}


	public String getDescripcionCorta() {
		return descripcionCorta;
	}


	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}


	public Integer getTipoInfraccionId() {
		return tipoInfraccionId;
	}


	public void setTipoInfraccionId(Integer tipoInfraccionId) {
		this.tipoInfraccionId = tipoInfraccionId;
	}


	public Integer getPuntos() {
		return puntos;
	}


	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}


	public String getNotasImpresion() {
		return notasImpresion;
	}


	public void setNotasImpresion(String notasImpresion) {
		this.notasImpresion = notasImpresion;
	}


	public String getImprimirPuntos() {
		return imprimirPuntos;
	}


	public void setImprimirPuntos(String imprimirPuntos) {
		this.imprimirPuntos = imprimirPuntos;
	}

	public String getOmitirCodBarra() {return omitirCodBarra;	}

	public void setOmitirCodBarra(String omitirCodBarra) { this.omitirCodBarra = omitirCodBarra;}


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
		this.codigo  = item;
	}


	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		if(this.id!=null && this.id!=-1)
			return  "Descripcion : " + this.descripcionCorta;
		else
			return "";
	}

}
