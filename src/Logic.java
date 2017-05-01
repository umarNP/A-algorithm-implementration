import java.awt.*;
import java.util.PriorityQueue;

/*w1583389- Umar N. Packeer
*/
public class Logic {

    private static int sI;
    private static int sJ;
    private static int eI;
    private static int eJ;
    private static final int finalCost = 1;
    private static Node[][] grid;
    private static boolean visited[][];
    private static PriorityQueue<Node> open;

    static class Node {
        int manhattanHCost = 0;
        int euclideanHCost = 0;
        int chebyshevHCost = 0;
        int nextNodeCost = Logic.finalCost;
        int finalCost = 0;
        int i;
        int j;
        Node parent;

        Node(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    //setting blocked nodes
    public static void setBlockedNode(int i, int j) {
        grid[i][j] = null;
    }

    //Setting starting node
    public static void setStartNode(int i, int j) {
        sI = i;
        sJ = j;
    }

    //Setting ending node
    public static void setEndNode(int i, int j) {
        eI = i;
        eJ = j;
    }

    //Finding the path
    private static void selectedPath(Color lineColor, int xOfGrid, int yOfGrid) {

        if (visited[eI][eJ]) {
            //Tracing back the path
            System.out.println("Path: ");
            Node current1 = grid[eI][eJ];
            Node current2 = grid[eI][eJ];

            System.out.print(current1.i + "," + current1.j);

            // Drawing the path
            while (current2.parent != null) {

                StdDraw.setPenColor(lineColor);
                StdDraw.line(current2.j, xOfGrid - current2.i - 1, current2.parent.j, xOfGrid - current2.parent.i - 1);

                current2 = current2.parent;
            }

            System.out.println();
        } else System.out.println("No possible path");
    }

    //Calculating cost
    public static void test(int x, int y, int iStart, int jStart, int iEnd, int jEnd, int[][] blockedCellsArray, int hSelection) {

        grid = new Node[x][y];
        visited = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Node c1 = (Node) o1;
            Node c2 = (Node) o2;

            return c1.finalCost < c2.finalCost ? -1 :
                    c1.finalCost > c2.finalCost ? 1 : 0;
        });
        // Set starting node
        setStartNode(iStart, jStart);  //Setting to 0,0 by default. Will be useful for the UI part

        // Set ending node
        setEndNode(iEnd, jEnd);

        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                grid[i][j] = new Node(i, j);

                // Calculating heuristic costs
                int manhattan = manhattanHeuristic(i, j, eI, eJ);
                int euclidean = euclideanHeuristic(i, j, eI, eJ);
                int chebyshev = chebyshevHeuristic(i, j, eI, eJ);

                grid[i][j].manhattanHCost = manhattan;
                grid[i][j].euclideanHCost = euclidean;
                grid[i][j].chebyshevHCost = chebyshev;
            }
        }

        grid[iStart][jStart].finalCost = 0;

        // Marking blocked nodes as null
        for (int i = 0; i < blockedCellsArray.length; ++i) {
            for (int j = 0; j < blockedCellsArray.length; ++j) {
                if (blockedCellsArray[i][j] == 0) {
                    setBlockedNode(i, j);
                }
            }
        }

        // Calling the Algo method.
        Algo(x, hSelection);

        // Display grid with blocks, start and end points.
        System.out.println();
        System.out.println("Grid: ");
        System.out.println();
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                if (i == iStart && j == jStart) {
                    System.out.print("S  ");
                } else if (i == iEnd && j == jEnd) {
                    System.out.print("E  ");
                } else if (grid[i][j] != null) {
                    System.out.printf("%-2d ", 0);
                } else System.out.print("-- ");
            }
            System.out.println();
        }
        System.out.println();

        // Manhattan
        if (hSelection == 1) {

            System.out.println("\nManhattan costs for nodes: ");
            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < x; ++j) {
                    if (grid[i][j] != null) {
                        System.out.printf("%-3d ", grid[i][j].manhattanHCost);
                    } else System.out.print("--  ");
                }
                System.out.println();
            }
            System.out.println();


            System.out.println("\nTotal cost with Manhattan: ");
            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < x; ++j) {
                    if (grid[i][j] != null) {
                        System.out.printf("%-3d ", grid[i][j].finalCost);
                    } else System.out.print("--  ");
                }
                System.out.println();
            }
            System.out.println();

            // drawing the path.
            selectedPath(Color.blue, x, y);

            // Euclidean
        } else if (hSelection == 2) {

            System.out.println("\nEuclidean costs for nodes: ");
            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < x; ++j) {
                    if (grid[i][j] != null) {
                        System.out.printf("%-3d ", grid[i][j].euclideanHCost);
                    } else System.out.print("--  ");
                }
                System.out.println();
            }
            System.out.println();


            System.out.println("\nTotal cost with Euclidean: ");
            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < x; ++j) {
                    if (grid[i][j] != null) {
                        System.out.printf("%-3d ", grid[i][j].finalCost);
                    } else System.out.print("--  ");
                }
                System.out.println();
            }
            System.out.println();

            // Drawing the path.
            selectedPath(Color.blue, x, y);

            // Chebyshev
        } else if (hSelection == 3) {

            System.out.println("\nChebyshev costs for nodes: ");
            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < x; ++j) {
                    if (grid[i][j] != null) {
                        System.out.printf("%-3d ", grid[i][j].chebyshevHCost);
                    } else System.out.print("--  ");
                }
                System.out.println();
            }
            System.out.println();


            System.out.println("\nTotal cost with Chebyshev: ");
            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < x; ++j) {
                    if (grid[i][j] != null) {
                        System.out.printf("%-3d ", grid[i][j].finalCost);
                    } else System.out.print("--  ");
                }
                System.out.println();
            }
            System.out.println();

            // Calls the method to draw the path.
            selectedPath(Color.blue, x, y);
        }
    }

    //Calculating heuristic of manhattan
    public static int manhattanHeuristic(int currentI, int currentJ, int iEnd, int jEnd) {

        // Manhattan formula = |x1 - x2| + |y1 - y2|
        int manhattan = Math.abs(currentI - iEnd) + Math.abs(currentJ - jEnd);
        return manhattan;
    }

    //Calculating heuristic of euclidean
    public static int euclideanHeuristic(int currentI, int currentJ, int iEnd, int jEnd) {

        // Euclidean formula = ((x1 - x2)^2 + (y1 - y2)^2)^1/2
        int euclidean = (int) Math.sqrt(Math.pow((currentI - iEnd), 2) + Math.pow((currentJ - jEnd), 2))*14;
        double euclidean =euclidean/10;

        return euclidean;
    }

    //Calculating heuristic of chebyshev
    public static int chebyshevHeuristic(int currentI, int currentJ, int iEnd, int jEnd) {

        // Chebyshev formula = max(|x1 - x2|, |y1 - y2|)
        int chebyshev = Math.max(Math.abs(currentI - iEnd), Math.abs(currentJ - jEnd));

        return chebyshev;
    }

    //Calculating G-cost
    public static void Algo(int sizeOfGrid, int heuristicSelection) {

        // Add the start location to the open queue.
        open.add(grid[sI][sJ]);

        Node currentNode;

        while (true) {
            // Retrieve and remove the head from the queue.
            currentNode = open.poll();

            // If the currentNode is null, break.
            if (currentNode == null)
                break;

            // Adding the current node to the visited array
            visited[currentNode.i][currentNode.j] = true;

            //Returning the current node if it is the end node
            if (currentNode.equals(grid[eI][eJ])) {

                // Line drawn from the last node
                StdDraw.line(currentNode.j, sizeOfGrid - currentNode.i - 1, currentNode.parent.j, sizeOfGrid - currentNode.parent.i - 1);

                return;
            }

            Node nodeToMove;
            if (heuristicSelection == 1) {
                // Move left
                if (currentNode.i - 1 >= 0) {
                    nodeToMove = grid[currentNode.i - 1][currentNode.j];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                }

                // Move up
                if (currentNode.j - 1 >= 0) {
                    nodeToMove = grid[currentNode.i][currentNode.j - 1];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                }

                // Move down
                if (currentNode.j + 1 < grid[0].length) {
                    nodeToMove = grid[currentNode.i][currentNode.j + 1];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                }

                // Move right
                if (currentNode.i + 1 < grid.length) {
                    nodeToMove = grid[currentNode.i + 1][currentNode.j];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                }

            } else if (heuristicSelection == 2) {
                // Move left
                if (currentNode.i - 1 >= 0) {
                    if (currentNode.j - 1 >= 0) {
                        nodeToMove = grid[currentNode.i - 1][currentNode.j - 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }

                    if (currentNode.j + 1 < grid.length) {
                        nodeToMove = grid[currentNode.i - 1][currentNode.j + 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }
                }

                // Move right
                if (currentNode.i + 1 < grid.length) {
                    if (currentNode.j - 1 >= 0) {
                        nodeToMove = grid[currentNode.i + 1][currentNode.j - 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }

                    if (currentNode.j + 1 < grid.length) {
                        nodeToMove = grid[currentNode.i + 1][currentNode.j + 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }

                }
            } else if(heuristicSelection == 3) {
                // Move left
                if (currentNode.i - 1 >= 0) {
                    nodeToMove = grid[currentNode.i - 1][currentNode.j];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                    if (currentNode.j - 1 >= 0) {
                        nodeToMove = grid[currentNode.i - 1][currentNode.j - 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }

                    if (currentNode.j + 1 < grid.length) {
                        nodeToMove = grid[currentNode.i - 1][currentNode.j + 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }
                }

                // Move up
                if (currentNode.j - 1 >= 0) {
                    nodeToMove = grid[currentNode.i][currentNode.j - 1];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                }

                // Move down
                if (currentNode.j + 1 < grid[0].length) {
                    nodeToMove = grid[currentNode.i][currentNode.j + 1];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);
                }

                // Move right
                if (currentNode.i + 1 < grid.length) {
                    nodeToMove = grid[currentNode.i + 1][currentNode.j];
                    calculateCost(currentNode, nodeToMove, heuristicSelection);

                    if (currentNode.j - 1 >= 0) {
                        nodeToMove = grid[currentNode.i + 1][currentNode.j - 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }

                    if (currentNode.j + 1 < grid.length) {
                        nodeToMove = grid[currentNode.i + 1][currentNode.j + 1];
                        calculateCost(currentNode, nodeToMove, heuristicSelection);
                    }

                }

            }

        }
    }

    //Calculating total cost
    public static void calculateCost(Node current, Node nextNode, int heuristicSelection) {


        if (nextNode == null || visited[nextNode.i][nextNode.j]) {
            return;
        }

        int finalCost = 0;

        if (heuristicSelection == 1) {
            finalCost = nextNode.manhattanHCost + nextNode.nextNodeCost + current.finalCost;
        } else if (heuristicSelection == 2) {
            finalCost = nextNode.euclideanHCost + nextNode.nextNodeCost + current.finalCost;
        } else if (heuristicSelection == 3) {
            finalCost = nextNode.chebyshevHCost + nextNode.nextNodeCost + current.finalCost;
        }

        boolean inOpen = open.contains(nextNode);
        if (!inOpen || finalCost < nextNode.finalCost) {
            nextNode.finalCost = finalCost;
            nextNode.parent = current;
            if (!inOpen) {
                open.add(nextNode);
            }
        }
    }

}


