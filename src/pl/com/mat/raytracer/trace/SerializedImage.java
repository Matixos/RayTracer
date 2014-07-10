package pl.com.mat.raytracer.trace;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SerializedImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int[][] pixels;
	
	private int partNumber;
	
	public SerializedImage(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		
		pixels = new int[width][height];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixels[j][i] = img.getRGB(j, i);
			}
		}
	}
	
	public BufferedImage getImage() {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result.setRGB(j, i, pixels[j][i]);
			}
		}
		
		return result;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getPartNumber() {
		return partNumber;
	}
	
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

}