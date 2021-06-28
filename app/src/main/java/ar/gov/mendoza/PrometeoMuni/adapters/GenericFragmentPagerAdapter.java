package ar.gov.mendoza.PrometeoMuni.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments.VerificacionAutomotor;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments.VerificacionMotoVehiculo;
import ar.gov.mendoza.PrometeoMuni.screens.verificaciones.fragments.VerificacionPersona;

public class GenericFragmentPagerAdapter  extends FragmentPagerAdapter {

	 final int PAGE_COUNT =3;
	
	public GenericFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		switch(arg0){
		 
        /** tab1 is selected */
        case 0:
            VerificacionPersona verificacionPersona = new VerificacionPersona();
            return verificacionPersona;

        /** tab2 is selected */
        case 1:
            VerificacionAutomotor verificacionAutomotor = new VerificacionAutomotor();
            return verificacionAutomotor;
            
       /** tab3 is selected */
        case 2:
            VerificacionMotoVehiculo verificacionMotoVehiculo = new VerificacionMotoVehiculo();
            return verificacionMotoVehiculo; 
    }
    return null;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

}
