package pl.com.mat.raytracer.material;

import pl.com.mat.raytracer.HitInfo;
import pl.com.mat.raytracer.Raytracer;
import pl.com.mat.raytracer.light.Light;
import pl.com.mat.raytracer.utils.ColorRGB;
import pl.com.mat.raytracer.utils.Vector3;

public class PerfectDiffuse implements IMaterial {

	private ColorRGB materialColor;

	public PerfectDiffuse(ColorRGB materialColor) {
		this.materialColor = materialColor;
	}

	public ColorRGB getMaterialColor() {
		return materialColor;
	}

	public void setMaterialColor(ColorRGB materialColor) {
		this.materialColor = materialColor;
	}

	@Override
	public ColorRGB shade(Raytracer tracer, HitInfo hit) {
		ColorRGB totalColor = ColorRGB.BLACK;
		for (Light light : hit.getWorld().getLights()) {
			Vector3 lightPos = light.Sample();
			Vector3 inDirection = lightPos.minus(hit.getHitPoint()).normalized();
			double diffuseFactor = inDirection.dot(hit.getNormal());
			if (diffuseFactor < 0) { continue; }
			if (hit.getWorld().anyObstacleBetween(hit.getHitPoint(), lightPos)) { continue; }
			
			totalColor = totalColor.plus(light.getColor().mult(materialColor).mult(diffuseFactor));
		}
		return totalColor;
	}

}