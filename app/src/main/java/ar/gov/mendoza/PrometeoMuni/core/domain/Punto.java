package ar.gov.mendoza.PrometeoMuni.core.domain;

public class Punto {

	private double latitud;
	private double longitud;
	
	public Punto () { }
	
	public Punto (double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public Punto(String latLan) {
		
		String[] coordenadas = latLan
				.replace("(", "")
				.replace(")", "")
				.split(",");
		
		latitud = Double.parseDouble(coordenadas[0]);
		longitud = Double.parseDouble(coordenadas[1]);
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public String toString() {
		return "(" + latitud + "," + longitud + ")";
	}
}
