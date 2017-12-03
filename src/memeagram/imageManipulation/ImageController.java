package memeagram.imageManipulation;/*
 * Class : memeagram.Main
 * Description : Driver class to instantiate and instance of memegrame applicaton
 * Revision Date : 11/11/2017
 * Revision Number: 1
 * Authors : Team Foxtrot 
 */

import com.sun.istack.internal.NotNull;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageController{

    public static BufferedImage getImage(File f) throws IOException{
        return ImageIO.read(f);
    }
    
    public static BufferedImage addText(BufferedImage img, String text, @NotNull Boolean pos) {
        int w = img.getWidth();
    	int h = img.getHeight();

        BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics g = newImg.getGraphics();
        g.drawImage(img, 0, 0, null);
    	g.setFont(g.getFont().deriveFont(35f));

    	if(pos) {
    	  g.drawString(text, 25 , 30);
    	}else {
          g.drawString(text, 25 , h - 25);
    	}

    	g.dispose();

    	return newImg;
    }
    
    
    //Takes an image and size argument and returns resized image
    public static BufferedImage resizeImage(BufferedImage resizeMe, int maxWidth, int maxHeight) {

        int type = resizeMe.getType() ==0? BufferedImage.TYPE_INT_ARGB : resizeMe.getType();

        int fHeight = maxHeight;
        int fWidth = maxWidth;
        if(resizeMe.getHeight() > maxHeight || resizeMe.getWidth() > maxWidth){
            fHeight = maxHeight;
            float sum = (float)resizeMe.getWidth() / (float)resizeMe.getHeight();
            fWidth = Math.round(fHeight* sum);
            if(fWidth > maxWidth){
                fHeight = Math.round(maxWidth /sum);
                fWidth = maxWidth;
            }
        }
        BufferedImage resizedImage = new BufferedImage(fWidth, fHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(resizeMe, 0, 0, fWidth, fHeight, null);
        g.dispose();

      
        return resizedImage;
    }
    
    //method to get the type of file
    public String getFileType(File f){
    	String path = f.getAbsolutePath();
    	String[] s = path.split(".",2);
    	return s[1];
    }

}
