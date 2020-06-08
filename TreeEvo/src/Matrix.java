import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Matrix
{
    private Pixel[][] matrix;
    ArrayList<Tree> trees = new ArrayList<>();
    private TreeMap<Integer, Integer[]> basicGeneticCode = new TreeMap<>();
    private TreeMap<Integer, Integer[]> clearGeneticCode = new TreeMap<>();
    public Matrix(int x, int y)
    {
        matrix = new Pixel[x/8][y/8];
        initMatrix();
        clearMatrix();
        //basicGeneticCode = getRandomGeneticCode(32);
        setUpGeneticCode("data/txt/basicGeneticCode", basicGeneticCode);
        setUpGeneticCode("data/txt/clearGeneticCode", clearGeneticCode);
    }

    public void clearMatrix()
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].setGene(-1);
                matrix[i][j].setType(PixelType.EMPTY);
                if (j==matrix[0].length-1) matrix[i][j].setType(PixelType.WALL);
                //if (i==matrix.length-1) matrix[i][j].setType(PixelType.WALL);
                //if (i==0) matrix[i][j].setType(PixelType.WALL);
                if (j==0) matrix[i][j].setType(PixelType.WALL);
            }
        }
    }
    public void initMatrix()
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Pixel(i*8, j*8, PixelType.EMPTY, this);
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i != 0) {
                    matrix[i][j].setLeft(matrix[i-1][j]);
                }
                else {
                    matrix[i][j].setLeft(matrix[matrix.length-1][j]);
                }
                if (i != matrix.length-1) {
                    matrix[i][j].setRight(matrix[i+1][j]);
                }
                else {
                    matrix[i][j].setRight(matrix[0][j]);
                }
                if (j != 0) {
                    matrix[i][j].setAbove(matrix[i][j-1]);
                }
                if (j != matrix[0].length-1) {
                    matrix[i][j].setAbove(matrix[i][j+1]);
                }
            }
        }

    }
    private void seedGravity()
    {
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 2; j >= 0; j--) {
                if (matrix[i][j].getType() == PixelType.FRUIT && matrix[i][j].gene == -2) {
                    matrix[i][j].setType(PixelType.SEED);
                }
                else if (matrix[i][j].getType() == PixelType.SEED && matrix[i][j+1].getType() == PixelType.EMPTY)
                {
                    matrix[i][j].setType(PixelType.EMPTY);
                    matrix[i][j+1].setGeneticCode(matrix[i][j].getGeneticCode());

                    matrix[i][j].clearGeneticCode();
                    matrix[i][j+1].setType(PixelType.SEED);
                }
                else if (matrix[i][j].getType() == PixelType.SEED & matrix[i][j+1].getType() != PixelType.WALL)
                {
                    matrix[i][j].setType(PixelType.EMPTY);
                }
            }
        }
    }
    private void treeGrow()
    {
        for (int i = 0; i < trees.size(); i++) {
            trees.get(i).iterate(matrix);
            if (!trees.get(i).alive) trees.remove(trees.get(i));
        }
        for (Pixel[] pixels : matrix) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                if (pixels[j + 1].getType() == PixelType.WALL & pixels[j].getType() == PixelType.SEED) {
                    trees.add(new Tree(matrix, pixels[j].getGeneticCode(), pixels[j]));
                    pixels[j].setType(PixelType.SPROUT);
                    pixels[j].setGene(0);
                }
            }
        }
    }
    public void matrixIteration()
    {
        if (trees.size() > 50) clearMatrix();
        seedGravity();
        treeGrow();
    }

    public Pixel[][] getMatrix() {
        return matrix;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }
    private void setUpGeneticCode(String path, TreeMap<Integer, Integer[]> map)
    {
        ArrayList<String[]> roughGeneticCode = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String cleanedLine;
            String line = reader.readLine();

            while (line != null) {
                cleanedLine = line.replaceAll("[^0-9]+", " ");
                roughGeneticCode.add(cleanedLine.split("\\s"));
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        roughGeneticCode.forEach(strings -> {
            map.put(Integer.parseInt(strings[0]), new Integer[] {
                    Integer.parseInt(strings[1]),
                    Integer.parseInt(strings[2]),
                    Integer.parseInt(strings[3]),
                    Integer.parseInt(strings[4])
            });
        });
    }

    private TreeMap<Integer, Integer[]> getRandomGeneticCode(int size) {
        TreeMap<Integer, Integer[]> randomGeneticCode = new TreeMap<>();
        Integer[] genes = new Integer[4];
        for (int i = 0; i < size; i++)
        {
            genes[0] = (int) Math.random()*64;
            genes[1] = (int) Math.random()*64;
            genes[2] = (int) Math.random()*64;
            genes[3] = (int) Math.random()*64;
            randomGeneticCode.put(i, genes);
        }
        return  randomGeneticCode;
    }

    public TreeMap<Integer, Integer[]> getBasicGeneticCode() {
        return basicGeneticCode;
    }

    public TreeMap<Integer, Integer[]> getClearGeneticCode() {
        return clearGeneticCode;
    }
}
