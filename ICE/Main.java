package ICE;

import javax.swing.*;
import java.awt.*;

public class Main {


    public static void main(String[] args) {
        ButtonHandler buttonHandler = new ButtonHandler();
        Tile tile = new Tile(0, 0);

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        window.add(ButtonHandler.pf);
        window.setTitle("Pathfinding");


        JPanel buttonPanel = new JPanel();

        buttonPanel.add(ButtonHandler.chatLabel);
        buttonPanel.add(ButtonHandler.placeStartTileButton);
        buttonPanel.add(ButtonHandler.placeGoalTileButton);
        buttonPanel.add(ButtonHandler.placeSolidTileButton);
        buttonPanel.add(ButtonHandler.runButton);
        buttonPanel.add(ButtonHandler.reset);
        buttonPanel.add(ButtonHandler.showCost);
        buttonPanel.add(ButtonHandler.randomizer);


        window.add(buttonPanel, BorderLayout.NORTH);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

}
