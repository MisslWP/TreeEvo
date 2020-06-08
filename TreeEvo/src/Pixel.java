import java.util.TreeMap;

public class Pixel
{
    private int x;
    private int y;
    private PixelType type;
    int gene;
    Matrix matrix;
    boolean isFruit;
    TreeMap<Integer, Integer[]> geneticCode;
    private Pixel above;
    private Pixel under;
    private Pixel left;
    private Pixel right;

    public Pixel(int x, int y, PixelType type, Matrix matrix)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.matrix = matrix;
        isFruit = false;
    }


    /** Directions of genes array
     *        1
     *      0 ↑
     *      ←   → 2
     *        ↓ 3
     */
    public void expressGene(TreeMap<Integer, Integer[]> geneticBook, Tree tree)
    {
        if (gene == 7 || gene == 20) {
            tree.generateFruit(this);
        }
        else if (gene <32 && gene > -1)
        {
            int temp = 0;
            Integer[] genes = geneticBook.get(gene);
            for (int i = 0; i < 4; i++) {
                if (i == 0 && genes[0] < 32 && genes[0] > -1)
                {
                    if (left.getType() == PixelType.EMPTY)
                    {
                        tree.matrix[left.getX()/8][left.getY()/8].setType(PixelType.SPROUT);
                        tree.matrix[left.getX()/8][left.getY()/8].setGene(genes[0]);
                        tree.treePixels.add(left);
                    }
                }
                else if (i == 1 && genes[1] < 32 && genes[1] > -1)
                {
                    if (tree.matrix[x/8][y/8-1].getType() == PixelType.EMPTY)
                    {
                        tree.matrix[x/8][y/8-1].setType(PixelType.SPROUT);
                        tree.matrix[x/8][y/8-1].setGene(genes[1]);
                        tree.treePixels.add(tree.matrix[x/8][y/8-1]);
                    }
                }
                else if (i == 2 && genes[2] < 32 && genes[2] > -1)
                {
                    if (right.getType() == PixelType.EMPTY)
                    {
                        tree.matrix[right.getX()/8][right.getY()/8].setType(PixelType.SPROUT);
                        tree.matrix[right.getX()/8][right.getY()/8].setGene(genes[2]);
                        tree.treePixels.add(right);
                    }
                }
                else if (i == 3 && genes[3] < 32 && genes[3] > -1)
                {
                    if (tree.matrix[x/8][y/8+1].getType() == PixelType.EMPTY)
                    {
                        tree.matrix[x/8][y/8+1].setType(PixelType.SPROUT);
                        tree.matrix[x/8][y/8+1].setGene(genes[3]);
                        tree.treePixels.add(tree.matrix[x/8][y/8+1]);
                    }
                }
            }
        }
    }

    private boolean isOnTop()
    {
        return (above == null);
    }
    private boolean isOnDown()
    {
        return (under == null);
    }

    public Pixel getAbove() {
        return above;
    }

    public Pixel getLeft() {
        return left;
    }

    public Pixel getRight() {
        return right;
    }

    public Pixel getUnder() {
        return under;
    }

    public void setAbove(Pixel above) {
        this.above = above;
    }

    public void setLeft(Pixel left) {
        this.left = left;
    }

    public void setRight(Pixel right) {
        this.right = right;
    }

    public void setUnder(Pixel under) {
        this.under = under;
    }

    public void setGene(int gene)
    {
        this.gene = gene;
    }

    public void setType(PixelType type) {
        this.type = type;
        if (type == PixelType.EMPTY | type == PixelType.WALL) gene = -1;
        isFruit = type == PixelType.FRUIT;
    }

    public PixelType getType() {
        return type;
    }

    public int getY() {
        return y;
    }

    public void setGeneticCode(TreeMap<Integer, Integer[]> geneticCode) {
        this.geneticCode = geneticCode;
    }

    public TreeMap<Integer, Integer[]> getGeneticCode() {
        return geneticCode;
    }

    public void clearGeneticCode()
    {
        geneticCode = matrix.getClearGeneticCode();
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
