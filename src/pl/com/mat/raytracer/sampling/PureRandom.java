package pl.com.mat.raytracer.sampling;

import java.util.Random;

import pl.com.mat.raytracer.utils.Vector2;

public class PureRandom implements ISampleGenerator {
	
	private Random r;
	
	public PureRandom(int seed) {
		this.r = new Random(seed);
	}
	
	@Override
	public Vector2[] sample(int sampleCt) {
		Vector2[] samples = new Vector2[sampleCt];
		for (int i = 0; i < sampleCt; i++) {
			samples[i] = new Vector2(r.nextDouble(), r.nextDouble());
		}
		
		return samples;
	}

}