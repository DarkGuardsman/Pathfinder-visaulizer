package com.builtbroken.visualization.ui;

import com.builtbroken.visualization.component.RenderPanel;
import com.builtbroken.visualization.data.Grid;
import com.builtbroken.visualization.logic.Pathfinders;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 10/26/2018.
 */
public class DisplayFrame extends JFrame
{
    ArrayList<Grid> renderLayers = new ArrayList();
    int layerIndex = 0;

    RenderPanel renderPanel;

    Button playButton;


    JLabel renderIndexLabel;
    JTextField playSpeedField;


    boolean currentlyPlaying;

    Timer timer = new Timer(500, action -> nextLayer());

    public DisplayFrame()
    {
        //Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setMinimumSize(new Dimension(800, 800));
        setLocation(200, 200);
        setTitle("Visualization - heat pathfinder");

        add(buildCenter());

        pack();
    }

    protected JPanel buildCenter()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(renderPanel = new RenderPanel(), BorderLayout.CENTER);
        renderPanel.setMinimumSize(new Dimension(600, 600));
        renderPanel.setPreferredSize(new Dimension(600, 600));

        panel.add(buildControls(), BorderLayout.WEST);
        return panel;
    }

    protected JPanel buildControls()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 2));
        panel.setMinimumSize(new Dimension(200, 0));

        //--------------------------------------------------------

        Button button = new Button("Breadth First");
        button.addActionListener(e -> generateBreadthFirst());
        panel.add(button);

        button = new Button("Depth First");
        button.addActionListener(e -> generateDepthFirst());
        panel.add(button);

        //--------------------------------------------------------


        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //--------------------------------------------------------

        button = new Button("Breadth First");
        button.addActionListener(e -> generateBreadthFirst());
        panel.add(button);

        button = new Button("----");
        //button.addActionListener(e -> generateDepthFirst());
        panel.add(button);


        //--------------------------------------------------------


        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //--------------------------------------------------------

        playButton = new Button("Play");
        playButton.addActionListener(e -> play(false));
        panel.add(playButton);

        panel.add(playSpeedField = new JTextField());
        playSpeedField.setText("" + timer.getDelay());

        //--------------------------------------------------------

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //--------------------------------------------------------
        panel.add(new JLabel("Layer: "));
        panel.add(renderIndexLabel = new JLabel("0"));

        button = new Button("Prev");
        button.addActionListener(e -> prevLayer());
        panel.add(button);

        button = new Button("Next");
        button.addActionListener(e -> nextLayer());
        panel.add(button);

        return panel;
    }

    protected void nextLayer()
    {
        if (layerIndex < (renderLayers.size() - 1))
        {
            layerIndex++;
            updateRenderPanel();
        }
    }

    protected void prevLayer()
    {
        if (layerIndex > 0)
        {
            layerIndex--;
            updateRenderPanel();
        }
    }

    protected void play(boolean forceStop)
    {
        if (currentlyPlaying || forceStop)
        {
            currentlyPlaying = false;
            timer.stop();
            playButton.setLabel("Play");
        }
        else
        {
            //Set play speed
            timer.setDelay(Integer.parseInt(playSpeedField.getText().trim()));

            //Note we are playing
            currentlyPlaying = true;

            //Start timer
            timer.start();

            //Change text to note the button can stop play
            playButton.setLabel("Stop");
        }
    }

    protected void updateRenderPanel()
    {
        if (layerIndex >= 0 && layerIndex < renderLayers.size())
        {
            renderIndexLabel.setText("" + layerIndex);
            renderPanel.currentLayer = renderLayers.get(layerIndex);
            renderPanel.repaint();
        }
    }

    protected void generateBreadthFirst()
    {
        System.out.println("Generating data");

        //Clear old data
        renderLayers.clear();
        play(true);
        layerIndex = 0;

        final int size = 101;

        //Generate data
        Grid grid = new Grid(size);
        Pathfinders.doBreadthPathfinder(grid,  renderLayers,51, 51);

        //Update render panel
        updateRenderPanel();

        System.out.println("Done generating...");
        renderPanel.repaint();
    }

    protected void generateDepthFirst()
    {
        System.out.println("Generating data");

        //Clear old data
        renderLayers.clear();
        play(true);
        layerIndex = 0;

        final int size = 101;

        //Generate data
        Grid grid = new Grid(size);
        Pathfinders.doDepthPathfinder(grid,  renderLayers,51, 51);

        //Update render panel
        updateRenderPanel();

        System.out.println("Done generating...");
        renderPanel.repaint();
    }
}
