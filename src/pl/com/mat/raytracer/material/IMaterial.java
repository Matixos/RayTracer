package pl.com.mat.raytracer.material;

import pl.com.mat.raytracer.HitInfo;
import pl.com.mat.raytracer.Raytracer;
import pl.com.mat.raytracer.utils.ColorRGB;

public interface IMaterial {

	ColorRGB shade(Raytracer tracer, HitInfo hit);
	
}