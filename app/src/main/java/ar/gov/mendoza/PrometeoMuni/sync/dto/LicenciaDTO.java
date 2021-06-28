package ar.gov.mendoza.PrometeoMuni.sync.dto;

import java.io.Serializable;

public class LicenciaDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8310736567291898515L;
	private String numeroLicencia;
	private String sistemaUnicoProvincial;
	private String claseLicencia;
	private String numeroDocumento;
	private String idSexo;
	private String apellido;
	private String nombre;
    private String fechaVencimiento;
	private String retenida;
	private String paisLicencia;
	private String nPaisLicencia;
	
	private String provinciaLicencia;
	private String nProvinciaLicencia;
	private String departamentoLicencia;
	private String nDepartamentoLicencia;
	private String localidadLicencia;
	private String nLocalidadLicencia;
	private String idPersona;
	private String estadoPersona;
	private String pedidoCaptura; 
	private String barrioTitular;
	private String calleTitular;
	private String alturaTitular;
	private String pisoTitular;
	private String deptoTitular;
	private String codigoPostalTitular;
	private String idTipoDocumento;
	private String licenciaAnulada;
	private String motivoAnulacionLicencia;
	private String fechaAnulacionLicencia;
	private String paisTitular;
	private String provinciaTitular;
	private String departamentoTitular;
	private String localidadTitular;
	private String licenciaVencida;
	private String restricciones;

	/* Getters and Setters */
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
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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
	public String getProvinciaLicencia() {
		return provinciaLicencia;
	}
	public void setProvinciaLicencia(String provinciaLicencia) {
		this.provinciaLicencia = provinciaLicencia;
	}
	public String getnProvinciaLicencia() {
		return nProvinciaLicencia;
	}
	public void setnProvinciaLicencia(String nProvinciaLicencia) {
		this.nProvinciaLicencia = nProvinciaLicencia;
	}
	public String getDepartamentoLicencia() {
		return departamentoLicencia;
	}
	public void setDepartamentoLicencia(String departamentoLicencia) {
		this.departamentoLicencia = departamentoLicencia;
	}
	public String getnDepartamentoLicencia() {
		return nDepartamentoLicencia;
	}
	public void setnDepartamentoLicencia(String nDepartamentoLicencia) {
		this.nDepartamentoLicencia = nDepartamentoLicencia;
	}
	public String getLocalidadLicencia() {
		return localidadLicencia;
	}
	public void setLocalidadLicencia(String localidadLicencia) {
		this.localidadLicencia = localidadLicencia;
	}
	public String getnLocalidadLicencia() {
		return nLocalidadLicencia;
	}
	public void setnLocalidadLicencia(String nLocalidadLicencia) {
		this.nLocalidadLicencia = nLocalidadLicencia;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getEstadoPersona() {
		return estadoPersona;
	}
	public void setEstadoPersona(String estadoPersona) {
		this.estadoPersona = estadoPersona;
	}
	public String getPedidoCaptura() {
		return pedidoCaptura;
	}
	public void setPedidoCaptura(String pedidoCaptura) {
		this.pedidoCaptura = pedidoCaptura;
	}
	public String getBarrioTitular() {
		return barrioTitular;
	}
	public void setBarrioTitular(String barrioTitular) {
		this.barrioTitular = barrioTitular;
	}
	public String getCalleTitular() {
		return calleTitular;
	}
	public void setCalleTitular(String calleTitular) {
		this.calleTitular = calleTitular;
	}
	public String getAlturaTitular() {
		return alturaTitular;
	}
	public void setAlturaTitular(String alturaTitular) {
		this.alturaTitular = alturaTitular;
	}
	public String getPisoTitular() {
		return pisoTitular;
	}
	public void setPisoTitular(String pisoTitular) {
		this.pisoTitular = pisoTitular;
	}
	public String getDeptoTitular() {
		return deptoTitular;
	}
	public void setDeptoTitular(String deptoTitular) {
		this.deptoTitular = deptoTitular;
	}
	public String getCodigoPostalTitular() {
		return codigoPostalTitular;
	}
	public void setCodigoPostalTitular(String codigoPostalTitular) {
		this.codigoPostalTitular = codigoPostalTitular;
	}
	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public String getLicenciaAnulada() {
		return licenciaAnulada;
	}
	public void setLicenciaAnulada(String licenciaAnulada) {
		this.licenciaAnulada = licenciaAnulada;
	}
	public String getMotivoAnulacionLicencia() {
		return motivoAnulacionLicencia;
	}
	public void setMotivoAnulacionLicencia(String motivoAnulacionLicencia) {
		this.motivoAnulacionLicencia = motivoAnulacionLicencia;
	}
	public String getFechaAnulacionLicencia() {
		return fechaAnulacionLicencia;
	}
	public void setFechaAnulacionLicencia(String fechaAnulacionLicencia) {
		this.fechaAnulacionLicencia = fechaAnulacionLicencia;
	}
	public String getPaisTitular() {
		return paisTitular;
	}
	public void setPaisTitular(String paisTitular) {
		this.paisTitular = paisTitular;
	}
	public String getProvinciaTitular() {
		return provinciaTitular;
	}
	public void setProvinciaTitular(String provinciaTitular) {
		this.provinciaTitular = provinciaTitular;
	}
	public String getDepartamentoTitular() {
		return departamentoTitular;
	}
	public void setDepartamentoTitular(String departamentoTitular) {
		this.departamentoTitular = departamentoTitular;
	}
	public String getLocalidadTitular() {
		return localidadTitular;
	}
	public void setLocalidadTitular(String localidadTitular) {
		this.localidadTitular = localidadTitular;
	}
	public String getLicenciaVencida() {
		return licenciaVencida;
	}
	public void setLicenciaVencida(String licenciaVencida) {
		this.licenciaVencida = licenciaVencida;
	}
	public String getRestricciones() {
		return restricciones;
	}
	public void setRestricciones(String restricciones) {
		this.restricciones = restricciones;
	}
	public String getPaisLicencia() {
		return paisLicencia;
	}
	public void setPaisLicencia(String paisLicencia) {
		this.paisLicencia = paisLicencia;
	}
	public String getnPaisLicencia() {
		return nPaisLicencia;
	}
	public void setnPaisLicencia(String nPaisLicencia) {
		this.nPaisLicencia = nPaisLicencia;
	}
	
	
}