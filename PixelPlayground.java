import java.awt.Color;

public class PixelPlayground {
    
    /**
     * Removes the blue from the picture
     * @param p the image to use
     * @return newPic the new image with no blue
     */
    public static Picture zeroBlue(Picture p){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

        for(int i = 0; i < pixels.length; i++){
            for(int x = 0; x < pixels[0].length; x++){
                pixels[i][x].setBlue(0);
            }
        }

        return newPic;
    }







    public static void main(String[] args)
    {
        Picture beachPic = new Picture("beach.jpg");

        beachPic.explore();

        Picture beachPicNoBlue = zeroBlue(beachPic);

        beachPicNoBlue.explore();
    }
}
