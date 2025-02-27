
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF weighted;
    private WeightedQuickUnionUF fullnessUF;
    private boolean[][] open;
    private int gridStructure;
    private int openCount = 0;

    // Constructor
    //To construct a weighted union graph
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridStructure = n;
        open = new boolean[n][n];
        weighted = new WeightedQuickUnionUF(n * n + 2); // +2 for virtual top and bottom
        fullnessUF = new WeightedQuickUnionUF(n * n + 1); // +1 for virtual top
    }

    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        if (!open[row][col]) {
            openCount++;
            open[row][col] = true;
            int current = getflattenedMatrixData(row, col);

            if (row == 0) {
                weighted.union(current, 0);
                fullnessUF.union(current, 0);
            }
            if (row == gridStructure - 1) {
                weighted.union(current, gridStructure * gridStructure + 1);
            }

            int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                if (newRow >= 0 && newRow < gridStructure && newCol >= 0 && newCol < gridStructure && open[newRow][newCol]) {
                    int neighbor = getflattenedMatrixData(newRow, newCol);
                    weighted.union(current, neighbor);
                    fullnessUF.union(current, neighbor);
                }
            }
        }
    }

    // Checks if the site (row, col) is open
    public boolean isOpen(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        return open[row][col];
    }

    // Checks if the site (row, col) is full
    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;
        validate(row, col);
        return isOpen(row + 1, col + 1) && fullnessUF.find(getflattenedMatrixData(row, col)) == fullnessUF.find(0);
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // Checks if the system percolates
    public boolean percolates() {
        return weighted.find(0) == weighted.find(gridStructure * gridStructure + 1);
    }

    private int getflattenedMatrixData(int row, int col) {
        return row * gridStructure + col + 1;
    }

    private void validate(int newRow, int newCol) {
        if (!(newRow >= 0 && newRow < gridStructure && newCol >= 0 && newCol < gridStructure)) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    // Main method to test the class
    public static void main(String[] args) {
        // Step 1: Call the constructor
        int n = 1;

        Percolation per = new Percolation(n);
        // per.isOpen(5, 0);

        System.out.println(per.percolates());
    }
}
