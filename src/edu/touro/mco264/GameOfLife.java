package edu.touro.mco264;



public class GameOfLife {
    static final int SIZE = 10;
    boolean grid[][];

    static final boolean __ = false, XX = true;

    public GameOfLife()
    {
        grid = new boolean[SIZE][SIZE];
    }

    public void print()
    {
        for(int row=0;row<grid.length;row++)
        {
            System.out.print("|");
            for (int col=0;col<grid[row].length;col++)
            {
                System.out.print(grid[row][col] ? 'X' : ' ');
                System.out.print("|");
            }
            System.out.println();
            for (int col=0;col<grid[row].length * 2 + 1;col++){
                System.out.print('-');
            }
            System.out.println();
        }
    }

    public void setImage(Pattern pat)
    {
                 setImage(pat.bitMap, 3, 3);
    }

    private void setImage(boolean[][] bitMap, int baseRow, int baseCol) {
        for (int row = 0; row <  bitMap.length; row++)
        for (int col = 0; col < bitMap[row].length; col++)
        {
            grid[baseRow + row][baseCol + col] = bitMap[row][col];
        }
    }

    public boolean isAliveNextGeneration(int row, int col)
    {
        /*
            Any live cell with fewer than two live neighbours dies, as if by underpopulation.
            Any live cell with two or three live neighbours lives on to the next generation.
            Any live cell with more than three live neighbours dies, as if by overpopulation.
            Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
         */
        int neighborCount = neighborCount(row, col);
        boolean isAlive = grid[row][col];
        if (isAlive){
            switch(neighborCount){
                case 0:
                case 1:
                    return false;
                case 2:
                case 3:
                    return true;
                default:
                    return false;
            }
        }
        else{ // dead
            return neighborCount == 3;
        }
    }

    public int neighborCount(int targetRow, int targetCol) {
        int total = 0;
        for (int row = -1; row <= +1; row++)
        for (int col = -1; col <= +1; col++)
        {
            if (row==0 && col==0)
                continue;
            if (grid[targetRow+row][targetCol+col])
                total++;
        }
        return total;
    }

    public boolean nextGeneration()
    {
        boolean[][]nextGenGrid = new boolean[SIZE][SIZE];
        boolean anyAlive = false;

        for (int row = 1; row < grid.length-1; row++)
        for (int col = 1; col < grid[row].length-1; col++)
        {
            nextGenGrid[row][col] = isAliveNextGeneration(row,col);
            anyAlive |=  nextGenGrid[row][col];
        }
        grid = nextGenGrid;

        return anyAlive;
    }

    enum Pattern
    {
        Blinker(new boolean[][]
                {{__, XX, __},
                 {__, XX, __},
                 {__, XX, __}}

        ), Spaceship( new boolean[][] // TODO make into real space ship
                    {{__, XX, XX},
                    {__, XX, XX},
                    {__, XX, XX}}

    );


        final boolean[][] bitMap;

        Pattern(boolean[][] bm)
        {
            bitMap = bm;
        }

    }

    public static void main(String[] args) {
        GameOfLife gol = new GameOfLife();
        gol.setImage(Pattern.Blinker);
        gol.print();
        System.out.println();

        gol.nextGeneration();
        gol.print();
        System.out.println();

        gol.nextGeneration();
        gol.print();
    }


}
