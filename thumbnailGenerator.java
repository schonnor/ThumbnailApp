package thumbnail;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class thumbnailGenerator {
	
    public static void main (String[] args){
		
		String base64Return = getBase64();	//Calls the helper method to return the image as a base64 code of the thumbnail
	}
	
	public static String getBase64() {
		
		URL url = new URL("https://wallpapercave.com/wp/7Helq65.jpg");	//
		Image image = ImageIO.read(url);
		Image thumbnail = image.getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);	//Scales the image into a 100x100 pixel version of the original image
		
		File file = new File("C:\Program Files\Image-Line\FL Studio 12.1\Data\Projects");
		ImageIO.write(thumbnail, "jpg", file);
		
		byte[] imageBytes = IOUtils.toByteArray(thumbnail);
		String base64 = Base64.getEncoder().encodeToString(imageBytes);
	}

}