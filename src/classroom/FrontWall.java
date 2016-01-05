/*
 * 
 */
package classroom;

import io.ResourceFinder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import visual.statik.SimpleContent;

/**
 * This class is used to display a simulated classroom.
 *
 * @author Ben Kirtley This work complies with the James Madison University
 *         honor code
 */
public class FrontWall implements SimpleContent
{
    
    protected ResourceFinder finder; // Finds file resources
    
    protected BufferedImage image; // The background image
    
    protected int imgHeight; // Height of the background image
    
    protected int imgWidth;// Width of the background image

    /**
     * Implicit Constructor.
     *
     * @param finder the finder
     */
    public FrontWall(ResourceFinder finder)
    {
        super();
        this.finder = finder;
        image = createCompatibleImage("/resources/images/frontwall.jpg");
        imgWidth = image.getWidth(null);
        imgHeight = image.getHeight(null);
    }

    /**
     * Clears the board according to light switch.
     *
     * @param lightsOn the lights on
     */
    public void clear(boolean lightsOn)
    {
        if (lightsOn)
            setImage(createCompatibleImage("/resources/images/frontwall.jpg"));
        else
            setImage(createCompatibleImage("/resources/images/frontWallLightsOff.jpg"));
    }

    /**
     * Create a compatible Image.
     *
     * @param filePath the file path
     * @return the buffered image
     */
    public BufferedImage createCompatibleImage(String filePath)
    {
        BufferedImage temp, tempImage = null;
        try
        {
            temp = ImageIO.read(finder.findInputStream(filePath));
            
            tempImage = new BufferedImage(temp.getWidth(), temp.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            
            tempImage.getGraphics().drawImage(temp, 0, 0, null);
        }
        catch (IOException e)
        {
            // Do nothing
        }

        return tempImage;
    }

    /**
     * Sets the image.
     *
     * @return image - The image
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Renders this simple content using a rendering engine.
     *
     * @param g            The rendering engine
     */
    public void render(Graphics g)
    {
        Graphics2D g2;

        g2 = (Graphics2D) g;

        g2.drawImage(image, 0, 0, null);
    }

    /**
     * Sets the image.
     *
     * @param img            The image to set
     */
    public void setImage(BufferedImage img)
    {
        image = img;
    }
}
