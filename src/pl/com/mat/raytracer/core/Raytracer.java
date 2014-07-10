package pl.com.mat.raytracer.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import pl.com.mat.raytracer.core.camera.ICamera;
import pl.com.mat.raytracer.core.material.IMaterial;
import pl.com.mat.raytracer.core.sampling.Sampler;
import pl.com.mat.raytracer.core.utils.ColorRGB;
import pl.com.mat.raytracer.core.utils.Vector2;


public class Raytracer {
	
	public static int sampleCt = 64;
	
	private int maxDepth;
	
	public Raytracer(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public BufferedImage raytrace(World world, ICamera camera, Dimension imageSize, int imagePart, Sampler sampler) {
		BufferedImage bmp = new BufferedImage((int) imageSize.getWidth(),
				(int) imageSize.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		int xMin = 0, yMin = 0, xMax = 0, yMax = 0;
		switch (imagePart) {
		case 1 : xMin = yMin = 0; 
		         xMax = (int)imageSize.getWidth(); yMax = (int)imageSize.getHeight(); 
		         break;
		case 2 : xMin = (int)imageSize.getWidth(); yMin = 0; 
				 xMax = (int)imageSize.getWidth() * 2; yMax = (int)imageSize.getHeight(); 
				 break;
		case 3 : xMin = 0; yMin = (int)imageSize.getHeight(); 
			     xMax = (int)imageSize.getWidth(); yMax = (int)imageSize.getHeight() * 2; 
			     break;
		case 4 : xMin = (int)imageSize.getWidth(); yMin = (int)imageSize.getHeight(); 
		 	     xMax = (int)imageSize.getWidth() * 2; yMax = (int)imageSize.getHeight() * 2;
		}
		
		for (int y = yMin; y < yMax; y++) {
			for (int x = xMin; x < xMax; x++) {
				ColorRGB totalColor = ColorRGB.BLACK;
				for (int i = 0; i < sampler.getSampleCount(); i++) {
					Vector2 sample = sampler.single();
					Vector2 pictureCoordinates = new Vector2( ((x + sample.getX()) / (imageSize.getWidth() * 2)) * 2 - 1,
							((y + sample.getY()) / (imageSize.getHeight()*2)) * 2 - 1);
					Ray ray = camera.GetRayTo(pictureCoordinates);
					totalColor = totalColor.plus(shadeRay(world, ray, 0).div(sampler.getSampleCount()));
				}
				bmp.setRGB(x - xMin, y - yMin, stripColor(totalColor).getRGB());
			}
		}
		
		return bmp;
	}

	public ColorRGB shadeRay(World world, Ray ray, int currentDepth) {
		if (currentDepth > maxDepth) { return ColorRGB.BLACK; }
		
		HitInfo info = world.traceRay(ray);
		info.setDepth(currentDepth + 1);
		
		if (info.getHitObject() == null) { return world.getBackgroundColor(); }
		IMaterial material = info.getHitObject().getMaterial();
		return material.shade(this, info);
	}

	private Color stripColor(ColorRGB colorInfo) {
		colorInfo.setR(colorInfo.getR() < 0 ? 0 : colorInfo.getR() > 1 ? 1
				: colorInfo.getR());
		colorInfo.setG(colorInfo.getG() < 0 ? 0 : colorInfo.getG() > 1 ? 1
				: colorInfo.getG());
		colorInfo.setB(colorInfo.getB() < 0 ? 0 : colorInfo.getB() > 1 ? 1
				: colorInfo.getB());

		return new Color((int) (colorInfo.getR() * 255),
				(int) (colorInfo.getG() * 255), (int) (colorInfo.getB() * 255));
	}

}
