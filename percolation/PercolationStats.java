/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private final int n;
    private final int trials;
    private double [] percolationThresholds;
    private double mean;
    private double stddev;

    private final double coeff = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Wrong arguments!");
        }
        this.n = n;
        this.trials = n;
        this.percolationThresholds = new double[trials];
    }

    // sample mean of percolation threshold
    public double mean() {
        int gridSize = n * n;
        for (int i = 0; i < percolationThresholds.length; i++) {
            Percolation itemPerc = new Percolation(n);
            while (!itemPerc.percolates()) {
                int rowRand = StdRandom.uniform(0, n) + 1;
                int colRand = StdRandom.uniform(0, n) + 1;
                itemPerc.open(rowRand, colRand);
            }
            int openSites = itemPerc.numberOfOpenSites();
            percolationThresholds[i] = openSites / (double) gridSize;
        }
        mean = StdStats.mean(percolationThresholds);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(percolationThresholds);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (coeff * stddev) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (coeff * stddev) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        double mean = percStats.mean();
        double stddev = percStats.stddev();
        double loConfidence = percStats.confidenceLo();
        double hiConfidence = percStats.confidenceHi();
        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + stddev);
        System.out.println("95% confidence interval = " + "[" + loConfidence + ", " + hiConfidence + "]");
    }
}
