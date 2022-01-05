package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private int N;
    private WeightedQuickUnionUF w;
    private int numberOfOpenSites;
    private boolean[] opens;
    
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N should not less than 0!");
        }
        this.N = N;
        w = new WeightedQuickUnionUF(N * N);
        opens = new boolean[N * N];
        numberOfOpenSites = 0;
    }

    private int findXY(int row, int col) {
        int xy = (N * row + col);
        return xy;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("There is no such position!");
        }

        if (!isOpen(row, col)) {
            //记录open状态
            opens[findXY(row, col)] = true;
            numberOfOpenSites++;
            //把它和它已经开了的邻居连接起来
            int[] neighbors = findOpenNeighbors(row, col);
            int xy = findXY(row, col);
            for (int i = 0; i < 4; i++) {
                //首先要存在已经开了的邻居
                if (neighbors[i] != -1) {
                    //如果本来没有连接，就连接起来
                    if (!w.connected(neighbors[i], xy)) {
                        w.union(neighbors[i], findXY(row, col));
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("There is no such position!");
        }
        return opens[findXY(row, col)];
    }

    /**
     * 数组中，如果有开了的邻居，就返回邻居的代码，如果没有开了的邻居，就返回-1
     * @param row
     * @param col
     * @return int[] neighbors
     */
    private int[] findOpenNeighbors(int row, int col) {
        int[] neighbors = new int[4];
        //上面那个邻居
        if (row == 0 || !isOpen(row - 1, col)) {
            neighbors[0] = -1;
        } else {
            neighbors[0] = findXY(row - 1, col);
        }
        //下面那个邻居
        if (row == N - 1 || !isOpen(row + 1, col)) {
            neighbors[1] = -1;
        } else {
            neighbors[1] = findXY(row + 1, col);
        }
        //左边那个邻居
        if (col == 0  || !isOpen(row, col - 1)) {
            neighbors[2] = -1;
        } else {
            neighbors[2] = findXY(row, col - 1);
        }
        //右边那个邻居
        if (col == N - 1  || !isOpen(row, col + 1)) {
            neighbors[3] = -1;
        } else {
            neighbors[3] = findXY(row, col + 1);
        }
        return neighbors;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)  {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("There is no such position!");
        }

        //如果没开，肯定没full
        if (!isOpen(row, col)) {
            return false;
        }

        boolean flag = false;
        for (int i = 0; i < N - 1; i++) {
            if (opens[i] && w.connected(i, findXY(row, col))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean flag = false;
        for (int col = 0; col < N; col++) {
            if (isFull(N - 1, col)){
                flag = true;
                break;
            }
        }
        return flag;
    }
    // use for unit testing (not required)
    public static void main(String[] args) {
    }

}
