package pl.com.mat.raytracer.material;

import pl.com.mat.raytracer.HitInfo;
import pl.com.mat.raytracer.Raytracer;
import pl.com.mat.raytracer.light.Light;
import pl.com.mat.raytracer.utils.ColorRGB;
import pl.com.mat.raytracer.utils.Vector3;

public class Phong implements IMaterial {

	private ColorRGB materialColor;
	private double diffuseCoeff;
	private double specular;
	private double specularExponent;

	public Phong(ColorRGB materialColor, double diffuse, double specular, double specularExponent) {
		this.materialColor = materialColor;
		this.diffuseCoeff = diffuse;
		this.specular = specular;
		this.specularExponent = specularExponent;
	}

	@Override
	public ColorRGB shade(Raytracer tracer, HitInfo hit) {
		ColorRGB totalColor = ColorRGB.BLACK;
		
		for (Light light: hit.getWorld().getLights()) {
			Vector3 lightPos = light.Sample();
			Vector3 inDirection = lightPos.minus(hit.getHitPoint()).normalized();
			double diffuseFactor = inDirection.dot(hit.getNormal());
			if (diffuseFactor < 0) { continue; }
			if (hit.getWorld().anyObstacleBetween(hit.getHitPoint(), lightPos)) { continue; }
			
			ColorRGB result = light.getColor().mult(materialColor).mult(diffuseFactor).mult(diffuseCoeff);
			double phongFactor = phongFactor(inDirection, hit.getNormal(), hit.getRay().getDirection().reverse());
			if (phongFactor != 0) { 
				result = result.plus(materialColor.mult(specular).mult(phongFactor)); 
			}
			totalColor = totalColor.plus(result);
		}
		
		return totalColor;
	}

	private double phongFactor(Vector3 inDirection, Vector3 normal, Vector3 toCameraDirection) {
		Vector3 reflected = inDirection.reflect(normal);
		double cosAngle = reflected.dot(toCameraDirection);
		if (cosAngle <= 0) { return 0; }
		
		return Math.pow(cosAngle, specularExponent);
	}

}