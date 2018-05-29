package java_redis.thumbnailgenerator;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

public class App {
	
	//Main Method that calls the getBase64 method to retrieve the base64 encoded image
	public static void main (String[] args) {
		 
			String base64Return = getBase64();	//Calls the helper method to return the image as a base64 code of the thumbnail
			System.out.println(base64Return);
	 }
		
	public static String getBase64() {

		BufferedImage image = null;  //Initializes several variables that are first used in try/catch statements
		File file = null;
		String imageString = null;
		
        try {  
            URL url = new URL("https://pbs.twimg.com/profile_images/844165912718598145/y4b7whP0_400x400.jpg");  
            image = ImageIO.read(url);	//BufferedImage bi = image;
            
            //Only one of the next two lines should be called, scale the image wuickly or scale it to a higher quality
            image = scaleQuick(url, 0.5);	//Calls helper method scaleQuick to shrink the image down by the given ratio as quickly as possible
            //image = scaleWell(url, 200);			//Calls the helper method scaleWell to shrink the image down by the given ration as smoothly as possible
            
            file = new File("C:\\Users\\conno\\Documents\\helloworld.jpg");	//FileWriter fw = new FileWriter(file);
            ImageIO.write(image,"jpg", file);	//fw.write(image);

        //If successful, process the message  
        } catch (IOException e) { 		//Receives an exception if the image is unable to be retreived from the URL entered
            System.err.println("Unable to retrieve Image");  
            e.printStackTrace();
        }
        
       try{
	       	FileInputStream fis = new FileInputStream(file);	//Creates a fileinputstream of the file path defined above
			byte byteArray[] = new byte[(int)file.length()];	//Creates a byte array the size of the file for the base64 code to be stored in
			fis.read(byteArray);								//Reads in the bytearray to the file
			imageString = Base64.encodeBase64String(byteArray);
			fis.close();
        }catch (IOException e) {
        	System.err.println("Unable to encode image to base64");  
            e.printStackTrace();
        }
		return imageString;
	}
	
	private static BufferedImage scaleWell(URL url, int size) {
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);	//Creates the bufferedImage shell at the size sent into the method
       
		try {
            img.createGraphics().drawImage(ImageIO.read(url).getScaledInstance(size, size, Image.SCALE_SMOOTH),0,0,null);		//Creates the bufferedimage from the sent in URL scaled to the size sent in
            
		} catch(IOException e) {	//Catches an exception if the url is unable to be converted into an image
			System.err.println("Unable to retrieve Image");  
            e.printStackTrace();
		}
        return img;
	}
	
	//These two helper methods are the quickest way to scale many images
	//Found from Stack Overflow page https://stackoverflow.com/questions/1069095/how-do-you-create-a-thumbnail-image-out-of-a-jpeg-in-java
	private static BufferedImage scaleQuick(URL url, double ratio) {
		BufferedImage source = null;
		
		try {
			source = ImageIO.read(url);

		} catch(IOException e) {
			System.err.println("Unable to retrieve Image");  
            e.printStackTrace();
		}
		
		int w = (int) (source.getWidth() * ratio);
		int h = (int) (source.getHeight() * ratio);
		BufferedImage bi = getCompatibleImage(w, h);
		Graphics2D g2d = bi.createGraphics();
		double xScale = (double) w / source.getWidth();
		double yScale = (double) h / source.getHeight();
		AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}

	private static BufferedImage getCompatibleImage(int w, int h) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}
}
