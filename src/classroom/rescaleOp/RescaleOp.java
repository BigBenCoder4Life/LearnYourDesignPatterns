package classroom.rescaleOp;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import math.Metric;
import math.RectilinearMetric;
import visual.statik.sampled.IdentityOp;

/**
 * This class is used to rescale a BufferedImage.
 * 
 * @author Prof. David Bernstein, Ben Kirtley This work complies with the James
 *         Madison University honor code
 */
public class RescaleOp extends IdentityOp
{

    private static final double TOLERANCE = 70.0; // Tolerance for difference in
                                                  // frequencies

    private double[] a = new double[3]; // Used for comparison

    private double[] b = new double[3]; // Used for comparison

    private int[] highlightColor; // Highlight color to retain

    private Metric metric; // The metric used for distance comparison

    /**
     * Implicit Value Constructor.
     */
    public RescaleOp()
    {
        this(81, 255, 13, new RectilinearMetric());
    }

    /**
     * Explicit Value Constructor.
     * 
     * @param r
     *            The red component of the color to leave
     * @param g
     *            The green component of the color to leave
     * @param b
     *            The blue component of the color to leave
     */
    public RescaleOp(int r, int g, int b)
    {
        this(r, g, b, new RectilinearMetric());
    }

    /**
     * Explicit Value Constructor.
     * 
     * @param r
     *            The red component of the color to leave
     * @param g
     *            The green component of the color to leave
     * @param b
     *            The blue component of the color to leave
     * @param metric
     *            The Metric to use to determine if two colors are similar
     */
    public RescaleOp(int r, int g, int b, Metric metric)
    {
        highlightColor = new int[3];
        highlightColor[0] = r;
        highlightColor[1] = g;
        highlightColor[2] = b;

        this.metric = metric;
    }

    /**
     * Determines if two colors are similar Note: This method only uses the red,
     * green, and blue components. It does not use the alpha component.
     * 
     * @param c
     * @param d
     * @return true, if similar
     */
    private boolean areSimilar(int[] c, int[] d)
    {
        boolean result; // Similar or not similar
        double distance;// Distance between two frequencies

        for (int i = 0; i < 3; i++)
        {
            a[i] = c[i];
            b[i] = d[i];
        }

        result = false;

        distance = metric.distance(a, b);

        if (distance <= TOLERANCE)
            result = true;

        return result;
    }

    /**
     * Filters the image to preserve a color and dim the others.
     * 
     * @param src
     *            - The buffered image to use
     * @param dest
     *            - The new buffered image
     * @param darken
     *            - To darken or restore the src image
     * @return dest - The destination buffered image
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest, boolean darken)
    {
        ColorModel srcColorModel, destColorModel;
        int dimRGB, highlightRGB, srcWidth, srcHeight;
        int[] dimColor, srcColor;
        int[] highlightColor = { 81, 255, 13 };
        Raster raster;

        srcWidth = src.getWidth();
        srcHeight = src.getHeight();

        srcColorModel = src.getColorModel();

        if (dest == null)
            dest = createCompatibleDestImage(src, srcColorModel);

        destColorModel = dest.getColorModel();

        highlightRGB = destColorModel.getDataElement(highlightColor, 0);

        raster = src.getRaster();

        dimColor = new int[4];
        srcColor = new int[4];

        for (int x = 0; x < srcWidth; x++)
        {
            for (int y = 0; y < srcHeight; y++)
            {
                raster.getPixel(x, y, srcColor);

                if (areSimilar(srcColor, highlightColor))
                {
                    dest.setRGB(x, y, highlightRGB);
                }
                else
                {

                    if (!darken)
                    {
                        for (int i = 0; i < srcColor.length; i++)
                            dimColor[i] = srcColor[i] / 2;
                    }
                    else
                    {
                        for (int i = 0; i < srcColor.length; i++)
                            dimColor[i] = srcColor[i] * 2;
                    }

                    dimRGB = srcColorModel.getDataElement(dimColor, 0);
                    dest.setRGB(x, y, dimRGB);
                }
            }
        }
        return dest;
    }

    /**
     * Set the Metric to use to calculate the distance between two colors.
     * 
     * @param metric
     *            The Metric to use
     */
    public void setMetric(Metric metric)
    {
        this.metric = metric;
    }
}
