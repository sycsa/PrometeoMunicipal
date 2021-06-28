package ar.gov.mendoza.PrometeoMuni.sync.dto;

import java.io.Serializable;

public class ActualizacionDTO implements Serializable
{
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4409876308137844830L;
	
	private String id;
	private String tipo;
	private String comando;
		
	

}