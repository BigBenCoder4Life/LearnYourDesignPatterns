package classroom.visual;

import io.ResourceFinder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import visual.Visualization;
import visual.VisualizationView;

/**
 * A menu for a Component.
 *
 * @author Ben Kirtley This work complies with the James Madison University
 *         honor code
 */
public class Menu extends MouseAdapter
{
    
    private static boolean exists = false;// Determine if instantiated
    
    private static Menu instance; // Menu
    
    private ResourceFinder finder;// Finds file resources
    
    private Iterator<?> it; // Iterator for simple content
    
    private VisualizationView view;// View for the content
    
    private Visualization visualization;// Holds simple content
    
    /**
     * Creates an instance of a menu Singleton pattern used.
     *
     * @param finder the finder
     * @return the menu
     */
    public synchronized static Menu createInstance(ResourceFinder finder)
    {
        if (!exists)
            instance = new Menu(finder);

        return instance;
    }

    /**
     * Implicit Constructor.
     *
     * @param finder            Finds file resources
     */
    private Menu(ResourceFinder finder)
    {
        this.finder = finder;

        visualization = new Visualization();

        view = visualization.getView();

        view.setBackground(Color.WHITE);

        view.setBounds(90, 20, 950, 400);

        view.addMouseListener(this);

        createMenuOptions();
    }

    /**
     * Creates the menu options for the menu.
     */
    private void createMenuOptions()
    {
        visualization.add(createOption("Iterator", 40, 40, finder));
        visualization.add(createOption("Singleton", 40, 80, finder));
        visualization.add(createOption("Composite", 40, 120, finder));
        visualization.add(createOption("Factory-Method", 40, 160, finder));
        visualization.add(createOption("Observer", 40, 200, finder));
        visualization.add(createOption("Decorator", 40, 240, finder));
        visualization.add(createOption("Strategy", 40, 280, finder));
        visualization.add(createOption("Model-View-Controller", 40, 320, finder));
        visualization.add(createOption("Command", 40, 360, finder));
    }

    /**
     * Creates a MenuOption from parameters.
     *
     * @param text            The string to render
     * @param x            The x-coordinate
     * @param y            The y-coordinate
     * @param finder            Finds file resources
     * @return the menu option
     */
    private MenuOption createOption(String text, int x, int y, ResourceFinder finder)
    {
        return new MenuOption(text, x, y, finder);
    }

    /**
     * Returns a view of the menu options.
     *
     * @return reference A visualization view
     */
    public VisualizationView getView()
    {
        return view;
    }

    /**
     * Loads the design pattern on the white board.
     *
     * @param option            The menu option selected
     */
    private void loadPattern(MenuOption option)
    {
        option.isVisible(true);
        view.repaint();
    }

    /**
    *
    *
    */
    public void mouseClicked(MouseEvent e)
    {
        MenuOption temp = null;

        it = visualization.iterator();

        while (it.hasNext())
        {
            temp = ((MenuOption) it.next());

            if (temp.isSelected(e.getPoint()))
            {
                loadPattern(temp);
                break;
            }
        }
    }
}
