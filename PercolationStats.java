import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double[] thresholdValues;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        thresholdValues = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            thresholdValues[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdValues);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdValues);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double loConfidence = mean() - ((1.96 * stddev()) / Math.sqrt(trials));
        return loConfidence;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double hiConfidence = mean() + ((1.96 * stddev()) / Math.sqrt(trials));
        return hiConfidence;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + "[" + stats.confidenceLo() + ", "
                               + stats.confidenceHi() + "]");
    }
}
