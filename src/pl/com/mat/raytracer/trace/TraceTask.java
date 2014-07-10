package pl.com.mat.raytracer.trace;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import pl.com.mat.raytracer.core.Raytracer;
import pl.com.mat.raytracer.core.sampling.Regular;
import pl.com.mat.raytracer.core.sampling.Sampler;
import pl.com.mat.raytracer.core.sampling.SquareDistributor;
import pl.com.mat.raytracer.rmi.ITask;


public final class TraceTask implements ITask<InputPack, InputPack, SerializedImage, SerializedImage> {

	private static final long serialVersionUID = 1L;

	@Override
    public ArrayList<InputPack> split(InputPack input) {
		ArrayList<InputPack> list = new ArrayList<InputPack>();
		
		for (int i = 0; i < 4; i++)
			list.add(input);
		
        return list;
    }

    @Override
    public SerializedImage compute(InputPack subInput, int workerIndex) {
    	Raytracer tracer = new Raytracer(5);
    	Sampler antiAlias = new Sampler(new Regular(), new SquareDistributor(), Raytracer.sampleCt, 1);
		BufferedImage image = tracer.raytrace(subInput.getWorld(), subInput.getCamera(), new Dimension(subInput.getDims(), 
				subInput.getDims()), 
				workerIndex, antiAlias);
		
		SerializedImage img = new SerializedImage(image);
		img.setPartNumber(workerIndex);
    	
        return img;
    }

    @Override
    public SerializedImage combine(ArrayList<SerializedImage> subOutputs) {
    	int dims = subOutputs.get(0).getWidth();
    	BufferedImage img = new BufferedImage(dims*2, dims*2, BufferedImage.TYPE_INT_RGB);
    	
    	ArrayList<BufferedImage> parts = new ArrayList<BufferedImage>();
    	for(int i = 0; i < 4; i++)
    		parts.add(null);
    	
    	for (SerializedImage subOutput: subOutputs) {
    		parts.set(subOutput.getPartNumber()-1, subOutput.getImage());
    	}
    	
    	Graphics2D graph = img.createGraphics();
    	
    	graph.drawImage(parts.get(0), 0, 0, null);
    	graph.drawImage(parts.get(1), dims, 0, null);
    	graph.drawImage(parts.get(2), 0, dims, null);
    	graph.drawImage(parts.get(3), dims, dims, null);

        return new SerializedImage(img);
    }

    @Override
    public boolean isComplete(ArrayList<SerializedImage> subOutputs) {
        return subOutputs.size() == 4;
    }
}
