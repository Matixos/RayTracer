package pl.com.mat.raytracer.sampling;

import pl.com.mat.raytracer.utils.Vector2;

public class SquareDistributor implements ISampleDistributor {
	
	@Override
	public Vector2 mapSample(Vector2 sample) { 
		return sample; 
	}

}