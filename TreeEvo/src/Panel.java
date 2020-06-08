import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener
{
    JFrame frame;
    Matrix matrix;
    Timer timer = new Timer(30,this);
    long sec = 0;
    long ms = 0;
    static Image treePixel = new ImageIcon("data\\sprites\\green_block.png").getImage();
    static Image emptyPixel = new ImageIcon("data\\sprites\\gray_block.png").getImage();
    static Image wallPixel = new ImageIcon("data\\sprites\\brown_block.png").getImage();
    static Image seedPixel = new ImageIcon("data\\sprites\\yellow_block.png").getImage();
    static Image sproutPixel = new ImageIcon("data\\sprites\\bright_green_block.png").getImage();
    static Image fruitPixel = new ImageIcon("data\\sprites\\red_block.png").getImage();

    public Panel(JFrame frame, Matrix matrix)
    {
        this.matrix = matrix;
        this.frame = frame;
        timer.start();
        generateSeeds(3);

        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.addKeyListener(new PauseListener(timer));
        frame.setFocusable(true);

    }
    public void paint(Graphics g)
    {
        paintMatrix(g);
        //drawGrid(g);
        g.drawString("AliveTrees: " + matrix.getTrees().size(), 20,20);
        g.drawString("Fruits on map: " + countFruits(), 20,40);
        g.drawString("Simulation secs: " + sec, 20,60);
    }

    private int countFruits()
    {
        int res = 0;
        for (int i = 0; i < matrix.getMatrix().length; i++) {
            for (int j = 0; j < matrix.getMatrix()[i].length; j++) {
                if (matrix.getMatrix()[i][j].getType() == PixelType.FRUIT) res++;
            }
        }
        return res;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        matrix.matrixIteration();
        ms += 30;
        if (ms > 1000) {
            ms-=1000;
            sec+=1;
        }
        repaint();
    }

    private void drawPixel(int x, int y, Graphics g, PixelType type)
    {
        if (type == PixelType.EMPTY) g.drawImage(emptyPixel, x, y, null);
        else if (type == PixelType.WALL) g.drawImage(wallPixel, x, y, null);
        else if (type == PixelType.TREE) g.drawImage(treePixel, x, y, null);
        else if (type == PixelType.SEED) g.drawImage(seedPixel, x, y, null);
        else if (type == PixelType.SPROUT) g.drawImage(sproutPixel, x, y, null);
        else if (type == PixelType.FRUIT) g.drawImage(fruitPixel, x, y, null);
    }
    private void paintMatrix(Graphics g)
    {
        for (int i = 0; i < matrix.getMatrix().length; i++) {
            for (int j = 0; j < matrix.getMatrix()[0].length; j++) {
                drawPixel(matrix.getMatrix()[i][j].getX(), matrix.getMatrix()[i][j].getY(), g, matrix.getMatrix()[i][j].getType());
            }
        }
    }

    private void drawGrid(Graphics g)
    {
        for (int i = 8; i < frame.getWidth(); i+= 8) {
            g.drawLine(i, 0, i, frame.getHeight());
        }
        for (int i = 8; i < frame.getHeight(); i+= 8){
            g.drawLine(0, i, frame.getWidth(), i);
        }

    }
    private void generateSeeds(int amount)
    {
        ArrayList<Integer> seedCords = new ArrayList<>();
        int suggest = -1;
        for (int i = 0; i < amount; i++) {

            do {
                suggest = (int) (matrix.getMatrix().length * Math.random());
                if (suggest < 1) suggest = 1;
                else if (suggest > matrix.getMatrix().length - 2) suggest = matrix.getMatrix().length - 2;
            }
            while (seedCords.contains(suggest));
            matrix.getMatrix()[suggest][1].setType(PixelType.SEED);
            matrix.getMatrix()[suggest][1].setGeneticCode(matrix.getBasicGeneticCode());
        }
    }
}

