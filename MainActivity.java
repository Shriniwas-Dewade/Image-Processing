package com.mycompany.myapp;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;



public class MainActivity extends Activity 
{
	ImageView iv;
	Button btn,btn2,btn3,btn4,btn5,btn6;
	Bitmap bMap,b1,b2,b3,b4,b5;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		getch();
		
		iv = (ImageView) findViewById(R.id.Image);
		iv.setImageResource(R.drawable.best);
		
		btn = (Button) findViewById(R.id.mainButton);
		btn.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(b1);
				}
			});
			
		btn3 = (Button) findViewById(R.id.mainButton3);
		btn3.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(b2);
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
					iv.setImageBitmap(b3);
				}
			});
		
		btn5 = (Button) findViewById(R.id.mainButton5);
		btn5.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(b4);
				}
			});
			
		btn6 = (Button) findViewById(R.id.mainButton6);
		btn6.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					iv.setImageBitmap(b5);
				}
			});
			
		Toast.makeText(this,"Wait Few Seconds",Toast.LENGTH_LONG).show();
		
    }

	@Override
	public void onBackPressed()
	{
		finish();
	}
	
	
	
	public void getch(){
		load l = new load();
		load2 l2 = new load2();
		l.start();
		l2.start();
	}
	
	class load extends Thread
	{

		@Override
		public void run()
		{
			try{
				bMap = BitmapFactory.decodeResource(getResources(), R.drawable.best);		
				b1 = doGreyscale(bMap);
				b2 = doInvert(bMap);
			} catch(Exception e){

			}
		}

	}

	class load2 extends Thread
	{

		@Override
		public void run()
		{
			try{
				bMap = BitmapFactory.decodeResource(getResources(), R.drawable.best);
				b3 = applySaturationFilter(bMap,3);
				b4 = highlight(bMap,50);
				b5 = Constrast(bMap,50);
			} catch(Exception e){

			}
		}

	}
	
	public static Bitmap Constrast(Bitmap src,double v){
		
		int w = src.getWidth();
		int h = src.getHeight();
		
		Bitmap out = Bitmap.createBitmap(w,h,src.getConfig());
		
		int A,R,G,B;
		int pixel;
		
		double c = Math.pow((100 + v)/100,2);
		
		for(int x=0;x<w;x++){
			for(int y=0;y<h;y++){
				
				pixel = src.getPixel(x,y);
				
				A = Color.alpha(pixel);
				
				R = Color.red(pixel);
				R = (int) (((((R / 255.0) - 0.5) * c )+ 0.5) * 255.0);
				if(R < 0) { R = 0; }
				else if(R > 255) { R = 255; }

				G = Color.red(pixel);
				G = (int)(((((G / 255.0) - 0.5) * c) + 0.5) * 255.0);
				if(G < 0) { G = 0; }
				else if(G > 255) { G = 255; }

				B = Color.red(pixel);
				B = (int)(((((B / 255.0) - 0.5) * c) + 0.5) * 255.0);
				if(B < 0) { B = 0; }
				else if(B > 255) { B = 255; }
				
				out.setPixel(x,y,Color.argb(A,R,G,B));
			}
		}
		
		return out;
	}
	
	public static Bitmap highlight(Bitmap src,int v){
		
		int w = src.getWidth();
		int h = src.getHeight();
		
		Bitmap out = Bitmap.createBitmap(w,h,src.getConfig());
		
		int r,g,b,a;
		int pixel;
		
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				
				pixel = src.getPixel(i,j);
				
				r = Color.red(pixel);
				g = Color.green(pixel);
				b = Color.blue(pixel);
				a = Color.alpha(pixel);
				
				r += v;
				if(r>255){r = 255;}
				else if(r<0){r=0;}
				
				g+= v;
				if(g>255){g = 255;}
				else if(g<0){g=0;}
				
				b += v;
				if(b>255){b = 255;}
				else if(b<0){b=0;}
		
				out.setPixel(i,j,Color.argb(a,r,g,b));
			}
		}
		
		return out;
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
