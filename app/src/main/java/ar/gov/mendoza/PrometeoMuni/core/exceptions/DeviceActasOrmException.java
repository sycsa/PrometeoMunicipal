package ar.gov.mendoza.PrometeoMuni.core.exceptions;

public class DeviceActasOrmException extends Exception {

	private static final long serialVersionUID = 1L;
	private DeviceActasOrmExceptionType type;
	
	/*
	 * Tipo de Ecepcion dependiendo del Metodo Ejecutado 
	 */
	public enum DeviceActasOrmExceptionType {
		GET_ALL,
		GET_BY_ID,
		INSERT,
		UPDATE,
		DELETE,
		CREATE_DATABASE,
		UPDATE_DATABASE
	}
	
	public DeviceActasOrmException(String message) {
		super(message);
	}
	
	public DeviceActasOrmException(DeviceActasOrmExceptionType type) {
		this.type = type;
	}
	
	public DeviceActasOrmExceptionType getType() {
		return this.type;
	}
}
