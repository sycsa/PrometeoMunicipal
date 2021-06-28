package ar.gov.mendoza.PrometeoMuni.validators;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;


public class KmValidator  extends TextValidator implements OnFocusChangeListener {

   HandlerValidatorInterface myHandler;
   public void setHandlerListener(HandlerValidatorInterface listener){
      myHandler=listener;
   }
	public KmValidator(TextView textView) {
		super(textView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(TextView textView) {//, String text
		Boolean validado = false;
		String text = textView.getText().toString().trim();
		if (text.equals(""))
		{
			textView.setError("El campo (Km/Altura) es un valor requerido");
			validado=false;
		}
		else
			validado=true;
		
		if(myHandler!=null)
		   myHandler.onValidate(validado);
		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(!hasFocus)
		{
			validate((TextView)v);
			
		}
	}

}
