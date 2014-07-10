package pl.com.mat.raytracer.trace;

import java.util.Arrays;

import pl.com.mat.raytracer.rmi.GenericFarmer;


public final class Farmer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Type farmer handle and then workers' handles");
            return;
        }

        String farmerHandle = args[0];
        String[] workerHandles = new String[args.length - 1];
        System.arraycopy(args, 1, workerHandles, 0, workerHandles.length);

        try {
            System.out.println("- starting farmer on handle " + farmerHandle);
            System.out.println("- trying to connect to workers: " + Arrays.toString(workerHandles));
            new GenericFarmer<InputPack, InputPack, SerializedImage, SerializedImage, TraceTask>(farmerHandle, workerHandles);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
