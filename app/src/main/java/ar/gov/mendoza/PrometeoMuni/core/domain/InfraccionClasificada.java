package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="T_INFRACCIONES_CLASIFICADAS")
public class InfraccionClasificada implements ISeleccionable<String>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2122520122821027100L;

	@SerializedName("IdInfraccionClasificada")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=false,isPrimaryKey=false)
	private Integer id;
	
	@SerializedName("Codigo")
	@Column(name="CODIGO")
	private String codigo;
	
	@SerializedName("Clasificacion")
	@Column(name="CLASIFICACION")
	private String clasificacion;

	
	@SerializedName("Descripcion")
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	
	@SerializedName("CostoAsociado")
	@Column(name="COSTO_ASOCIADO")
	private Integer costoAsociado;
	
	
	@SerializedName("Puntos")
	@Column(name="PUNTOS")
	private Integer puntos;
	
	@SerializedName("NotasImpresion")
	@Column(name="NOTAS_IMPRESION")
	private String notasImpresion;

	
	@SerializedName("VerificarViaWebService")
	@Column(name="VERIFICAR_VIA_WEBSERVICE")
	private String verificarViaWebService;

	@SerializedName("ImprimirPuntos")
	@Column(name="IMPRIMIR_PUNTOS")
	private String imprimirPuntos;

	
	public Integer getId() {
		return id;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
	public InfraccionClasificada(){}
	
	public InfraccionClasificada(Integer id, String clasificacion){
		super();
		this.id = id;
		this.clasificacion=clasificacion;
	}
	
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}






	public Integer getCostoAsociado() {
		return costoAsociado;
	}


	public void setCostoAsociado(Integer costoAsociado) {
		this.costoAsociado = costoAsociado;
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
			return  "Clasificacon : " + this.clasificacion;
		else
			return "";
	}


	public String getClasificacion() {
		return clasificacion;
	}


	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}


	public String getVerificarViaWebService() {
		return verificarViaWebService;
	}


	public void setVerificarViaWebService(String verificarViaWebService) {
		this.verificarViaWebService = verificarViaWebService;
	}

}
