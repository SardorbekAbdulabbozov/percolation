import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int numberOfOpenSites;
    private int virtualTop;
    private int virtualBottom;
    private boolean[] openSites;
    private WeightedQuickUnionUF finalUf;
    private WeightedQuickUnionUF virtualUf;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        openSites = new boolean[virtualTop];
        finalUf = new WeightedQuickUnionUF(virtualTop + 1);
        virtualUf = new WeightedQuickUnionUF(virtualTop + 2);
        for (int i = 0; i < n; i++) {
            finalUf.union(virtualTop, i);
            virtualUf.union(virtualTop, i);
        }
    }

    private void validate(int row, int col) {
        boolean isValid = (row > 0 && row <= n && col > 0 && col <= n);
        if (!isValid) throw new IllegalArgumentException();

    }

    private boolean validateNeighbours(int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n;
    }

    private int twoDimensionToOneDimension(int row, int col) {
        return (col - 1) + n * (row - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;
        int neighbour;
        int site = twoDimensionToOneDimension(row, col);
        openSites[site] = true;
        numberOfOpenSites++;
        if (validateNeighbours(row - 1, col)) {
            neighbour = twoDimensionToOneDimension(row - 1, col);
            if (openSites[neighbour]) {
                finalUf.union(site, neighbour);
                virtualUf.union(site, neighbour);
            }
        }
        if (validateNeighbours(row + 1, col)) {
            neighbour = twoDimensionToOneDimension(row + 1, col);
            if (openSites[neighbour]) {
                finalUf.union(site, neighbour);
                virtualUf.union(site, neighbour);
            }
        }
        if (validateNeighbours(row, col - 1)) {
            neighbour = twoDimensionToOneDimension(row, col - 1);
            if (openSites[neighbour]) {
                finalUf.union(site, neighbour);
                virtualUf.union(site, neighbour);
            }
        }
        if (validateNeighbours(row, col + 1)) {
            neighbour = twoDimensionToOneDimension(row, col + 1);
            if (openSites[neighbour]) {
                finalUf.union(site, neighbour);
                virtualUf.union(site, neighbour);
            }
        }
        if (row == n) virtualUf.union(site, virtualBottom);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[twoDimensionToOneDimension(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col)
                && finalUf.find(twoDimensionToOneDimension(row, col)) == finalUf.find(
                virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return virtualUf.find(virtualTop) == virtualUf.find(virtualBottom);
    }

    public static void main(String[] args) {
    }
}
