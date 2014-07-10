package pl.com.mat.raytracer.core.sampling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.com.mat.raytracer.core.utils.Vector2;


public class Sampler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Random r;
	private List<Vector2[]> sets;
	private int sampleNdx;
	private int setNdx;
	
	private int sampleCount;
	
	public Sampler(ISampleGenerator sampler, ISampleDistributor mapper, int sampleCt, int setCt) {
		this.sets = new ArrayList<Vector2[]>(setCt);
		this.r = new Random(0);
		this.sampleCount = sampleCt;
		for (int i = 0; i < setCt; i++) {
			Vector2[] samples = sampler.sample(sampleCt);
			Vector2[] mappedSamples = new Vector2[samples.length];
			
			for (int j = 0; j < samples.length; j++) {
				mappedSamples[j] = mapper.mapSample(samples[j]);
			}
			sets.add(mappedSamples);
		}
	}
	
	public Vector2 single() {
		Vector2 sample = sets.get(setNdx)[sampleNdx];
		
		sampleNdx++;
		if (sampleNdx >= sets.get(setNdx).length) { 
			sampleNdx = 0; 
			setNdx = r.nextInt(sets.size()); 
		}
		return sample;
	}

	public int getSampleCount() {
		return sampleCount;
	}
	
	public int getSetCount() {
		return sets.size();
	}

}