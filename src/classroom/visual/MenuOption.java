package classroom.visual;

import io.ResourceFinder;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import visual.statik.SimpleContent;

/**
 * A option for a menu that can be selected by the user.
 * 
 * @author Ben Kirtley This work complies with the James Madison University
 *         honor code
 */
public class MenuOption implements SimpleContent
{

    private Rectangle2D.Double bounds; // Bounds of menu option

    private Image img; // The patterns image

    public String text; // The name of the design

    private boolean visible = false; // Toggle switch

    private int x; // The x-coordinate

    private int y;// The y-coordinate

    /**
     * Implicit Constructor.
     * 
     * @param str
     *            The name of the pattern
     * @param x
     *            The x-coordinate
     * @param y
     *            The y-coordinate
     * @param finder
     *            Finds file resources
     */
    public MenuOption(String str, int x, int y, ResourceFinder finder)
    {
        this.text = str;
        this.x = x;
        this.y = y;

        try
        {
            img = ImageIO.read(finder.findInputStream("/resources/patterns/" + text + ".png"));
        }
        catch (IOException e)
        {
            // Do nothing
        }
    }

    /**
     * The pattern's image.
     * 
     * @return img - The image
     */
    public Image getPattern()
    {
        return img;
    }

    /**
     * The Pattern's name.
     * 
     * @return text The string representation of the design
     */
    public String getText()
    {
        return text;
    }

    /**
     * Checks if point is in bounds of the menu option.
     * 
     * @param point
     *            - The point to check
     * @return reference - true or false
     */
    public boolean isSelected(Point2D point)
    {
        if (bounds.contains(point))
            return true;
        else
            return false;
    }

    /**
     * Determines if the options image is visible.
     * 
     * @param bool
     *            True for visible else false
     */
    public void isVisible(boolean bool)
    {
        this.visible = bool;
    }

    /**
     * Paint the option features.
     * 
     * @param g2
     *            - The graphics engine
     */
    protected void paintFeatures(Graphics2D g2)
    {
        Font font; // Font to render
        FontMetrics metric; // Characteristics of Glyphs

        font = new Font(Font.DIALOG_INPUT, Font.ITALIC, 30);

        font = font.deriveFont(Font.BOLD);

        metric = g2.getFontMetrics(font);

        bounds = new Rectangle2D.Double(x, y - 30, metric.stringWidth(text), 40);

        g2.setColor(Color.BLACK);

        g2.setFont(font);

        // Determine if option is selected and render features
        if (visible)
        {
            g2.setPaint(new GradientPaint(x, y, new Color(69, 0, 132), x + 40, y + 40, new Color(
                    203, 182, 119)));

            g2.fill(bounds);

            g2.drawImage(img, 420, 0, null);

            isVisible(false);
        }

        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
    }

    /**
     * Renders the simple content.
     * 
     * @param g
     *            - The graphics engine
     */
    public void render(Graphics g)
    {
        Graphics2D g2;

        g2 = (Graphics2D) g;

        paintFeatures(g2);
    }
}
