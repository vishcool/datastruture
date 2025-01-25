import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF weighted;
    private int count;
    // Constructor
    //To construct a weighted union graph
    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        count = n*n+1;
        this.weighted = new WeightedQuickUnionUF(n*n + 2);
        for (int i = 1; i <= n; i++) {
            this.open(0, i);
            this.open(count, (n*n + 1) - i);
        }
       
    }

    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        weighted.union(row,col);
    }

    // Checks if the site (row, col) is open
    public boolean isOpen(int row, int col) {
        return weighted.find(row) != weighted.find(col);
        
    }

    // Checks if the site (row, col) is full
    public boolean isFull(int row, int col) {
        return weighted.find(row) == weighted.find(col);
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return weighted.count();
    }

    // Checks if the system percolates
    public boolean percolates() {
        return weighted.find(0) == weighted.find(this.count);
    }

    // Main method to test the class
    public static void main(String[] args) {
        // Step 1: Call the constructor
        int gridSize = 3; // Example grid size
        Percolation pre = new Percolation(gridSize);
        pre.open(1, 4);
        pre.open(4, 7);
        pre.open(8, 7);
        System.out.println(pre.percolates());
    }
}