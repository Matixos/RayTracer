package pl.com.mat.raytracer.sampling;

import java.util.Random;

import pl.com.mat.raytracer.utils.Vector2;

public class NRooks implements ISampleGenerator {

	private Random r;
	
	public NRooks(int seed) {
		this.r = new Random(seed);
	}
	
	@Override
	public Vector2[] sample(int sampleCt) {
		Vector2[] samples = new Vector2[sampleCt];
		for (int i = 0; i < sampleCt; i++) {
			samples[i] = new Vector2(
			(i + r.nextDouble()) / sampleCt,
			(i + r.nextDouble()) / sampleCt);
		}
		shuffleX(samples, sampleCt);
		
		return samples;
	}
		
	private void shuffleX(Vector2[] samples, int sampleCt) {
		for (int i = 0; i < sampleCt - 1; i++) {
			int target = r.nextInt() % sampleCt;
			double temp = samples[i].getX();
			samples[i].setX(samples[target].getX());
			samples[target].setX(temp);
		}
	}
	
}