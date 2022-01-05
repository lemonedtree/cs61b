package hw2;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private int N;
    private int T;
    private int[] openSitesRecord;
    private PercolationFactory pf;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        this.pf = pf;
        openSitesRecord = new int[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                //random是0-1之间的浮点数， 我要的是 0 - N 之间的整数
                int row = (int) Math.random() * N;
                int col = (int) Math.random() * N;
                p.open(row, col);
            }
            openSitesRecord[i] = p.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += openSitesRecord[i];
        }
        return (sum / T);
    }

    // sample standard deviation of percolation threshold
    //应该还要开个根号，妈的
    public double stddev() {
        double mean = mean();
        double sum2 = 0;
        for (int i = 0; i < T; i++) {
            sum2 = sum2 + ((openSitesRecord[i] - mean) * (openSitesRecord[i] - mean));
        }
        return Math.sqrt((sum2 / (T - 1)));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
