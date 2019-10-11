package com.pos.sdkdemo.print;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.basewin.log.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PicUtils {
	/***
	 * 图片的缩放方法 a method to zoom the scaling of Image
	 * 
	 * @param bgimage
	 *            ：源图片资源(the suorce of bitmap)
	 * @param newWidth
	 *            ：缩放后宽度(the with of distination bitmap)
	 * @return new bitmap
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth) { // 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		if (width > newWidth) {
			// 创建操作图片用的matrix对象
			Matrix matrix = new Matrix();
			// 计算宽高缩放率
			float scaleWidth = ((float) newWidth) / width;
			// float scaleHeight = ((float) newHeight) / height;
			// float scaleWidth = ((float) newWidth) / width;
			// float scaleHeight = height*scaleWidth;
			LogUtil.i(PicUtils.class, "缩放图片");
			LogUtil.i(PicUtils.class, "原图片width " + width);
			LogUtil.i(PicUtils.class, "原图片height " + height);
			LogUtil.i(PicUtils.class, "scaleWidth " + scaleWidth);
//			LogUtil.i(PicUtils.class, "scaleHeight " + scaleHeight);
			// 缩放图片动作
			matrix.postScale(scaleWidth, scaleWidth);
			Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
			return bitmap;
		} else {
			return bgimage;
		}
	}

	/**
	 * get the picture from bytes
	 * 
	 * @param bytes
	 * @param opts
	 * @return
	 */
	public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	/**
	 * get the bytes from InputStream
	 * 
	 * @param is
	 * @param bufsiz
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromInputStream(InputStream is, int bufsiz) throws IOException {
		int total = 0;
		byte[] bytes = new byte[4096];
		ByteBuffer bb = ByteBuffer.allocate(bufsiz);
		while (true) {
			int read = is.read(bytes);
			if (read == -1)
				break;
			bb.put(bytes, 0, read);
			total += read;
		}
		byte[] content = new byte[total];
		bb.flip();
		bb.get(content, 0, total);
		return content;
	}

	/**
	 * 将彩色图转换为灰度图
	 * 
	 * @param img
	 *            位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	public static Bitmap switchColor(Bitmap switchBitmap) {
		int width = switchBitmap.getWidth();
		int height = switchBitmap.getHeight();

		Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawBitmap(switchBitmap, new Matrix(), new Paint());

		int current_color, red, green, blue, alpha, avg = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				current_color = switchBitmap.getPixel(i, j);
				red = Color.red(current_color);
				green = Color.green(current_color);
				blue = Color.blue(current_color);
				alpha = Color.alpha(current_color);
				avg = (red + green + blue) / 3;
				if (avg >= 210) {
					newBitmap.setPixel(i, j, Color.rgb(255, 255, 255));// white
				}
//				else if (avg < 210 && avg >= 80) { // avg<126 && avg>=115
//					newBitmap.setPixel(i, j, Color.rgb( 108, 108, 108));// grey
//				}
				else {
					newBitmap.setPixel(i, j, Color.rgb( 0, 0, 0));// black
				}
			}
		}
		return newBitmap;
	}

}
