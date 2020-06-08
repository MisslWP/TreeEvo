import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PauseListener extends KeyAdapter
{
    Timer timer;
    boolean isPaused;
    public PauseListener(Timer timer) {
        isPaused = false;
        this.timer = timer;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isPaused) {
                isPaused = false;
                timer.start();
            }
            else {
                isPaused = true;
                timer.stop();
            }
        }
    }
}
