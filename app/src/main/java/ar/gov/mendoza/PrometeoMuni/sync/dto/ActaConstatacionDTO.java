package ar.gov.mendoza.PrometeoMuni.sync.dto;

import java.io.Serializable;

//import ar.gov.mendoza.deviceactas.core.domain.Uti;
import com.google.gson.annotations.SerializedName;

import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;

public class ActaConstatacionDTO   implements ISeleccionable<String>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9014005274093278098L;


	@SerializedName("IdActaConstatacion")
	private Integer idActaConstatacion;
	
	@SerializedName("NumeroActa")
	private String numeroActa ;
	
	@SerializedName("Ruta")
	private String ruta;
	
	@SerializedName("NumeroRuta")
	private String numeroRuta;
	
	@SerializedName("Km")
	private String km ;
	
	@SerializedName("FechaLabrado")
	private String FechaLabrado;
	
	@SerializedName("NumeroLicencia")
	private String numeroLicencia;
	
	@SerializedName("SistemaUnicoProvincial")
	private String sistemaUnicoProvincial;
	
	@SerializedName("ClaseLicencia")
	private String claseLicencia;
	
	@SerializedName("FechaVencimiento")
	private String fechaVencimiento;
	
	@SerializedName("Retenida")
	private String retenida;

	@SerializedName("PaisActa")
	private String paisActa;

	@SerializedName("DependenciaExpeditaActa")
	private String dependenciaExpeditaActa;

	@SerializedName("LocalidadAlterntiva")
	private String localidadAlternativa;
	
	@SerializedName("ProvinciaLicencia")
	private String provinciaLicencia;
	
	@SerializedName("IdDepartamentoLicencia")
	private String IdDepartamentoLicencia;

	@SerializedName("DepartamentoLicencia")
	private String departamentoLicencia;
	
	@SerializedName("NumeroDocumento")
	private String numeroDocumento;
	
	@SerializedName("IdTipoDocumento")
	private String idTipoDocumento;
	
	@SerializedName("IdSexo")
	private String idSexo;
	
	@SerializedName("Apellido")
	private String apellido;
	
	@SerializedName("Nombre")
	private String nombre;
	
	@SerializedName("Calle")
	private String calle;
	
	@SerializedName("Piso")
	private String piso;
	
	@SerializedName("Depto")
	private String depto;
	
	@SerializedName("CodigoPostal")
	private String codigoPostal;
	
	@SerializedName("Barrio")
	private String barrio;
	
	@SerializedName("IdLocalidadConductor")
	private String idLocalidadConductor;
	
	@SerializedName("ProvinciaConductor")
	private String provinciaConductor;

	@SerializedName("ProvinciaConductorNuevo")
	private String provinciaConductorNuevo;
	
	@SerializedName("DeptoProvinciaConductor")
	private String deptoProvinciaConductor;
	
	@SerializedName("IdDeptoProvinciaConductor")
	private String idDeptoProvinciaConductor;

	
	
	@SerializedName("Dominio")
	private String dominio;
	
	@SerializedName("Marca")
	private String marca;
	
	@SerializedName("MarcaAlternativa")
	private String marcaAlternativa;
	
	@SerializedName("Color")
	private String color;
	
	@SerializedName("ColorAlternativo")
	private String colorAlternativo;
	
	@SerializedName("IdInfraccion1")
	private String idInfraccion1;
	
	@SerializedName("CodigoInfraccion1")
	private String codigoInfraccion1;

	@SerializedName("DescripcionInfraccion1")
	private String descripcionInfraccion1;
	
	@SerializedName("IdInfraccion2")
	private String idInfraccion2;

	@SerializedName("CodigoInfraccion2")
	private String codigoInfraccion2;

	@SerializedName("DescripcionInfraccion2")
	private String descripcionInfraccion2;

	public String getDescripcionInfraccion1() {
		return descripcionInfraccion1;
	}

	public void setDescripcionInfraccion1(String descripcionInfraccion1) {
		this.descripcionInfraccion1 = descripcionInfraccion1;
	}

	public String getDescripcionInfraccion2() {
		return descripcionInfraccion2;
	}

	public void setDescripcionInfraccion2(String descripcionInfraccion2) {
		this.descripcionInfraccion2 = descripcionInfraccion2;
	}


	@SerializedName("Observaciones")
	private String observaciones;
	

	@SerializedName("IdJuzgado")
	private String idJuzgado;
	

	@SerializedName("CalleJuzgado")
	private String calleJuzgado;

	@SerializedName("NumeroJuzgado")
	private String numeroJuzgado;
	
	@SerializedName("LocalidadJuzgado")
	private String localidadJuzgado;
	
	@SerializedName("CodigoPostalJuzgado")
	private String codigoPostalJuzgado;

	@SerializedName("CodigoBarra")
	private String codigoBarra;


	@SerializedName("FechaVencimientoCupon")
	private String fechaVencimientoCupon;
	
	@SerializedName("MontoCupon")
	private String montoCUpon;
	
	@SerializedName("Anulada")
	private String anulada;
	

	public String getAnulada() {
		return anulada;
	}

	public void setAnulada(String anulada) {
		this.anulada = anulada;
	}

	public Integer getIdActaConstatacion() {
		return idActaConstatacion;
	}

	public void setIdActaConstatacion(Integer idActaConstatacion) {
		this.idActaConstatacion = idActaConstatacion;
	}

	public String getNumeroActa() {
		return numeroActa;
	}

	public void setNumeroActa(String numeroActa) {
		this.numeroActa = numeroActa;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNumeroRuta() {
		return numeroRuta;
	}

	public void setNumeroRuta(String numeroRuta) {
		this.numeroRuta = numeroRuta;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getFechaLabrado() {
		return FechaLabrado;
	}

	public void setFechaLabrado(String fechaLabrado) {
		FechaLabrado = fechaLabrado;
	}

	public String getNumeroLicencia() {
		return numeroLicencia;
	}

	public void setNumeroLicencia(String numeroLicencia) {
		this.numeroLicencia = numeroLicencia;
	}

	public String getSistemaUnicoProvincial() {
		return sistemaUnicoProvincial;
	}

	public void setSistemaUnicoProvincial(String sistemaUnicoProvincial) {
		this.sistemaUnicoProvincial = sistemaUnicoProvincial;
	}

	public String getClaseLicencia() {
		return claseLicencia;
	}

	public void setClaseLicencia(String claseLicencia) {
		this.claseLicencia = claseLicencia;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getRetenida() {
		return retenida;
	}

	public void setRetenida(String retenida) {
		this.retenida = retenida;
	}

	public String getPaisActa() {
		return paisActa;
	}

	public void setPaisActa(String paisActa) {
		this.paisActa = paisActa;
	}

	public String getDependenciaExpeditaActa() {
		return dependenciaExpeditaActa;
	}

	public void setDependenciaExpeditaActa(String dependenciaExpeditaActa) {
		this.dependenciaExpeditaActa = dependenciaExpeditaActa;
	}

	public String getLocalidadAlternativa() {
		return localidadAlternativa;
	}

	public void setLocalidadAlternativa(String localidadAlternativa) {
		this.localidadAlternativa = localidadAlternativa;
	}

	public String getProvinciaLicencia() {
		return provinciaLicencia;
	}

	public void setProvinciaLicencia(String provinciaLicencia) {
		this.provinciaLicencia = provinciaLicencia;
	}

	public String getIdDepartamentoLicencia() {
		return IdDepartamentoLicencia;
	}

	public void setIdDepartamentoLicencia(String idDepartamentoLicencia) {
		IdDepartamentoLicencia = idDepartamentoLicencia;
	}

	public String getDepartamentoLicencia() {
		return departamentoLicencia;
	}

	public void setDepartamentoLicencia(String departamentoLicencia) {
		this.departamentoLicencia = departamentoLicencia;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getIdSexo() {
		return idSexo;
	}

	public void setIdSexo(String idSexo) {
		this.idSexo = idSexo;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getIdLocalidadConductor() {
		return idLocalidadConductor;
	}

	public void setIdLocalidadConductor(String idLocalidadConductor) {
		this.idLocalidadConductor = idLocalidadConductor;
	}

	public String getProvinciaConductor() {
		return provinciaConductor;
	}

	public void setProvinciaConductor(String provinciaConductor) {
		this.provinciaConductor = provinciaConductor;
	}

	public String getProvinciaConductorNuevo() {
		return provinciaConductorNuevo;
	}

	public void setProvinciaConductorNuevo(String provinciaConductorNuevo) {
		this.provinciaConductorNuevo = provinciaConductorNuevo;
	}

	public String getDeptoProvinciaConductor() {
		return deptoProvinciaConductor;
	}

	public void setDeptoProvinciaConductor(String deptoProvinciaConductor) {
		this.deptoProvinciaConductor = deptoProvinciaConductor;
	}

	public String getIdDeptoProvinciaConductor() {
		return idDeptoProvinciaConductor;
	}

	public void setIdDeptoProvinciaConductor(String idDeptoProvinciaConductor) {
		this.idDeptoProvinciaConductor = idDeptoProvinciaConductor;
	}

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

	public String getMarcaAlternativa() {
		return marcaAlternativa;
	}

	public void setMarcaAlternativa(String marcaAlternativa) {
		this.marcaAlternativa = marcaAlternativa;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorAlternativo() {
		return colorAlternativo;
	}

	public void setColorAlternativo(String colorAlternativo) {
		this.colorAlternativo = colorAlternativo;
	}

	public String getIdInfraccion1() {
		return idInfraccion1;
	}

	public void setIdInfraccion1(String idInfraccion1) {
		this.idInfraccion1 = idInfraccion1;
	}

	public String getCodigoInfraccion1() {
		return codigoInfraccion1;
	}

	public void setCodigoInfraccion1(String codigoInfraccion1) {
		this.codigoInfraccion1 = codigoInfraccion1;
	}

	public String getIdInfraccion2() {
		return idInfraccion2;
	}

	public void setIdInfraccion2(String idInfraccion2) {
		this.idInfraccion2 = idInfraccion2;
	}

	public String getCodigoInfraccion2() {
		return codigoInfraccion2;
	}

	public void setCodigoInfraccion2(String codigoInfraccion2) {
		this.codigoInfraccion2 = codigoInfraccion2;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	public String getCalleJuzgado() {
		return calleJuzgado;
	}

	public void setCalleJuzgado(String calleJuzgado) {
		this.calleJuzgado = calleJuzgado;
	}

	public String getNumeroJuzgado() {
		return numeroJuzgado;
	}

	public void setNumeroJuzgado(String numeroJuzgado) {
		this.numeroJuzgado = numeroJuzgado;
	}

	public String getLocalidadJuzgado() {
		return localidadJuzgado;
	}

	public void setLocalidadJuzgado(String localidadJuzgado) {
		this.localidadJuzgado = localidadJuzgado;
	}

	public String getCodigoPostalJuzgado() {
		return codigoPostalJuzgado;
	}

	public void setCodigoPostalJuzgado(String codigoPostalJuzgado) {
		this.codigoPostalJuzgado = codigoPostalJuzgado;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getFechaVencimientoCupon() {
		return fechaVencimientoCupon;
	}

	public void setFechaVencimientoCupon(String fechaVencimientoCupon) {
		this.fechaVencimientoCupon = fechaVencimientoCupon;
	}

	public String getMontoCUpon() {
		return montoCUpon;
	}

	public void setMontoCUpon(String montoCUpon) {
		this.montoCUpon = montoCUpon;
	}

	@Override
	public String getItemId() {
		// TODO Auto-generated method stub
		return this.idActaConstatacion.toString();
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		if(this.idActaConstatacion!=null && this.idActaConstatacion!=-1)
			return "Numero de Acta :" + this.numeroActa  ;
			else
			return	this.numeroActa;
	}

	@Override
	public void setItemId(String item) {
		this.idActaConstatacion =Integer.parseInt(item);
		
	}

	@Override
	public void setItemName(String item) {
		this.numeroActa  = item;// + this.apellido + ", " + this.nombre + " " + this.numeroDocumento + " " + this.fechaHoraCarga ;
		
	}

	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		if(this.idActaConstatacion!=null && this.idActaConstatacion!=-1)
			return  "Infractor : " + this.apellido + ", " + this.nombre + "\nDNI " + this.numeroDocumento + "\nFecha Labrado :" + this.FechaLabrado ;
			else
			return "";
	}


	@Override
	public String toString() {

		StringBuilder strActaDto = new  StringBuilder();
		strActaDto.append("Numero Acta :").append(this.getNumeroActa())
		.append("\nFecha Labrado :").append(this.FechaLabrado)
		.append("\nNro.Documento :").append(this.numeroDocumento)
		.append("\nApellido y Nombre :").append(this.apellido).append(", ").append(this.nombre);
		if(this.codigoInfraccion1!=null)
		{
			strActaDto.append("\nCodigo Infraccion (1) :").append(this.codigoInfraccion1);
			strActaDto.append("\n").append(this.descripcionInfraccion1);
		}
		
		if(this.codigoInfraccion2!=null)
		{
			strActaDto.append("\nCodigo Infraccion (2) :").append(this.codigoInfraccion2);
			strActaDto.append("\n").append(this.descripcionInfraccion2);
		}
		return strActaDto.toString();
	}
	
}
