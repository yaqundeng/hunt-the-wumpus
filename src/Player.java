public class Player {
    mazeCommon maze;

    public Player(int row, int col, TypeOfMaze type, int remainingWall) {
        if (type == TypeOfMaze.PERFECT) {
            throw new IllegalArgumentException("Remaining walls should be neglected.");
        }
        if (type == TypeOfMaze.NONWRAPPED) {
            this.maze = new nonWrapping(row, col, remainingWall);
        } else if (type == TypeOfMaze.WRAPPED) {
            this.maze = new wrapping(row, col, remainingWall);
        }

    }

    public Player(int row, int col, TypeOfMaze type) {
        if (type != TypeOfMaze.PERFECT) {
            throw new IllegalArgumentException("Please specify the remaining walls.");
        }
        this.maze = new perfect(row, col, row * col - row - col + 1);

    }

    public void setStart(int row, int col) {
        this.maze.setStart(row, col);
    }

    public void setGoal(int row, int col) {
        this.maze.setGoal(row, col);
    }

    public mazeCommon getMaze() {
        return this.maze;
    }

    public static void main(String[] args) {
//        Player p = new Player(5, 6, TypeOfMaze.PERFECT);
//        p.getMaze().setStart(0, 2);
//        p.getMaze().setGoal(4, 5);
//        p.getMaze().assignCoins();
//        p.getMaze().bringDown();
//        p.getMaze().printMap();
//        p.getMaze().makeMoves();
        Player p = new Player(2, 3, TypeOfMaze.WRAPPED, 5);
        p.setStart(0, 1);
        p.setGoal(1, 2);
        p.getMaze().assignCoins();
        p.getMaze().bringDown();
        p.getMaze().printMap();
        p.getMaze().makeMoves();
    }
}
