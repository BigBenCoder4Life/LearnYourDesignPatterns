package classroom;

import io.ResourceFinder;
import java.awt.*;
import java.awt.geom.*;

/**
 * A GUI component that displays the front wall of a classroom with a projector
 * screen (that can be partially lowered).
 *
 * @author Ben Kirtley
 * This work complies with the James Madison University honor code
 */
public class FrontWallWithScreen extends FrontWall
{
    
    static final private double screenWidth = 970; //The screen width
    
    static final private double x = 80; // The x-coordinate
    
    static final private double y = 0; // The y-coordinate
    
    private int amountLowered; // Amount the screen is lowered
    
    private double maxScreenHeight = 490; //The screen height
    
    protected Rectangle2D.Double screen; // The Menu screen

    /**
     * Default Constructor.
     *
     * @param finder the finder
     */
    public FrontWallWithScreen(ResourceFinder finder)
    {
        super(finder);
        screen = new Rectangle2D.Double(x, y, screenWidth, maxScreenHeight);
        setAmountLowered(0);
    }

    /**
     * Calculates the amount the screen is lowered.
     *
     * @return the double
     */
    private double calculateAmountLowered()
    {
        return maxScreenHeight * (getAmountLowered() * .01);
    }

    /**
     * Change the amount that the screen is lowered.
     *
     * @param change            The amount of the change
     */
    public void changeAmountLowered(int change)
    {
        setAmountLowered(getAmountLowered() + change);
    }

    /**
     * Clears the board.
     *
     * @param lightsOn - Lights on/off
     */
    public void clearBoard(boolean lightsOn)
    {
        super.clear(lightsOn);
    }

    /**
     * Gets the amount lowered.
     *
     * @return the amountLowered
     */
    public int getAmountLowered()
    {
        return amountLowered;
    }

    /**
     * Render the projector screen on the front wall.
     *
     * @param g - The rendering engine to use
     */
    public void render(Graphics g)
    {
        Graphics2D g2;

        g2 = (Graphics2D) g;

        //Render the background image
        super.render(g2);

        //Adjust the screen height
        screen.setRect(x, y, screenWidth,calculateAmountLowered());
        
        //Render the screen
        g2.setPaint(Color.WHITE);
        g2.fill(screen);
        g2.draw(screen);             
    }

    /**
     * Set how low the movie screen is (i.e., what percentage of the height of
     * the front wall is covered by the movie screen)
     *
     * @param amount the new amount lowered
     */
    private void setAmountLowered(int amount)
    {
        if ((amount >= 0) && (amount <= 100))
            amountLowered = amount;
    }
}
