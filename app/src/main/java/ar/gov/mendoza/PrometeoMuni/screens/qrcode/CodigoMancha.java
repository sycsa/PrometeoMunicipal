package ar.gov.mendoza.PrometeoMuni.screens.qrcode;

import java.util.Date;

//import org.simpleframework.xml.Element;
//import org.simpleframework.xml.Root;

import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
	 //@Root
	 public class CodigoMancha
	    {
		 	//preguntar por este valor.. porque no esta en el codigo
		 	//@Element
	        public String CodigoSeguridadLicencia ;

		 	//@Element
	        public Date FechaNacimiento;

		 	//@Element
	        public Date FechaEmision ;
		 	
		 	//@Element
	        public Date FechaVencimiento ;

		 	//@Element
	        public String IdClaseLicencia ;


		 	//@Element
	        public String PaisNacionalidad ;

		 	//@Element
	        public String NumeroDocumento ;
		 	//@Element
	        public String TipoDocumento ;
		 	
		 	//@Element
	        public String IdSexo ;
		 	
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


	        public CodigoMancha(String strContenidoCodigo)
	        {
	        	try
	        	{
		        	String[] datos = strContenidoCodigo.split("\\|");
		        	if (datos!=null && datos.length>0)
		        	{
		        		this.TipoDocumento = datos[0].trim();
		        		this.NumeroDocumento = datos[1].trim();
		        		this.CodigoSeguridadLicencia = datos[1].trim();
		        		this.IdSexo= datos[2].trim();
		        		this.Nombre=datos[3].trim();
		        		this.Apellido=datos[4].trim();
		        		//this.FechaNacimiento=datos[5];
		        		this.PaisNacionalidad=datos[6].trim();
		        		this.Calle =datos[7].trim();
		        		this.NumeroCalle = datos[8].trim();
		        		
		        		String piso = datos[9].trim();
		        		if (piso!=null)
		        			piso = piso.replace("Piso:", "");
		        		
		        		this.Piso =piso;
		        		
		        		String departamentoEdificio = datos[10].trim();
		        		if(departamentoEdificio!=null)
		        			departamentoEdificio= departamentoEdificio.replace("Depto:", "");
		        		
		        		this.DepartamentoEdificio = departamentoEdificio ;
		        		
		        		String barrio = datos[11].trim();
		        		if (barrio !=null)
		        			barrio = barrio.replace("Barrio:", "");
		        		
		        		this.Barrio = barrio;
		        		//this.DepartamentoNacionalidad =datos[12];
		        		this.CodigoPostal =datos[13].trim();
		        		//this.FechaEmision =datos[14];
		        		try
		        		{
		        			Date fechaVto = Tools.ConvertStringToDate(datos[15].trim(), "dd/MM/yyyy");//Date.ParseExact(dateString, "yyyyMMdd", CultureInfo.InvariantCulture);
		        			this.FechaVencimiento = fechaVto;// convertir a fecha
		        		}catch(Exception ex){
		        			
		        		}
		        		this.IdClaseLicencia =datos[16];	
		        		
		        	}		
	        	}catch(Exception ex){
	        		
	        	}     

	        }
	    
}
