import java.awt.*;
import java.util.Scanner;

/*************************************************************************
 *  Author: Dr E Kapetanios
 *  Last update: 22-02-2017
 *  w1583389- Umar N. Packeer
 *************************************************************************/

public class PathFindingOnSquaredGrid {

    private static int startYVal;
    private static int startXVal;
    private static int endYVal;
    private static int endXVal;

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;
    
        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }
    	
        return full;
    }
    
    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i+1, j);   // down
        flow(open, full, i, j+1);   // right
        flow(open, full, i, j-1);   // left
        flow(open, full, i-1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;
    	
        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N-1][j]) return true;
        }
    	
        return false;
    }
    
 // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;
    	
        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
        	if (full[N-1][j]) {
        		// StdOut.println("Hello");
        		directPerc = 1;
        		int rowabove = N-2;
        		for (int i = rowabove; i >= 0; i--) {
        			if (full[i][j]) {
        				// StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
        				directPerc++;
        			}
        			else break;
        		}
        	}
        }
    	
        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true; 
        else return false;
    }
    
    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which) {
                    StdDraw.setPenColor(Color.black);
                    StdDraw.square(j, N - i - 1, .5);
                } else {
                    StdDraw.setPenColor(Color.black);
                    StdDraw.filledSquare(j, N - i - 1, .5);
                }
    }

    // Draw the NxN boolean matrix to standard draw, including the points A (startXVal, startYVal) and B (endXVal, endYVal) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int startX, int startY, int endX, int endY) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {
                    // Draw a circle to indicate the starting cell.
                    if ((i == startX && j == startY)) {
                        StdDraw.setPenColor(Color.black);
                        StdDraw.filledCircle(j, N - i - 1, .1);
                    } else if ((i == endX && j == endY)) {
                        // Draw a rectangle to indicate the ending cell.
                        StdDraw.setPenColor(Color.red);
                        StdDraw.filledCircle(j, N -i - 1, 0.1);
                    }
                }
            }
        }
    }
    
    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    public static void tester(int number, boolean[][] randomlyGenMatrix, int[][] blockedCellsArray) {

        if (startYVal >= 0 && startYVal < number) {

            if (startXVal >= 0 && startXVal < number) {

                if (endYVal >= 0 && endYVal < number) {

                    if (endXVal >= 0 && endXVal < number) {
                        // Calls the following two methods only if the x and y values are within the accepted range.
                        show(randomlyGenMatrix, true, startYVal, startXVal, endYVal, endXVal);

                        Scanner in = new Scanner(System.in);

                        System.out.println();
                        System.out.println("Select the method needed to find the shortest path from");
                        System.out.println("1 for Manhattan Distance");
                        System.out.println("2 for Euclidean Distance");
                        System.out.println("3 for Chebyshev Distance");
                        System.out.println("4 to Exit");

                        int selection1 = in.nextInt();

                        if (selection1 == 1) {

                            // Start the stopwatch to measure the time taken
                            Stopwatch timeToFindPath = new Stopwatch();

                            Logic.test(number, number, startYVal, startXVal, endYVal, endXVal, blockedCellsArray, selection1);

                            // Call this method again to display the start and end points over the filled circles.
                            show(randomlyGenMatrix, true, startYVal, startXVal, endYVal, endXVal);

                            // Call this method again to display the lines of the grid over the filled circle.
                            show(randomlyGenMatrix, true);

                            System.out.println("Total cost is " + Logic.manhattanHeuristic(startXVal, startYVal, endXVal, endYVal));

                            // Stop clock
                            double timeTaken = timeToFindPath.elapsedTime();

                            System.out.println("Total time taken to find path : " + timeTaken);

                        }else if (selection1 == 2) {
                            // Start the stopwatch to measure the time taken
                            Stopwatch timeToFindPath = new Stopwatch();

                            Logic.test(number, number, startYVal, startXVal, endYVal, endXVal, blockedCellsArray, selection1);

                            // Call this method again to display the start and end points over the filled circles.
                            show(randomlyGenMatrix, true, startYVal, startXVal, endYVal, endXVal);

                            // Call this method again to display the lines of the grid over the filled circle.
                            show(randomlyGenMatrix, true);

                            System.out.println("Total cost is " + Logic.euclideanHeuristic(startXVal, startYVal, endXVal, endYVal));

                            // Stop clock
                            double timeTaken = timeToFindPath.elapsedTime();

                            System.out.println("Total time taken to find path : " + timeTaken);

                        }else if (selection1 == 3) {
                            // Start the stopwatch to measure the time taken
                            Stopwatch timeToFindPath = new Stopwatch();

                            Logic.test(number, number, startYVal, startXVal, endYVal, endXVal, blockedCellsArray, selection1);

                            // Call this method again to display the start and end points over the filled circles.
                            show(randomlyGenMatrix, true, startYVal, startXVal, endYVal, endXVal);

                            // Call this method again to display the lines of the grid over the filled circle.
                            show(randomlyGenMatrix, true);

                            System.out.println("Total cost is " + Logic.chebyshevHeuristic(startXVal, startYVal, endXVal, endYVal));

                            // Stop clock
                            double timeTaken = timeToFindPath.elapsedTime();

                            System.out.println("Total time taken to find path : " + timeTaken);
                        }
                        else if (selection1 == 4) {
                            System.exit(0);
                        }


                    } else {
                        System.out.println("Please enter a given number ");
                    }
                } else {
                    System.out.println("Please enter a given number");
                }
            } else {
                System.out.println("Please enter a given number");
            }
        } else {
            System.out.println("Please enter a given number ");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the required size of the grid: ");
        int number = scanner.nextInt();
        boolean[][] randomlyGenMatrix = random(number, 0.6);
        int[][] blockedNodesArray = new int[number][number];

        for (int i = 0; i < randomlyGenMatrix.length; i++) {
            for (int j = 0; j < randomlyGenMatrix[i].length; j++) {
                blockedNodesArray[i][j] = 1;
                if (randomlyGenMatrix[i][j] == false){
                    blockedNodesArray[i][j] = 0;
                }
            }
        }
    	
    	StdArrayIO.print(randomlyGenMatrix);
    	show(randomlyGenMatrix, true);
    	
    	System.out.println();
    	System.out.println("The system percolates: " + percolates(randomlyGenMatrix));
    	
    	System.out.println();
    	System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
    	System.out.println();

    	Scanner in = new Scanner(System.in);
        System.out.println("Enter y value for for A : ");
        startYVal = in.nextInt();

        System.out.println("Enter x value for for A : ");
        startXVal = in.nextInt();

        System.out.println("Enter y value for for B : ");
        endYVal = in.nextInt();

        System.out.println("Enter x value for for B : ");
        endXVal = in.nextInt();

        tester(number, randomlyGenMatrix, blockedNodesArray);
    }
}



