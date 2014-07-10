package pl.com.mat.raytracer.core.material;

import java.io.Serializable;

import pl.com.mat.raytracer.core.HitInfo;
import pl.com.mat.raytracer.core.Raytracer;
import pl.com.mat.raytracer.core.utils.ColorRGB;


public interface IMaterial extends Serializable {

	ColorRGB shade(Raytracer tracer, HitInfo hit);
	
}