package Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/***************************************************************************
 *   MIT License
 *   
 *   Copyright (c) 2021 Sadig Akhund
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 *
 * 
 **************************************************************************/

public class ImageParser {
    
    public static final String JAVA_ICON = "java.png";
    public static final String GOOGLE_ICON = "google.png";
    public static final String IBM_ICON = "ibm.png";
    public static final String DOGE_ICON = "doge.png";
    public static final String PHUT_HON_ICON = "phao.png";

    
    public static String[] parseImagePattern(String imagePath) {
	BufferedImage image = null;
	try {
	    image = ImageIO.read(new File("./res/pixel_art/" + imagePath));
	} catch (IOException e) {
	}
	
	
	int width = image.getWidth();
	int height = image.getHeight();
	String [] stra = new String[height];
	for(int i = 0; i < height; i++) {
	    stra[i] = "";
	    for(int j = 0; j < width; j++) {
	            if( (image.getRGB(j, i)>>24) == 0x00 ) {
	        	stra[i] += ".";
	            }else
	        	stra[i]+= "O";
		}
	}
	return stra;
	

    }

}
