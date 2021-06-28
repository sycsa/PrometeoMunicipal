 package com.zebra.android.zebrautilitiesmza.util;
 
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.BitmapFactory.Options;
 import java.io.FileInputStream;
 import java.io.IOException;
 
 public class ImageHelper
 {
		   public static Bitmap getBitmap(String imagePath)
		   throws IOException
		   {
		     if (imagePath == null) {
		       throw new IOException("Could not load image");
		     }
		     
		     Options sizeOps = new Options();
		     sizeOps.inJustDecodeBounds = true;
		     BitmapFactory.decodeFile(imagePath, sizeOps);
		     long imageSize = sizeOps.outHeight * sizeOps.outWidth;

		     Options options = new Options();
		     if (imageSize > 128000000L) {
		       options.inSampleSize = 32;
		     }
		     else if (imageSize > 64000000L) 
		     {
	           options.inSampleSize = 16;
	         }
	         else if (imageSize > 32000000L) 
	         {
		       options.inSampleSize = 8;
	         }
	         else if (imageSize > 16000000L) 
	         {
	           options.inSampleSize = 4;
	         }
	         else if (imageSize > 8000000L) 
	         {
	           options.inSampleSize = 2;
	         }
	         else 
	         {
	           options.inSampleSize = 1;
	         }
		     //options.inSampleSize = 4;
		     
		       FileInputStream fis = new FileInputStream(imagePath);
		       Bitmap myBitmap = BitmapFactory.decodeStream(fis, null, options);
		       fis.close();
			   return myBitmap;
		   }
 }