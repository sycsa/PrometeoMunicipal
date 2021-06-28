package ar.gov.mendoza.PrometeoMuni.core.domain;

public interface ISeleccionable <T>{
	T getItemId();
	String getItemName();
	void setItemId(T item);
	void setItemName(String item);
	
	String getItemDescription();
}

