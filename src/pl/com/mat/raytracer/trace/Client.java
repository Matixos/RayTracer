package pl.com.mat.raytracer.trace;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.Naming;

import javax.imageio.ImageIO;

import pl.com.mat.raytracer.core.light.Light;
import pl.com.mat.raytracer.core.utils.ColorRGB;
import pl.com.mat.raytracer.core.utils.Vector3;
import pl.com.mat.raytracer.rmi.IFarmer;
import pl.com.mat.raytracer.utils.FileReader;


public final class Client {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error! Type argument - input file");
            return;
        }

        String inputFile = args[0];
        String farmerHandle = args[1];

        InputPack input = null;
        try {
            input = FileReader.readFile(inputFile);
            System.out.println("Data loaded from " + inputFile);
        } catch (Exception e) {
            System.out.println("Error Loading");
            e.printStackTrace();
            return;
        }
        
        input.getWorld().addLight(new Light(new Vector3(0, 5, -5), ColorRGB.YELLOW));

        IFarmer<InputPack, SerializedImage, TraceTask> farmer;
        try {
            System.out.println("- looking up farmer at " + farmerHandle);
            farmer = (IFarmer<InputPack, SerializedImage, TraceTask>) Naming.lookup(farmerHandle);
        } catch (Exception e) {
            System.out.println("Farmer lookup failed!");
            e.printStackTrace();
            return;
        }

        BufferedImage image = null;
        try {
            image = farmer.compute(new TraceTask(), input).getImage();
        } catch (Exception e) {
            System.out.println("Remote Computation Failed!");
            e.printStackTrace();
            return;
        }
        
        File outputFile = new File("raytraced.png");
		try {
			ImageIO.write(image, "PNG", outputFile);
		} catch (IOException e) {}

        System.out.println(" ====== DONE ====== ");
    }
}

