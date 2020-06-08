import java.util.ArrayList;
import java.util.TreeMap;

public class Tree
{
    ArrayList<Pixel> treePixels = new ArrayList<>();
    Pixel[][] matrix;
    TreeMap<Integer, Integer[]> geneticCode;
    int energy;
    boolean alive;
    Pixel root;
    static final int startEnergy = 5000;

    public Tree(Pixel[][] matrix, TreeMap<Integer, Integer[]> geneticCode, Pixel rootPixel)
    {
        this.matrix = matrix;
        this.geneticCode = geneticCode;
        alive = true;
        root = rootPixel;
        treePixels.add(rootPixel);
        energy = startEnergy;
    }
    public void iterate(Pixel[][] matrix)
    {
        this.matrix = matrix;
        energy-=treePixels.size()-1;
        if (energy <= 0) {
            alive = false;
            for(Pixel[] pixels : matrix)
            {
                for (Pixel p : pixels)
                {
                    if (treePixels.contains(p)) {

                        if (p.getType() != PixelType.FRUIT) {
                            p.setType(PixelType.EMPTY);
                            p.clearGeneticCode();
                        }
                        p.setGene(-2);
                    }
                }
            }
        }
        else {
            int lastTimePixels = treePixels.size();
            for (int i = 0; i < lastTimePixels; i++) {
                if (treePixels.get(i).getType() == PixelType.SPROUT)
                {
                    treePixels.get(i).expressGene(geneticCode, this);
                    treePixels.get(i).setType(PixelType.TREE);
                }
            }
        }

    }
    public void generateFruit(Pixel pixel) {
        if ((energy>startEnergy/5) && matrix[pixel.getX() / 8][pixel.getY() / 8 + 1].getType() == PixelType.EMPTY) {
            matrix[pixel.getX() / 8][pixel.getY() / 8 + 1].setType(PixelType.FRUIT);
            matrix[pixel.getX() / 8][pixel.getY() / 8 + 1].setGeneticCode(root.getGeneticCode());
            treePixels.add(matrix[pixel.getX() / 8][pixel.getY() / 8 + 1]);
            energy -= 500;
        }
    }
}
