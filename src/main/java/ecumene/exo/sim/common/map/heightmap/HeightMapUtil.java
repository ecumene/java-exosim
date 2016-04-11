package ecumene.exo.sim.common.map.heightmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public final strictfp class HeightMapUtil {
	private HeightMapUtil() {
	}

	public final static float modulo(float x, float n) {
		return x - n*(float)Math.floor(x/n);
	}

	public final static int modulo(int x, int n) {
		return (int)(x - n*(float)Math.floor((float)x/n));
	}

	public final static float interpolateLinear(float v1, float v2, float fraction) {
		return v1*(1f - fraction) + v2*fraction;
	}

	public final static float interpolateSmooth(float v1, float v2, float fraction) {
		if (fraction < 0.5f) {
			fraction = 2f*fraction*fraction;
		} else {
			fraction = 1f - 2f*(fraction - 1f)*(fraction - 1f);
		}
		return v1*(1f - fraction) + v2*fraction;
	}

	public final static float interpolateSmooth2(float v1, float v2, float fraction) {
		float fraction2 = fraction*fraction;
		fraction = 3*fraction2 - 2f*fraction*fraction2;
		return v1*(1f - fraction) + v2*fraction;
	}

	public final static float interpolateCubic(float v0, float v1, float v2, float v3, float fraction) {
		float p = (v3 - v2) - (v0 - v1);
		float q = (v0 - v1) - p;
		float r = v2 - v0;
		float fraction2 = fraction*fraction;
		return p*fraction*fraction2 + q*fraction2 + r*fraction + v1;
	}

	public final static float rampLinear(float start, float end, float segment_length, float x) {
		x = modulo(x, segment_length);
		if (x < start) return 0f;
		if (x > end) return 1f;
		return interpolateLinear(0f, 1f, (x - start)/(end - start));
	}

	public final static float step(float start, float end, float segment_length, float x) {
		x = modulo(x, segment_length);
		if (x < start || x > end) return 0f;
		return 1f;
	}

	public final static float sawtooth(float x) {
		x++; // hack!
		float x_frac = x - (int)x;
		if (x_frac < 0.5) {
			return 2*x_frac;
		} else {
			return -2*x_frac + 2;
		}
	}

	public final static float gaussify(float x) {
		if (x >=0 && x < 0.5) {
			return 0.5f*(float)Math.sqrt(2*x);
		}
		if (x >=0.5f && x <= 1f) {
			return 1f - 0.5f*(float)Math.sqrt(-2*x + 2);
		}
		return 0;
	}

	public final static float gaussify(float x, float exponent) {
		if (x >=0 && x < 0.5) {
			return 0.5f*(float)Math.pow(2*x, exponent);
		}
		if (x >=0.5f && x <= 1f) {
			return 1f - 0.5f*(float)Math.pow(-2*x + 2, exponent);
		}
		return 0;
	}

	public final static float gain(float gain, float x) {
		if (x < 0.5f)
			return (float)(Math.pow(2 * x, Math.log(1 - gain)/Math.log(0.5d))/2f);
		else
			return 1f - (float)(Math.pow(2 - 2 * x, Math.log(1 - gain)/Math.log(0.5d))/2f);
	}

	public static void saveAsTGA(File file, ByteBuffer pixel_data, int width, int height) {
		long before = System.currentTimeMillis();
		try {
			FileOutputStream fout = new FileOutputStream(file);

			//write TGA header
			fout.write(0); //ID length, 0 because no image id field
			fout.write(0); //no color map
			fout.write(2); //image type (24 bit RGB, uncompressed)
			fout.write(0); //color map origin, ignore because no color map
			fout.write(0); //color map origin, ignore because no color map
			fout.write(0); //color map origin, ignore because no color map
			fout.write(0); //color map length, ignore because no color map
			fout.write(0); //color map entry size, ignore because no color map
			fout.write(0); //x origin
			fout.write(0); //x origin
			fout.write(0); //x origin
			fout.write(0); //y origin
			short s = (short)width;
			fout.write((byte)(s & 0x00ff));	  //image width low byte
			fout.write((byte)((s & 0xff00)>>8)); //image width high byte
			s = (short)height;
			fout.write((byte)(s & 0x00ff));	  //image height low byte
			fout.write((byte)((s & 0xff00)>>8)); //image height high byte
			fout.write(32); //bpp
			fout.write(0); //description bits

			pixel_data.rewind();
			//write TGA image data
			fout.getChannel().write(pixel_data);

			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// int = 0xAARRGGBB A=alpha R=red G=green B=blue
	public final static float intToRed(int color) {
		int red = (color >> 16) & 0xff;
		return ((float)red)/255;
	}

	public final static float intToGreen(int color) {
		int green = (color >> 8) & 0xff;
		return ((float)green)/255;
	}

	public final static float intToBlue(int color) {
		int blue = color & 0xff;
		return ((float)blue)/255;
	}

	public final static boolean isPowerOf2(int n) {
		return n == 0 || (n > 0 && (n & (n - 1)) == 0);
	}

	public final static int nextPowerOf2(int n) {
		int x = 1;
		while (x < n) {
			x <<= 1;
		}
		return x;
	}

	public final static void flip(ByteBuffer bytes, int width, int height) {
		byte[] line = new byte[width];
		byte[] line2 = new byte[width];

		for (int i = 0; i < height/2; i++) {
			bytes.position(i*width);
			bytes.get(line);
			bytes.position((height - i - 1)*width);
			bytes.get(line2);
			bytes.position(i*width);
			bytes.put(line2);
			bytes.position((height - i - 1)*width);
			bytes.put(line);
		}
	}

	public static void saveAsBMP(File file, ByteBuffer pixel_data, int width, int height) {
		long before = System.currentTimeMillis();
		int pad = 4 - (width*3)%4;
		if (pad == 4)
			pad = 0;
		int size = (width*3 + pad)*height + 54;
		ByteBuffer buffer = ByteBuffer.allocate(size);

		//write BMP header
		buffer.put((byte)0x42);							 // signature, must be 4D42 hex
		buffer.put((byte)0x4D);							 // ...
		buffer.put((byte)(size & 0x000000ff));		// size of BMP file in bytes
		buffer.put((byte)((size & 0x0000ff00)>>8));   // ...
		buffer.put((byte)((size & 0x00ff0000)>>16));  // ...
		buffer.put((byte)((size & 0xff000000)>>24));  // ...
		buffer.put((byte)0);								// reserved, must be zero
		buffer.put((byte)0);								// reserved, must be zero
		buffer.put((byte)0);								// reserved, must be zero
		buffer.put((byte)0);								// reserved, must be zero
		buffer.put((byte)54);							   // offset to start of image data in bytes
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)40);							   // size of BITMAPINFOHEADER structure, must be 40
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)(width & 0x000000ff));	   // image width in pixels
		buffer.put((byte)((width & 0x0000ff00)>>8));  // ...
		buffer.put((byte)((width & 0x00ff0000)>>16)); // ...
		buffer.put((byte)((width & 0xff000000)>>24)); // ...
		buffer.put((byte)(height & 0x000000ff));	  // image width in pixels
		buffer.put((byte)((height & 0x0000ff00)>>8)); // ...
		buffer.put((byte)((height & 0x00ff0000)>>16));// ...
		buffer.put((byte)((height & 0xff000000)>>24));// ...
		buffer.put((byte)1);								// number of planes in the image, must be 1
		buffer.put((byte)0);								// ...
		buffer.put((byte)24);		   // number of bits per pixel (1, 4, 8, or 24)
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// compression type (0=none, 1=RLE-8, 2=RLE-4)
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)((size - 54) & 0x000000ff));		// size of image data in bytes (including padding)
		buffer.put((byte)(((size - 54) & 0x0000ff00)>>8));   // ...
		buffer.put((byte)(((size - 54) & 0x00ff0000)>>16));  // ...
		buffer.put((byte)(((size - 54) & 0xff000000)>>24));  // ...
		buffer.put((byte)0);								// horizontal resolution in pixels per meter (unreliable)
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// vertical resolution in pixels per meter (unreliable)
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// number of colors in image, or zero
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// number of important colors, or zero
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...
		buffer.put((byte)0);								// ...

		pixel_data.rewind();
		IntBuffer int_pixel_data = pixel_data.asIntBuffer();
		//write BMP image data
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				int pixel = int_pixel_data.get(y*width + x);
				byte r = (byte)((pixel >> 24) & 0xff);
				byte g = (byte)((pixel >> 16) & 0xff);
				byte b = (byte)((pixel >> 8) & 0xff);
				buffer.put(b);
				buffer.put(g);
				buffer.put(r);
			}
			for (int i = 0; i < pad; i++) {
				buffer.put((byte)0);
			}

		}
		buffer.rewind();
		File image_file = file;
		try {
			FileOutputStream fout = new FileOutputStream(image_file);
			fout.write(buffer.array());
			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static int powerOf2Log2(int n) {
		assert isPowerOf2(n): n + " is not a power of 2";
		for (int i = 0; i < 31; i++) {
			if ((n & 1) == 1) {
				return i;
			}
			n >>= 1;
		}
		return 0;
	}

}
