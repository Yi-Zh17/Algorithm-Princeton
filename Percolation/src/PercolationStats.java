import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // Constant
    private static final double CONFIDENCE_95 = 1.96;
    // Instance variables
    private final double[] sample;
    private final int numOfTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Invalid size or trial number");
        // Initialize variables
        sample = new double[trials];
        numOfTrials = trials;
        // start simulation
        for (int i = 0; i < trials; i++)
        {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates())
            {
                int x = StdRandom.uniformInt(1, n+1);
                int y = StdRandom.uniformInt(1, n+1);
                if (!percolation.isOpen(x, y))
                    percolation.open(x, y);
            }
            double sampleX = (double) percolation.numberOfOpenSites() / (n * n);
            sample[i] = sampleX;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(sample);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sample);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(numOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(numOfTrials);
    }

    // testing client
    public static void main(String[] args) {
        PercolationStats newExperiment = new PercolationStats(Integer.parseInt(args[0]),
                                                              Integer.parseInt(args[1]));
        StdOut.println("mean = " + newExperiment.mean());
        StdOut.println("stddev = " + newExperiment.stddev());
        StdOut.println("95% confidence interval = [" + newExperiment.confidenceLo() +
                ", " + newExperiment.confidenceHi() + "]");
    }
}
