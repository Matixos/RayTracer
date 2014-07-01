package pl.com.mat.raytracer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import pl.com.mat.raytracer.camera.ICamera;
import pl.com.mat.raytracer.material.IMaterial;
import pl.com.mat.raytracer.sampling.Regular;
import pl.com.mat.raytracer.sampling.Sampler;
import pl.com.mat.raytracer.sampling.SquareDistributor;
import pl.com.mat.raytracer.utils.ColorRGB;
import pl.com.mat.raytracer.utils.Vector2;

public class Raytracer {
	
	private int maxDepth;
	
	public Raytracer(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	public BufferedImage raytrace(World world, ICamera camera, Dimension imageSize) {
		Sampler singleSample = new Sampler(new Regular(), new SquareDistributor(), 1, 1);
		return this.raytrace(world, camera, imageSize, singleSample);
	}

	public BufferedImage raytrace(World world, ICamera camera, Dimension imageSize, Sampler sampler) {
		BufferedImage bmp = new BufferedImage((int) imageSize.getWidth(),
				(int) imageSize.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < imageSize.getHeight(); y++) {
			for (int x = 0; x < imageSize.getWidth(); x++) {
				ColorRGB totalColor = ColorRGB.BLACK;
				for (int i = 0; i < sampler.getSampleCount(); i++) {
					Vector2 sample = sampler.single();
					Vector2 pictureCoordinates = new Vector2( ((x + sample.getX()) / imageSize.getWidth()) * 2 - 1,
							((y + sample.getY()) / imageSize.getHeight()) * 2 - 1);
					Ray ray = camera.GetRayTo(pictureCoordinates);
					totalColor = totalColor.plus(shadeRay(world, ray, 0).div(sampler.getSampleCount()));
				}
				bmp.setRGB(x, y, stripColor(totalColor).getRGB());
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
