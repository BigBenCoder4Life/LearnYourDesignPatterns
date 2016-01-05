

import io.ResourceFinder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import classroom.FrontWallWithScreenAndLights;
import classroom.colorOp.ColorManager;
import classroom.visual.Menu;
import event.Metronome;
import event.MetronomeListener;
import visual.Visualization;
import visual.VisualizationView;
import app.AbstractMultimediaApp;

/**
 * This class is the driver class for applications and applets.
 * 
 * @author Ben Kirtley This work complies with the James Madison University
 *         honor code.
 */
public class AboutApp extends AbstractMultimediaApp implements MouseListener, MouseWheelListener,
        MetronomeListener, ActionListener, MouseMotionListener, KeyListener
{
    
    private JPanel contentPane; // The content pane
    
    private ResourceFinder finder; // Finds file resources
    
    public FrontWallWithScreenAndLights defaultView; // The default view
    
    private boolean lowering = false; // Indicates lowering of the screen
    
    private Metronome metronome; // Timer
    
    private VisualizationView view; // The view
    
    private Visualization visualization; // Holds the views
    
    private JButton lightSwitch; // The light switch button
    
    private JButton markerButton; // The marker button
    
    private JButton eraserButton; // The eraser button
    
    private Rectangle2D.Double box; // Bounds for the white board
    
    private Menu menu; // Menu for the projector screen
    
    private ColorManager colorManager;

    /**
     * Called when this MultimediaApp has been loaded.
     */
    public void init()
    {
        // Create a resource finder
        finder = ResourceFinder.createInstance();

        // Create a timer
        metronome = new Metronome();

        // Create the bounds for the white board
        box = new Rectangle2D.Double(60, 0, 1090, 490);

        // Create a visualization to hold simple content
        visualization = new Visualization();

        // Get the content pane
        contentPane = (JPanel) rootPaneContainer.getContentPane();

        // Create the default view for the App
        defaultView = new FrontWallWithScreenAndLights(finder);

        colorManager = new ColorManager();

        // Add the default view to the visualization
        visualization.add(defaultView);

        // Get the view from the visualization
        view = visualization.getView();

        // Set the bounds of the view
        view.setBounds(0, 0, 1253, 641);

        // Create a light Switch
        createButtons();

        // Create a menu for the projector screen
        menu = Menu.createInstance(finder);

        menu.getView().setVisible(false);

        contentPane.add(menu.getView());

        // Add the default view to the content pane
        contentPane.add(view);

        // Add Listeners to this App for user interaction
        metronome.addListener(this);
        view.addMouseListener(this);
        view.addMouseWheelListener(this);
        view.addMouseMotionListener(this);
        view.addKeyListener(this);
    }

    /**
     * Creates interactive buttons.
     */
    private void createButtons()
    {
        lightSwitch = new JButton(createIcon("/resources/images/lightSwitch.jpg", 30, 30));
        markerButton = new JButton(createIcon("/resources/images/marker.png", 30, 15));
        eraserButton = new JButton("");

        lightSwitch.setActionCommand("lightSwitch");
        markerButton.setActionCommand("markerButton");
        eraserButton.setActionCommand("eraserButton");

        lightSwitch.setBorderPainted(false);
        markerButton.setBorderPainted(false);
        eraserButton.setBorderPainted(false);
        markerButton.setContentAreaFilled(false);

        lightSwitch.setSize(30, 30);
        markerButton.setSize(30, 10);
        eraserButton.setSize(30, 10);

        eraserButton.setBackground(Color.BLACK);

        lightSwitch.setLocation(1200, 250);
        markerButton.setLocation(300, 490);
        eraserButton.setLocation(250, 490);

        lightSwitch.addActionListener(this);
        markerButton.addActionListener(this);
        eraserButton.addActionListener(this);

        contentPane.add(lightSwitch);
        contentPane.add(markerButton);
        contentPane.add(eraserButton);
    }

    /**
     * Handle metronome ticks.
     *
     * @param milli            The time past in millis since start was called
     */
    public void handleTick(int milli)
    {
        if (lowering)
            defaultView.changeAmountLowered(20);
        else
            defaultView.changeAmountLowered(-20);

        view.repaint();

        checkAmountLowered();
    }

    /**
     * Checks the amount the projector screen is lowered.
     */
    private void checkAmountLowered()
    {
        if (defaultView.getAmountLowered() >= 100)
        {
            metronome.stop();
            menu.getView().setVisible(true);
        }
        else if (defaultView.getAmountLowered() <= 100)
            menu.getView().setVisible(false);

        if (defaultView.getAmountLowered() <= 0)
            metronome.stop();
    }

    /**
     * Finds and resizes image files for Image icons.
     *
     * @param fileName            The image file to use
     * @param width            The width of the icon
     * @param height            The height of the icon
     * @return the image icon
     */
    public ImageIcon createIcon(String fileName, int width, int height)
    {
        ImageIcon icon = null; // The icon to create
        Image image; //Image
        
        try
        {
            image = ImageIO.read(finder.findInputStream(fileName));
           
            image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    
            icon = new ImageIcon(image, null);     
        }
        catch (IOException e)
        {
            // Do nothing
        }

        return icon;
    }

    /**
     * Called when an action event has been fired from a registered object.
     *
     * @param event            The fired event
     */
    public void actionPerformed(ActionEvent event)
    {

        if (event.getActionCommand() == "lightSwitch")
        {
            // Toggle the lights
            defaultView.toggleLights();

            if (!lowering)
                lowering = true;
            else
                lowering = false;

            if (!defaultView.isLightsOn())
                colorManager.dim();
            else
                colorManager.restore();

            metronome.start();
        }
        else if (event.getActionCommand() == "markerButton")
        {
            colorManager.change();

            if (!defaultView.isLightsOn())
                colorManager.dim();
            else
                colorManager.restore();
        }
        else if (event.getActionCommand() == "eraserButton")
        {
            playSoundEffect("/resources/sounds/cleaningSound.wav");

            defaultView.clear();
            view.repaint();
        }
    }

    /**
     * Play Sound effect for cleaning the board.
     *
     * @param filePath - The File path
     */
    public void playSoundEffect(String filePath)
    {
        Clip clip; //Clip to hold sampled audio content
        AudioInputStream is; //Input stream for sound

        try
        {
            is = AudioSystem.getAudioInputStream(new BufferedInputStream(finder.findInputStream(filePath)));
            
            clip = AudioSystem.getClip();
            
            clip.open(is);
            
            clip.start();
            
            is.close();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Called when mouse scroll/wheel is moved.
     *
     * @param e            The mouse event
     */
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        checkAmountLowered();

        defaultView.changeAmountLowered(e.getWheelRotation() * 10);
        view.repaint();
    }

    /**
     * Called when the mouse is dragged by the user.
     *
     * @param e            The fired event
     */
    public void mouseDragged(MouseEvent e)
    {
        Graphics2D g2, g2d; // Graphics engine
        int x;// x-coordinate
        int y;// y-coordinate

        g2d = (Graphics2D) view.getGraphics();
        g2 = (Graphics2D) defaultView.getImage().getGraphics();

        g2.setColor(colorManager.get());
        g2d.setColor(colorManager.get());

        x = e.getX();
        y = e.getY();

        if (box.contains(x, y))
        {
            g2.fillOval(x, y, 8, 8);
            g2d.fillOval(x, y, 8, 8);
        }
    }

    /**
     * Called when a registered component fires a keylistener event.
     *
     * @param e            The event that was fired
     */
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            colorManager.glowInDark();
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            colorManager.change();

            if (!defaultView.isLightsOn())
                colorManager.dim();
            
        }
    }

    /**
     * Called when a registered component fires a keylistener event.
     *
     * @param e            The event that was fired
     */
    public void keyReleased(KeyEvent e)
    {
    }

    /**
     * Called when a registered component fires a keylistener event.
     *
     * @param e            The event that was fired
     */
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Called when the mouse is moved by the user.
     *
     * @param e            The fired event
     */
    public void mouseMoved(MouseEvent e)
    {
    }

    /**
     * Called when the mouse is pressed.
     *
     * @param e            The mouse event
     */
    public void mouseClicked(MouseEvent e)
    {

    }

    /**
     * Called when the mouse enters the view.
     *
     * @param e            The mouse event
     */
    public void mouseEntered(MouseEvent e)
    {
    }

    /**
     * Called when the mouse exits the view.
     *
     * @param e            The mouse event
     */
    public void mouseExited(MouseEvent e)
    {
    }

    /**
     * Called when the mouse is pressed.
     *
     * @param e            The mouse event
     */
    public void mousePressed(MouseEvent e)
    {
    }

    /**
     * Called when the mouse is released.
     *
     * @param e            The mouse event
     */
    public void mouseReleased(MouseEvent e)
    {
    }
}
