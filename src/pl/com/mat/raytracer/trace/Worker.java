package pl.com.mat.raytracer.trace;

import pl.com.mat.raytracer.rmi.GenericWorker;

public final class Worker {
	
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Type worker's handle");
            return;
        }

        String workerHandle = args[0];
        int idx = Integer.parseInt(workerHandle.substring(workerHandle.length()-1, workerHandle.length()));
        
        try {
            System.out.println("Worker works on handle - " + workerHandle);
            new GenericWorker<InputPack, SerializedImage, TraceTask>(workerHandle, idx);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
