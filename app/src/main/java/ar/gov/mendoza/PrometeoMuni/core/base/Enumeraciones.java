package ar.gov.mendoza.PrometeoMuni.core.base;

public class Enumeraciones 
{
	public enum TipoNovedadEnum {
		NOVEDAD_DEFICIENCIA,
	    NOVEDAD_DEFECTO,
	    RECLAMO_DEFICIENCIA,
	    RECLAMO_DEFECTO
	}
	
	 public enum TipoCoberturaEnum
	    {
	        PUNTO,
	        AREA,
	        RUTA
	    }
	 
	public enum EstadoNovedadEnum {
		ABIERTA,
        CUMPLIDO_SIN_VALIDAR,
        NO_CUMPLIDO_SIN_VALIDAR,
        NO_CUMPLIDO_EN_PROCESO_LEGAL,
        NO_CUMPLIDO_VALIDADO,
        CUMPLIDO_VALIDADO
	}
	
	public enum TipoControlEnum {
		CPPS,
		CDPS,
		CE
	}
	
	   public enum EstadoNovedadMovilEnum
	    {
	        NUEVA,
	        EXISTENTE,
	        MODIFICADA,
	        CARGADA
	    }
}
