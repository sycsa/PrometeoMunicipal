package ar.gov.mendoza.PrometeoMuni.core.domain;

public class AndroidWidgetControl {

	private Integer idOfControl;
	private String nameOfControl;
	private String message;

	public AndroidWidgetControl(String nombreControl, String mensajeAMostrar)
	{
		this.nameOfControl = nombreControl;
		this.message = mensajeAMostrar;
		
	}
	public Integer getIdOfControl() {
		return idOfControl;
	}
	public void setIdOfControl(Integer idOfControl) {
		this.idOfControl = idOfControl;
	}
	public String getNameOfControl() {
		return nameOfControl;
	}
	public void setNameOfControl(String nameOfControl) {
		this.nameOfControl = nameOfControl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
