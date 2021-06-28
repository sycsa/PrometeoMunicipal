package ar.gov.mendoza.PrometeoMuni.sync.dto;

import com.google.gson.annotations.SerializedName;

public class DtoInicioInspeccion {

	@SerializedName("IdInspeccion")
	private int inspeccionId;
	
	@SerializedName("IdUsuario")
	private int usuarioId;
	
	@SerializedName("NombreUsuario")
	private String usuarioNombre;
	
	@SerializedName("MensajeError")
	private String error;
	
	@SerializedName("ContratoId")
	private int contratoId;
	
	@SerializedName("EmpresaId")  
	private int empresaId;
	
	@SerializedName("EmpresaRazonSocial")
	private String empresaRazonSocial;
	
	@SerializedName("UTIId")
	private int UTIId;
	
	@SerializedName("UTINombre")
	private String UTINombre;
	
	@SerializedName("UTICoordenadas")
	private String UTICoordenadas;
	
//	@SerializedName("ParametroGeneral")
//	private ArrayList<ParametroGeneral> parametros;
//	
//	
//	@SerializedName("DetallePenalidad")
//	private ArrayList<DetallePenalidad> detallesPenalidad;
	
	
//	@SerializedName("Defectos")
//	private ArrayList<TipoDefecto> defectos;
//
//	@SerializedName("Novedades")
//	private ArrayList<Novedad> novedades;
	
	@SerializedName("Auditor")
	private boolean auditor;
	
	public int getInspeccionId() {
		return inspeccionId;
	}

	public void setInspeccionId(int inspeccionId) {
		this.inspeccionId = inspeccionId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

//	public ArrayList<ParametroGeneral> getParametros() {
//		return parametros;
//	}

//	public void setParametros(ArrayList<ParametroGeneral> parametros) {
//		this.parametros = parametros;
//	}

	public int getContratoId() {
		return contratoId;
	}

	public void setContratoId(int contratoId) {
		this.contratoId = contratoId;
	}

	public int getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(int empresaId) {
		this.empresaId = empresaId;
	}
	
	public String getEmpresaRazonSocial() {
		return empresaRazonSocial;
	}

	public void setEmpresaRazonSocial(String empresaRazonSocial) {
		this.empresaRazonSocial = empresaRazonSocial;
	}
	
	public int getUTIId() {
		return UTIId;
	}

	public void setUTIId(int UTIId) {
		this.UTIId = UTIId;
	}
	
	public String getUTINombre() {
		return UTINombre;
	}

	public void setUTINombre(String UTINombre) {
		this.UTINombre = UTINombre;
	}
	
	public String getUTICoordenadas() {
		return UTICoordenadas;
	}

	public void setUTICoordenadas(String UTICoordenadas) {
		this.UTICoordenadas = UTICoordenadas;
	}
	
//	
//	public ArrayList<DetallePenalidad> getDetallesPenalidad() {
//		return detallesPenalidad;
//	}
//
//	public void setDetallesPenalidad(ArrayList<DetallePenalidad> detallesPenalidad) {
//		this.detallesPenalidad = detallesPenalidad;
//	}
//
//	public ArrayList<Novedad> getNovedades() {
//		return novedades;
//	}

//	public void setNovedades(ArrayList<Novedad> novedades) {
//		this.novedades = novedades;
//	}
//
//	public ArrayList<TipoDefecto> getDefectos() {
//		return defectos;
//	}
//
//	public void setDefectos(ArrayList<TipoDefecto> defectos) {
//		this.defectos = defectos;
//	}
	
	public boolean getAuditor() {
		return auditor;
	}

	public void setAuditor(boolean auditor) {
		this.auditor = auditor;
	}
}
