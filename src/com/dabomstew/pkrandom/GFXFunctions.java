package com.dabomstew.pkrandom;

/*----------------------------------------------------------------------------*/
/*--  GFXFunctions.java - functions relating to graphics rendering.         --*/
/*--                      Mainly used for rendering the sprites.            --*/
/*--                                                                        --*/
/*--  Part of "Universal Pokemon Randomizer" by Dabomstew                   --*/
/*--  Pokemon and any associated names and the like are                     --*/
/*--  trademark and (C) Nintendo 1996-2012.                                 --*/
/*--                                                                        --*/
/*--  The custom code written here is licensed under the terms of the GPL:  --*/
/*--                                                                        --*/
/*--  This program is free software: you can redistribute it and/or modify  --*/
/*--  it under the terms of the GNU General Public License as published by  --*/
/*--  the Free Software Foundation, either version 3 of the License, or     --*/
/*--  (at your option) any later version.                                   --*/
/*--                                                                        --*/
/*--  This program is distributed in the hope that it will be useful,       --*/
/*--  but WITHOUT ANY WARRANTY; without even the implied warranty of        --*/
/*--  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the          --*/
/*--  GNU General Public License for more details.                          --*/
/*--                                                                        --*/
/*--  You should have received a copy of the GNU General Public License     --*/
/*--  along with this program. If not, see <http://www.gnu.org/licenses/>.  --*/
/*----------------------------------------------------------------------------*/

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class GFXFunctions {

    public static BufferedImage drawTiledImage(byte[] data, int[] palette, int width, int height, int bpp) {
        return drawTiledImage(data, palette, 0, width, height, 8, 8, bpp);
    }

    public static BufferedImage drawTiledImage(byte[] data, int[] palette, int offset, int width, int height, int bpp) {
        return drawTiledImage(data, palette, offset, width, height, 8, 8, bpp);
    }

    public static BufferedImage drawTiledImage(byte[] data, int[] palette, int offset, int width, int height,
            int tileWidth, int tileHeight, int bpp) {
        if (bpp != 1 && bpp != 2 && bpp != 4 && bpp != 8) {
            throw new IllegalArgumentException("Bits per pixel must be a multiple of 2.");
        }
        int pixelsPerByte = 8 / bpp;
        if (width * height / pixelsPerByte + offset > data.length) {
            throw new IllegalArgumentException("Invalid input image.");
        }

        int bytesPerTile = tileWidth * tileHeight / pixelsPerByte;
        int numTiles = width * height / (tileWidth * tileHeight);
        int widthInTiles = width / tileWidth;

        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int tile = 0; tile < numTiles; tile++) {
            int tileX = tile % widthInTiles;
            int tileY = tile / widthInTiles;
            for (int yT = 0; yT < tileHeight; yT++) {
                for (int xT = 0; xT < tileWidth; xT++) {
                    int value = data[tile * bytesPerTile + yT * tileWidth / pixelsPerByte + xT / pixelsPerByte + offset] & 0xFF;
                    if (pixelsPerByte != 1) {
                        value = (value >>> (xT % pixelsPerByte) * bpp) & ((1 << bpp) - 1);
                    }
                    bim.setRGB(tileX * tileWidth + xT, tileY * tileHeight + yT, palette[value]);
                }
            }
        }

        return bim;
    }

    public static int conv16BitColorToARGB(int palValue) {

       // System.out.println("palValue:"+palValue);
      //  System.out.println("palValueHEX:"+Integer.toHexString(palValue));
        int red = (int) ((palValue & 0x1F) * 8);
       // System.out.println("red:"+red+"|"+Integer.toHexString(red)+"|"+(palValue & 0x1F));
        int green = (int) (((palValue & 0x3E0) >> 5) * 8);
      //  System.out.println("green:"+green+"|"+Integer.toHexString(green)+"|"+(palValue & 0x3E0));
        int blue = (int) (((palValue & 0x7C00) >> 10) *8);
      //  System.out.println("blue:"+blue+"|"+Integer.toHexString(blue)+"|"+(palValue & 0x7C00));

      /*  red=(red+green+blue)/3;
        blue=0;
        green=0;


        int RGB888 = (red << 16) | (green << 8) | blue;
        int nred=(((int)((red)/(8)) ));
        int ngreen=(((int)(green/(8)) << 5));
        int nblue=((int)(blue/(8))<<10);
        System.out.println("nred:"+(nred%31)+"| ngreen:"+(ngreen%31)+"| nblue:"+(nblue%31));
        int RGB565 = nred | ngreen | nblue;

        System.out.println("RGB888:"+RGB888+"|"+Integer.toHexString(RGB888)+"|RGB565:"+RGB565+"|"+Integer.toHexString(RGB565));
        palValue=RGB565;
        System.out.println("palValue:"+palValue);
        red = (int) ((palValue & 0x1F) * 8);
        System.out.println("red:"+red+"|"+Integer.toHexString(red));
        green = (int) (((palValue & 0x3E0) >> 5) * 8);
        System.out.println("green:"+green+"|"+Integer.toHexString(green));
        blue = (int) (((palValue & 0x7C00) >> 10) * 8);
        System.out.println("blue:"+blue+"|"+Integer.toHexString(blue));

        int offs1=(RGB565 >>8) & 0xFF;
        int offs2=(RGB565 ) & 0xFF;
        System.out.println("offs1:"+Integer.toHexString(offs1));
        System.out.println("offs2:"+Integer.toHexString(offs2));

       */
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    public static int colorRed(int palValue) {

      //  System.out.println("palValue:"+palValue);
       // System.out.println("palValueHEX:"+Integer.toHexString(palValue));
        int red = (int) ((palValue & 0x1F) * 8);
     //   System.out.println("red:"+red+"|"+Integer.toHexString(red)+"|"+(palValue & 0x1F));
        int green = (int) (((palValue & 0x3E0) >> 5) * 8);
     //   System.out.println("green:"+green+"|"+Integer.toHexString(green)+"|"+(palValue & 0x3E0));
        int blue = (int) (((palValue & 0x7C00) >> 10) *8);
      //  System.out.println("blue:"+blue+"|"+Integer.toHexString(blue)+"|"+(palValue & 0x7C00));

        red=(red+green+blue)/3;
        blue=0;
        green=0;


        int RGB888 = (red << 16) | (green << 8) | blue;
        int nred=(((int)((red)/(8)) ));
        int ngreen=(((int)(green/(8)) << 5));
        int nblue=((int)(blue/(8))<<10);
       // System.out.println("nred:"+(nred%31)+"| ngreen:"+(ngreen%31)+"| nblue:"+(nblue%31));
        int RGB565 = nred | ngreen | nblue;

       // System.out.println("RGB888:"+RGB888+"|"+Integer.toHexString(RGB888)+"|RGB565:"+RGB565+"|"+Integer.toHexString(RGB565));
        palValue=RGB565;
       // System.out.println("palValue:"+palValue);
        red = (int) ((palValue & 0x1F) * 8);
       // System.out.println("red:"+red+"|"+Integer.toHexString(red));
        green = (int) (((palValue & 0x3E0) >> 5) * 8);
       // System.out.println("green:"+green+"|"+Integer.toHexString(green));
        blue = (int) (((palValue & 0x7C00) >> 10) * 8);
     //   System.out.println("blue:"+blue+"|"+Integer.toHexString(blue));

        int offs1=(RGB565 >>8) & 0xFF;
        int offs2=(RGB565 ) & 0xFF;
     //   System.out.println("offs1:"+Integer.toHexString(offs1));
      //  System.out.println("offs2:"+Integer.toHexString(offs2));
        return RGB565;
    }
    public static int colorGreen(int palValue) {

        //  System.out.println("palValue:"+palValue);
        // System.out.println("palValueHEX:"+Integer.toHexString(palValue));
        int red = (int) ((palValue & 0x1F) * 8);
        //   System.out.println("red:"+red+"|"+Integer.toHexString(red)+"|"+(palValue & 0x1F));
        int green = (int) (((palValue & 0x3E0) >> 5) * 8);
        //   System.out.println("green:"+green+"|"+Integer.toHexString(green)+"|"+(palValue & 0x3E0));
        int blue = (int) (((palValue & 0x7C00) >> 10) *8);
        //  System.out.println("blue:"+blue+"|"+Integer.toHexString(blue)+"|"+(palValue & 0x7C00));

        red=0;
        blue=0;
        green=(red+green+blue)/3;


        int RGB888 = (red << 16) | (green << 8) | blue;
        int nred=(((int)((red)/(8)) ));
        int ngreen=(((int)(green/(8)) << 5));
        int nblue=((int)(blue/(8))<<10);
        // System.out.println("nred:"+(nred%31)+"| ngreen:"+(ngreen%31)+"| nblue:"+(nblue%31));
        int RGB565 = nred | ngreen | nblue;

        // System.out.println("RGB888:"+RGB888+"|"+Integer.toHexString(RGB888)+"|RGB565:"+RGB565+"|"+Integer.toHexString(RGB565));
        palValue=RGB565;
        // System.out.println("palValue:"+palValue);
        red = (int) ((palValue & 0x1F) * 8);
        // System.out.println("red:"+red+"|"+Integer.toHexString(red));
        green = (int) (((palValue & 0x3E0) >> 5) * 8);
        // System.out.println("green:"+green+"|"+Integer.toHexString(green));
        blue = (int) (((palValue & 0x7C00) >> 10) * 8);
        //   System.out.println("blue:"+blue+"|"+Integer.toHexString(blue));

        int offs1=(RGB565 >>8) & 0xFF;
        int offs2=(RGB565 ) & 0xFF;
        //   System.out.println("offs1:"+Integer.toHexString(offs1));
        //  System.out.println("offs2:"+Integer.toHexString(offs2));
        return RGB565;
    }

    public static int colorCyan(int palValue) {

        //  System.out.println("palValue:"+palValue);
        // System.out.println("palValueHEX:"+Integer.toHexString(palValue));
        int red = (int) ((palValue & 0x1F) * 8);
        //   System.out.println("red:"+red+"|"+Integer.toHexString(red)+"|"+(palValue & 0x1F));
        int green = (int) (((palValue & 0x3E0) >> 5) * 8);
        //   System.out.println("green:"+green+"|"+Integer.toHexString(green)+"|"+(palValue & 0x3E0));
        int blue = (int) (((palValue & 0x7C00) >> 10) *8);
        //  System.out.println("blue:"+blue+"|"+Integer.toHexString(blue)+"|"+(palValue & 0x7C00));
int avg=(red+green+blue)/3;
        red=0;
        blue=avg;
        green=avg;


        int RGB888 = (red << 16) | (green << 8) | blue;
        int nred=(((int)((red)/(8)) ));
        int ngreen=(((int)(green/(8)) << 5));
        int nblue=((int)(blue/(8))<<10);
        // System.out.println("nred:"+(nred%31)+"| ngreen:"+(ngreen%31)+"| nblue:"+(nblue%31));
        int RGB565 = nred | ngreen | nblue;

        // System.out.println("RGB888:"+RGB888+"|"+Integer.toHexString(RGB888)+"|RGB565:"+RGB565+"|"+Integer.toHexString(RGB565));
        palValue=RGB565;
        // System.out.println("palValue:"+palValue);
        red = (int) ((palValue & 0x1F) * 8);
        // System.out.println("red:"+red+"|"+Integer.toHexString(red));
        green = (int) (((palValue & 0x3E0) >> 5) * 8);
        // System.out.println("green:"+green+"|"+Integer.toHexString(green));
        blue = (int) (((palValue & 0x7C00) >> 10) * 8);
        //   System.out.println("blue:"+blue+"|"+Integer.toHexString(blue));

        int offs1=(RGB565 >>8) & 0xFF;
        int offs2=(RGB565 ) & 0xFF;
        //   System.out.println("offs1:"+Integer.toHexString(offs1));
        //  System.out.println("offs2:"+Integer.toHexString(offs2));
        return RGB565;
    }
    public static int colorBlue(int palValue) {

        //  System.out.println("palValue:"+palValue);
        // System.out.println("palValueHEX:"+Integer.toHexString(palValue));
        int red = (int) ((palValue & 0x1F) * 8);
        //   System.out.println("red:"+red+"|"+Integer.toHexString(red)+"|"+(palValue & 0x1F));
        int green = (int) (((palValue & 0x3E0) >> 5) * 8);
        //   System.out.println("green:"+green+"|"+Integer.toHexString(green)+"|"+(palValue & 0x3E0));
        int blue = (int) (((palValue & 0x7C00) >> 10) *8);
        //  System.out.println("blue:"+blue+"|"+Integer.toHexString(blue)+"|"+(palValue & 0x7C00));

        red=0;
        blue=(red+green+blue)/3;
        green=0;


        int RGB888 = (red << 16) | (green << 8) | blue;
        int nred=(((int)((red)/(8)) ));
        int ngreen=(((int)(green/(8)) << 5));
        int nblue=((int)(blue/(8))<<10);
        // System.out.println("nred:"+(nred%31)+"| ngreen:"+(ngreen%31)+"| nblue:"+(nblue%31));
        int RGB565 = nred | ngreen | nblue;

        // System.out.println("RGB888:"+RGB888+"|"+Integer.toHexString(RGB888)+"|RGB565:"+RGB565+"|"+Integer.toHexString(RGB565));
        palValue=RGB565;
        // System.out.println("palValue:"+palValue);
        red = (int) ((palValue & 0x1F) * 8);
        // System.out.println("red:"+red+"|"+Integer.toHexString(red));
        green = (int) (((palValue & 0x3E0) >> 5) * 8);
        // System.out.println("green:"+green+"|"+Integer.toHexString(green));
        blue = (int) (((palValue & 0x7C00) >> 10) * 8);
        //   System.out.println("blue:"+blue+"|"+Integer.toHexString(blue));

        int offs1=(RGB565 >>8) & 0xFF;
        int offs2=(RGB565 ) & 0xFF;
        //   System.out.println("offs1:"+Integer.toHexString(offs1));
        //  System.out.println("offs2:"+Integer.toHexString(offs2));
        return RGB565;
    }

    public static int colorOrange(int palValue) {

        //  System.out.println("palValue:"+palValue);
        // System.out.println("palValueHEX:"+Integer.toHexString(palValue));
        int red = (int) ((palValue & 0x1F) * 8);
        //   System.out.println("red:"+red+"|"+Integer.toHexString(red)+"|"+(palValue & 0x1F));
        int green = (int) (((palValue & 0x3E0) >> 5) * 8);
        //   System.out.println("green:"+green+"|"+Integer.toHexString(green)+"|"+(palValue & 0x3E0));
        int blue = (int) (((palValue & 0x7C00) >> 10) *8);
        //  System.out.println("blue:"+blue+"|"+Integer.toHexString(blue)+"|"+(palValue & 0x7C00));

        red=255;
        blue=0;
        green=(red+green+blue)/3;


        int RGB888 = (red << 16) | (green << 8) | blue;
        int nred=(((int)((red)/(8)) ));
        int ngreen=(((int)(green/(8)) << 5));
        int nblue=((int)(blue/(8))<<10);
        // System.out.println("nred:"+(nred%31)+"| ngreen:"+(ngreen%31)+"| nblue:"+(nblue%31));
        int RGB565 = nred | ngreen | nblue;

        // System.out.println("RGB888:"+RGB888+"|"+Integer.toHexString(RGB888)+"|RGB565:"+RGB565+"|"+Integer.toHexString(RGB565));
        palValue=RGB565;
        // System.out.println("palValue:"+palValue);
        red = (int) ((palValue & 0x1F) * 8);
        // System.out.println("red:"+red+"|"+Integer.toHexString(red));
        green = (int) (((palValue & 0x3E0) >> 5) * 8);
        // System.out.println("green:"+green+"|"+Integer.toHexString(green));
        blue = (int) (((palValue & 0x7C00) >> 10) * 8);
        //   System.out.println("blue:"+blue+"|"+Integer.toHexString(blue));

        int offs1=(RGB565 >>8) & 0xFF;
        int offs2=(RGB565 ) & 0xFF;
        //   System.out.println("offs1:"+Integer.toHexString(offs1));
        //  System.out.println("offs2:"+Integer.toHexString(offs2));
        return RGB565;
    }

    public static void pseudoTransparency(BufferedImage img, int transColor) {
        int width = img.getWidth();
        int height = img.getHeight();
        Queue<Integer> visitPixels = new LinkedList<Integer>();
        boolean[][] queued = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            queuePixel(x, 0, width, height, visitPixels, queued);
            queuePixel(x, height - 1, width, height, visitPixels, queued);
        }

        for (int y = 0; y < height; y++) {
            queuePixel(0, y, width, height, visitPixels, queued);
            queuePixel(width - 1, y, width, height, visitPixels, queued);
        }

        while (!visitPixels.isEmpty()) {
            int nextPixel = visitPixels.poll();
            int x = nextPixel % width;
            int y = nextPixel / width;
            if (img.getRGB(x, y) == transColor) {
                img.setRGB(x, y, 0);
                queuePixel(x - 1, y, width, height, visitPixels, queued);
                queuePixel(x + 1, y, width, height, visitPixels, queued);
                queuePixel(x, y - 1, width, height, visitPixels, queued);
                queuePixel(x, y + 1, width, height, visitPixels, queued);
            }
        }
    }

    private static void queuePixel(int x, int y, int width, int height, Queue<Integer> queue, boolean[][] queued) {
        if (x >= 0 && x < width && y >= 0 && y < height && !queued[x][y]) {
            queue.add((y) * width + (x));
            queued[x][y] = true;
        }
    }

}
