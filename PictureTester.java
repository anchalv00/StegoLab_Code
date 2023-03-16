import java.awt.Color;

public class PictureTester {
    
    /**
     * Removes the blue from the picture
     * @param p the image to modify
     * @return newPic the new image with only blue
     */
    public static Picture keepOnlyBlue(Picture p){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

        for(Pixel[] row : pixels){
            for(Pixel actualPixel : row){
                actualPixel.setGreen(0);
                actualPixel.setRed(0);
            }
        }

        return newPic;
    }

    /**
     * Removes the blue from the picture
     * @param p the image to modify
     * @return newPic the new image with negated values
     */
    public static Picture negate(Picture p){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

        for(Pixel[] row : pixels){
            for(Pixel actualPixel : row){
                actualPixel.setGreen(255 - actualPixel.getGreen());
                actualPixel.setRed(255 - actualPixel.getRed());
                actualPixel.setBlue(255 - actualPixel.getBlue());
            }
        }

        return newPic;
    }

    /**
     * Removes the blue from the picture
     * @param p the image to modify
     * @return newPic the new image with grayscale values
     */
    public static Picture grayscale(Picture p){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

        for(Pixel[] row : pixels){
            for(Pixel actualPixel : row){
                actualPixel.setBlue((int) actualPixel.getAverage());
                actualPixel.setGreen((int) actualPixel.getAverage());
                actualPixel.setRed((int) actualPixel.getAverage());
            }
        }

        return newPic;
    }

    /**
     * Removes the blue from the picture
     * @param p the image to use
     * @return newPic the new image with fishes displayed in a different color
     */
    public static Picture zeroBlue(Picture p){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

            for(int i = 0; i < pixels.length; i++){
                for(int x = 0; x < pixels[0].length; x++){
                    if(pixels[i][x].getRed() >= 15 && pixels[i][x].getRed() <=25 && pixels[i][x].getGreen() >=150 && pixels[i][x].getGreen() <=170){
                        pixels[i][x].setBlue(0);
                    }
                }
            }


        return newPic;
    }

    public static void main(String[] args)
    {
        Picture beachPic = new Picture("beach.jpg");
        Picture waterPic = new Picture("water.jpg");

        /* 


        beachPic.explore();

        Picture beachPicOnlyBlue = keepOnlyBlue(beachPic);

        beachPicOnlyBlue.explore();

        Picture beachPicNegate = negate(beachPic);

        beachPicNegate.explore();

        Picture beachPicGrayscale = grayscale(beachPic);

        beachPicGrayscale.explore();

        */
        
        waterPic.explore();

        Picture waterPicZeroBlue = zeroBlue(waterPic);
        waterPicZeroBlue.explore();


    }
}
