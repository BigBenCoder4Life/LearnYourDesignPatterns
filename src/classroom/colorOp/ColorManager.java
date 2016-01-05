package classroom.colorOp;

import java.awt.Color;
import java.util.ListIterator;
import java.util.Vector;

/**
 * This class is used for managing colors in an drawing application
 * 
 * @author Ben Kirtley This work complies with the James Madison University
 *         honor code
 */
public class ColorManager
{
    Vector<Color> list; // List the holds the colors
    private Color color; // Primary color being used
    private int index = 1; // index of color

    /**
     * Implicit Constructor
     */
    public ColorManager()
    {
        this(0, 0, 0);
    }

    /**
     * Explicit Constructor
     * 
     * @param red
     *            - The red frequency
     * @param green
     *            - The green frequency
     * @param blue
     *            - The blue frequency
     */
    public ColorManager(int red, int green, int blue)
    {
        color = new Color(red, green, blue);
        list = new Vector<Color>();
        add(color);
        load();
    }

    /**
     * Load colors into the manager
     */
    private void load()
    {
        add(Color.BLUE);
        add(Color.CYAN);
        add(new Color(69, 0, 132));
        add(new Color(255, 140, 0));
        add(new Color(33, 127, 244));
        add(new Color(203, 33, 157));
        add(new Color(0, 100, 0));        
        add(new Color(255, 20, 147));
        add(new Color(222, 127, 80));
        add(new Color(0, 0, 139));
    }

    /**
     * Add a color to the manager
     * 
     * @param color
     *            - The color object
     */
    public void add(Color color)
    {
        list.add(color);
    }

    /**
     * Remove a color from the manager
     * @param color
     *            - The color object
     */
    public void remove(Color color)
    {
        list.remove(color);
    }

    /**
     * Change the color
     */
    public void change()
    {
        if (list.size() != 0)
        {
            color = list.get(index % list.size());
        }

        index++;
    }

    /**
     * Get the current color
     * 
     * @return color The color
     */
    public Color get()
    {
        return color;
    }

    /**
     * Dims the current color by half
     */
    public void dim()
    {
        int red, green, blue; // Color frequency

        red   = color.getRed() / 2;
        green = color.getGreen() / 2;
        blue  = color.getBlue() / 2;

        color = new Color(red, green, blue);
    }

    /**
     * Restores the current color to the original frequency RGB
     */
    public void restore()
    {
        if (list.size() != 0 && (index - 1) >= 1)
        {
            color = list.get((index - 1) % list.size());
        }
    }

    /**
     * Change the current color to glow in the dark
     */
    public void glowInDark()
    {
        color = new Color(81, 255, 13);
    }

    /**
     * String Representation
     * 
     * @return str - The string representation
     */
    public String toString()
    {
        ListIterator<Color> it = list.listIterator();
        String str = "";

        str = "Current Color: " + color.toString() + "\nStored Colors: ";

        while (it.hasNext())
            str += it.next().toString() + "\n";

        return str;
    }
}
