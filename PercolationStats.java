
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresholds; // Array to store percolation thresholds
    private final int trials; // Number of trials

    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }

        this.trials = trials;
        thresholds = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1); // Random row between 1 and n
                int col = StdRandom.uniformInt(1, n + 1); // Random column between 1 and n
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            // Record the fraction of open sites when the system percolates
            thresholds[t] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // Sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // Test client
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java PercolationStats <grid size> <number of trials>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
