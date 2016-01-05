package classroom;

import io.ResourceFinder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import classroom.rescaleOp.RescaleOp;

/**
 * This class is used to display a simulated classroom with on/off light
 * functionality and a projection screen that can be partially lowered.
 *
 * @author Ben Kirtley This work complies with the James Madison University
 *         honor code
 */
public class FrontWallWithScreenAndLights extends FrontWallWithScreen
{
    
    private BufferedImage lightsOffImage; // Darkened image of parent class
    
    protected boolean lightsOn = true;// Boolean for if lights are on/off
    
    private BufferedImage lightsOnImage; // Original image of parent class

    /**
     * Implicit Constructor.
     *
     * @param finder the finder
     */
    public FrontWallWithScreenAndLights(ResourceFinder finder)
    {
        super(finder);
        this.lightsOnImage = image;
        this.lightsOffImage = createBackground();
    }

    /**
     * Clears the board.
     */
    public void clear()
    {
        super.clear(lightsOn);
    }

    /**
     * This method creates a image that is weighted which simulates the rooms
     * lights being turned off/on.
     *
     * @return reference The darkened image
     */
    private BufferedImage createBackground()
    {
        RescaleOp op; // Rescales pixel-by-pixel

        // Create a rescaleOp
        op = new RescaleOp();

        return op.filter(image, null, lightsOn);
    }
    
    /**
     * If lights are on/off.
     *
     * @return lightOn If lights are on
     */
    public boolean isLightsOn()
    {
        return lightsOn;
    }

    /**
     * Render the background image on this component.
     * 
     * @param g
     *          The rendering engine to use
     */
    public void render(Graphics g)
    {
        Graphics2D g2;

        g2 = (Graphics2D) g;

        // Render the new image for the background
        super.render(g2);      
    }
  
    /**
     * Toggles the lights on or off.
     */
    public void toggleLights()
    {
        if (lightsOn)
            turnLightsOff();
        else
            turnLightsOn();
    }

    /**
     * Turn the lights off.
     */
    public void turnLightsOff()
    {
        lightsOn = false;

        //Update lights on image
        lightsOnImage = getImage();
        
        //Update lights off image
        this.lightsOffImage = createBackground();
        
        // Set the parent class image
        setImage(lightsOffImage);      
    }

    /**
     * Turn the lights on.
     */
    public void turnLightsOn()
    {
        lightsOn = true;

       //Update lights on image
        this.lightsOnImage = createBackground();

        // Set the parent class image
        setImage(lightsOnImage);
    }
}
