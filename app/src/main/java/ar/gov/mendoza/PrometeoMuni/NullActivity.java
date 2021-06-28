package ar.gov.mendoza.PrometeoMuni;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import ar.gov.mendoza.PrometeoMuni.admin.views.CustomViewGroup;

public class NullActivity extends Activity {
	
	
	protected boolean backPressedToExitOnce = false;
	private Toast toast = null;
	
	
	public static final String DEBUG_TAG = "DeviceActas Activity Log";
	  // DIALOG
	static final int PASSWORD_DIALOG_ID = 0;
	
	 private PackageManager manager;
	 private WindowManager windowManager;
	  
	@Override
	public void onBackPressed() {
	    if (backPressedToExitOnce) {
	        super.onBackPressed();
	    } else {
	        this.backPressedToExitOnce = true;
	        showToast("Presione de nuevo para Salir");
	        new Handler().postDelayed(new Runnable() {

	            @Override
	            public void run() {
	                backPressedToExitOnce = false;
	            }
	        }, 500);
	    }
	}
	 
	
	/**
	 * Created to make sure that you toast doesn't show miltiple times, if user pressed back
	 * button more than once. 
	 * @param message Message to show on toast.
	 */
	protected void showToast(String message) {
	    if (this.toast == null) {
	        // Create toast if found null, it would he the case of first call only
	        this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

	    } else if (this.toast.getView() == null) {
	        // Toast not showing, so create new one
	        this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

	    } else {
	        // Updating toast message is showing
	        this.toast.setText(message);
	    }

	    // Showing toast finally
	    this.toast.show();
	}
	
	/**
	 * Kill the toast if showing. Supposed to call from onPause() of activity.
	 * So that toast also get removed as activity goes to background, to improve
	 * better app experiance for user
	 */
	protected void killToast() {
	    if (this.toast != null) {
	        this.toast.cancel();
	    }
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        
	     windowManager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

	 WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
	 localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
	 localLayoutParams.gravity = Gravity.TOP;
	 localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|

	             // this is to enable the notification to recieve touch events
	             WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

	             // Draws over status bar
	             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

	     localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
	     localLayoutParams.height = (int) (25 * getResources().getDisplayMetrics().scaledDensity);
	     localLayoutParams.format = PixelFormat.TRANSPARENT;//OPAQUE

	     CustomViewGroup view = new CustomViewGroup(this);

	     windowManager.addView(view, localLayoutParams);
	     
	     
     manager = getPackageManager();
       
     
     
    }
    
 
}