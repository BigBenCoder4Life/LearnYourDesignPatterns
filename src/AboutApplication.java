
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import app.MultimediaApplication;

/**
 * Driver for a MultimediaApplication.
 *
 * @author Ben Kirtley
 * This work complies with the James Madison University honor code
 */
public class AboutApplication extends MultimediaApplication
{  
  
  /**
   * Entry point for the AboutApplication.
   *
   * @param args The terminal arguments
   * @throws InvocationTargetException Exception Wrapper
   * @throws InterruptedException Thrown if thread is interrupted
   */
  public static void main(String[] args) throws InvocationTargetException, InterruptedException
  {   
      SwingUtilities.invokeAndWait(new AboutApplication(args, 1253, 641));
  }

  /**
   * Explicit Constructor.
   *
   * @param args The terminal arguments
   * @param width The width of the application window
   * @param height The height of the application window
   */
  AboutApplication(String[] args, int width, int height)
  {   
      super(args, new AboutApp(), width, height);
  }
}
