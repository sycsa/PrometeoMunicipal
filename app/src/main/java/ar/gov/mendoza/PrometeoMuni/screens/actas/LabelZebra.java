package ar.gov.mendoza.PrometeoMuni.screens.actas;

public class LabelZebra {

    	
    	public String AnchoPagina;
    	public Integer LargoPagina;
    	public Integer PosicionX;
    	public Integer PosicionY;
    	public LabelZebra(Integer largoPagina)
    	{ 
    		this.AnchoPagina = "800";
    		this.PosicionX =0;
    		this.PosicionY =0;
    		this.LargoPagina=largoPagina;
    	}

    	public Integer AddY(Integer valor)
    	{
    		this.PosicionY +=valor;
    		return this.PosicionY;
    	}
    	public Integer AddX(Integer valor)
    	{
    		this.PosicionX +=valor;
    		return this.PosicionX;
    	}
    	
    	


}
