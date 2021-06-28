package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;

@Table(name ="SUPORT_TABLE")
public class SuportTable implements ISeleccionable<String> , Serializable, Cloneable {
	
	@SerializedName("IdSuportTable")
	@Column(name="_id" ,type="INTEGER",isAutoincrement=true,isPrimaryKey=true)
	private Integer id;
	
	@SerializedName("LicenciaMinCar")
	@Column(name="LICENCIA_MIN_CAR" , type = "INTEGER") 
	private Integer licenciaMinCar;
	
	@SerializedName("TipoDocMin")
	@Column(name="TIPO_DOC_MIN_CAR", type="INTEGER") 
	private Integer tipoDocMinCar;
	
	@SerializedName("DominioMinCar")
	@Column(name="DOMINIO_MIN_CAR", type="INTEGER") 
	private Integer dominioMinCar;
	
	
	@SerializedName("NroEquipo")
	@Column(name="NRO_EQUIPO", type="INTEGER") 
	private Integer nroEquipo;
	
	@SerializedName("CurrentCounter")
	@Column(name="CURRENT_COUNTER", type="INTEGER") 
	private Integer currentCounter;
	
	@SerializedName("FemaleName")
	@Column(name="FEMALE_NAME") 
	private String femaleName;
	
	@SerializedName("MaleName")
	@Column(name="MALE_NAME") 
	private String maleName;
	
	@SerializedName("PrinterCOM")
	@Column(name="PRINTER_COM") 
	private String printerCom;
	
	@SerializedName("PrinterTransferencia")
	@Column(name="PRINTER_TRANSFERENCIA")	 
	private String printerTransferencia;

	
	@SerializedName("PrinterParidad")
	@Column(name="PRINTER_PARIDAD")	 
	private String printerParidad;
	
	@SerializedName("PrinterDataBits")
	@Column(name="PRINTER_DATA_BITS")	 
	private String printerDataBits;
	
	@SerializedName("PrinterStop")
	@Column(name="PRINTER_STOP")	 
	private String printerStop;

	@SerializedName("WSLocation")
	@Column(name="WSLOCATION")	 
	private String wsLocation;

	@SerializedName("CriptoKey")
	@Column(name="CRIPTO_KEY")	 
	private String criptoKey;

	@SerializedName("CriptoVector")
	@Column(name="CRIPTO_VECTOR")	 
	private String criptoVector;

	@SerializedName("ImprimeCuponOrig")
	@Column(name="IMPRIME_CUPON_ORIG")	 
	private String imprimeCupoOrig;

	@SerializedName("ImporteNP")
	@Column(name="IMPORTE_NP", type ="REAL")	 
	private Double importeNP;

	@SerializedName("PorcDescuento")
	@Column(name="PORC_DESCUENTO", type ="REAL")	 
	private Double porcDescuento;

	@SerializedName("Convenio")
	@Column(name="CONVENIO")	 
	private String convenio;

	@SerializedName("GrupoDeConvenio")
	@Column(name="GRUPO_DE_CONVENIO")	 
	private String grupoDeConvenio;

	@SerializedName("IdUsuarioLogueado")
	@Column(name="ID_USUARIO_LOGUEADO",type="INTEGER")	 
	private Integer idUsuarioLogueado;

	@SerializedName("TimerTick" )
	@Column(name="TIMER_TICK",type="INTEGER")	 
	private Integer timerTick;

	@SerializedName("CantDiasInformes" )
	@Column(name="CANT_DIAS_INFORMES",type="INTEGER")	 
	private Integer cantDiasInformes;

	@SerializedName("SoftwareNuevo")
	@Column(name="SOFTWARE_NUEVO")	 
	private String softwareNuevo;

	@SerializedName("PermisoLabrarActas")
	@Column(name="PERMISO_LABRAR_ACTAS")	 
	private String permisoLabrarActas;
	
	@SerializedName("WSPoliciaViaWSRepat")
	@Column(name="WSPOLICIA_VIA_WSREPAT")	 
	private String wsPoliciaViaRepat;
	
	@SerializedName("PermisoActualizarPosicionGPS")
	@Column(name="PERMISO_ACTUALIZAR_POSICION_GPS")	 
	private String permisoActualizarPosicionGPS;
	
	@SerializedName("EnviarLogPDA")
	@Column(name="ENVIAR_LOG_PDA")	 
	private String enviaLogPDA;

	@SerializedName("FotosObligatoriasEnActas" )
	@Column(name="FOTOS_OBLIGATORIAS_EN_ACTAS")	 
	private String fotosObligatoriasEnActas;

	@SerializedName("CantDias1erVtoActa" )
	@Column(name="CANT_DIAS_1ER_VTO_ACTA",type="INTEGER")	 
	private Integer cantDias1erVtoActa;


	@SerializedName("CantDias2doVtoActa" )
	@Column(name="CANT_DIAS_2DO_VTO_ACTA",type="INTEGER")	 
	private Integer cantDias2doVtoActa;


	public Integer getCantDias2doVtoActa() {
		return cantDias2doVtoActa;
	}

	public void setCantDias2doVtoActa(Integer cantDias2doVtoActa) {
		this.cantDias2doVtoActa = cantDias2doVtoActa;
	}

	@SerializedName("HabilitarCHKNoPoseeLicencia" )
	@Column(name="HABILITAR_CHK_NO_POSEE_LICENCIA")	 
	private String habilitarCHKNoPoseeLicencia;
	
	@SerializedName("BusquedaObligatoriaLicencia" )
	@Column(name="BUSQUEDA_OBLIGATORIA_LICENCIA")	 
	private String busquedaObligatoriaLicencia;
	
	@SerializedName("BusquedaObligatoriaDocumento" )
	@Column(name="BUSQUEDA_OBLIGATORIA_DOCUMENTO")	 
	private String busquedaObligatoriaDocumento;
	
	@SerializedName("BusquedaObligatoriaDominio" )
	@Column(name="BUSQUEDA_OBLIGATORIA_DOMINIO")	 
	private String busquedaObligatoriaDominio;
	
	@SerializedName("HabilitarBTNCodigoQR" )
	@Column(name="HABILITAR_BTN_CODIGO_QR")	 
	private String habilitarBTNCodigoQR;
	
	@SerializedName("UsarCupoCodigoBarraDoble" )
	@Column(name="USAR_CUPON_CODIGOBARRA_DOBLE")	 
	private String usarCupoCodigoBarraDoble;
	
	@SerializedName("TimerTickConexionServicioWeb" )
	@Column(name="TIMER_TICK_CONEXION_SERIVICIO_WEB",type="INTEGER")	 
	private Integer timerTickConexionServicioWeb;

	@SerializedName("CodigoPostalObligatorioEnActas" )
	@Column(name="CODIGO_POSTAL_OBLIGATORIO_EN_ACTAS")	 
	private String codigoPostalObligatorioEnActas;
	
	@SerializedName("HabilitarGrabarFotoViaWebRequest" )
	@Column(name="HABILITAR_GRABAR_FOTO_VIA_WEBREQUEST")	 
	private String habilitarGrabarFotoViaWebRequest;
	
	@SerializedName("MaximoTiempoEsperaWS")
	@Column(name="MAXIMO_TIEMPO_ESPERA_WS",type="INTEGER")	 
	private Integer maximoTiempoEsperaWS;

	@SerializedName("TimerExclusionGPS")
	@Column(name="TIMER_EXCLUSION_GPS",type="INTEGER")	 
	private Integer timerExclusionGPS;

	@SerializedName("DisMinVerifExclusionGPS")
	@Column(name="DIST_MIN_VERIF_EXCLUSION_GPS",type="INTEGER")	 
	private Integer disMinVerifExclusionGPS;

	
	@SerializedName("ImprimirOperadorPDA" )
	@Column(name="IMPRIMIR_OPERADOR_PDA")	 
	private String imprimirOperadorPDA;
	
	@SerializedName("Rw420FriendlyName" )
	@Column(name="RW420_FRIENDLY_NAME")	 
	private String rw420FriendlyName;
	
	@SerializedName("Rw420MacAddress" )
	@Column(name="RW420_MAC_ADDRESS")	 
	private String rw420MacAddress;
	

	@SerializedName("maxCantActasPermitidas")
	@Column(name="MAX_CANT_ACTAS_PERMITIDAS",type="INTEGER")	 
	private Integer maxCantActasPermitidas;

	@SerializedName("UsarParidadJuzgado" )
	@Column(name="USAR_PARIDAD_JUZGADO")	 
	private String usarParidadJuzgado;
	
	@SerializedName("UsarImpresionConFotos" )
	@Column(name="USAR_IMPRESION_CON_FOTOS")		
	private String usarImpresionConFotos;
	
	
	
	@SerializedName("UsarRutaKmConvenio" )
	@Column(name="USAR_RUTA_KM_CONVENIO")
	private String usarRutaKmConvenio;
	
	@SerializedName("IdEntidadJuzgadoPolicialCba" )
	@Column(name="ID_ENTIDAD_JUZGADO_POLICIAL_CBA" ,type="INTEGER")
	private Integer idEntidadJuzgadoPolicialCba;
	
	@SerializedName("IdEntidadJuzgadoMunicipalCba" )
	@Column(name="ID_ENTIDAD_JUZGADO_MUNICIPAL_CBA" ,type="INTEGER")
	private Integer idEntidadJuzgadoMunicipalCba;
	
	@SerializedName("DivisorCostoAsociado" )
	@Column(name="DIVISOR_COSTO_ASOCIADO",type="INTEGER")
	private Integer divisorCostoAsociado;
	
	@SerializedName("UsarWifi" )
	@Column(name="USAR_WIFI")
	private String usarWifi;
	
	@SerializedName("UsarXmlReducido" )
	@Column(name="USAR_XML_REDUCIDO")
	private String usarXmlReducido;
	
	@SerializedName("MinimoNivelBateria" )
	@Column(name="MINIMO_NIVEL_DE_BATERIA",type ="INTEGER")
	private Integer minimoNivelBateria;
	
	@SerializedName("MinimaFechaDelDispositivo" )
	@Column(name="MINIMA_FECHA_DEL_DISPOSITIVO",type ="INTEGER")
	private Date minimaFechaDelDispositivo;
	
	@SerializedName("BrilloPantalla" )
	@Column(name="BRILLO_PANTALLA",type ="INTEGER")
	private Integer brilloPantalla;
	
	@SerializedName("UsarGPS" )
	@Column(name="USAR_GPS")
	private String usarGPS;
	

	@SerializedName("UltimoNumeroActa" )
	@Column(name="ULTIMO_NUMERO_ACTA")
	private String ultimoNumeroActa;

	@SerializedName("ActasNoSincronizadas" )
	@Column(name="ACTAS_NO_SINCRONIZADAS")
	private String actasNoSincronizadas;
	
	@SerializedName("ZONA" )
	@Column(name="ID_ZONA")
	private String idZona;
	
	@SerializedName("ServicioWeb" )
	@Column(name="SERVICIO_WEB")
	private String servicioWeb;

	@SerializedName("UnidadesFijasConcurso" )
	@Column(name="UNIDADES_FIJAS_CONCURSO",type="INTEGER")
	private Integer unidadesFijasConcurso;

	@SerializedName("DescripcionLey" )
	@Column(name="DESCRIPCION_LEY")
	private String descripcionLey;

	@SerializedName("LicenseKey" )
	@Column(name="LICENSE_KEY")
	private String licenseKey;

	@SerializedName("LectorPDF417" )
	@Column(name="LECTOR_PDF417")
	private String lectorPDF417;


	@SerializedName("Usar_Webservice_Policia" )
	@Column(name="USAR_WEBSERVICE_POLICIA")
	private String usarWebServicePolicia;

	@SerializedName("Webservice_Policia" )
	@Column(name="WEBSERVICE_POLICIA")
	private String webServicePolicia;
	
	@SerializedName("Calidad_Foto" )
	@Column(name="CALIDAD_FOTO",type="INTEGER")
	private Integer calidadFoto;

	public Integer getUfAlcoholemia() {
		return ufAlcoholemia;
	}

	@SerializedName("UnidadesFijasAlcoholemia")
	@Column(name="UNIDADES_FIJAS_ALCOHOLEMIA", type="INTEGER")
	private Integer ufAlcoholemia;
	
	public Integer getCalidadFoto() {
		return calidadFoto;
	}

	public void setCalidadFoto(Integer calidadFoto) {
		this.calidadFoto = calidadFoto;
	}

	public String getUsarWebServicePolicia() {
		return usarWebServicePolicia;
	}

	public void setUsarWebServicePolicia(String usarWebServicePolicia) {
		this.usarWebServicePolicia = usarWebServicePolicia;
	}

	public String getWebServicePolicia() {
		return webServicePolicia;
	}

	public void setWebServicePolicia(String webServicePolicia) {
		this.webServicePolicia = webServicePolicia;
	}

	public String getDescripcionLey() {
		return descripcionLey;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getLectorPDF417() {
		return lectorPDF417;
	}

	public void setLectorPDF417(String lectorPDF417) {
		this.lectorPDF417 = lectorPDF417;
	}

	public void setDescripcionLey(String descripcionLey) {
		this.descripcionLey = descripcionLey;
	}

	public Integer getUnidadesFijasConcurso() {
		return unidadesFijasConcurso;
	}

	public void setUnidadesFijasConcurso(Integer unidadesFijasConcurso) {
		this.unidadesFijasConcurso = unidadesFijasConcurso;
	}

	public String getServicioWeb() {
		return servicioWeb;
	}

	public void setServicioWeb(String servicioWeb) {
		this.servicioWeb = servicioWeb;
	}

	public String getUltimoNumeroActa() {
		return ultimoNumeroActa;
	}

	public void setUltimoNumeroActa(String ultimoNumeroActa) {
		this.ultimoNumeroActa = ultimoNumeroActa;
	}

	public String getActasNoSincronizadas() {
		return actasNoSincronizadas;
	}

	public void setActasNoSincronizadas(String actasNoSincronizadas) {
		this.actasNoSincronizadas = actasNoSincronizadas;
	}

	public String getIdZona() {
		return idZona;
	}

	public void setIdZona(String idZona) {
		this.idZona = idZona;
	}

	public String getUsarGPS() {
		return usarGPS;
	}

	public void setUsarGPS(String usarGPS) {
		this.usarGPS = usarGPS;
	}

	public Integer getBrilloPantalla() {
		return brilloPantalla;
	}

	public void setBrilloPantalla(Integer brilloPantalla) {
		this.brilloPantalla = brilloPantalla;
	}

	public Integer getMinimoNivelBateria() {
		return minimoNivelBateria;
	}

	public void setMinimoNivelBateria(Integer minimoNivelBateria) {
		this.minimoNivelBateria = minimoNivelBateria;
	}

	public Date getMinimaFechaDelDispositivo() {
		return minimaFechaDelDispositivo;
	}

	public void setMinimaFechaDelDispositivo(Date minimaFechaDelDispositivo) {
		this.minimaFechaDelDispositivo = minimaFechaDelDispositivo;
	}

	public String getUsarRutaKmConvenio() {
		return usarRutaKmConvenio;
	}

	public void setUsarRutaKmConvenio(String usarRutaKmConvenio) {
		this.usarRutaKmConvenio = usarRutaKmConvenio;
	}

	public Integer getIdEntidadJuzgadoPolicialCba() {
		return idEntidadJuzgadoPolicialCba;
	}

	public void setIdEntidadJuzgadoPolicialCba(Integer idEntidadJuzgadoPolicialCba) {
		this.idEntidadJuzgadoPolicialCba = idEntidadJuzgadoPolicialCba;
	}

	public Integer getIdEntidadJuzgadoMunicipalCba() {
		return idEntidadJuzgadoMunicipalCba;
	}

	public void setIdEntidadJuzgadoMunicipalCba(Integer idEntidadJuzgadoMunicipalCba) {
		this.idEntidadJuzgadoMunicipalCba = idEntidadJuzgadoMunicipalCba;
	}

	public Integer getDivisorCostoAsociado() {
		return divisorCostoAsociado;
	}

	public void setDivisorCostoAsociado(Integer divisorCostoAsociado) {
		this.divisorCostoAsociado = divisorCostoAsociado;
	}

	public String getUsarWifi() {
		return usarWifi;
	}

	public void setUsarWifi(String usarWifi) {
		this.usarWifi = usarWifi;
	}

	public String getUsarXmlReducido() {
		return usarXmlReducido;
	}

	public void setUsarXmlReducido(String usarXmlReducido) {
		this.usarXmlReducido = usarXmlReducido;
	}

	private String callePublica ="CALLE PUBLICA";
	private String alturaSinNumero ="";
	
	public String getCallePublica() {
		return callePublica;
	}

	public void setCallePublica(String callePublica) {
		this.callePublica = callePublica;
	}

	public String getAlturaSinNumero() {
		return alturaSinNumero;
	}

	public void setAlturaSinNumero(String alturaSinNumero) {
		this.alturaSinNumero = alturaSinNumero;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getLicenciaMinCar() {
		return licenciaMinCar;
	}



	public void setLicenciaMinCar(Integer licenciaMinCar) {
		this.licenciaMinCar = licenciaMinCar;
	}



	public Integer getTipoDocMinCar() {
		return tipoDocMinCar;
	}



	public void setTipoDocMinCar(Integer tipoDocMinCar) {
		this.tipoDocMinCar = tipoDocMinCar;
	}



	public Integer getDominioMinCar() {
		return dominioMinCar;
	}



	public void setDominioMinCar(Integer dominioMinCar) {
		this.dominioMinCar = dominioMinCar;
	}



	public Integer getNroEquipo() {
		return nroEquipo;
	}



	public void setNroEquipo(Integer nroEquipo) {
		this.nroEquipo = nroEquipo;
	}



	public Integer getCurrentCounter() {
		return currentCounter;
	}



	public void setCurrentCounter(Integer currentCounter) {
		this.currentCounter = currentCounter;
	}



	public String getFemaleName() {
		return femaleName;
	}



	public void setFemaleName(String femaleName) {
		this.femaleName = femaleName;
	}



	public String getMaleName() {
		return maleName;
	}



	public void setMaleName(String maleName) {
		this.maleName = maleName;
	}



	public String getPrinterCom() {
		return printerCom;
	}



	public void setPrinterCom(String printerCom) {
		this.printerCom = printerCom;
	}



	public String getPrinterTransferencia() {
		return printerTransferencia;
	}



	public void setPrinterTransferencia(String printerTransferencia) {
		this.printerTransferencia = printerTransferencia;
	}



	public String getPrinterParidad() {
		return printerParidad;
	}



	public void setPrinterParidad(String printerParidad) {
		this.printerParidad = printerParidad;
	}



	public String getPrinterDataBits() {
		return printerDataBits;
	}



	public void setPrinterDataBits(String printerDataBits) {
		this.printerDataBits = printerDataBits;
	}



	public String getPrinterStop() {
		return printerStop;
	}



	public void setPrinterStop(String printerStop) {
		this.printerStop = printerStop;
	}



	public String getWsLocation() {
		return wsLocation;
	}



	public void setWsLocation(String wsLocation) {
		this.wsLocation = wsLocation;
	}



	public String getCriptoKey() {
		return criptoKey;
	}



	public void setCriptoKey(String criptoKey) {
		this.criptoKey = criptoKey;
	}



	public String getCriptoVector() {
		return criptoVector;
	}



	public void setCriptoVector(String criptoVector) {
		this.criptoVector = criptoVector;
	}



	public String getImprimeCupoOrig() {
		return imprimeCupoOrig;
	}



	public void setImprimeCupoOrig(String imprimeCupoOrig) {
		this.imprimeCupoOrig = imprimeCupoOrig;
	}



	public Double getImporteNP() {
		return importeNP;
	}



	public void setImporteNP(Double importeNP) {
		this.importeNP = importeNP;
	}



	public Double getPorcDescuento() {
		return porcDescuento;
	}



	public void setPorcDescuento(Double porcDescuento) {
		this.porcDescuento = porcDescuento;
	}



	public String getConvenio() {
		return convenio;
	}



	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}



	public String getGrupoDeConvenio() {
		return grupoDeConvenio;
	}



	public void setGrupoDeConvenio(String grupoDeConvenio) {
		this.grupoDeConvenio = grupoDeConvenio;
	}



	public Integer getIdUsuarioLogueado() {
		return idUsuarioLogueado;
	}



	public void setIdUsuarioLogueado(Integer idUsuarioLogueado) {
		this.idUsuarioLogueado = idUsuarioLogueado;
	}



	public Integer getTimerTick() {
		return timerTick;
	}



	public void setTimerTick(Integer timerTick) {
		this.timerTick = timerTick;
	}



	public Integer getCantDiasInformes() {
		return cantDiasInformes;
	}



	public void setCantDiasInformes(Integer cantDiasInformes) {
		this.cantDiasInformes = cantDiasInformes;
	}



	public String getSoftwareNuevo() {
		return softwareNuevo;
	}



	public void setSoftwareNuevo(String softwareNuevo) {
		this.softwareNuevo = softwareNuevo;
	}



	public String getPermisoLabrarActas() {
		return permisoLabrarActas;
	}



	public void setPermisoLabrarActas(String permisoLabrarActas) {
		this.permisoLabrarActas = permisoLabrarActas;
	}



	public String getWsPoliciaViaRepat() {
		return wsPoliciaViaRepat;
	}



	public void setWsPoliciaViaRepat(String wsPoliciaViaRepat) {
		this.wsPoliciaViaRepat = wsPoliciaViaRepat;
	}



	public String getPermisoActualizarPosicionGPS() {
		return permisoActualizarPosicionGPS;
	}



	public void setPermisoActualizarPosicionGPS(String permisoActualizarPosicionGPS) {
		this.permisoActualizarPosicionGPS = permisoActualizarPosicionGPS;
	}



	public String getEnviaLogPDA() {
		return enviaLogPDA;
	}



	public void setEnviaLogPDA(String enviaLogPDA) {
		this.enviaLogPDA = enviaLogPDA;
	}



	public String getFotosObligatoriasEnActas() {
		return fotosObligatoriasEnActas;
	}



	public void setFotosObligatoriasEnActas(String fotosObligatoriasEnActas) {
		this.fotosObligatoriasEnActas = fotosObligatoriasEnActas;
	}



	public Integer getCantDias1erVtoActa() {
		return cantDias1erVtoActa;
	}



	public void setCantDias1erVtoActa(Integer cantDias1erVtoActa) {
		this.cantDias1erVtoActa = cantDias1erVtoActa;
	}



	public String getHabilitarCHKNoPoseeLicencia() {
		return habilitarCHKNoPoseeLicencia;
	}



	public void setHabilitarCHKNoPoseeLicencia(String habilitarCHKNoPoseeLicencia) {
		this.habilitarCHKNoPoseeLicencia = habilitarCHKNoPoseeLicencia;
	}



	public String getBusquedaObligatoriaLicencia() {
		return busquedaObligatoriaLicencia;
	}



	public void setBusquedaObligatoriaLicencia(String busquedaObligatoriaLicencia) {
		this.busquedaObligatoriaLicencia = busquedaObligatoriaLicencia;
	}



	public String getBusquedaObligatoriaDocumento() {
		return busquedaObligatoriaDocumento;
	}



	public void setBusquedaObligatoriaDocumento(String busquedaObligatoriaDocumento) {
		this.busquedaObligatoriaDocumento = busquedaObligatoriaDocumento;
	}



	public String getBusquedaObligatoriaDominio() {
		return busquedaObligatoriaDominio;
	}



	public void setBusquedaObligatoriaDominio(String busquedaObligatoriaDominio) {
		this.busquedaObligatoriaDominio = busquedaObligatoriaDominio;
	}



	public String getHabilitarBTNCodigoQR() {
		return habilitarBTNCodigoQR;
	}



	public void setHabilitarBTNCodigoQR(String habilitarBTNCodigoQR) {
		this.habilitarBTNCodigoQR = habilitarBTNCodigoQR;
	}



	public String getUsarCupoCodigoBarraDoble() {
		return usarCupoCodigoBarraDoble;
	}



	public void setUsarCupoCodigoBarraDoble(String usarCupoCodigoBarraDoble) {
		this.usarCupoCodigoBarraDoble = usarCupoCodigoBarraDoble;
	}



	public Integer getTimerTickConexionServicioWeb() {
		return timerTickConexionServicioWeb;
	}



	public void setTimerTickConexionServicioWeb(Integer timerTickConexionServicioWeb) {
		this.timerTickConexionServicioWeb = timerTickConexionServicioWeb;
	}



	public String getCodigoPostalObligatorioEnActas() {
		return codigoPostalObligatorioEnActas;
	}



	public void setCodigoPostalObligatorioEnActas(
			String codigoPostalObligatorioEnActas) {
		this.codigoPostalObligatorioEnActas = codigoPostalObligatorioEnActas;
	}



	public String getHabilitarGrabarFotoViaWebRequest() {
		return habilitarGrabarFotoViaWebRequest;
	}



	public void setHabilitarGrabarFotoViaWebRequest(
			String habilitarGrabarFotoViaWebRequest) {
		this.habilitarGrabarFotoViaWebRequest = habilitarGrabarFotoViaWebRequest;
	}



	public Integer getMaximoTiempoEsperaWS() {
		return maximoTiempoEsperaWS;
	}



	public void setMaximoTiempoEsperaWS(Integer maximoTiempoEsperaWS) {
		this.maximoTiempoEsperaWS = maximoTiempoEsperaWS;
	}



	public Integer getTimerExclusionGPS() {
		return timerExclusionGPS;
	}



	public void setTimerExclusionGPS(Integer timerExclusionGPS) {
		this.timerExclusionGPS = timerExclusionGPS;
	}



	public Integer getDisMinVerifExclusionGPS() {
		return disMinVerifExclusionGPS;
	}



	public void setDisMinVerifExclusionGPS(Integer disMinVerifExclusionGPS) {
		this.disMinVerifExclusionGPS = disMinVerifExclusionGPS;
	}



	public String getImprimirOperadorPDA() {
		return imprimirOperadorPDA;
	}



	public void setImprimirOperadorPDA(String imprimirOperadorPDA) {
		this.imprimirOperadorPDA = imprimirOperadorPDA;
	}



	public String getRw420FriendlyName() {
		return rw420FriendlyName;
	}



	public void setRw420FriendlyName(String rw420FriendlyName) {
		this.rw420FriendlyName = rw420FriendlyName;
	}



	public String getRw420MacAddress() {
		return rw420MacAddress;
	}



	public void setRw420MacAddress(String rw420MacAddress) {
		this.rw420MacAddress = rw420MacAddress;
	}



	public Integer getMaxCantActasPermitidas() {
		return maxCantActasPermitidas;
	}



	public void setMaxCantActasPermitidas(Integer maxCantActasPermitidas) {
		this.maxCantActasPermitidas = maxCantActasPermitidas;
	}



	public String getUsarParidadJuzgado() {
		return usarParidadJuzgado;
	}



	public void setUsarParidadJuzgado(String usarParidadJuzgado) {
		this.usarParidadJuzgado = usarParidadJuzgado;
	}
	
	/* ISeleccionable methods */
	public String getItemId() {
		return id.toString();
	}

	public String getItemName() {
		return nroEquipo.toString();
	}
	
	public SuportTable(){}
	
	public SuportTable(Integer id, String nombre){
		super();
		this.id = id;
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

	public String getUsarImpresionConFotos() {
		return usarImpresionConFotos ==null? "N":usarImpresionConFotos;
	}

	public void setUsarImpresionConFotos(String usarImpresionConFotos) {
		this.usarImpresionConFotos = usarImpresionConFotos;
	}
	
	
	 public Object clone()
     {
         try
         {
             return super.clone();
         }
	     catch( CloneNotSupportedException e )
	     {
	             return null;
         }
     } 

}
