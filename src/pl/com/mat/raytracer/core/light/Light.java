package pl.com.mat.raytracer.core.light;

import java.io.Serializable;

import pl.com.mat.raytracer.core.sampling.Sampler;
import pl.com.mat.raytracer.core.utils.ColorRGB;
import pl.com.mat.raytracer.core.utils.Vector2;
import pl.com.mat.raytracer.core.utils.Vector3;


public class Light implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Vector3 center;
	private double radius;
	private Sampler sampler;
	private ColorRGB color;
	
	public Light(Vector3 position, ColorRGB color) {       // swiatlo punktowe
		this(position, color, null, 0);
	}
	
	
	public Light(Vector3 center, ColorRGB color, Sampler sampler, double radius) {     // pe³ny konstruktor
		this.center = center;
		this.radius = radius;
		this.sampler = sampler;
		this.color = color;
	}
	
	public Vector3 Sample() {
		if (radius == 0) { return center; } 	// symulowanie œwiat³a punktowego
		Vector2 sample = sampler.single();
		return center.plus(remapSampleToUnitSphere(sample).mult(radius));
	}
	
	private Vector3 remapSampleToUnitSphere(Vector2 sample) {
		double z = 2 * sample.getX() - 1;
		double t = 2 * Math.PI * sample.getY();
		double r = Math.sqrt(1 - z * z);
		return new Vector3(r * Math.cos(t), r * Math.sin(t), z);
	}
	
	public ColorRGB getColor() {
		return this.color;
	}

}