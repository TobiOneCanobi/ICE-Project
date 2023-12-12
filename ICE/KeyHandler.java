package ICE;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    Pathfinding pf;
    public KeyHandler(Pathfinding pf) {
        this.pf = pf;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER) {
            //pf.search();
            pf.autoSearch();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
