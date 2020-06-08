import javax.swing.*;

public class ScreenLoader
{
    private Matrix matrix;
    public ScreenLoader(int width, int height) {
        matrix = new Matrix(width,height);
        JFrame frame = new JFrame("TreeEvolution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setUndecorated(false);
        frame.add(new Panel(frame, matrix));

        frame.setVisible(true);
    }

}
