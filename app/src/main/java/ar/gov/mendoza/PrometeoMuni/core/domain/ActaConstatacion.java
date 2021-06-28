package ar.gov.mendoza.PrometeoMuni.core.domain;

import com.google.gson.annotations.SerializedName;

//import org.simpleframework.xml.Element;
//import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.db.Column;
import ar.gov.mendoza.PrometeoMuni.core.db.Table;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;

@Table(name = "T_ACTA_CONSTATACION")
//@Root
public class ActaConstatacion  implements ISeleccionable<String>, Serializable, Cloneable {

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
	
	String formatoFechaHora = "dd/MM/yyyy HH:mm:ss";
	String formatoFecha = "dd/MM/yyyy";

	////@Element
	@SerializedName("id")//("IdActaConstatacion")
	@Column(name = "_id", type = "INTEGER",isPrimaryKey=true,isAutoincrement=true)
	private Integer idActaConstatacion;

	public Double getImporteSancion() {
		return importeSancion;
	}
	public void setImporteSancion(Double importeSancion) {
		this.importeSancion = importeSancion;
	}
	public String getImporteSancionString() {
		return importeSancionString;
	}
	public void setImporteSancionString(String importeSancionString) {
		this.importeSancionString = importeSancionString;
	}
	public Double getDescuentoAplicadoSancion() {
		return descuentoAplicadoSancion;
	}
	public void setDescuentoAplicadoSancion(Double descuentoAplicadoSancion) {
		this.descuentoAplicadoSancion = descuentoAplicadoSancion;
	}
	public String getDescuenteAplicadoSancionString() {
		return descuenteAplicadoSancionString;
	}
	public void setDescuenteAplicadoSancionString(String descuenteAplicadoSancionString) {
		this.descuenteAplicadoSancionString = descuenteAplicadoSancionString;
	}

	////@Element
	@SerializedName("nacta")//("NumeroActa")
	@Column(name="NUMERO_ACTA",isDescription = true) //usaremos Description para obtener un campo descripcion de la fila
	private String numeroActa;

	////@Element
	@SerializedName("lserie")//("LetraSerie")
	@Column(name="LETRA_SERIE") 
	private String letraSerie;

	public String getLetraSerie() {
		return letraSerie;
	}
	public void setLetraSerie(String letraSerie) {
		this.letraSerie = letraSerie;
	}

	////@Element
	//@SerializedName("DigitoVerificador")
	@Column(name="DIGITO_VERIFICADOR")
	private String digitoVerificador;

	////@Element
	@SerializedName("CodB")//("CodigoBarra")
	@Column(name="CODIGO_BARRA")
	private String codigoBarra;
	
	////@Element
	@SerializedName("CodB2")//("CodigoBarra2Do")
	@Column(name="CODIGO_BARRA_2DO")
	private String codigoBarra2Do;

/*	public String getCodigoBarra1() {
		return codigoBarra1;
	}
	public String getCodigoBarra2() {
		return codigoBarra2;
	}*/
	
//	private String codigoBarra1;
//	private String codigoBarra2;
	
	////@Element
	@SerializedName("IdMov")//("IdMovil")
	@Column(name="ID_MOVIL",type="INTEGER")
	private Integer idMovil;

	////@Element
	@SerializedName("Anio")//("IdMovil")
	@Column(name="ANIO",type="INTEGER")
	private String anioModeloVehiculo;

	////@Element
	@SerializedName("FVtoCup")//("FechaVencimientoCupon")
	@Column(name="FECHA_VENCIMIENTO_CUPON",type ="INTEGER")// ANTES DATETIME
	private Date fechaVencimientoCupon;
	
	////@Element	
	//@SerializedName("FechaVencimientoCuponString")
	@Column(name="FECHA_VENCIMIENTO_CUPON_STRING")
	private String fechaVencimientoCuponString;

	
	////@Element
	//@SerializedName("FechaVencimiento2DoCupon")
	@Column(name="FECHA_VENCIMIENTO_2DO_CUPON",type ="INTEGER")// ANTES DATETIME
	private Date fechaVencimiento2DoCupon;
	
	////@Element	
	//@SerializedName("FechaVencimiento2DoCuponString")
	@Column(name="FECHA_VENCIMIENTO_2DO_CUPON_STRING")
	private String fechaVencimiento2DoCuponString;

	

	////@Element	
	@SerializedName("MntCup")//("MontoCupon")
	@Column(name="MONTO_CUPON",type ="REAL")
	private Double montoCupon;
	
	////@Element	
	//@SerializedName("MontoCuponString")
	@Column(name="MONTO_CUPON_STRING",type ="TEXT")
	private String montoCuponString;

	////@Element	
	@SerializedName("ImporteSancion")//("MontoCupon")
	@Column(name="IMPORTE_SANCION",type ="REAL")
	private Double importeSancion;
	
	////@Element	
	//@SerializedName("MontoCuponString")
	@Column(name="IMPORTE_SANCION_STRING",type ="TEXT")
	private String importeSancionString;

	////@Element	
	@SerializedName("DescuentoAplicadoSancion")//("MontoCupon")
	@Column(name="DESCUENTO_APLICADO_SANCION",type ="REAL")
	private Double descuentoAplicadoSancion;
	
	////@Element	
	//@SerializedName("MontoCuponString")
	@Column(name="DESCUENTO_APLICADO_SANCION_STRING",type ="TEXT")
	private String descuenteAplicadoSancionString;

	
	////@Element	
	//@SerializedName("Monto2DoCupon")
	@Column(name="MONTO_2DO_CUPON",type ="REAL")
	private Double monto2DoCupon;
	
	////@Element	
	//@SerializedName("Monto2DoCuponString")
	@Column(name="MONTO_2DO_CUPON_STRING",type ="TEXT")
	private String monto2DoCuponString;
	
	////@Element	
	@SerializedName("FHCarga")//("FechaHoraCarga")
	@Column(name="FECHA_HORA_CARGA",type ="INTEGER")//DATETIME
	private Date fechaHoraCarga;
	
	////@Element
	//@SerializedName("FechaHoraCargaString")
	@Column(name="FECHA_HORA_CARGA_STRING",type ="TEXT")
	private String fechaHoraCargaString;
	
	////@Element
	@SerializedName("FHLab")//("FechaHoraLabrado")
	@Column(name="FECHA_HORA_LABRADO",type ="INTEGER")//DATETIME
	private Date fechaHoraLabrado;
	
	////@Element
	//@SerializedName("FechaHoraLabradoString")
	@Column(name="FECHA_HORA_LABRADO_STRING",type ="TEXT")
	private String fechaHoraLabradoString;
	
	/* Datos del Lugar */
	////@Element	
	//@SerializedName("IdTipoRuta")
	@Column(name="ID_TIPO_RUTA",type ="INTEGER")
	private String idTipoRuta;   //1 caracter
	
	private TipoRuta oTipoRuta;
	public void setoTipoRuta(TipoRuta oTipoRuta) {
		this.oTipoRuta = oTipoRuta;
		
		Boolean objetcIsNull = oTipoRuta == null;
		this.idTipoRuta = objetcIsNull?null:oTipoRuta.getId();
		this.tipoRuta = objetcIsNull?null:oTipoRuta.getNombre();
	}
	
	////@Element	
	@SerializedName("TRut")//("TipoRuta")
	@Column(name="TIPO_RUTA",type ="TEXT")
	private String tipoRuta;   //Nacional  Provincial
	
	
	private Ruta oRuta;
	
	public void setoRuta(Ruta oRuta) {
		this.oRuta = oRuta;
		Boolean objetcIsNull = oRuta == null;
		this.idNumeroRuta = objetcIsNull?null: oRuta.getId();
		this.numeroRuta = objetcIsNull?null: oRuta.getNombre();
	}

	////@Element
	//@SerializedName("IdNumeroRuta")
	@Column(name="ID_NUMERO_RUTA",type ="INTEGER")
	private String idNumeroRuta; //3 caracteres

	////@Element
	@SerializedName("nroRut")//("NumeroRuta")
	@Column(name="NUMERO_RUTA",type ="TEXT")
	private String numeroRuta; //3 caracteres

	////@Element
	@SerializedName("Km")
	@Column(name="KM",type ="TEXT")
	private String km;
	
	
	
	private Entidad oEntidad;
	
	public void setoEntidad(Entidad oEntidad) {
		this.oEntidad = oEntidad;
		Boolean objetcIsNull = oEntidad == null;
		this.idJuzgado = objetcIsNull?null: oEntidad.getId();
		this.juzgado = objetcIsNull?null: oEntidad.getNombre();
		this.calleJuzgado = objetcIsNull?null: oEntidad.getDomicilioCalle();
		this.alturaJuzgado = objetcIsNull?null: oEntidad.getDomicilioNumero();
		this.localidadJuzgado = objetcIsNull?null: oEntidad.getDomicilioLocalidad();
		this.codigoPostalJuzgado = objetcIsNull?null: oEntidad.getDomicilioCodigoPostal();
		this.telefonoJuzgado= objetcIsNull?null: oEntidad.getTelefono();
		this.emailJuzgado= objetcIsNull?null: oEntidad.getEmail();
	}

	private Seccional oSeccional;
	
	public void setoSeccional(Seccional oSeccional) {
		this.oSeccional= oSeccional;
		Boolean objetcIsNull = oSeccional == null;
		this.idSeccional = objetcIsNull ? null: oSeccional.getId();
		this.seccional= objetcIsNull?null: oSeccional.getNombre();
	}

	//@SerializedName("ProveedorPosicionGPS")
	@Column(name="PROVEEDOR_POSICION_GPS",type ="TEXT")
	private String proveedorPosicionGPS; // Red telefonia, GPS, WiFi
	
	////@Element
	@SerializedName("Lat")//("Latitud")
	@Column(name="LATITUD",type ="TEXT")
	private String latitud;
	
	////@Element
	@SerializedName("Lon")//("Longitud")
	@Column(name="LONGITUD",type ="TEXT")
	private String longitud;

	////@Element
	@SerializedName("TLic")//("TieneLiencia")
	@Column(name="TIENE_LICENCIA",type ="TEXT")
	private String tieneLicencia;
	
	

	/*
	 *  DATOS DE LA LICENCIA 
	 */
	//@SerializedName("LicenciaDesdeCodigoQR")
	@Column(name="LICENCIA_DESDE_CODIGO_QR",type ="TEXT")
	private Boolean licenciaDesdeCodigoQR;
	
	//@SerializedName("CoberturaDesdeCodigoQR")
	@Column(name="COBERTURA_DESDE_CODIGO_QR",type ="TEXT")
	private Boolean coberturaDesdeCodigoQR;
	
	
	////@Element
	@SerializedName("LRet")//("LicenciaRetenida")
	@Column(name="LICENCIA_RETENIDA",type ="TEXT")
	private String licenciaRetendia;
	
	////@Element
	//@SerializedName("LicenciaUnicaProvincial")
	@Column(name="LICENCIA_UNICA_PROVINCIAL",type ="TEXT")
	private String licenciaUnicaProvincial;
	
	//@SerializedName("CoberturaDesdeNumeroLicencia")
	@Column(name="COBERTURA_DESDE_NUMERO_LICENCIA",type ="TEXT")
	private Boolean coberturaDesdeNumeroLicencia;
	
	////@Element
	@SerializedName("nroLic")//("NumeroLicencia")
	@Column(name="NUMERO_LICENCIA",type ="TEXT")
	private String numeroLicencia;
	
	////@Element(required=false)
	//@SerializedName("IdClaseLicencia")
	@Column(name="ID_CLASE_LICENCIA",type ="TEXT")
	private String idClaseLicencia;

	////@Element
	@SerializedName("CLic")//("ClaseLicencia")
	@Column(name="CLASE_LICENCIA",type ="TEXT")
	private String claseLicencia;
	
	
	////@Element	
	@SerializedName("FVtoLic")//("FechaVencimientoLicencia")
	@Column(name="FECHA_VENCIMIENTO_LICENCIA",type ="TEXT")
	private Date fechaVencimientoLicencia;
	
	////@Element
	//@SerializedName("FechaVencimientoLicenciaString")
	@Column(name="FECHA_VENCIMIENTO_LICENCIA_STRING")
	private String fechaVencimientoLicenciaString;
	
	private Pais oPaisLicencia;
	
	public void setoPaisLicencia(Pais oPaisLicencia) {
		this.oPaisLicencia = oPaisLicencia;
		Boolean objetcIsNull = oPaisLicencia == null;
		this.idPaisLicencia = objetcIsNull==null?null: oPaisLicencia.getId();
		this.paisLicencia = objetcIsNull==null?this.paisLicencia: oPaisLicencia.getNombre();
	}
	
	////@Element
	//@SerializedName("IdPaisLicencia")
	@Column(name="ID_PAIS_LICENCIA",type ="TEXT")
	private String idPaisLicencia; // ARG  => Argentina
	
	////@Element
	@SerializedName("PLic")//("PaisLicencia")
	@Column(name="PAIS_LICENCIA",type ="TEXT")
	private String paisLicencia;
	
	
	private Provincia oProvinciaLicencia;

	public void setoProvinciaLicencia(Provincia oProvinciaLicencia) {
		this.oProvinciaLicencia = oProvinciaLicencia;
		Boolean objetcIsNull = oProvinciaLicencia == null;
		this.idProvinciaLicencia = objetcIsNull==null?null: oProvinciaLicencia.getId();
		this.provinciaLicencia = objetcIsNull==null?this.provinciaLicencia: oProvinciaLicencia.getNombre();
	}
	
	////@Element
	//@SerializedName("IdProvinciaLicencia")
	@Column(name="ID_PROVINCIA_LICENCIA",type ="TEXT")
	private String idProvinciaLicencia; // X => Cordoba
	
	////@Element
	@SerializedName("PrLic")//("ProvinciaLicencia")
	@Column(name="PROVINCIA_LICENCIA",type ="TEXT")
	private String provinciaLicencia;
	
	private Departamento oDepartamentoLicencia;
	
	public void setoDepartamentoLicencia(Departamento oDepartamentoLicencia) {
		this.oDepartamentoLicencia = oDepartamentoLicencia;
		Boolean objetcIsNull = oDepartamentoLicencia == null;
		this.idDepartamentoLicencia = objetcIsNull==null?null: oDepartamentoLicencia.getId();
		this.departamentoLicencia= objetcIsNull==null?this.departamentoLicencia: oDepartamentoLicencia.getNombre();

	}

	////@Element
	//@SerializedName("IdDepartamentoLicencia")
	@Column(name="ID_DEPARTAMENTO_LICENCIA",type ="TEXT")
	private Integer idDepartamentoLicencia;
	
	
	////@Element
	@SerializedName("DptoLic")//("DepartamentoLicencia")
	@Column(name="DEPARTAMENTO_LICENCIA",type ="TEXT")
	private String departamentoLicencia;
	
	private Localidad oLocalidadLicencia;

	public void setoLocalidadLicencia(Localidad oLocalidadLicencia) {
		this.oLocalidadLicencia = oLocalidadLicencia;
		Boolean objetcIsNull = oLocalidadLicencia == null;
		this.idLocalidadLicencia = objetcIsNull==true?null: oLocalidadLicencia.getId();
		this.localidadLicencia = objetcIsNull==true?this.localidadLicencia: oLocalidadLicencia.getNombre();
	}

	////@Element
	//@SerializedName("IdLocalidadLicencia")
	@Column(name="ID_LOCALIDAD_LICENCIA",type ="TEXT")
	private Integer idLocalidadLicencia;
	
	////@Element
	@SerializedName("LocLic")//("LocalidadLicencia")
	@Column(name="LOCALIDAD_LICENCIA",type ="TEXT")
	private String localidadLicencia;

	
	
	private TipoDocumento oTipoDocumento;

	public void setoTipoDocumento(TipoDocumento oTipoDocumento) {
		this.oTipoDocumento = oTipoDocumento;
		Boolean objetcIsNull = oTipoDocumento == null;
		this.tipoDocumento= objetcIsNull==null?null: oTipoDocumento.getId();
	}
	// lo vamos a usar para las validaciones 
	public TipoDocumento getoTipoDocumento() {
		return oTipoDocumento;
	}

	
	/*Datos de la persona*/
	////@Element	
	@SerializedName("TDoc")//("TipoDocumento")
	@Column(name="TIPO_DOCUMENTO",type ="TEXT")
	private String tipoDocumento;
	
	//@SerializedName("CoberturaDesdeNumeroDocumento")
	@Column(name="COBERTURA_DESDE_NUMERO_DOCUMENTO",type ="TEXT")
	private Boolean coberturaDesdeNumeroDocumento;

	////@Element
	@SerializedName("NDoc")//("NumeroDocumento")
	@Column(name="NUMERO_DOCUMENTO",type ="TEXT")
	private String numeroDocumento;
	
	////@Element	
	@SerializedName("ape")//("Apellido")
	@Column(name="APELLIDO",type ="TEXT")
	private String apellido;
	
	////@Element	
	@SerializedName("nom")//("Nombre")
	@Column(name="NOMBRE",type ="TEXT")
	private String nombre;
 
	////@Element
	//@SerializedName("EsCallePublica")
	@Column(name="ES_CALLE_PUBLICA",type ="TEXT")
	private Boolean esCallePublica;
	
	////@Element
	@SerializedName("Calle")
	@Column(name="CALLE",type ="TEXT")
	private String calle; //
	
	////@Element
	//@SerializedName("EsAlturaSinNumero")
	@Column(name="ES_ALTURA_SIN_NUMERO",type ="TEXT")
	private Boolean esAlturaSinNumero;
	
	////@Element
	@SerializedName("Altura")
	@Column(name="ALTURA",type ="TEXT")
	private String altura; //
	
	////@Element
	@SerializedName("Piso")
	@Column(name="PISO",type ="TEXT")
	private String piso;
	
	////@Element
	@SerializedName("Dpto")//("Departamento")
	@Column(name="DEPARTAMENTO",type ="TEXT")
	private String departamento;
	
	////@Element
	@SerializedName("Barrio")
	@Column(name="BARRIO",type ="TEXT")
	private String barrio;
	
	private Pais oPaisDomicilio;

	public void setoPaisDomicilio(Pais oPaisDomicilio) {
		this.oPaisDomicilio = oPaisDomicilio;
		Boolean objetcIsNull = oPaisDomicilio == null;
		this.idPaisDomicilio= objetcIsNull==null?null: oPaisDomicilio.getId();
		this.paisDomicilio= objetcIsNull==null?this.paisDomicilio: oPaisDomicilio.getNombre();
	}

	////@Element
	//@SerializedName("IdPaisDomicilio")
	@Column(name="ID_PAIS_DOMICILIO",type ="TEXT")
	private String idPaisDomicilio; // ARG  => Argentina
	
	////@Element
	@SerializedName("PDom")//("PaisDomicilio")
	@Column(name="PAIS_DOMICILIO",type ="TEXT")
	private String paisDomicilio;
	
	private Provincia oProvinciaDomicilio;
	
	public void setoProvinciaDomicilio(Provincia oProvinciaDomicilio) {
		this.oProvinciaDomicilio = oProvinciaDomicilio;
		Boolean objetcIsNull = oProvinciaDomicilio == null;
		this.idProvinciaDomicilio = objetcIsNull==null?null: oProvinciaDomicilio.getId();
		this.provinciaDomicilio= objetcIsNull==null?this.provinciaDomicilio: oProvinciaDomicilio.getNombre();
	}

	////@Element
	//@SerializedName("IdProvinciaDomicilio")
	@Column(name="ID_PROVINCIA_DOMICILIO",type ="TEXT")
	private String idProvinciaDomicilio; // X => Cordoba
	
	////@Element
	@SerializedName("ProvDom")//("ProvinciaDomicilio")
	@Column(name="PROVINCIA_DOMICILIO",type ="TEXT")
	private String provinciaDomicilio;
	
	private Departamento oDepartamentoDomicilio;
	
	public void setoDepartamentoDomicilio(Departamento oDepartamentoDomicilio) {
		this.oDepartamentoDomicilio = oDepartamentoDomicilio;
		Boolean objetcIsNull = oDepartamentoDomicilio == null;
		this.idDepartamentoDomicilio = objetcIsNull==null?null: oDepartamentoDomicilio.getId();
		this.departamentoDomicilio= objetcIsNull==null?this.departamentoDomicilio: oDepartamentoDomicilio.getNombre();
	}	
	
	////@Element
	//@SerializedName("IdDepartamentoDomicilio")
	@Column(name="ID_DEPARTAMENTO_DOMICILIO",type ="TEXT")
	private Integer idDepartamentoDomicilio;

	////@Element
	@SerializedName("DptoDom")//("DepartamentoDomicilio")
	@Column(name="DEPARTAMENTO_DOMICILIO",type ="TEXT")
	private String departamentoDomicilio;
	
	public void setoLocalidadDomicilio(Localidad oLocalidadDomicilio) {
		this.oLocalidadDomicilio = oLocalidadDomicilio;
		Boolean objetcIsNull = oLocalidadDomicilio == null;
		this.idLocalidadDomicilio= objetcIsNull==null?null: oLocalidadDomicilio.getId();
		this.localidadDomicilio= objetcIsNull==null?this.localidadDomicilio: oLocalidadDomicilio.getNombre();

	}

	private Localidad oLocalidadDomicilio;
	
	////@Element
	//@SerializedName("IdLocalidadDomicilio")
	@Column(name="ID_LOCALIDAD_DOMICILIO",type ="TEXT")
	private Integer idLocalidadDomicilio;
	
	////@Element
	@SerializedName("LocDom")//("LocalidadDomicilio")
	@Column(name="LOCALIDAD_DOMICILIO",type ="TEXT")
	private String localidadDomicilio;
	
	////@Element
	@SerializedName("CP")//("CodigoPostal")
	@Column(name="CODIGO_POSTAL",type ="TEXT")
	private String codigoPostal;
	
	private TipoVehiculo oTipoVehiculo;
	
	public void setoTipoVehiculo(TipoVehiculo oTipoVehiculo) {
		this.oTipoVehiculo = oTipoVehiculo;
		Boolean objetcIsNull = oTipoVehiculo == null;
		this.idTipoVehiculo = objetcIsNull==null?null: oTipoVehiculo.getId();
	}
	public TipoVehiculo getoTipoVehiculo() {
		return oTipoVehiculo;
	}


	////@Element
	//@SerializedName("IdTipoVehiculo")
	@Column(name="ID_TIPO_VEHICULO",type ="TEXT")
	private String idTipoVehiculo; // A Automotor  M  MotoVehiculo
	
	////@Element
	//@SerializedName("IdTipoPatente")
	@Column(name="ID_TIPO_PATENTE",type ="TEXT")
	private String idTipoPatente;  // N Nacional   I Internacional
	
	//@SerializedName("CoberturaDesdeDominio")
	@Column(name="COBERTURA_DESDE_DOMINIO",type ="TEXT")
	private Boolean coberturaDesdeDominio;
	
	////@Element
	@SerializedName("Pat")//("Dominio")
	@Column(name="DOMINIO",type ="TEXT")
	private String dominio;

	private ar.gov.mendoza.PrometeoMuni.core.domain.Marca oMarca;
	
	public void setoMarca(ar.gov.mendoza.PrometeoMuni.core.domain.Marca oMarca) {
		this.oMarca = oMarca;
		Boolean objetcIsNull = oMarca == null;
		this.idMarca = objetcIsNull==null?null: oMarca.getId();
		this.Marca = objetcIsNull==null?null: oMarca.getNombre();
		
	}

	////@Element
	//@SerializedName("IdMarca")
	@Column(name="ID_MARCA",type ="TEXT")
	private Integer idMarca;
	
	////@Element
	@SerializedName("Marca")
	@Column(name="MARCA",type ="TEXT")
	private String Marca;
	
	private Color oColor;
	
	public void setoColor(Color oColor) {
		this.oColor = oColor;
		Boolean objetcIsNull = oColor == null;
		this.idColor = objetcIsNull==null?null: oColor.getId();
		this.Color = objetcIsNull==null?null: oColor.getNombre();
	}
	
	//@Element
	//@SerializedName("IdColor")
	@Column(name="ID_COLOR",type ="TEXT")
	private Integer idColor;
	
	//@Element
	@SerializedName("Color")
	@Column(name="COLOR",type ="TEXT")
	private String Color;
	
	
	private InfraccionNomenclada oInfraccionNomenclada1;
	
	public void setoInfraccionNomenclada1(InfraccionNomenclada oInfraccionNomenclada1) {
		this.oInfraccionNomenclada1 = oInfraccionNomenclada1;
		
		Boolean objetcIsNull = oInfraccionNomenclada1 == null;
		this.idInfraccion1 = objetcIsNull?null:oInfraccionNomenclada1.getId();
		this.codigoInfraccion1 = objetcIsNull?null:oInfraccionNomenclada1.getCodigo();
		this.descripcionInfraccion1= objetcIsNull?null:oInfraccionNomenclada1.getDescripcionCorta();
		this.imprimirPuntos1= objetcIsNull?null:oInfraccionNomenclada1.getImprimirPuntos();
		this.puntos1= objetcIsNull?null:oInfraccionNomenclada1.getPuntos();
		this.notasImpresion1= objetcIsNull?null:oInfraccionNomenclada1.getNotasImpresion();
	}

	public InfraccionNomenclada getoInfraccionNomenclada1() {
		return oInfraccionNomenclada1;
	}
	public InfraccionNomenclada getoInfraccionNomenclada2() {
		return oInfraccionNomenclada2;
	}

	public InfraccionNomenclada getoInfraccionNomenclada3() {
		return oInfraccionNomenclada3;
	}

	public InfraccionNomenclada getoInfraccionNomenclada4() {
		return oInfraccionNomenclada4;
	}

	public InfraccionNomenclada getoInfraccionNomenclada5() {
		return oInfraccionNomenclada5;
	}

	public InfraccionNomenclada getoInfraccionNomenclada6() {
		return oInfraccionNomenclada6;
	}

	//@Element
	//@SerializedName("IdInfraccion1")
	@Column(name="ID_INFRACCION1",type ="TEXT")
	private Integer idInfraccion1;
	
	//@Element
	@SerializedName("CodInf1")//("CodigoInfraccion1")
	@Column(name="CODIGO_INFRACCION1",type ="TEXT")
	private String codigoInfraccion1;
	
	private String descripcionInfraccion1;
	
	private String notasImpresion1;
	private String imprimirPuntos1;
	private Integer puntos1;
	
	public Integer getPuntos1() {
		return puntos1;
	}
	public void setPuntos1(Integer puntos1) {
		this.puntos1 = puntos1;
	}
	public Integer getPuntos2() {
		return puntos2;
	}
	public void setPuntos2(Integer puntos2) {
		this.puntos2 = puntos2;
	}
	public String getNotasImpresion1() {
		return notasImpresion1;
	}
	public void setNotasImpresion1(String notasImpresion1) {
		this.notasImpresion1 = notasImpresion1;
	}
	public String getDescripcionInfraccion1() {
		return descripcionInfraccion1;
	}
	public void setDescripcionInfraccion1(String descripcionInfraccion1) {
		this.descripcionInfraccion1 = descripcionInfraccion1;
	}
	
	//@SerializedName("EsApercibimientoInfraccion1")
	@Column(name="ES_APERCIBIMIENTO_INFRACCION1",type ="TEXT")
	private Boolean esApercibimientoInfraccion1;
	
	//@SerializedName("CoberturaDesdeApercibimiento1")
	@Column(name="COBERTURA_DESDE_INFRACCION1",type ="TEXT")
	private Boolean coberturaDesdeApercibimiento1;
	
	private InfraccionNomenclada oInfraccionNomenclada2;
	
	public void setoInfraccionNomenclada2(InfraccionNomenclada oInfraccionNomenclada2) {
		this.oInfraccionNomenclada2 = oInfraccionNomenclada2;
		
		Boolean objetcIsNull = oInfraccionNomenclada2 == null;
		this.idInfraccion2 = objetcIsNull?null:oInfraccionNomenclada2.getId();
		this.codigoInfraccion2 = objetcIsNull?null:oInfraccionNomenclada2.getCodigo();
		this.descripcionInfraccion2= objetcIsNull?null:oInfraccionNomenclada2.getDescripcionCorta();
		this.imprimirPuntos2= objetcIsNull?null:oInfraccionNomenclada2.getImprimirPuntos();
		this.puntos2= objetcIsNull?null:oInfraccionNomenclada2.getPuntos();
		this.notasImpresion2= objetcIsNull?null:oInfraccionNomenclada2.getNotasImpresion();
		
	}

	//@Element	
	//@SerializedName("IdInfraccion2")
	@Column(name="ID_INFRACCION2",type ="TEXT")
	private Integer idInfraccion2;

	//@Element
	//@SerializedName("CodigoInfraccion2")
	@Column(name="CODIGO_INFRACCION2",type ="TEXT")
	private String codigoInfraccion2;
	
	private String descripcionInfraccion2;
	private String notasImpresion2;
	private String imprimirPuntos2;
	private Integer puntos2;
	
/* INFRACIONES DEL 3 A LAS 6*/
	private InfraccionNomenclada oInfraccionNomenclada3;
	
	public void setoInfraccionNomenclada3(InfraccionNomenclada oInfraccionNomenclada3) {
		this.oInfraccionNomenclada3 = oInfraccionNomenclada3;
		
		Boolean objetcIsNull = oInfraccionNomenclada3 == null;
		this.idInfraccion3 = objetcIsNull?null:oInfraccionNomenclada3.getId();
		this.codigoInfraccion3 = objetcIsNull?null:oInfraccionNomenclada3.getCodigo();
		this.descripcionInfraccion3= objetcIsNull?null:oInfraccionNomenclada3.getDescripcionCorta();
		this.imprimirPuntos3= objetcIsNull?null:oInfraccionNomenclada3.getImprimirPuntos();
		this.puntos3= objetcIsNull?null:oInfraccionNomenclada3.getPuntos();
		this.notasImpresion3= objetcIsNull?null:oInfraccionNomenclada3.getNotasImpresion();
		
	}

	//@Element	
	//@SerializedName("IdInfraccion3")
	@Column(name="ID_INFRACCION3",type ="TEXT")
	private Integer idInfraccion3;

	//@Element
	//@SerializedName("CodigoInfraccion2")
	@Column(name="CODIGO_INFRACCION3",type ="TEXT")
	private String codigoInfraccion3;
	
	private String descripcionInfraccion3;
	private String notasImpresion3;
	private String imprimirPuntos3;
	private Integer puntos3;

	/**/
	private InfraccionNomenclada oInfraccionNomenclada4;
	
	public void setoInfraccionNomenclada4(InfraccionNomenclada oInfraccionNomenclada4) {
		this.oInfraccionNomenclada4 = oInfraccionNomenclada4;
		
		Boolean objetcIsNull = oInfraccionNomenclada4 == null;
		this.idInfraccion4 = objetcIsNull?null:oInfraccionNomenclada4.getId();
		this.codigoInfraccion4 = objetcIsNull?null:oInfraccionNomenclada4.getCodigo();
		this.descripcionInfraccion4= objetcIsNull?null:oInfraccionNomenclada4.getDescripcionCorta();
		this.imprimirPuntos4= objetcIsNull?null:oInfraccionNomenclada4.getImprimirPuntos();
		this.puntos4= objetcIsNull?null:oInfraccionNomenclada4.getPuntos();
		this.notasImpresion4= objetcIsNull?null:oInfraccionNomenclada4.getNotasImpresion();
		
	}

	//@Element	
	//@SerializedName("IdInfraccion4")
	@Column(name="ID_INFRACCION4",type ="TEXT")
	private Integer idInfraccion4;

	//@Element
	//@SerializedName("CodigoInfraccion2")
	@Column(name="CODIGO_INFRACCION4",type ="TEXT")
	private String codigoInfraccion4;
	
	private String descripcionInfraccion4;
	private String notasImpresion4;
	private String imprimirPuntos4;
	private Integer puntos4;
	/**/
	private InfraccionNomenclada oInfraccionNomenclada5;
	
	public void setoInfraccionNomenclada5(InfraccionNomenclada oInfraccionNomenclada5) {
		this.oInfraccionNomenclada5 = oInfraccionNomenclada5;
		
		Boolean objetcIsNull = oInfraccionNomenclada5 == null;
		this.idInfraccion5 = objetcIsNull?null:oInfraccionNomenclada5.getId();
		this.codigoInfraccion5 = objetcIsNull?null:oInfraccionNomenclada5.getCodigo();
		this.descripcionInfraccion5= objetcIsNull?null:oInfraccionNomenclada5.getDescripcionCorta();
		this.imprimirPuntos5= objetcIsNull?null:oInfraccionNomenclada5.getImprimirPuntos();
		this.puntos5= objetcIsNull?null:oInfraccionNomenclada5.getPuntos();
		this.notasImpresion5= objetcIsNull?null:oInfraccionNomenclada5.getNotasImpresion();
		
	}

	//@Element	
	//@SerializedName("IdInfraccion5")
	@Column(name="ID_INFRACCION5",type ="TEXT")
	private Integer idInfraccion5;

	//@Element
	//@SerializedName("CodigoInfraccion5")
	@Column(name="CODIGO_INFRACCION5",type ="TEXT")
	private String codigoInfraccion5;
	
	public String getNotasImpresion3() {
		return notasImpresion3;
	}
	public void setNotasImpresion3(String notasImpresion3) {
		this.notasImpresion3 = notasImpresion3;
	}
	public String getImprimirPuntos3() {
		return imprimirPuntos3;
	}
	public void setImprimirPuntos3(String imprimirPuntos3) {
		this.imprimirPuntos3 = imprimirPuntos3;
	}
	public Integer getPuntos3() {
		return puntos3;
	}
	public void setPuntos3(Integer puntos3) {
		this.puntos3 = puntos3;
	}
	public String getNotasImpresion4() {
		return notasImpresion4;
	}
	public void setNotasImpresion4(String notasImpresion4) {
		this.notasImpresion4 = notasImpresion4;
	}
	public String getImprimirPuntos4() {
		return imprimirPuntos4;
	}
	public void setImprimirPuntos4(String imprimirPuntos4) {
		this.imprimirPuntos4 = imprimirPuntos4;
	}
	public Integer getPuntos4() {
		return puntos4;
	}
	public void setPuntos4(Integer puntos4) {
		this.puntos4 = puntos4;
	}
	public String getNotasImpresion5() {
		return notasImpresion5;
	}
	public void setNotasImpresion5(String notasImpresion5) {
		this.notasImpresion5 = notasImpresion5;
	}
	public String getImprimirPuntos5() {
		return imprimirPuntos5;
	}
	public void setImprimirPuntos5(String imprimirPuntos5) {
		this.imprimirPuntos5 = imprimirPuntos5;
	}
	public Integer getPuntos5() {
		return puntos5;
	}
	public void setPuntos5(Integer puntos5) {
		this.puntos5 = puntos5;
	}
	public String getNotasImpresion6() {
		return notasImpresion6;
	}
	public void setNotasImpresion6(String notasImpresion6) {
		this.notasImpresion6 = notasImpresion6;
	}
	public String getImprimirPuntos6() {
		return imprimirPuntos6;
	}
	public void setImprimirPuntos6(String imprimirPuntos6) {
		this.imprimirPuntos6 = imprimirPuntos6;
	}
	public Integer getPuntos6() {
		return puntos6;
	}
	public void setPuntos6(Integer puntos6) {
		this.puntos6 = puntos6;
	}
	public Integer getIdInfraccion3() {
		return idInfraccion3;
	}
	public void setIdInfraccion3(Integer idInfraccion3) {
		this.idInfraccion3 = idInfraccion3;
	}
	public String getDescripcionInfraccion3() {
		return descripcionInfraccion3;
	}
	public void setDescripcionInfraccion3(String descripcionInfraccion3) {
		this.descripcionInfraccion3 = descripcionInfraccion3;
	}
	public Integer getIdInfraccion4() {
		return idInfraccion4;
	}
	public void setIdInfraccion4(Integer idInfraccion4) {
		this.idInfraccion4 = idInfraccion4;
	}
	public String getDescripcionInfraccion4() {
		return descripcionInfraccion4;
	}
	public void setDescripcionInfraccion4(String descripcionInfraccion4) {
		this.descripcionInfraccion4 = descripcionInfraccion4;
	}
	public Integer getIdInfraccion5() {
		return idInfraccion5;
	}
	public void setIdInfraccion5(Integer idInfraccion5) {
		this.idInfraccion5 = idInfraccion5;
	}
	public String getDescripcionInfraccion5() {
		return descripcionInfraccion5;
	}
	public void setDescripcionInfraccion5(String descripcionInfraccion5) {
		this.descripcionInfraccion5 = descripcionInfraccion5;
	}
	public Integer getIdInfraccion6() {
		return idInfraccion6;
	}
	public void setIdInfraccion6(Integer idInfraccion6) {
		this.idInfraccion6 = idInfraccion6;
	}
	public String getDescripcionInfraccion6() {
		return descripcionInfraccion6;
	}
	public void setDescripcionInfraccion6(String descripcionInfraccion6) {
		this.descripcionInfraccion6 = descripcionInfraccion6;
	}
	public String getCodigoInfraccion3() {
		return codigoInfraccion3;
	}
	public void setCodigoInfraccion3(String codigoInfraccion3) {
		this.codigoInfraccion3 = codigoInfraccion3;
	}
	public String getCodigoInfraccion4() {
		return codigoInfraccion4;
	}
	public void setCodigoInfraccion4(String codigoInfraccion4) {
		this.codigoInfraccion4 = codigoInfraccion4;
	}
	public String getCodigoInfraccion5() {
		return codigoInfraccion5;
	}
	public void setCodigoInfraccion5(String codigoInfraccion5) {
		this.codigoInfraccion5 = codigoInfraccion5;
	}
	public String getCodigoInfraccion6() {
		return codigoInfraccion6;
	}
	public void setCodigoInfraccion6(String codigoInfraccion6) {
		this.codigoInfraccion6 = codigoInfraccion6;
	}

	private String descripcionInfraccion5;
	private String notasImpresion5;
	private String imprimirPuntos5;
	private Integer puntos5;
	/**/
	private InfraccionNomenclada oInfraccionNomenclada6;
	
	public void setoInfraccionNomenclada6(InfraccionNomenclada oInfraccionNomenclada6) {
		this.oInfraccionNomenclada6 = oInfraccionNomenclada6;
		
		Boolean objetcIsNull = oInfraccionNomenclada6 == null;
		this.idInfraccion6 = objetcIsNull?null:oInfraccionNomenclada6.getId();
		this.codigoInfraccion6 = objetcIsNull?null:oInfraccionNomenclada6.getCodigo();
		this.descripcionInfraccion6= objetcIsNull?null:oInfraccionNomenclada6.getDescripcionCorta();
		this.imprimirPuntos6= objetcIsNull?null:oInfraccionNomenclada6.getImprimirPuntos();
		this.puntos6= objetcIsNull?null:oInfraccionNomenclada6.getPuntos();
		this.notasImpresion6= objetcIsNull?null:oInfraccionNomenclada6.getNotasImpresion();
		
	}

	//@Element	
	//@SerializedName("IdInfraccion6")
	@Column(name="ID_INFRACCION6",type ="TEXT")
	private Integer idInfraccion6;

	//@Element
	//@SerializedName("CodigoInfraccion6")
	@Column(name="CODIGO_INFRACCION6",type ="TEXT")
	private String codigoInfraccion6;
	
	private String descripcionInfraccion6;
	private String notasImpresion6;
	private String imprimirPuntos6;
	private Integer puntos6;
	/**/
	
/* FIN DE LAS INFRACCIONES DE LA 3 A LAS 6*/
	
	public String getImprimirPuntos1() {
		return imprimirPuntos1;
	}
	public void setImprimirPuntos1(String imprimirPuntos1) {
		this.imprimirPuntos1 = imprimirPuntos1;
	}
	public String getImprimirPuntos2() {
		return imprimirPuntos2;
	}
	public void setImprimirPuntos2(String imprimirPuntos2) {
		this.imprimirPuntos2 = imprimirPuntos2;
	}
	public String getNotasImpresion2() {
		return notasImpresion2;
	}
	public void setNotasImpresion2(String notasImpresion2) {
		this.notasImpresion2 = notasImpresion2;
	}
	public String getDescripcionInfraccion2() {
		return descripcionInfraccion2;
	}
	public void setDescripcionInfraccion2(String descripcionInfraccion2) {
		this.descripcionInfraccion2 = descripcionInfraccion2;
	}
	//@SerializedName("EsApercibimientoInfraccion2")
	@Column(name="ES_APERCIBIMIENTO_INFRACCION2",type ="TEXT")
	private Boolean esApercibimientoInfraccion2;
	
	//@SerializedName("CoberturaDesdeApercibimiento2")
	@Column(name="COBERTURA_DESDE_INFRACCION2",type ="TEXT")
	private Boolean coberturaDesdeApercibimiento2;
	
	//@Element
	//@SerializedName("Fotos")
	@Column(name="FOTOS",type ="TEXT")
	private String fotos;
	
	//@SerializedName("FotoLicencia")
	//@Column(name="FOTO_LICENCIA",type ="BLOB")
	private byte[] fotoLicencia;
	
	//@SerializedName("FotoDocumento")
	//@Column(name="FOTO_DOCUMENTO",type ="BLOB")
	private byte[] fotoDocumento;
	
//	@SerializedName("FotoOtro")
//	@Column(name="FOTO_OTRO",type ="BLOB")
	private byte[] fotoOtro;
	
	public byte[] getFotoLicencia() {
		return fotoLicencia;
	}
	public void setFotoLicencia(byte[] fotoLicencia) {
		this.fotoLicencia = fotoLicencia;
	}
	public byte[] getFotoDocumento() {
		return fotoDocumento;
	}
	public void setFotoDocumento(byte[] fotoDocumento) {
		this.fotoDocumento = fotoDocumento;
	}
	public byte[] getFotoOtro() {
		return fotoOtro;
	}
	public void setFotoOtro(byte[] fotoOtro) {
		this.fotoOtro = fotoOtro;
	}
	
	//@Element
	@SerializedName("obs")//("Observaciones")
	@Column(name="OBSERVACIONES",type ="TEXT")
	private String observaciones;
	
	//@Element
	//@SerializedName("Observaciones2")
	@Column(name="OBSERVACIONES2",type ="TEXT")
	private String observaciones2;

	//@Element
	//@SerializedName("ConductorEsTitular")
	@Column(name="CONDUCTOR_ES_TITULAR",type ="TEXT")
	private String conductorEsTitular;

	
	public String getObservaciones2() {
		return observaciones2;
	}
	public void setObservaciones2(String observaciones2) {
		this.observaciones2 = observaciones2;
	}
	public String getConductorEsTitular() {
		return conductorEsTitular;
	}
	public void setConductorEsTitular(String conductorEsTitular) {
		this.conductorEsTitular = conductorEsTitular;
	}

	//@Element
	//@SerializedName("IdJuzgado")
	@Column(name="ID_JUZGADO",type ="TEXT")
	private Integer idJuzgado;
	
	//@Element
	//@SerializedName("IdSeccional")
	@Column(name="ID_SECCIONAL",type ="TEXT")
	private String idSeccional;
	
	
	//@Element
	@SerializedName("LocalidadJuzgado")
	@Column(name="LOCALIDAD_JUZGADO",type ="TEXT")
	private String localidadJuzgado;
	
	//@Element
	@SerializedName("CodigoPostalJuzgado")
	@Column(name="CODIGO_POSTAL_JUZGADO",type ="TEXT")
	private String codigoPostalJuzgado;
	
	//@Element
	@SerializedName("CalleJuzgado")
	@Column(name="CALLE_JUZGADO",type ="TEXT")
	private String calleJuzgado;
	
	//@Element
	@SerializedName("AlturaJuzgado")
	@Column(name="ALTURA_JUZGADO",type ="TEXT")
	private String alturaJuzgado;
	
	//@Element
	@SerializedName("IdUsuarioPDA")
	@Column(name="ID_USUARIO_PDA",type ="TEXT")
	private String idUsuarioPDA;
	
	//@SerializedName("NivelCoberturaMovil")
	@Column(name="NIVEL_COBERTURA_MOVIL",type ="TEXT")
	private String nivelCoberturaMovil;
	
	//@SerializedName("TipoCoberturaMovil")
	@Column(name="TIPO_COBERTURA_MOVIL",type ="TEXT")
	private String tipoCoberturaMovil;

	//@Element
	//@SerializedName("Sincronizada")
	@Column(name="SINCRONIZADA",type ="TEXT")
	private String sincronizada;
	
	/* Inicio de lo Agregado */
	//@Element
	//@SerializedName("Ubicacion")
	@Column(name="UBICACION",type ="TEXT")
	private String ubicacion;

	//@Element
	//@SerializedName("DescripcionUbicacion")
	@Column(name="DESCRIPCION_UBICACION",type ="TEXT")
	private String descripcionUbicacion;
	
	//@Element
	@SerializedName("ModeloVehiculo")
	@Column(name="MODELO_VEHICULO",type ="TEXT")
	private String modeloVehiculo;

	
	//@Element
	//@SerializedName("TelefonoJuzgado")
	@Column(name="TELEFONO_JUZGADO",type ="TEXT")
	private String telefonoJuzgado;
	
	//@Element
	//@SerializedName("EmailJuzgado")
	@Column(name="EMAIL_JUZGADO",type ="TEXT")
	private String emailJuzgado;
	
	
	public String getTelefonoJuzgado() {
		return telefonoJuzgado;
	}
	public void setTelefonoJuzgado(String telefonoJuzgado) {
		this.telefonoJuzgado = telefonoJuzgado;
	}
	public String getEmailJuzgado() {
		return emailJuzgado;
	}
	public void setEmailJuzgado(String emailJuzgado) {
		this.emailJuzgado = emailJuzgado;
	}
	public String getModeloVehiculo() {
		return modeloVehiculo;
	}
	public void setModeloVehiculo(String modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getDescripcionUbicacion() {
		return descripcionUbicacion;
	}
	public void setDescripcionUbicacion(String descripcionUbicacion) {
		this.descripcionUbicacion = descripcionUbicacion;
	}
	
	
	private TipoDocumento oTipoDocumentoTitular;

	public void setoTipoDocumentoTitular(TipoDocumento oTipoDocumento) {
		this.oTipoDocumentoTitular = oTipoDocumento;
		Boolean objetcIsNull = oTipoDocumento == null;
		this.tipoDocumentoTitular= objetcIsNull==null?null: oTipoDocumento.getId();
	}
	// lo vamos a usar para las validaciones 
	public TipoDocumento getoTipoDocumentoTitular() {
		return oTipoDocumentoTitular;
	}

	
	/*Datos de la persona*/
	//@Element	
	@SerializedName("TipoDocumentoTitular")
	@Column(name="TIPO_DOCUMENTO_TITULAR",type ="TEXT")
	private String tipoDocumentoTitular;
	
	//@SerializedName("CoberturaDesdeNumeroDocumentoTitular")
	@Column(name="COBERTURA_DESDE_NUMERO_DOCUMENTO_TITULAR",type ="TEXT")
	private Boolean coberturaDesdeNumeroDocumentoTitular;

	//@Element
	@SerializedName("NumeroDocumentoTitular")
	@Column(name="NUMERO_DOCUMENTO_TITULAR",type ="TEXT")
	private String numeroDocumentoTitular;
	/*
	private Genero oGeneroTitular;

	public void setoGeneroTitular(Genero oGenero) {
		this.oGeneroTitular = oGenero;
		Boolean objetcIsNull = oGenero== null? true:false;
		this.idSexoTitular= objetcIsNull==null?null: oGenero.getId();
		this.sexoTitular = objetcIsNull==null?null:oGenero.getNombre();
	}
	
	//@Element
	@SerializedName("IdSexoTitular")
	@Column(name="ID_SEXO_TITULAR",type ="TEXT")
	private String idSexoTitular; // 2 caracteres
	
	//@Element	
	private String sexoTitular; // 2 caracteres
	
	public String getSexoTitular() {
		return sexoTitular;
	}
	public void setSexoTitular(String sexo) {
		this.sexo = sexo;
	}
	*/
	//@Element	
	@SerializedName("ApellidoTitular")
	@Column(name="APELLIDO_TITULAR",type ="TEXT")
	private String apellidoTitular;
	
	//@Element	
	@SerializedName("NombreTitular")
	@Column(name="NOMBRE_TITULAR",type ="TEXT")
	private String nombreTitular;
 
	//@Element
	//@SerializedName("EsCallePublicaTitular")
	@Column(name="ES_CALLE_PUBLICA_TITULAR",type ="TEXT")
	private Boolean esCallePublicaTitular;
	
	//@Element
	@SerializedName("CalleTitular")
	@Column(name="CALLE_TITULAR",type ="TEXT")
	private String calleTitular; //
	
	//@Element
	//@SerializedName("EsAlturaSinNumeroTitular")
	@Column(name="ES_ALTURA_SIN_NUMERO_TITULAR",type ="TEXT")
	private Boolean esAlturaSinNumeroTitular;
	
	//@Element
	@SerializedName("AlturaTitular")
	@Column(name="ALTURA_TITULAR",type ="TEXT")
	private String alturaTitular; //
	
	//@Element
	@SerializedName("PisoTitular")
	@Column(name="PISO_TITULAR",type ="TEXT")
	private String pisoTitular;
	
	//@Element
	@SerializedName("DepartamentoTitular")
	@Column(name="DEPARTAMENTO_TITULAR",type ="TEXT")
	private String departamentoTitular;
	
	//@Element
	@SerializedName("BarrioTitular")
	@Column(name="BARRIO_TITULAR",type ="TEXT")
	private String barrioTitular;
	
	private Pais oPaisDomicilioTitular;

	public void setoPaisDomicilioTitular(Pais oPaisDomicilio) {
		this.oPaisDomicilioTitular = oPaisDomicilio;
		Boolean objetcIsNull = oPaisDomicilio == null;
		this.idPaisDomicilioTitular= objetcIsNull==null?null: oPaisDomicilio.getId();
		this.paisDomicilioTitular= objetcIsNull==null?this.paisDomicilioTitular: oPaisDomicilio.getNombre();
	}

	//@Element
	//@SerializedName("IdPaisDomicilioTitular")
	@Column(name="ID_PAIS_DOMICILIO_TITULAR",type ="TEXT")
	private String idPaisDomicilioTitular; // ARG  => Argentina
	
	//@Element
	@SerializedName("PaisDomicilioTitular")
	@Column(name="PAIS_DOMICILIO_TITULAR",type ="TEXT")
	private String paisDomicilioTitular;
	
	private Provincia oProvinciaDomicilioTitular;
	
	public void setoProvinciaDomicilioTitular(Provincia oProvinciaDomicilio) {
		this.oProvinciaDomicilioTitular = oProvinciaDomicilio;
		Boolean objetcIsNull = oProvinciaDomicilio == null;
		this.idProvinciaDomicilioTitular = objetcIsNull==null?null: oProvinciaDomicilio.getId();
		this.provinciaDomicilioTitular = objetcIsNull==null?this.provinciaDomicilioTitular: oProvinciaDomicilio.getNombre();
	}

	//@Element
	//@SerializedName("IdProvinciaDomicilioTitular")
	@Column(name="ID_PROVINCIA_DOMICILIO_TITULAR",type ="TEXT")
	private String idProvinciaDomicilioTitular; // X => Cordoba
	
	//@Element
	@SerializedName("ProvinciaDomicilioTitular")
	@Column(name="PROVINCIA_DOMICILIO_TITULAR",type ="TEXT")
	private String provinciaDomicilioTitular;
	
	private Departamento oDepartamentoDomicilioTitular;
	
	public void setoDepartamentoDomicilioTitular(Departamento oDepartamentoDomicilio) {
		this.oDepartamentoDomicilioTitular = oDepartamentoDomicilio;
		Boolean objetcIsNull = oDepartamentoDomicilio == null;
		this.idDepartamentoDomicilioTitular = objetcIsNull==null?null: oDepartamentoDomicilio.getId();
		this.departamentoDomicilioTitular= objetcIsNull==null?this.departamentoDomicilioTitular: oDepartamentoDomicilio.getNombre();
	}	
	
	//@Element
	//@SerializedName("IdDepartamentoDomicilioTitular")
	@Column(name="ID_DEPARTAMENTO_DOMICILIO_TITULAR",type ="TEXT")
	private Integer idDepartamentoDomicilioTitular;

	//@Element
	@SerializedName("DepartamentoDomicilioTitular")
	@Column(name="DEPARTAMENTO_DOMICILIO_TITULAR",type ="TEXT")
	private String departamentoDomicilioTitular;
	
	public void setoLocalidadDomicilioTitular(Localidad oLocalidadDomicilio) {
		this.oLocalidadDomicilioTitular = oLocalidadDomicilio;
		Boolean objetcIsNull = oLocalidadDomicilio == null;
		this.idLocalidadDomicilioTitular= objetcIsNull==null?null: oLocalidadDomicilio.getId();
		this.localidadDomicilioTitular= objetcIsNull==null?this.localidadDomicilioTitular: oLocalidadDomicilio.getNombre();

	}

	private Localidad oLocalidadDomicilioTitular;
	
	//@Element
	//@SerializedName("IdLocalidadDomicilioTitular")
	@Column(name="ID_LOCALIDAD_DOMICILIO_TITULAR",type ="TEXT")
	private Integer idLocalidadDomicilioTitular;
	
	//@Element
	@SerializedName("LocalidadDomicilioTitular")
	@Column(name="LOCALIDAD_DOMICILIO_TITULAR",type ="TEXT")
	private String localidadDomicilioTitular;
	
	//@Element
	@SerializedName("CodigoPostalTitular")
	@Column(name="CODIGO_POSTAL_TITULAR",type ="TEXT")
	private String codigoPostalTitular;


	//@Element
	@SerializedName("FechaNacimientoString")
	@Column(name="FECHA_NACIMIENTO_STRING")
	private String fechaNacimientoString;
	
	//@Element	
	@SerializedName("FechaNacimiento")
	@Column(name="FECHA_NACIMIENTO",type ="TEXT")
	private Date fechaNacimiento;


	@SerializedName("Grad_Alcohol")
	@Column(name = "GRAD_ALCOHOL")
	private String grad_alcohol;
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		if (fechaNacimiento!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaNacimientoString = formatter.format(fechaNacimiento);
		}
	}

	//@Element
	//@SerializedName("FechaNacimientoTitularString")
	@Column(name="FECHA_NACIMIENTO_TITULAR_STRING")
	private String fechaNacimientoTitularString;
	
	//@Element	
	@SerializedName("FechaNacimientoTitular")
	@Column(name="FECHA_NACIMIENTO_TITULAR",type ="TEXT")
	private Date fechaNacimientoTitular;
	
	public Date getFechaNacimientoTitular() {
		return fechaNacimientoTitular;
	}

	public void setFechaNacimientoTitular(Date fechaNacimiento) {
		this.fechaNacimientoTitular = fechaNacimiento;
		if (fechaNacimiento!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaNacimientoTitularString = formatter.format(fechaNacimientoTitular);
		}
	}
	
	/* Fin de lo Agregado */
	
	public ActaConstatacion(){}
	
	public ActaConstatacion(Integer id, String nombre){
		super();
		this.idActaConstatacion = id;
		this.nombre=nombre;
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

	public String getDigitoVerificador() {
		return digitoVerificador;
	}

	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
		//this.codigoBarra1 = codigoBarra.substring(0,42);
		//this.codigoBarra2 = codigoBarra.substring(42,codigoBarra.length());
	}
	public String getCodigoBarra2Do() {
		return codigoBarra2Do;
	}

	public void setCodigoBarra2Do(String codigoBarra) {
		this.codigoBarra2Do = codigoBarra;
		//this.codigoBarra1 = codigoBarra.substring(0,42);
		//this.codigoBarra2 = codigoBarra.substring(42,codigoBarra.length());
	}

	public Integer getIdMovil() {
		return idMovil;
	}

	public void setIdMovil(Integer idMovil) {
		this.idMovil = idMovil;
	}

	public String getAnioModeloVehiculo() {
		return anioModeloVehiculo;
	}

	public void setAnioModeloVehiculo(String anioModeloVehiculo) {
		this.anioModeloVehiculo = anioModeloVehiculo;
	}

	/*2do venci*/
	public Date getFechaVencimiento2DoCupon() {
		return fechaVencimiento2DoCupon;
	}
	public void setFechaVencimiento2DoCupon(Date fechaVencimiento2DoCupon) {
		this.fechaVencimiento2DoCupon = fechaVencimiento2DoCupon;
		if (fechaVencimiento2DoCupon!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaVencimiento2DoCuponString = formatter.format(fechaVencimiento2DoCupon);
		}
	}
	public String getFechaVencimiento2DoCuponString() {
		return fechaVencimiento2DoCuponString;
	}
	public void setFechaVencimiento2DoCuponString(String fechaVencimiento2DoCuponString) {
		this.fechaVencimiento2DoCuponString = fechaVencimiento2DoCuponString;
	}
	public Double getMonto2DoCupon() {
		return monto2DoCupon;
	}
	public void setMonto2DoCupon(Double monto2DoCupon) {
		this.monto2DoCupon = monto2DoCupon;
		if (monto2DoCupon!=null)
		{
			this.monto2DoCuponString = Tools.DecimalFormat(monto2DoCupon);  //String.format("%10.2f", montoCupon);
		}
	}
	public String getMonto2DoCuponString() {
		return monto2DoCuponString;
	}
	public void setMonto2DoCuponString(String monto2DoCuponString) {
		this.monto2DoCuponString = monto2DoCuponString;
	}
	/*fin 2do venci */
	public Date getFechaVencimientoCupon() {
		return fechaVencimientoCupon;
	}
	public void setFechaVencimientoCupon(Date fechaVencimientoCupon) {
		this.fechaVencimientoCupon = fechaVencimientoCupon;
		if (fechaVencimientoCupon!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaVencimientoCuponString = formatter.format(fechaVencimientoCupon);
		}
	}
	public String getFechaVencimientoCuponString() {
		return fechaVencimientoCuponString;
	}
	public void setFechaVencimientoCuponString(String fechaVencimientoCuponString) {
		this.fechaVencimientoCuponString = fechaVencimientoCuponString;
	}
	public Double getMontoCupon() {
		return montoCupon;
	}
	public void setMontoCupon(Double montoCupon) {
		this.montoCupon = montoCupon;
		if (montoCupon!=null)
		{
			this.montoCuponString = Tools.DecimalFormat(montoCupon);  //String.format("%10.2f", montoCupon);
		}
	}

	public String getMontoCuponString() {
		return montoCuponString;
	}

	public void setMontoCuponString(String montoCuponString) {
		this.montoCuponString = montoCuponString;
	}

	public Date getFechaHoraCarga() {
		return fechaHoraCarga;
	}

	public void setFechaHoraCarga(Date fechaHoraCarga) {
		this.fechaHoraCarga = fechaHoraCarga;
		if (fechaHoraCarga!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaHoraCargaString = formatter.format(fechaHoraCarga);
		}
	}

	public String getFechaHoraCargaString() {
		return fechaHoraCargaString;
	}

	public void setFechaHoraCargaString(String fechaHoraCargaString) {
		this.fechaHoraCargaString = fechaHoraCargaString;
	}

	public Date getFechaHoraLabrado() {
		return fechaHoraLabrado;
	}

	public void setFechaHoraLabrado(Date fechaHoraLabrado) {
		this.fechaHoraLabrado = fechaHoraLabrado;
		if (fechaHoraLabrado!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.fechaHoraLabradoString = formatter.format(fechaHoraLabrado);
		}

	}

	public String getFechaHoraLabradoString() {
		return fechaHoraLabradoString;
	}

	public void setFechaHoraLabradoString(String fechaHoraLabradoString) {
		this.fechaHoraLabradoString = fechaHoraLabradoString;
	}

	public String getIdTipoRuta() {
			return idTipoRuta;
	}
	
	public void setIdTipoRuta(String idTipoRuta) {
		this.idTipoRuta = idTipoRuta;
	}

	public String getTipoRuta() {
		return tipoRuta;
	}

	public void setTipoRuta(String tipoRuta) {
		this.tipoRuta = tipoRuta;
	}

	public String getIdNumeroRuta() {
		return idNumeroRuta;
	}
	
	public void setIdNumeroRuta(String idNumeroRuta) {
		this.idNumeroRuta = idNumeroRuta;
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

	public String getProveedorPosicionGPS() {
		return proveedorPosicionGPS;
	}

	public void setProveedorPosicionGPS(String proveedorPosicionGPS) {
		this.proveedorPosicionGPS = proveedorPosicionGPS;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Boolean getLicenciaDesdeCodigoQR() {
		return licenciaDesdeCodigoQR;
	}

	public void setLicenciaDesdeCodigoQR(Boolean LicenciaDesdeCodigoQR) {
		this.licenciaDesdeCodigoQR = LicenciaDesdeCodigoQR;
	}

	public Boolean getCoberturaDesdeCodigoQR() {
		return coberturaDesdeCodigoQR;
	}

	public void setCoberturaDesdeCodigoQR(Boolean CoberturaDesdeCodigoQR) {
		this.coberturaDesdeCodigoQR = CoberturaDesdeCodigoQR;
	}

	public String getLicenciaRetendia() {
		return licenciaRetendia == null? "N":licenciaRetendia;
	}

	public void setLicenciaRetendia(String licenciaRetendia) {
		this.licenciaRetendia = licenciaRetendia;
	}

	public String getLicenciaUnicaProvincial() {
		return licenciaUnicaProvincial;
	}

	public void setLicenciaUnicaProvincial(String licenciaUnicaProvincial) {
		this.licenciaUnicaProvincial = licenciaUnicaProvincial;
	}

	public Boolean getCoberturaDesdeNumeroLicencia() {
		return coberturaDesdeNumeroLicencia;
	}

	public void setCoberturaDesdeNumeroLicencia(Boolean CoberturaDesdeNumeroLicencia) {
		this.coberturaDesdeNumeroLicencia = CoberturaDesdeNumeroLicencia;
	}

	public String getNumeroLicencia() {
		return numeroLicencia;
	}

	public void setNumeroLicencia(String numeroLicencia) {
		this.numeroLicencia = numeroLicencia;
	}

	public String getIdClaseLicencia() {
		return idClaseLicencia;
	}

	public void setIdClaseLicencia(String idClaseLicencia) {
		this.idClaseLicencia = idClaseLicencia;
	}

	public String getClaseLicencia() {
		return claseLicencia;
	}

	public void setClaseLicencia(String claseLicencia) {
		this.claseLicencia = claseLicencia;
	}

	public Date getFechaVencimientoLicencia() {
		return fechaVencimientoLicencia;
	}

	public void setFechaVencimientoLicencia(Date fechaVencimientoLicencia) {
		this.fechaVencimientoLicencia = fechaVencimientoLicencia;
		if (fechaVencimientoLicencia!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaVencimientoLicenciaString = formatter.format(fechaVencimientoLicencia);
		}
	}

	public String getIdPaisLicencia() {
		return idPaisLicencia;
	}

	public void setIdPaisLicencia(String idPaisLicencia) {
		this.idPaisLicencia = idPaisLicencia;
	}

	public String getPaisLicencia() {
		return paisLicencia;
	}

	public void setPaisLicencia(String paisLicencia) {
		this.paisLicencia = paisLicencia;
	}

	public String getIdProvinciaLicencia() {
		return idProvinciaLicencia;
	}

	public void setIdProvinciaLicencia(String idPovinciaLicencia) {
		this.idProvinciaLicencia = idPovinciaLicencia;
	}

	public String getProvinciaLicencia() {
		return provinciaLicencia;
	}

	public void setProvinciaLicencia(String provinciaLicencia) {
		this.provinciaLicencia = provinciaLicencia;
	}

	public Integer getIdDepartamentoLicencia() {
		return idDepartamentoLicencia;
	}

	public void setIdDepartamentoLicencia(Integer idDepartamentoLicencia) {
		this.idDepartamentoLicencia = idDepartamentoLicencia;
	}

	public String getDepartamentoLicencia() {
		return departamentoLicencia;
	}

	public void setDepartamentoLicencia(String departamentoLicencia) {
		this.departamentoLicencia = departamentoLicencia;
	}

	public Integer getIdLocalidadLicencia() {
		return idLocalidadLicencia;
	}

	public void setIdLocalidadLicencia(Integer idLocalidadLicencia) {
		this.idLocalidadLicencia = idLocalidadLicencia;
	}

	public String getLocalidadLicencia() {
		return localidadLicencia;
	}

	public void setLocalidadLicencia(String localidadLicencia) {
		this.localidadLicencia = localidadLicencia;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getCoberturaDesdeNumeroDocumento() {
		return coberturaDesdeNumeroDocumento;
	}

	public void setCoberturaDesdeNumeroDocumento(Boolean conexionAlBuscarNumeroDocumento) {
		this.coberturaDesdeNumeroDocumento = conexionAlBuscarNumeroDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
   /*
	public String getIdSexo() {
		return idSexo;
	}

	public void setIdSexo(String idSexo) {
		this.idSexo = idSexo;
	}
    */
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

	public Boolean getEsCallePublica() {
		return esCallePublica;
	}

	public void setEsCallePublica(Boolean esCallePublica) {
		this.esCallePublica = esCallePublica;
	}

	public String getCalle() {
		return calle==null?"":calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Boolean getEsAlturaSinNumero() {
		return esAlturaSinNumero;
	}

	public void setEsAlturaSinNumero(Boolean esAlturaSinNumero) {
		this.esAlturaSinNumero = esAlturaSinNumero;
	}

	public String getAltura() {
		return altura==null?"":altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getIdPaisDomicilio() {
		return idPaisDomicilio;
	}

	public void setIdPaisDomicilio(String idPaisDomicilio) {
		this.idPaisDomicilio = idPaisDomicilio;
	}

	public String getPaisDomicilio() {
		return paisDomicilio;
	}

	public void setPaisDomicilio(String paisDomicilio) {
		this.paisDomicilio = paisDomicilio;
	}

	public String getIdProvinciaDomicilio() {
		return idProvinciaDomicilio;
	}

	public void setIdProvinciaDomicilio(String idPovinciaDomicilio) {
		this.idProvinciaDomicilio = idPovinciaDomicilio;
	}

	public String getProvinciaDomicilio() {
		return provinciaDomicilio;
	}

	public void setProvinciaDomicilio(String provinciaDomicilio) {
		this.provinciaDomicilio = provinciaDomicilio;
	}

	public Integer getIdDepartamentoDomicilio() {
		return idDepartamentoDomicilio;
	}

	public void setIdDepartamentoDomicilio(Integer idDepartamentoDomicilio) {
		this.idDepartamentoDomicilio = idDepartamentoDomicilio;
	}

	public String getDepartamentoDomicilio() {
		return departamentoDomicilio;
	}

	public void setDepartamentoDomicilio(String departamentoDomicilio) {
		this.departamentoDomicilio = departamentoDomicilio;
	}

	public Integer getIdLocalidadDomicilio() {
		return idLocalidadDomicilio;
	}

	public void setIdLocalidadDomicilio(Integer idLocalidadDomicilio) {
		this.idLocalidadDomicilio = idLocalidadDomicilio;
	}

	public String getLocalidadDomicilio() {
		return localidadDomicilio;
	}

	public void setLocalidadDomicilio(String localidadDomicilio) {
		this.localidadDomicilio = localidadDomicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	
	/* getter and setter  Titular */
	
	public String getTipoDocumentoTitular() {
		return tipoDocumentoTitular;
	}

	public void setTipoDocumentoTitular(String tipoDocumento) {
		this.tipoDocumentoTitular = tipoDocumento;
	}

	public Boolean getCoberturaDesdeNumeroDocumentoTitular() {
		return coberturaDesdeNumeroDocumentoTitular;
	}

	public void setCoberturaDesdeNumeroDocumentoTitular(Boolean conexionAlBuscarNumeroDocumento) {
		this.coberturaDesdeNumeroDocumentoTitular = conexionAlBuscarNumeroDocumento;
	}

	public String getNumeroDocumentoTitular() {
		return numeroDocumentoTitular;
	}

	public void setNumeroDocumentoTitular(String numeroDocumento) {
		this.numeroDocumentoTitular = numeroDocumento;
	}
/*
	public String getIdSexoTitular() {
		return idSexoTitular;
	}

	public void setIdSexoTitular(String idSexo) {
		this.idSexoTitular = idSexo;
	}
*/
	public String getApellidoTitular() {
		return apellidoTitular;
	}

	public void setApellidoTitular(String apellido) {
		this.apellidoTitular = apellido;
	}

	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombre) {
		this.nombreTitular = nombre;
	}

	public Boolean getEsCallePublicaTitular() {
		return esCallePublicaTitular;
	}

	public void setEsCallePublicaTitular(Boolean esCallePublica) {
		this.esCallePublicaTitular = esCallePublica;
	}

	public String getCalleTitular() {
		return calleTitular==null?"":calleTitular;
	}

	public void setCalleTitular(String calle) {
		this.calleTitular = calle;
	}

	public Boolean getEsAlturaSinNumeroTitular() {
		return esAlturaSinNumeroTitular;
	}

	public void setEsAlturaSinNumeroTitular(Boolean esAlturaSinNumero) {
		this.esAlturaSinNumeroTitular = esAlturaSinNumero;
	}

	public String getAlturaTitular() {
		return alturaTitular==null?"":alturaTitular;
	}

	public void setAlturaTitular(String altura) {
		this.alturaTitular = altura;
	}

	public String getPisoTitular() {
		return pisoTitular;
	}

	public void setPisoTitular(String piso) {
		this.pisoTitular = piso;
	}

	public String getDepartamentoTitular() {
		return departamentoTitular;
	}

	public void setDepartamentoTitular(String departamento) {
		this.departamentoTitular = departamento;
	}

	public String getBarrioTitular() {
		return barrioTitular;
	}

	public void setBarrioTitular(String barrio) {
		this.barrioTitular = barrio;
	}

	public String getIdPaisDomicilioTitular() {
		return idPaisDomicilioTitular;
	}

	public void setIdPaisDomicilioTitular(String idPaisDomicilio) {
		this.idPaisDomicilioTitular = idPaisDomicilio;
	}

	public String getPaisDomicilioTitular() {
		return paisDomicilioTitular;
	}

	public void setPaisDomicilioTitular(String paisDomicilio) {
		this.paisDomicilioTitular = paisDomicilio;
	}

	public String getIdProvinciaDomicilioTitular() {
		return idProvinciaDomicilioTitular;
	}

	public void setIdProvinciaDomicilioTitular(String idPovinciaDomicilio) {
		this.idProvinciaDomicilioTitular = idPovinciaDomicilio;
	}

	public String getProvinciaDomicilioTitular() {
		return provinciaDomicilioTitular;
	}

	public void setProvinciaDomicilioTitular(String provinciaDomicilio) {
		this.provinciaDomicilioTitular = provinciaDomicilio;
	}

	public Integer getIdDepartamentoDomicilioTitular() {
		return idDepartamentoDomicilioTitular;
	}

	public void setIdDepartamentoDomicilioTitular(Integer idDepartamentoDomicilio) {
		this.idDepartamentoDomicilioTitular = idDepartamentoDomicilio;
	}

	public String getDepartamentoDomicilioTitular() {
		return departamentoDomicilioTitular;
	}

	public void setDepartamentoDomicilioTitular(String departamentoDomicilio) {
		this.departamentoDomicilioTitular = departamentoDomicilio;
	}

	public Integer getIdLocalidadDomicilioTitular() {
		return idLocalidadDomicilioTitular;
	}

	public void setIdLocalidadDomicilioTitular(Integer idLocalidadDomicilio) {
		this.idLocalidadDomicilioTitular = idLocalidadDomicilio;
	}

	public String getLocalidadDomicilioTitular() {
		return localidadDomicilioTitular;
	}

	public void setLocalidadDomicilioTitular(String localidadDomicilio) {
		this.localidadDomicilioTitular = localidadDomicilio;
	}

	public String getCodigoPostalTitular() {
		return codigoPostalTitular;
	}

	public void setCodigoPostalTitular(String codigoPostal) {
		this.codigoPostalTitular = codigoPostal;
	}
	
	/* Fin getter and setter Titular*/
	
	public String getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(String idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public String getIdTipoPatente() {
		return idTipoPatente;
	}

	public void setIdTipoPatente(String idTipoPatente) {
		this.idTipoPatente = idTipoPatente;
	}

	public Boolean getCoberturaDesdeDominio() {
		return coberturaDesdeDominio;
	}

	public void setCoberturaDesdeDominio(Boolean CoberturaDominio) {
		this.coberturaDesdeDominio = CoberturaDominio;
	}

	
	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}

	public Integer getIdColor() {
		return idColor;
	}

	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public String getTieneLicencia() {
		return tieneLicencia==null?"N":tieneLicencia;
	}


	public void setTieneLicencia(String tieneLicencia) {
		this.tieneLicencia = tieneLicencia;
	}

	public Integer getIdInfraccion1() {
		return idInfraccion1;
	}
	
	public void setIdInfraccion1(Integer idInfraccion1) {
		this.idInfraccion1 = idInfraccion1;
	}
	public void setIdInfraccionNomenclada1(Integer idInfraccionNomenclada1) {
		this.idInfraccion1 = idInfraccionNomenclada1;
	}

	public String getCodigoInfraccion1() {
		return codigoInfraccion1;
	}

	public void setCodigoInfraccion1(String codigoInfraccionNomenclada1) {
		this.codigoInfraccion1 = codigoInfraccionNomenclada1;
		
	}

	public Boolean getEsApercibimientoInfraccion1() {
		return esApercibimientoInfraccion1;
	}

	public void setEsApercibimientoInfraccion1(Boolean esApercibimientoInfraccion1) {
		this.esApercibimientoInfraccion1 = esApercibimientoInfraccion1;
	}

	public Boolean getCoberturaDesdeApercibimiento1() {
		return coberturaDesdeApercibimiento1;
	}

	public void setCoberturaDesdeApercibimiento1(Boolean conexionAlVerificarApercibimiento1) {
		this.coberturaDesdeApercibimiento1 = conexionAlVerificarApercibimiento1;
	}

	public Integer getIdInfraccion2() {
		return idInfraccion2;
	}

	public void setIdInfraccion2(Integer idInfraccionNomenclada2) {
		this.idInfraccion2 = idInfraccionNomenclada2;
	}

	public String getCodigoInfraccion2() {
		return codigoInfraccion2;
	}

	public void setCodigoInfraccion2(String codigoInfraccionNomenclada2) {
		this.codigoInfraccion2 = codigoInfraccionNomenclada2;
	}

	public Boolean getEsApercibimientoInfraccion2() {
		return esApercibimientoInfraccion2;
	}

	public void setEsApercibimientoInfraccion2(Boolean esApercibimientoInfraccion2) {
		this.esApercibimientoInfraccion2 = esApercibimientoInfraccion2;
	}

	public Boolean getCoberturaDesdeApercibimiento2() {
		return coberturaDesdeApercibimiento2;
	}

	public void setCoberturaDesdeApercibimiento2(Boolean conexionAlVerificarApercibimiento2) {
		this.coberturaDesdeApercibimiento2 = conexionAlVerificarApercibimiento2;
	}

	public String getFotos() {
		return fotos;
	}

	public void setFotos(String fotos) {
		this.fotos = fotos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(Integer idJuzgado) {
		this.idJuzgado = idJuzgado;
	}
	
	private String juzgado;
	
	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}

	private String seccional;
	
	public String getSeccional() {
		return seccional;
	}

	public void setSeccional(String seccional) {
		this.seccional = seccional;
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

	public String getCalleJuzgado() {
		return calleJuzgado;
	}

	public void setCalleJuzgado(String calleJuzgado) {
		this.calleJuzgado = calleJuzgado;
	}

	public String getAlturaJuzgado() {
		return alturaJuzgado;
	}

	public void setAlturaJuzgado(String alturaJuzgado) {
		this.alturaJuzgado = alturaJuzgado;
	}

	public String getIdUsuarioPDA() {
		return idUsuarioPDA;
	}

	public void setIdUsuarioPDA(String idUsuarioPDA) {
		this.idUsuarioPDA = idUsuarioPDA;
	}

	public String getNivelCoberturaMovil() {
		return nivelCoberturaMovil;
	}

	public void setNivelCoberturaMovil(String nivelCoberturaMovil) {
		this.nivelCoberturaMovil = nivelCoberturaMovil;
	}

	public String getTipoCoberturaMovil() {
		return tipoCoberturaMovil;
	}

	public void setTipoCoberturaMovil(String tipoCoberturaMovil) {
		this.tipoCoberturaMovil = tipoCoberturaMovil;
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
		this.idActaConstatacion = Integer.parseInt(item);
		
	}
	@Override
	public void setItemName(String item) {
		this.numeroActa  = item;// + this.apellido + ", " + this.nombre + " " + this.numeroDocumento + " " + this.fechaHoraCarga ;
		
	}
	@Override
	public String getItemDescription() {
		// TODO Auto-generated method stub
		if(this.idActaConstatacion!=null && this.idActaConstatacion!=-1)
			return  "Infractor : " + (this.apellido==null?"":this.apellido) + ", " + (this.nombre==null?"":this.nombre) + "\nDNI " + (this.numeroDocumento==null?"":this.numeroDocumento) + "Fecha Registro :" + this.fechaHoraCargaString + "\nSincronizada : " + (this.getSincronizada().equals("S")?"Si":"No");
			else
			return "";
	}
	public String getSincronizada() {
		return sincronizada==null?"N":sincronizada;
	}
	public void setSincronizada(String sincronizada) {
		this.sincronizada = sincronizada;
	}

	
	
	/* agregado 2017  TESTIGO */
	private TipoDocumento oTipoDocumentoTestigo;

	public void setoTipoDocumentoTestigo(TipoDocumento oTipoDocumento) {
		this.oTipoDocumentoTestigo = oTipoDocumento;
		Boolean objetcIsNull = oTipoDocumento == null;
		this.tipoDocumentoTestigo= objetcIsNull==null?null: oTipoDocumento.getId();
	}
	// lo vamos a usar para las validaciones 
	public TipoDocumento getoTipoDocumentoTestigo() {
		return oTipoDocumentoTestigo;
	}

	
	/*Datos de la persona Testigo*/
	//@Element	
	@SerializedName("TipoDocumentoTestigo")
	@Column(name="TIPO_DOCUMENTO_TESTIGO",type ="TEXT")
	private String tipoDocumentoTestigo;
	
	//@SerializedName("CoberturaDesdeNumeroDocumentoTestigo")
	@Column(name="COBERTURA_DESDE_NUMERO_DOCUMENTO_TESTIGO",type ="TEXT")
	private Boolean coberturaDesdeNumeroDocumentoTestigo;

	//@Element
	@SerializedName("NumeroDocumentoTestigo")
	@Column(name="NUMERO_DOCUMENTO_TESTIGO",type ="TEXT")
	private String numeroDocumentoTestigo;
	/*
	private Genero oGeneroTestigo;

	public void setoGeneroTestigo(Genero oGenero) {
		this.oGeneroTestigo = oGenero;
		Boolean objetcIsNull = oGenero== null? true:false;
		this.idSexoTestigo= objetcIsNull==null?null: oGenero.getId();
		this.sexoTestigo = objetcIsNull==null?null:oGenero.getNombre();
	}
	
	//@Element
	@SerializedName("IdSexoTestigo")
	@Column(name="ID_SEXO_TESTIGO",type ="TEXT")
	private String idSexoTestigo; // 2 caracteres
	
	//@Element	
	private String sexoTestigo; // 2 caracteres
	
	public String getSexoTestigo() {
		return sexoTestigo;
	}
	public void setSexoTestigo(String sexo) {
		this.sexo = sexo;
	}
	*/
	//@Element	
	@SerializedName("ApellidoTestigo")
	@Column(name="APELLIDO_TESTIGO",type ="TEXT")
	private String apellidoTestigo;
	
	//@Element	
	@SerializedName("NombreTestigo")
	@Column(name="NOMBRE_TESTIGO",type ="TEXT")
	private String nombreTestigo;
 
	//@Element
	//@SerializedName("EsCallePublicaTestigo")
	@Column(name="ES_CALLE_PUBLICA_TESTIGO",type ="TEXT")
	private Boolean esCallePublicaTestigo;
	
	//@Element
	@SerializedName("CalleTestigo")
	@Column(name="CALLE_TESTIGO",type ="TEXT")
	private String calleTestigo; //
	
	//@Element
	//@SerializedName("EsAlturaSinNumeroTestigo")
	@Column(name="ES_ALTURA_SIN_NUMERO_TESTIGO",type ="TEXT")
	private Boolean esAlturaSinNumeroTestigo;
	
	//@Element
	@SerializedName("AlturaTestigo")
	@Column(name="ALTURA_TESTIGO",type ="TEXT")
	private String alturaTestigo; //
	
	//@Element
	@SerializedName("PisoTestigo")
	@Column(name="PISO_TESTIGO",type ="TEXT")
	private String pisoTestigo;
	
	//@Element
	@SerializedName("DepartamentoTestigo")
	@Column(name="DEPARTAMENTO_TESTIGO",type ="TEXT")
	private String departamentoTestigo;
	
	//@Element
	@SerializedName("BarrioTestigo")
	@Column(name="BARRIO_TESTIGO",type ="TEXT")
	private String barrioTestigo;
	
	private Pais oPaisDomicilioTestigo;

	public void setoPaisDomicilioTestigo(Pais oPaisDomicilio) {
		this.oPaisDomicilioTestigo = oPaisDomicilio;
		Boolean objetcIsNull = oPaisDomicilio == null;
		this.idPaisDomicilioTestigo= objetcIsNull==null?null: oPaisDomicilio.getId();
		this.paisDomicilioTestigo= objetcIsNull==null?this.paisDomicilioTestigo: oPaisDomicilio.getNombre();
	}

	//@Element
	//@SerializedName("IdPaisDomicilioTestigo")
	@Column(name="ID_PAIS_DOMICILIO_TESTIGO",type ="TEXT")
	private String idPaisDomicilioTestigo; // ARG  => Argentina
	
	//@Element
	@SerializedName("PaisDomicilioTestigo")
	@Column(name="PAIS_DOMICILIO_TESTIGO",type ="TEXT")
	private String paisDomicilioTestigo;
	
	private Provincia oProvinciaDomicilioTestigo;
	
	public void setoProvinciaDomicilioTestigo(Provincia oProvinciaDomicilio) {
		this.oProvinciaDomicilioTestigo = oProvinciaDomicilio;
		Boolean objetcIsNull = oProvinciaDomicilio == null;
		this.idProvinciaDomicilioTestigo = objetcIsNull==null?null: oProvinciaDomicilio.getId();
		this.provinciaDomicilioTestigo = objetcIsNull==null?this.provinciaDomicilioTestigo: oProvinciaDomicilio.getNombre();
	}

	//@Element
	//@SerializedName("IdProvinciaDomicilioTestigo")
	@Column(name="ID_PROVINCIA_DOMICILIO_TESTIGO",type ="TEXT")
	private String idProvinciaDomicilioTestigo; // X => Cordoba
	
	//@Element
	@SerializedName("ProvinciaDomicilioTestigo")
	@Column(name="PROVINCIA_DOMICILIO_TESTIGO",type ="TEXT")
	private String provinciaDomicilioTestigo;
	
	/**/
	private Departamento oDepartamentoInfraccion;
	
	public String getIdSeccional() {
		return idSeccional;
	}
	public void setIdSeccional(String idSeccional) {
		this.idSeccional = idSeccional;
	}

	public Integer getIdDepartamentoInfraccion() {
		return idDepartamentoInfraccion;
	}
	public void setIdDepartamentoInfraccion(Integer idDepartamentoInfraccion) {
		this.idDepartamentoInfraccion = idDepartamentoInfraccion;
	}
	public String getDepartamentoInfraccion() {
		return departamentoInfraccion;
	}
	public void setDepartamentoInfraccion(String departamentoInfraccion) {
		this.departamentoInfraccion = departamentoInfraccion;
	}
	public Integer getIdLocalidadInfraccion() {
		return idLocalidadInfraccion;
	}
	public void setIdLocalidadInfraccion(Integer idLocalidadInfraccion) {
		this.idLocalidadInfraccion = idLocalidadInfraccion;
	}
	public String getLocalidadInfraccion() {
		return localidadInfraccion;
	}
	public void setLocalidadInfraccion(String localidadInfraccion) {
		this.localidadInfraccion = localidadInfraccion;
	}
	
	public void setoDepartamentoInfraccion(Departamento oDepartamentoInfraccion) {
		this.oDepartamentoInfraccion = oDepartamentoInfraccion;
		Boolean objetcIsNull = oDepartamentoInfraccion == null;
		this.idDepartamentoInfraccion = objetcIsNull==null?null: oDepartamentoInfraccion.getId();
		this.departamentoInfraccion= objetcIsNull==null?this.departamentoInfraccion: oDepartamentoInfraccion.getNombre();
	}	

	//@Element
	//@SerializedName("IdDepartamentoDomicilioTestigo")
	@Column(name="ID_DEPARTAMENTO_INFRACCION",type ="TEXT")
	private Integer idDepartamentoInfraccion;

	//@Element
	@SerializedName("DepartamentoInfraccion")
	@Column(name="DEPARTAMENTO_INFRACCION",type ="TEXT")
	private String departamentoInfraccion;
	/***/

	public void setoLocalidadInfraccion(Localidad oLocalidadInfraccion) {
		this.oLocalidadInfraccion = oLocalidadInfraccion;
		Boolean objetcIsNull = oLocalidadInfraccion == null;
		this.idLocalidadInfraccion= objetcIsNull==true?null: oLocalidadInfraccion.getId();
		this.localidadInfraccion= objetcIsNull==true?this.localidadInfraccion: oLocalidadInfraccion.getNombre();
	}

	private Localidad oLocalidadInfraccion;
	
	//@Element
	//@SerializedName("IdLocalidadDomicilioTestigo")
	@Column(name="ID_LOCALIDAD_INFRACCION",type ="TEXT")
	private Integer idLocalidadInfraccion;
	
	//@Element
	@SerializedName("LocalidadInfraccion")
	@Column(name="LOCALIDAD_INFRACCION",type ="TEXT")
	private String localidadInfraccion;

	
	/**/
	private Departamento oDepartamentoDomicilioTestigo;
	
	public void setoDepartamentoDomicilioTestigo(Departamento oDepartamentoDomicilio) {
		this.oDepartamentoDomicilioTestigo = oDepartamentoDomicilio;
		Boolean objetcIsNull = oDepartamentoDomicilio == null;
		this.idDepartamentoDomicilioTestigo = objetcIsNull==null?null: oDepartamentoDomicilio.getId();
		this.departamentoDomicilioTestigo= objetcIsNull==null?this.departamentoDomicilioTestigo: oDepartamentoDomicilio.getNombre();
	}	

	
	//@Element
	//@SerializedName("IdDepartamentoDomicilioTestigo")
	@Column(name="ID_DEPARTAMENTO_DOMICILIO_TESTIGO",type ="TEXT")
	private Integer idDepartamentoDomicilioTestigo;

	//@Element
	@SerializedName("DepartamentoDomicilioTestigo")
	@Column(name="DEPARTAMENTO_DOMICILIO_TESTIGO",type ="TEXT")
	private String departamentoDomicilioTestigo;
	
	public void setoLocalidadDomicilioTestigo(Localidad oLocalidadDomicilio) {
		this.oLocalidadDomicilioTestigo = oLocalidadDomicilio;
		Boolean objetcIsNull = oLocalidadDomicilio == null;
		this.idLocalidadDomicilioTestigo= objetcIsNull==null?null: oLocalidadDomicilio.getId();
		this.localidadDomicilioTestigo= objetcIsNull==null?this.localidadDomicilioTestigo: oLocalidadDomicilio.getNombre();

	}

	private Localidad oLocalidadDomicilioTestigo;
	
	//@Element
	//@SerializedName("IdLocalidadDomicilioTestigo")
	@Column(name="ID_LOCALIDAD_DOMICILIO_TESTIGO",type ="TEXT")
	private Integer idLocalidadDomicilioTestigo;
	
	//@Element
	@SerializedName("LocalidadDomicilioTestigo")
	@Column(name="LOCALIDAD_DOMICILIO_TESTIGO",type ="TEXT")
	private String localidadDomicilioTestigo;
	
	//@Element
	@SerializedName("CodigoPostalTestigo")
	@Column(name="CODIGO_POSTAL_TESTIGO",type ="TEXT")
	private String codigoPostalTestigo;

	//@Element
	@SerializedName("ManifestacionTestigo")
	@Column(name="MANIFESTACION_TESTIGO",type ="TEXT")
	private String manifestacionTestigo;
	
	
	//@Element
	@SerializedName("esConductorTitular")
	@Column(name="ES_CONDUCTOR_TITULAR",type ="TEXT")
	private String esConductorTitular;
	
	

	//@Element
	@SerializedName("SinConductor")
	@Column(name="SIN_CONDUCTOR",type ="TEXT")
	private String sinConductor;
	
	//@Element
	@SerializedName("SinTitular")
	@Column(name="SIN_TITULAR",type ="TEXT")
	private String sinTitular;
	
	//@Element
	@SerializedName("SinTestigo")
	@Column(name="SIN_TESTIGO",type ="TEXT")
	private String sinTestigo;
	
	//@Element
	@SerializedName("EmailConductor")
	@Column(name="EMAIL_CONDUCTOR",type ="TEXT")
	private String emailConductor;
	
	//@Element
	@SerializedName("TelefonoConductor")
	@Column(name="TELEFONO_CONDUCTOR",type ="TEXT")
	private String telefonoConductor;
	
	//@Element
	@SerializedName("Referencia")
	@Column(name="REFERENCIA",type ="TEXT")
	private String referencia;
	
	//@Element
	@SerializedName("ConduccionPeligrosa")
	@Column(name="CONDUCCION_PELIGROSA",type ="TEXT")
	private String conduccionPeligrosa;

	
	//@Element
	@SerializedName("VehiculoRetenido")
	@Column(name="VEHICULO_RETENIDO",type ="TEXT")
	private String vehiculoRetenido;

	//@Element
	@SerializedName("DejaCopia")
	@Column(name="DEJA_COPIA",type ="TEXT")
	private String dejaCopia;
	

	public String getDejaCopia() {
		return dejaCopia;
	}
	public void setDejaCopia(String dejaCopia) {
		this.dejaCopia = dejaCopia;
	}
	public String getVehiculoRetenido() {
		return vehiculoRetenido;
	}
	public void setVehiculoRetenido(String vehiculoRetenido) {
		this.vehiculoRetenido = vehiculoRetenido;
	}
	public String getEmailConductor() {
		return emailConductor;
	}
	public void setEmailConductor(String emailConductor) {
		this.emailConductor = emailConductor;
	}
	public String getTelefonoConductor() {
		return telefonoConductor;
	}
	public void setTelefonoConductor(String telefonoConductor) {
		this.telefonoConductor = telefonoConductor;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getConduccionPeligrosa() {
		return conduccionPeligrosa;
	}
	public void setConduccionPeligrosa(String conduccionPeligrosa) {
		this.conduccionPeligrosa = conduccionPeligrosa;
	}

	public String getGrad_alcohol() {
		return grad_alcohol;
	}

	public void setGrad_alcohol(String grad_alcohol) {
		this.grad_alcohol = grad_alcohol;
	}

	/*
	//@Element
	@SerializedName("FechaNacimientoString")
	@Column(name="FECHA_NACIMIENTO_STRING")
	private String fechaNacimientoString;
	
	//@Element	
	@SerializedName("FechaNacimiento")
	@Column(name="FECHA_NACIMIENTO",type ="TEXT")
	private Date fechaNacimiento;
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		if (fechaNacimiento!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaNacimientoString = formatter.format(fechaNacimiento);
		}
	}
	*/
	//@Element
	//@SerializedName("FechaNacimientoTestigoString")
	@Column(name="FECHA_NACIMIENTO_TESTIGO_STRING")
	private String fechaNacimientoTestigoString;
	
	//@Element	
	@SerializedName("FechaNacimientoTestigo")
	@Column(name="FECHA_NACIMIENTO_TESTIGO",type ="TEXT")
	private Date fechaNacimientoTestigo;
	
	public Date getFechaNacimientoTestigo() {
		return fechaNacimientoTestigo;
	}

	public void setFechaNacimientoTestigo(Date fechaNacimiento) {
		this.fechaNacimientoTestigo = fechaNacimiento;
		if (fechaNacimiento!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatoFechaHora);
			this.fechaNacimientoTestigoString = formatter.format(fechaNacimientoTestigo);
		}
	}
	
	/* Fin de lo Agregado */

	/* getter and setter  Testigo */
	
	public String getTipoDocumentoTestigo() {
		return tipoDocumentoTestigo;
	}

	public void setTipoDocumentoTestigo(String tipoDocumento) {
		this.tipoDocumentoTestigo = tipoDocumento;
	}

	public Boolean getCoberturaDesdeNumeroDocumentoTestigo() {
		return coberturaDesdeNumeroDocumentoTestigo;
	}

	public void setCoberturaDesdeNumeroDocumentoTestigo(Boolean conexionAlBuscarNumeroDocumento) {
		this.coberturaDesdeNumeroDocumentoTestigo = conexionAlBuscarNumeroDocumento;
	}

	public String getNumeroDocumentoTestigo() {
		return numeroDocumentoTestigo;
	}

	public void setNumeroDocumentoTestigo(String numeroDocumento) {
		this.numeroDocumentoTestigo = numeroDocumento;
	}
    /*
	public String getIdSexoTestigo() {
		return idSexoTestigo;
	}

	public void setIdSexoTestigo(String idSexo) {
		this.idSexoTestigo = idSexo;
	}
    */
	public String getApellidoTestigo() {
		return apellidoTestigo;
	}

	public void setApellidoTestigo(String apellido) {
		this.apellidoTestigo = apellido;
	}

	public String getNombreTestigo() {
		return nombreTestigo;
	}

	public void setNombreTestigo(String nombre) {
		this.nombreTestigo = nombre;
	}

	public Boolean getEsCallePublicaTestigo() {
		return esCallePublicaTestigo;
	}

	public void setEsCallePublicaTestigo(Boolean esCallePublica) {
		this.esCallePublicaTestigo = esCallePublica;
	}

	public String getCalleTestigo() {
		return calleTestigo==null?"":calleTestigo;
	}

	public void setCalleTestigo(String calle) {
		this.calleTestigo = calle;
	}

	public Boolean getEsAlturaSinNumeroTestigo() {
		return esAlturaSinNumeroTestigo;
	}

	public void setEsAlturaSinNumeroTestigo(Boolean esAlturaSinNumero) {
		this.esAlturaSinNumeroTestigo = esAlturaSinNumero;
	}

	public String getAlturaTestigo() {
		return alturaTestigo==null?"":alturaTestigo;
	}

	public void setAlturaTestigo(String altura) {
		this.alturaTestigo = altura;
	}

	public String getPisoTestigo() {
		return pisoTestigo;
	}

	public void setPisoTestigo(String piso) {
		this.pisoTestigo = piso;
	}

	public String getDepartamentoTestigo() {
		return departamentoTestigo;
	}

	public void setDepartamentoTestigo(String departamento) {
		this.departamentoTestigo = departamento;
	}

	public String getBarrioTestigo() {
		return barrioTestigo;
	}

	public void setBarrioTestigo(String barrio) {
		this.barrioTestigo = barrio;
	}

	public String getIdPaisDomicilioTestigo() {
		return idPaisDomicilioTestigo;
	}

	public void setIdPaisDomicilioTestigo (String idPaisDomicilio) {
		this.idPaisDomicilioTestigo = idPaisDomicilio;
	}

	public String getPaisDomicilioTestigo() {
		return paisDomicilioTestigo;
	}

	public void setPaisDomicilioTestigo(String paisDomicilio) {
		this.paisDomicilioTestigo = paisDomicilio;
	}

	public String getIdProvinciaDomicilioTestigo() {
		return idProvinciaDomicilioTestigo;
	}

	public void setIdProvinciaDomicilioTestigo(String idPovinciaDomicilio) {
		this.idProvinciaDomicilioTestigo = idPovinciaDomicilio;
	}

	public String getProvinciaDomicilioTestigo() {
		return provinciaDomicilioTestigo;
	}

	public void setProvinciaDomicilioTestigo(String provinciaDomicilio) {
		this.provinciaDomicilioTestigo = provinciaDomicilio;
	}

	public Integer getIdDepartamentoDomicilioTestigo() {
		return idDepartamentoDomicilioTestigo;
	}

	public void setIdDepartamentoDomicilioTestigo(Integer idDepartamentoTestigo) {
		this.idDepartamentoDomicilioTestigo= idDepartamentoDomicilio;
	}

	public String getDepartamentoDomicilioTestigo() {
		return departamentoDomicilioTestigo;
	}

	public void setDepartamentoDomicilioTestigo(String departamentoDomicilio) {
		this.departamentoDomicilioTestigo = departamentoDomicilio;
	}

	public Integer getIdLocalidadDomicilioTestigo() {
		return idLocalidadDomicilioTestigo;
	}

	public void setIdLocalidadDomicilioTestigo(Integer idLocalidadDomicilio) {
		this.idLocalidadDomicilioTestigo = idLocalidadDomicilio;
	}

	public String getLocalidadDomicilioTestigo() {
		return localidadDomicilioTestigo;
	}

	public void setLocalidadDomicilioTestigo(String localidadDomicilio) {
		this.localidadDomicilioTestigo = localidadDomicilio;
	}

	public String getCodigoPostalTestigo() {
		return codigoPostalTestigo;
	}

	public void setCodigoPostalTestigo(String codigoPostal) {
		this.codigoPostalTestigo = codigoPostal;
	}
	
	public String getManifestacionTestigo() {
		return manifestacionTestigo;
	}

	public void setManifestacionTestigo(String manifestacionTestigo) {
		this.manifestacionTestigo = manifestacionTestigo;
	}
	
	public String getEsConductorTitular() {
		return esConductorTitular;
	}
	public void setEsConductorTitular(String esConductorTitular) {
		this.esConductorTitular = esConductorTitular;
	}
	
	public String getSinConductor() {
		return sinConductor;
	}
	public void setSinConductor(String sinConductor) {
		this.sinConductor = sinConductor;
	}
	public String getSinTitular() {
		return sinTitular;
	}
	public void setSinTitular(String sinTitular) {
		this.sinTitular = sinTitular;
	}
	public String getSinTestigo() {
		return sinTestigo;
	}
	public void setSinTestigo(String sinTestigo) {
		this.sinTestigo = sinTestigo;
	}
	
	/* Fin getter and setter Testigo */
	/* fin agregado 2017 */


	
}
