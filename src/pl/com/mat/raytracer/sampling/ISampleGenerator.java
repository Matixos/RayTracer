package pl.com.mat.raytracer.sampling;

import pl.com.mat.raytracer.utils.Vector2;

public interface ISampleGenerator {
	
	Vector2[] sample(int count);

}