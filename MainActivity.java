package com.mycompany.myapp;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;



public class MainActivity extends Activity 
{
	ImageView iv;
	Button btn,btn2,btn3,btn4;
	Bitmap bMap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		bMap = BitmapFactory.decodeResource(getResources(), R.drawable.best);		
		
		iv = (ImageView) findViewById(R.id.Image);
		iv.setImageResource(R.drawable.best);
		
		btn = (Button) findViewById(R.id.mainButton);
		btn.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(doGreyscale(bMap));
				}
			});
			
		btn3 = (Button) findViewById(R.id.mainButton3);
		btn3.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(doInvert(bMap));
				}
			});
			
		btn2 = (Button) findViewById(R.id.mainButton2);
		btn2.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageResource(R.drawable.best);
				}
			});
		
		btn4 = (Button) findViewById(R.id.mainButton4);
		btn4.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(applySaturationFilter(bMap,3));
				}
			});
		
    }
	
	public static Bitmap applySaturationFilter(Bitmap source, int level) {
		// get image size
		int width = source.getWidth();
		int height = source.getHeight();
		int[] pixels = new int[width * height];
		float[] HSV = new float[3];
		// get pixel array from source
		source.getPixels(pixels, 0, width, 0, 0, width, height);

		int index = 0;
		// iteration through pixels
		for(int y = 0; y < height; ++y) {
			for(int x = 0; x < width; ++x) {
				// get current index in 2D-matrix
				index = y * width + x;
				// convert to HSV
				Color.colorToHSV(pixels[index], HSV);
				// increase Saturation level
				HSV[1] *= level;
				HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
				// take color back
				pixels[index] |= Color.HSVToColor(HSV);
			}
		}
		// output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
		return bmOut;
	}
	
	public static Bitmap doInvert(Bitmap src) {
		// create new bitmap with the same settings as source bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
		// color info
		int A, R, G, B;
		int pixelColor;
		// image size
		int height = src.getHeight();
		int width = src.getWidth();

		// scan through every pixel
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				// get one pixel
				pixelColor = src.getPixel(x, y);
				// saving alpha channel
				A = Color.alpha(pixelColor);
				// inverting byte for each R/G/B channel
				R = 255 - Color.red(pixelColor);
				G = 255 - Color.green(pixelColor);
				B = 255 - Color.blue(pixelColor);
				// set newly-inverted pixel to output image
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final bitmap
		return bmOut;
	}
	
	public static Bitmap doGreyscale(Bitmap src) {
		// constant factors
		final double GS_RED = 0.299;
		final double GS_GREEN = 0.587;
		final double GS_BLUE = 0.114;

		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
		// pixel information
		int A, R, G, B;
		int pixel;

		// get image size
		int width = src.getWidth();
		int height = src.getHeight();

		// scan through every single pixel
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				// get one pixel color
				pixel = src.getPixel(x, y);
				// retrieve color of all channels
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// take conversion up to one single value
				R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}
}
