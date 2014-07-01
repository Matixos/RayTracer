package pl.com.mat.raytracer.material;

import pl.com.mat.raytracer.HitInfo;
import pl.com.mat.raytracer.Ray;
import pl.com.mat.raytracer.Raytracer;
import pl.com.mat.raytracer.utils.ColorRGB;
import pl.com.mat.raytracer.utils.Vector3;

public class Reflective implements IMaterial {
	
	private Phong direct; 				// do bezpoœredniego oœwietlenia
	private double reflectivity;
	private ColorRGB reflectionColor;
	
	public Reflective(ColorRGB materialColor, double diffuse, double specular, double exponent, 
			double reflectivity) {
		this.direct = new Phong(materialColor, diffuse, specular, exponent);
		this.reflectivity = reflectivity;
		this.reflectionColor = materialColor;
	}
	
	@Override
	public ColorRGB shade(Raytracer tracer, HitInfo hit) {
		Vector3 toCameraDirection = hit.getRay().getDirection().reverse();
		ColorRGB radiance = direct.shade(tracer, hit);
		Vector3 reflectionDirection = toCameraDirection.reflect(hit.getNormal());
		
		Ray reflectedRay = new Ray(hit.getHitPoint(), reflectionDirection);
		
		radiance =  radiance.plus(tracer.shadeRay(hit.getWorld(), reflectedRay, hit.getDepth()).mult(reflectionColor)
				.mult(reflectivity));
		
		return radiance;
		
	}

}