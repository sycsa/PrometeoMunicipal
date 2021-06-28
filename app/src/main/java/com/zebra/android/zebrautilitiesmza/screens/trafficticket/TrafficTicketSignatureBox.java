 package com.zebra.android.zebrautilitiesmza.screens.trafficticket;
 
 import android.content.Context;
 import android.graphics.Bitmap;
 import android.graphics.Bitmap.Config;
 import android.graphics.Canvas;
 import android.graphics.Paint;
 import android.util.AttributeSet;
 import android.view.MotionEvent;
 import android.view.SurfaceHolder;
 import android.view.SurfaceHolder.Callback;
 import android.view.SurfaceView;
 
public class TrafficTicketSignatureBox
extends SurfaceView
implements Callback
{
	   private Paint black;
	   private float lastPointX;
	   private float lastPointY;
	   private Bitmap signatureAreaContent;
	   
	   public TrafficTicketSignatureBox(Context context)
	   {
	     this(context, null);
	   }
	   
	   public TrafficTicketSignatureBox(Context context, AttributeSet attrs)
	   {
	     super(context, attrs);
				this.signatureAreaContent = null;
				SurfaceHolder holder = getHolder();
	            holder.addCallback(this);
				setFocusable(true);
				this.black = new Paint();
				this.black.setColor(-16777216);
				this.black.setStrokeWidth(3.5F);
				setWillNotDraw(false);
	   }
	   
	   public boolean onTouchEvent(MotionEvent event)
	   {
	     if (event.getAction() == 0)
	     {
	       this.lastPointX = event.getX();
	       this.lastPointY = event.getY();
	     }
	     else
	     {
	       if (event.getAction() == 2)
	       {
	         Canvas c = new Canvas(this.signatureAreaContent);
	         c.drawLine(this.lastPointX, this.lastPointY, event.getX(), event.getY(), this.black);
	         this.lastPointX = event.getX();
	         this.lastPointY = event.getY();
	         postInvalidate();
	         return true;
	       }
	       if (event.getAction() == 1)
	       {
	    	   Canvas c = new Canvas(this.signatureAreaContent);
	  	       c.drawLine(this.lastPointX, this.lastPointY, event.getX(), event.getY(), this.black);
		       postInvalidate();
		       return true;

	       }   
	     } 
	     return true;
	   }

	   public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	   {
	     Paint lightGray = new Paint();
	     lightGray.setColor(-1);
	     this.signatureAreaContent = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	     Canvas c = new Canvas(this.signatureAreaContent);
	     c.drawRect(0.0F, 0.0F, width, height, lightGray);
	     invalidate();
	   }
	   
   
	   protected void onDraw(Canvas canvas)
	   {
	     if (this.signatureAreaContent != null) {
	       canvas.drawBitmap(this.signatureAreaContent, 0.0F, 0.0F, null);
	     }
	     super.onDraw(canvas);
	   }
	   
	   public Bitmap getBitmap()
	   {
	     return this.signatureAreaContent;
	   }
	   
	   public void surfaceCreated(SurfaceHolder holder) {}
	   
	   public void surfaceDestroyed(SurfaceHolder holder) {}
	 }

