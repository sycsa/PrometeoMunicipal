package ar.gov.mendoza.PrometeoMuni.screens.qrcode;

import java.util.Date;

//import org.simpleframework.xml.Element;
//import org.simpleframework.xml.Root;

import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
	 //@Root
	 public class CodigoQR
	    {
		 	//@Element
	        public String CodigoSeguridadLicencia ;
		 	//@Element
	        public Date FechaEmision ;
		 	//@Element
	        public Date FechaVencimiento ;
		 	//@Element
	        public String IdClaseLicencia ;
	        //public String ClaseLicencia ;
	        //private String _primeraLicencia;
		 	//@Element
	        public String PrimeraLicencia ;
	        //{
	        //    get
	        //    {
	        //        if (_primeraLicencia.Equals("S"))
	        //            return "PRIMERA LICENCIA";
	        //        else
	        //            return "REVALIDACION";
	        //    }

	        //    set
	        //    {
	        //        _primeraLicencia = value;
	        //    }
	        //}
		 	//@Element
	        public String IdPaisLicencia ;
		 	//@Element
	        public String IdProvinciaLicencia ;
		 	//@Element
	        public String IdDepartamentoLicencia ;
		 	//@Element
	        public String IdLocalidadLicencia ;

		 	//@Element
	        public String Numero_Identificacion_Municipio ;
	        //public String Municipalidad ;
		 	//@Element
	        public String IdPaisNacionalidad ;
	        //public String PaisNacionalidad ;
		 	//@Element
	        public String NumeroDocumento ;
		 	//@Element
	        public String TipoDocumento ;
		 	//@Element
	        public String IdSexo ;
		 	//@Element		 	
	        public Date FechaNacimiento ;
		 	//@Element
	        public String Apellido ;
		 	//@Element		 	
	        public String Nombre ;
		 	//@Element
	        public String Calle ;       //DOMICILIO
		 	//@Element
	        public String NumeroCalle ; // ALTURA
		 	//@Element
	        public String Piso ;
		 	//@Element
	        public String DepartamentoEdificio ;
		 	//@Element
	        public String CodigoPostal ;
		 	//@Element
	        public String Barrio ;
		 	//@Element
	        public String IdPaisConductor ;
		 	//@Element
	        public String IdProvinciaConductor ;
		 	//@Element
	        public String IdDepartamentoConductor ;
		 	//@Element
	        public String IdLocalidadConductor ;
		 	//@Element
	        public String Restricciones ;
		 	//@Element
	        public String Alergias ;
		 	//@Element
	        public String MedicacionPermanente ;
		 	//@Element
	        public String DonanteOrganos ;
		 	//@Element
	        public String GrupoSanguineo ;
		 	//@Element
	        public String TelefonoEmergencia ;


	        public CodigoQR(String strContenidoCodigo)
	        {
	            this.CodigoSeguridadLicencia = getValorPosicion(strContenidoCodigo,0, 10);

	            Date dtTemp = getFechaPosicion(strContenidoCodigo, 10, 8);
	            this.FechaEmision = dtTemp;//new Date(dtTemp.Year,dtTemp.Month,dtTemp.Day);

	            dtTemp = getFechaPosicion(strContenidoCodigo, 18, 8);
	            this.FechaVencimiento = dtTemp;//new Date(dtTemp.Year, dtTemp.Month, dtTemp.Day);

	            this.IdClaseLicencia = getValorPosicion(strContenidoCodigo, 26, 3);
	            this.IdPaisLicencia = getValorPosicion(strContenidoCodigo, 29, 3);
	            this.IdProvinciaLicencia = getValorPosicion(strContenidoCodigo, 32, 2);
	            this.IdDepartamentoLicencia = getValorPosicion(strContenidoCodigo, 34, 6);
	            this.IdLocalidadLicencia = getValorPosicion(strContenidoCodigo, 40, 6);
	            this.Numero_Identificacion_Municipio = getValorPosicion(strContenidoCodigo, 46, 4);
	            this.IdPaisNacionalidad = getValorPosicion(strContenidoCodigo, 50, 6);
	            this.NumeroDocumento = getValorPosicion(strContenidoCodigo, 56, 12);
	            this.TipoDocumento = getValorPosicion(strContenidoCodigo, 68, 4);
	            this.IdSexo = getValorPosicion(strContenidoCodigo, 72, 1);
	            if (this.IdSexo.equals("M"))
	                this.IdSexo = "01";
	            else
	            { 
	                if (this.IdSexo.equals("F"))
	                    this.IdSexo = "02";
	            }

	            dtTemp = getFechaPosicion(strContenidoCodigo, 73, 8);
	            this.FechaNacimiento = dtTemp;//new Date(dtTemp.Year,dtTemp.Month,dtTemp.Day);


	            this.Apellido = getValorPosicion(strContenidoCodigo, 81, 25);
	            this.Nombre = getValorPosicion(strContenidoCodigo, 106, 30);
	            this.Calle = getValorPosicion(strContenidoCodigo, 136, 30);
	            this.NumeroCalle = getValorPosicion(strContenidoCodigo, 166, 4);
	            this.Piso= getValorPosicion(strContenidoCodigo, 170, 2);
	            this.DepartamentoEdificio = getValorPosicion(strContenidoCodigo, 172, 2);
	            this.CodigoPostal = getValorPosicion(strContenidoCodigo, 174, 10);
	            this.Barrio = getValorPosicion(strContenidoCodigo, 184, 20);
	            this.IdPaisConductor = getValorPosicion(strContenidoCodigo, 204, 3);
	            this.IdProvinciaConductor = getValorPosicion(strContenidoCodigo, 207, 2);
	            this.IdDepartamentoConductor = getValorPosicion(strContenidoCodigo, 209, 6);
	            this.IdLocalidadConductor = getValorPosicion(strContenidoCodigo, 215, 6);
	            this.Restricciones = getValorPosicion(strContenidoCodigo, 221, 10);
	            this.Alergias= getValorPosicion(strContenidoCodigo, 231, 1);
	            this.MedicacionPermanente = getValorPosicion(strContenidoCodigo, 232, 1);
	            this.DonanteOrganos = getValorPosicion(strContenidoCodigo, 233, 1);
	            this.GrupoSanguineo = getValorPosicion(strContenidoCodigo, 234, 1);
	            this.TelefonoEmergencia = getValorPosicion(strContenidoCodigo, 235, 10);


	        }
	        private Date getFechaPosicion(String strContenido, int intInicio, int intLongitud)
	        {
	            String dateString = getValorPosicion(strContenido, intInicio, intLongitud);

	            Date yourDate = Tools.ConvertStringToDate(dateString, "yyyyMMdd");//Date.ParseExact(dateString, "yyyyMMdd", CultureInfo.InvariantCulture);
	            return yourDate;
	        }

	        private String getValorPosicion(String strContenido, int intInicio, int intLongitud)
	        { 
	            String resultado ="";
	            try
	            {
	                resultado = strContenido.substring(intInicio,intInicio + intLongitud);
	            }
	            catch(Exception ex)
	            {
	                resultado = "";
	            }
	            return resultado.trim();
	        }
	    
}
