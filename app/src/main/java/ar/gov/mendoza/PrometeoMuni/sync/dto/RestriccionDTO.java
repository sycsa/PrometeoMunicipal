package ar.gov.mendoza.PrometeoMuni.sync.dto;

import java.io.Serializable;

public class RestriccionDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2830827177814022194L;
	private String resultado;
	private String hayDatos;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getHayDatos() {
		return hayDatos;
	}
	public void setHayDatos(String hayDatos) {
		this.hayDatos = hayDatos;
	}
}