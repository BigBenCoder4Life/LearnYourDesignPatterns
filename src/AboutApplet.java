
import app.MultimediaApplet;

/**
 * This class is a entry point for the AboutApp class.
 * 
 * @author Ben Kirtley 
 * This work complies with the James Madison honor code.
 */

public class AboutApplet extends MultimediaApplet
{   
    private static final long serialVersionUID = 1L;

    /**
     * Explicit Constructor.
     */
    public AboutApplet()
    {
        //Call super constructor in multimediaApplet class
        super(new AboutApp());
    }
}
