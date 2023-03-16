import java.awt.Color;
import java.util.ArrayList;
import java.awt.Point;

public class Steganography {

        /** 
    * Clear the lower (rightmost) two bits in a pixel.
    */ 
    public static void clearLow(Pixel p) 
    {
        p.setRed((p.getRed()/4) *4);
        p.setGreen((p.getGreen()/4) *4);
        p.setBlue((p.getBlue()/4) *4);
    }

    /** 
    * returns a picture with the lower (rightmost) two bits in a pixel cleared.
    */ 
    public static Picture testClearLow(Picture p){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

        for(int i = 0; i < pixels.length; i++){
            for(int x = 0; x < pixels[0].length; x++){
                clearLow(pixels[i][x]);
            }
        }

     return newPic;
    }


    /** 
    * Set the lower 2 bits in a pixel to the highest 2 bits in c 
    */
    public static void setLow (Pixel p, Color c) 
    {
        clearLow(p);
        //Color newColor = new Color(c.getRed()/64, c.getGreen()/64, c.getBlue()/64);

        p.setRed(p.getRed() + (c.getRed()/64));
        p.setGreen(p.getGreen() + (c.getGreen()/64));
        p.setBlue(p.getBlue() + (c.getBlue()/64));
        
    }

    public static Picture testSetLow(Picture p, Color c){
        Picture newPic = new Picture(p);
        Pixel[][] pixels = newPic.getPixels2D();

        for(int i = 0; i < pixels.length; i++){
            for(int x = 0; x < pixels[0].length; x++){
                setLow(pixels[i][x], c);
            }
        }

     return newPic;
    }

        /** 
    * Sets the highest two bits of each pixel's colors to the lowest two bits of each pixel's colors 
    */ 
    public static Picture revealPicture(Picture hidden) 
    { 
        Picture copy = new Picture(hidden); 
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D(); 
        for (int r = 0; r < pixels.length - 1; r++)
        { 
            for (int c = 0; c < pixels[0].length - 1; c++)
            { 	
                Color col = source[r][c].getColor();

                pixels[r][c].setRed(pixels[r][c].getRed()%4 *64);
                pixels[r][c].setGreen(pixels[r][c].getGreen()%4 *64);
                pixels[r][c].setBlue(pixels[r][c].getBlue()%4 *64);
            }
        }
        return copy; 
    }

    /** 
    * Determines whether secret can be hidden in source, which is true if source and secret are the same dimensions. 
    * @param source is not null 
    * @param secret is not null 
    * @return true if secret can be hidden in source, false otherwise. 
    */

    public static boolean canHide(Picture source, Picture secret){
        if( source.getHeight() <= secret.getHeight() && source.getWidth() <= secret.getWidth()){
            return true;
        }
        return false;
    }

    /** 
    * Creates a new Picture with data from secret hidden in data from source * @param source is not null 
    * @param secret is not null 
    * @return combined Picture with secret hidden in source 
    * precondition: source is same width and height as secret 
    */
    public static Picture hidePicture(Picture source, Picture secret)
    {
        Picture copy = new Picture(source); 
        copy.setTitle("hidePicture");
        Pixel[][] sourceArray = copy.getPixels2D();
        Pixel[][] secretArray = secret.getPixels2D();
        for (int r = 0; r < sourceArray.length; r++)
        { 
            for (int c = 0; c < sourceArray[0].length; c++)
            { 	
                setLow(sourceArray[r][c], (secretArray[r][c].getColor()));
            }
        }
        return copy;
    }

    /** 
    * Creates a new Picture with data from secret hidden in data from source * @param source is not null 
    * @param secret is not null 
    * @return combined Picture with secret hidden in source 
    * precondition: source is same width and height as secret 
    */
    public static Picture hidePicture(Picture source, Picture secret, int startRow, int startCol)
    {
        Picture copy = new Picture(source); 
        copy.setTitle("hidePicture");
        Pixel[][] sourceArray = copy.getPixels2D();
        Pixel[][] secretArray = secret.getPixels2D();
        //if(canHide(source, secret) && startCol + secretArray.length < sourceArray.length && startRow + secretArray[0].length < sourceArray[0].length){

            for (int r = startRow, r2 = 0; r2 <secretArray.length; r++, r2++)
            { 
                for (int c = startCol, c2 = 0; c2 < secretArray[0].length; c++, c2++)
                { 	
                    setLow(sourceArray[r][c], (secretArray[r2][c2].getColor()));
                }
            }
        //}
       
        return copy;
    }

    public static boolean isSame(Picture one, Picture two)
    {
        Pixel[][] oneArray = one.getPixels2D();
        Pixel[][] twoArray = two.getPixels2D();
        for (int r = 0; r < oneArray.length; r++)
        { 
            for (int c = 0; c < oneArray[0].length; c++)
            { 	
                if(!oneArray[r][c].getColor().equals(twoArray[r][c].getColor()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<Point> findDifferences(Picture one, Picture two){
        Pixel[][] oneArray = one.getPixels2D();
        Pixel[][] twoArray = two.getPixels2D();
        ArrayList<Point> newArr = new ArrayList<Point>();

        if(one.getHeight() == two.getHeight() && one.getWidth() == two.getWidth()){

            for (int r = 0; r < oneArray.length; r++)
             { 
                for (int c = 0; c < oneArray[0].length; c++)
                { 	
                    if(!oneArray[r][c].getColor().equals(twoArray[r][c].getColor()))
                    {
                        newArr.add(new Point(r, c));
                    }
                }
            }
        }
        return newArr;
    }


    public static Picture showDifferentArea(Picture pic, ArrayList<Point> arr){
        Picture copy = new Picture(pic); 
        copy.setTitle("draws rectangle");
        Pixel[][] copyArray = copy.getPixels2D();
        double maxX = arr.get(0).getX();
        double maxY = arr.get(0).getY();
        double minX = arr.get(0).getX();
        double minY = arr.get(0).getY();

        for (int r = 1; r < arr.size(); r++)
        { 
            if(arr.get(r).getX() > maxX){
                maxX = arr.get(r).getX();
            }
            if(arr.get(r).getY() > maxY){
                maxY = arr.get(r).getY();
            }
            if(arr.get(r).getX() < minX){
                minX = arr.get(r).getX();
            }
            if(arr.get(r).getY() < minY){
                minY = arr.get(r).getY();
            }
        }

        double x = maxY;
        int i = 0;

        while(x >= minY){
            copyArray[(int) maxX][(int) maxY - i].setColor(new Color(100, 0 ,0));
            i++;
            x--;
        }

        x = maxX;
        i = 0;

        while(x >= minX){
            copyArray[(int) maxX - i][(int) maxY].setColor(new Color(100, 0 ,0));
            i++;
            x--;
        }
        
        x = minY;
        i = 0;

        while(x <= maxY){
            copyArray[(int) minX][(int) minY + i].setColor(new Color(100, 0 ,0));
            i++;
            x++;
        }

        x = minX;
        i = 0;

         while(x <= maxX){
            copyArray[(int) minX + i][(int) minY].setColor(new Color(100, 0 ,0));
            i++;
            x++;
        }
        return copy;
    }

    public static void main(String[] args){
        /*
        Picture beach = new Picture("beach.jpg"); 
        beach.explore(); 
        Picture copy = testClearLow(beach);
        copy.explore();
        Picture copy2 = testSetLow(beach, Color.PINK); 
        copy2.explore(); 
        Picture copy3 = revealPicture(copy2);
        copy3.explore(); 
        
        Picture beach2 = new Picture("beach.jpg"); 
        Picture motorcycle = new Picture("redMotorcycle.jpg");

        if(canHide(beach2,motorcycle) == true)
        {
            Picture copy4 = hidePicture(beach2,motorcycle);
            copy4.setTitle("motorcycle hidden in beach");
            copy4.explore(); 
            

            Picture copy5 = revealPicture(beach2);
            copy4.setTitle("motorcycle revealed from beach");
            copy5.explore(); 
        }
        

        Picture beach = new Picture("beach.jpg"); 
        Picture robot = new Picture("robot.jpg"); 
        Picture flower1 = new Picture("flower1.jpg");
        //beach.explore(); 

        // these lines hide 2 pictures
        Picture hidden1 = hidePicture(beach, robot, 65, 208);
        Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
        hidden2.explore(); 
        Picture unhidden = revealPicture(hidden2);
        unhidden.explore();

        

        Picture swan = new Picture("swan.jpg"); 
        Picture swan2 = new Picture("swan.jpg");

        System.out.println("swan and swan2 are the same: " + isSame(swan, swan2));

        swan = testClearLow(swan);
        System.out.println("swan and swan2 are the same (after clearLow run on swan): "+ isSame(swan, swan2)); 

        

        Picture arch = new Picture("arch.jpg");
        Picture arch2 = new Picture("arch.jpg");
        Picture koala = new Picture("koala.jpg");
        Picture robot1 = new Picture("robot.jpg");

        ArrayList<Point> pointList = findDifferences(arch, arch2); 
        System.out.println("PointList after comparing two identical pictures has a size of " + pointList.size());

        pointList = findDifferences(arch, koala); 
        System.out.println("PointList after comparing two different sized pictures has a size of " + pointList.size());

        Picture arch3 = hidePicture(arch, robot1, 65, 102);
        pointList = findDifferences (arch, arch3);
        System.out.println("Pointlist after hiding a picture has a size of " + pointList.size());

        arch.show();
        arch3.show(); 
        */

        Picture hall = new Picture("femaleLionAndHall.jpg");
        Picture robot2 = new Picture("robot.jpg");
        Picture flower2 = new Picture("flower1.jpg"); 

        // hide pictures
        Picture hall2 = hidePicture(hall, robot2, 50, 300);
        Picture hall3 = hidePicture(hall2, flower2, 115, 275);
        hall3.explore();

        if(!isSame (hall, hall3))
        {
            Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
            hall4.show();
            Picture unhiddenHall3 = revealPicture(hall3);
            unhiddenHall3.show();
        }

    }
}
