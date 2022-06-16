/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int size;
    private int [] grid;
    private int openSitesNum = 0;
    private final WeightedQuickUnionUF uf;
    private final int topVirtualIdx;
    private final int bottomVirtualIdx;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Grid size is either null or negative!");
        size = n;
        final int virtualSitesNum = 2;
        final int elementsNum = n * n + virtualSitesNum;
        topVirtualIdx = 0;
        bottomVirtualIdx = elementsNum - 1;
        grid = new int[elementsNum];
        for (int i = 1; i < elementsNum - 1; i++) {
            grid[i] = 1;
        }
        uf = new WeightedQuickUnionUF(elementsNum);
    }

    private boolean checkSiteQuiet(int row, int col) {
        return !(row <= 0 || row > size || col <= 0 || col > size);
    }

    private void checkSiteWithException(int row, int col) {
        if (!checkSiteQuiet(row, col)) {
            throw new IllegalArgumentException("Wrong arguments: " + row + ", " + col);
        }
    }

    private int getElementIdx(int row, int col) {
        checkSiteWithException(row, col);
        int idx = size * (row - 1) + col; // -1 to handle indexing from 1
        return idx;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int elementIdx = getElementIdx(row, col);
        grid[elementIdx] = 0;
        openSitesNum++;
        // every opened top site is connected with the virtual top site
        boolean isTopRow = row == 1;
        if (isTopRow) {
            uf.union(topVirtualIdx, elementIdx);
        }
        boolean isBottomRow = row == size;
        if (isBottomRow) {
            uf.union(bottomVirtualIdx, elementIdx);
        }
        if (checkSiteQuiet(row - 1, col)) {
            boolean isTopNeigborOpen = isOpen(row - 1, col);
            if (isTopNeigborOpen) {
                int neigborIdx = getElementIdx(row - 1, col);
                uf.union(elementIdx, neigborIdx);
            }
        }
        if (checkSiteQuiet(row, col - 1)) {
            boolean isLeftNeigborOpen = isOpen(row, col - 1);
            if (isLeftNeigborOpen) {
                int neigborIdx = getElementIdx(row, col - 1);
                uf.union(elementIdx, neigborIdx);
            }
        }
        if (checkSiteQuiet(row, col + 1)) {
            boolean isRightNeigborOpen = isOpen(row, col + 1);
            if (isRightNeigborOpen) {
                int neigborIdx = getElementIdx(row, col + 1);
                uf.union(elementIdx, neigborIdx);
            }
        }
        if (checkSiteQuiet(row + 1, col)) {
            boolean isBottomNeigborOpen = isOpen(row + 1, col);
            if (isBottomNeigborOpen) {
                int neigborIdx = getElementIdx(row + 1, col);
                uf.union(elementIdx, neigborIdx);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int elementIdx = getElementIdx(row, col);
        return grid[elementIdx] == 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int elementIdx = getElementIdx(row, col);
        return uf.find(topVirtualIdx) == uf.find(elementIdx);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        // top virt cell conected with botton virt cell
        return uf.find(topVirtualIdx) == uf.find(bottomVirtualIdx);
    }

    // test client (optional)
    //@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    public static void main(String[] args) {

        //┌───────────────┐
        //│  o    o   o   │
        //│ xxx  xxo xxo  │
        //│ xxx  xox xoo  │
        //│ xxx  xxo xox  │
        //│  o    o   o   │
        //└──1────2───3───┘
        int size = 3;
        Percolation perc = new Percolation(size);
        // case 1 - does't percolate
        System.out.println("System in CASE 1 percolates: " + perc.percolates());
        // case 2 - does't percolate
        perc.open(1, 3);
        perc.open(2, 2);
        perc.open(3, 3);
        System.out.println("System in CASE 2 percolates: " + perc.percolates());
        // case 3 - percolates
        perc.open(2, 3);
        System.out.println("System in CASE 3 percolates: " + perc.percolates());

        int newSize = 10;
        Percolation newPerc = new Percolation(size);
        perc.open(1, 3);
        perc.open(1, 3);
        perc.open(1, 3);


    }
}
