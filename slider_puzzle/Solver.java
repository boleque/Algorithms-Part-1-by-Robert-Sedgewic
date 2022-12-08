/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private Iterable<Board> solution;
    private int moves = -1;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        int manhattan;
        SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = board.manhattan() + moves;
        }
        public int getManhattan() {
            return this.manhattan;
        }

        public int compareTo(SearchNode o) {
            int priority1 = getManhattan();
            int priority2 = o.getManhattan();
            if (priority1 < priority2) return -1;
            if (priority1 > priority2) return +1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.solution = solve(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        if (moves == -1) {
            for (Board board : this.solution())
                moves++;
        }
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private Iterable<Board> solve(Board board) {
        MinPQ<SearchNode> initialPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        SearchNode searchNode = new SearchNode(board, 0, null);
        SearchNode twinSearchNode = new SearchNode(board.twin(), 0, null);

        initialPQ.insert(searchNode);
        twinPQ.insert(twinSearchNode);
        Boolean isInitialSolvable = false;
        Boolean isTwinSolvable = false;
        while (!isInitialSolvable && !isTwinSolvable) {
            searchNode = initialPQ.delMin();
            isInitialSolvable = searchNode.board.isGoal();
            for (Board b : searchNode.board.neighbors()) {
                if (searchNode.prev == null || !b.equals(searchNode.prev.board)) {
                    SearchNode childNode = new SearchNode(b, searchNode.moves + 1, searchNode);
                    initialPQ.insert(childNode);
                }
            }
            twinSearchNode = twinPQ.delMin();
            isTwinSolvable = twinSearchNode.board.isGoal();
            for (Board b : twinSearchNode.board.neighbors()) {
                if (twinSearchNode.prev == null || !b.equals(twinSearchNode.prev.board)) {
                    SearchNode childNode = new SearchNode(b, twinSearchNode.moves + 1, twinSearchNode);
                    twinPQ.insert(childNode);
                }
            }
        }
        if (isTwinSolvable)
            return null;
        LinkedList<Board> solution = new LinkedList<>();
        while (searchNode != null) {
            solution.addFirst(searchNode.board);
            searchNode = searchNode.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
