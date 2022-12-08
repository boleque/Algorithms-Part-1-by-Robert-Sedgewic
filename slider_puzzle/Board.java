/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;

public final class Board {

    private final int n;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    private Board copyWithSwappedTiles(int i0, int j0, int i1, int j1) {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        // swap
        int tmp = newTiles[i0][j0];
        newTiles[i0][j0] = newTiles[i1][j1];
        newTiles[i1][j1] = tmp;
        return new Board(newTiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(n);
        ret.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ret.append(" ");
                ret.append(tiles[i][j]);
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    private int to1DimCoord(int row, int col) {
        return (n * row + col + 1);
    }

    private int[] to2DimCoord(int value) {
        if (value == 0) {
            return new int[] {0, 0};
        }
        int row = value % n == 0 ? (value / n) - 1 : (value / n);
        int col = value - n * row - 1;
        return new int[] {row, col};
    }

    private int[] getBlankTileCoord() {
        int[] blankTile = new int[2];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankTile[0] = i;
                    blankTile[1] = j;
                }
            }
        }
        return blankTile;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int ret = 0;
        int maxValue = n * n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // last tile is for null
                if (to1DimCoord(i, j) == maxValue) break;
                int value = tiles[i][j];
                if (value == 0) {
                    if (i != (n - 1) || j != (n - 1)) {
                        ret += 1;
                    }
                    continue;
                }
                int[] coords = to2DimCoord(value);
                int dist = Math.abs(coords[0] - i) + Math.abs(coords[1] - j);
                if (dist != 0) {
                    ret += 1;
                }
            }
        }
        return ret;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int ret = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];
                if (value == 0) continue;
                int[] coords = to2DimCoord(value);
                int dist = Math.abs(coords[0] - i) + Math.abs(coords[1] - j);
                ret += dist;
            }
        }
        return ret;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Board)) return false;
        Board otherBoard = (Board) other;
        return (this.n == otherBoard.n) && Arrays.deepEquals(tiles, otherBoard.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();
        int[] blankTile = getBlankTileCoord();
        int row = blankTile[0];
        int col = blankTile[1];
        if (row == 0) {
            neighbors.add(copyWithSwappedTiles( row, col , row + 1, col));
        }
        else if (row == n - 1) {
            neighbors.add(copyWithSwappedTiles(row, col, row - 1, col));
        }
        else {
            neighbors.add(copyWithSwappedTiles(row, col, row + 1, col));
            neighbors.add(copyWithSwappedTiles(row, col, row - 1, col));
        }
        if (col == 0) {
            neighbors.add(copyWithSwappedTiles(row, col, row, col + 1));
        }
        else if (col == n - 1) {
            neighbors.add(copyWithSwappedTiles(row, col, row, col - 1));
        }
        else {
            neighbors.add(copyWithSwappedTiles(row, col, row, col + 1));
            neighbors.add(copyWithSwappedTiles(row, col, row, col - 1));
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // twin is a board that is obtained by exchanging any pair of tiles
        // empty square is not a tile.
        Boolean findFirstTile = false;
        int i0 = 0, j0 = 0, i1 = 0, j1 = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {

                    if (!findFirstTile) {
                        i0 = i;
                        j0 = j;
                        findFirstTile = true;
                        continue;
                    }
                    i1 = i;
                    j1 = j;
                    break;
                }
            }
        }
        return copyWithSwappedTiles(i0, j0, i1, j1);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                {0, 2},
                {1, 3}
        };
        Board board = new Board(tiles);
        System.out.println("Original: " + board);
        System.out.println("Twin: " + board.twin());
        Iterable<Board> neighbors = board.neighbors();
        for(Board b: neighbors){
             System.out.println("Neighbor: " + b.toString());
        }
    }
}
