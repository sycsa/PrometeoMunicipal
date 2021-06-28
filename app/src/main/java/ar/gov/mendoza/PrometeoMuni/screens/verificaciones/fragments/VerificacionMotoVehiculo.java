package ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VerificacionMotoVehiculo extends VerificacionGenerica{

	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	//this.LABEL ="Patente";
    	this.TIPO_OBJETO="MOTOVEHICULOS";
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
