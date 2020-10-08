package com.uniplore.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProcessPng {
	
	public static void main(String[] args) {
		String filePath = "‪/52091_27702.png";
		String outPath = "/52091_27702new.png";
		repalceColor(filePath, outPath);
	}
	public static void repalceColor(String imagePath,String outFile) {
	       //指定某种颜色替换成另一种
	       BufferedImage image = null;
	       try {
	    	   File pngfile = new File(imagePath);
	    	   System.out.println("file path:"+pngfile.getAbsolutePath()+",filesize"+pngfile.getTotalSpace());
	    	   image = ImageIO.read(pngfile);
			} catch (Exception e) {
				e.printStackTrace();
			}

           int w = image.getWidth();
           int h = image.getHeight();
           int minx = image.getMinTileX();
           int miny = image.getMinTileY();

           for(int i = minx;i<w;i++){
               for(int j = miny;j<h;j++){
                  int rgb = image.getRGB(i,j);
                   //以黑色为例
                  int RGB = Color.BLACK.getRGB();
//
                  int r = (rgb & 0xff0000) >>16;
                  int g = (rgb & 0xff00) >> 8;
                  int b = (rgb & 0xff);
//
//	                   int R = (RGB & 0xff0000) >>16;
//	                   int G = (RGB & 0xff00) >> 8;
//	                   int B = (RGB & 0xff);
//	                  if(Math.abs(R-r)<75&&Math.abs(G-g)<75&&Math.abs(B-b)<75 ){
	                       //0xff0000是红色的十六进制代码
                   if(r == 82 && g ==45 && b == 0)
                      image.setRGB(i,j,0xff0000);
//	                   }
              }

            try {
				ImageIO.write(image,"png",new File(outFile));
			} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      }

       }

}
