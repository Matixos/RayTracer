package pl.com.mat.raytracer.core.sampling;

import pl.com.mat.raytracer.core.utils.Vector2;

public interface ISampleGenerator {
	
	Vector2[] sample(int count);

}