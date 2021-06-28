package ar.gov.mendoza.PrometeoMuni.screens.actas.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
public class VerificacionActa extends VerificacionActasGenerica {

	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    	//this.LABEL ="Numero Documento";
	    	this.TIPO_OBJETO = ActaConstatacionSync.ACTA_CONSTATACION;
	        return super.onCreateView(inflater, container, savedInstanceState);
	    }
	
}
