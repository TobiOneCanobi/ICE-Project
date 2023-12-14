package ICE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler extends JPanel implements ActionListener {

    Tile tile;
    static Pathfinding pf = new Pathfinding();
    static JButton placeStartTileButton = new JButton("Start");
    static JButton placeGoalTileButton = new JButton("Goal");
    static JButton placeSolidTileButton = new JButton("Solid");
    static JButton runButton = new JButton("Run");
    static JButton reset = new JButton("reset");
    static JButton showCost = new JButton("cost");
    static JButton randomizer = new JButton("Randomizer");
    static JButton activeButton;
    static Boolean costActive = false;
    static JLabel chatLabel = new JLabel();


    public ButtonHandler() {
        tile = new Tile(0, 0);
        tile = new Tile(this);


        placeStartTileButton.addActionListener(this);
        placeGoalTileButton.addActionListener(this);
        placeSolidTileButton.addActionListener(this);
        runButton.addActionListener(this);
        reset.addActionListener(this);
        showCost.addActionListener(this);
        randomizer.addActionListener(this);

        this.add(placeStartTileButton);
        this.add(placeGoalTileButton);
        this.add(placeSolidTileButton);
        this.add(runButton);
        this.add(reset);
        this.add(showCost);
        this.add(randomizer);

        placeStartTileButton.setFocusable(false);
        placeGoalTileButton.setFocusable(false);
        placeSolidTileButton.setFocusable(false);
        runButton.setFocusable(false);
        reset.setFocusable(false);
        showCost.setFocusable(false);
        randomizer.setFocusable(false);
    }

    public void setChatMessage(String message) {
        chatLabel.setText(message + "");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        activeButton = (JButton) e.getSource();

        if (activeButton == placeStartTileButton) {
            setChatMessage("Place a start tile");

        }
        if (activeButton == placeGoalTileButton) {
            setChatMessage("Place a goal tile");
        }
        if (activeButton == placeSolidTileButton) {
            setChatMessage("Place a solid tile or click a solid tile to undo");
        }
        if (activeButton == runButton) {
            if (pf.allTiles.contains(pf.startTile) && pf.allTiles.contains(pf.goalTile)) {
                setChatMessage("executing");
                pf.setCostOnTiles();
                pf.autoSearch();
            } else {
                setChatMessage("Make sure you have a start and goal tile");
            }
        }
        if (activeButton == reset) {
            setChatMessage("Resetting");
            pf.reset();
        }
        if (activeButton == showCost) {
            if (costActive == false) {
                costActive = true;
                setChatMessage("Showing tile costs");
                pf.updateTileCosts();
            } else if (costActive == true) {
                costActive = false;
                setChatMessage("hiding tile costs");
                pf.updateTileCosts();
            }
        }
        if (activeButton == randomizer) {
            setChatMessage("Generating a randomized map");
            pf.generateRandom();
        }
    }
}
