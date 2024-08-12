import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Instance variables
    private final WeightedQuickUnionUF union;
    private final boolean[][] grid;
    private final int size;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throwException();
        openCount = 0;
        size = n;
        grid = new boolean[n][n];
        // Use a flattened union-find structure
        union = new WeightedQuickUnionUF(n * n + 2); // add 2 entries at the end
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkException(row, col);
        // set site to open
        grid[row - 1][col - 1] = true;
        // links open sites
        linkNeighbours(row, col);
        // link to top or bottom if applicable
        if (row == 1) union.union(xyTo1D(row, col), size * size);
        if (row == size) // Only connect to bottom if full
            union.union(xyTo1D(row, col), size * size + 1);
        // add to count
        openCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkException(row, col);
        // check if the site is connected to the top node
        return union.find(xyTo1D(row, col)) == union.find(size * size);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        // check if the top node is connected to the bottom
        return union.find(size * size) == union.find(size * size + 1);
    }

    // Testing client
    public static void main(String[] args) {

    }

    // helper methods

    /**
     * converts 2D indices to 1D. While row and col starts from 1,
     * the converted index should start from 0
     *
     * @param row row index
     * @param col column index
     * @return an int that denotes 1D index
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    // Find open neighbours and link them together
    private void linkNeighbours(int row, int col) {
        if (hasNeighbour(row + 1, col) && isOpen(row + 1, col)) link(row, col, row + 1, col);
        if (hasNeighbour(row - 1, col) && isOpen(row - 1, col)) link(row, col, row - 1, col);
        if (hasNeighbour(row, col + 1) && isOpen(row, col + 1)) link(row, col, row, col + 1);
        if (hasNeighbour(row, col - 1) && isOpen(row, col - 1)) link(row, col, row, col - 1);
    }

    // Link two open sites
    private void link(int row, int col, int rowNew, int colNew) {
        union.union(xyTo1D(row, col), xyTo1D(rowNew, colNew));
    }

    private boolean hasNeighbour(int row, int col) {
        return row > 0 && row <= size && col > 0 && col <= size;
    }

    private void checkException(int row, int col) {
        if (row > size || col > size || row <= 0 || col <= 0) {
            throwException();
        }
    }

    private void throwException() {
        throw new IllegalArgumentException("Index out of bound.");
    }
}
