public class Move {
    private int startRow;
    private int startColumn;
    private int destinationRow;
    private int destinationColumn;
    private boolean pawnTraded;
    private String pieceForPawn;
    private int teamNumber;

    public Move(int startRow, int startColumn, int destinationRow, int destinationColumn, boolean pawnTraded, String pieceForPawn, int teamNumber){
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.pawnTraded = pawnTraded;
        this.pieceForPawn = pieceForPawn;
        this.teamNumber = teamNumber;
    }

    @Override
    public boolean equals(Object move){
        Move newMove = (Move)move;
        return newMove != null
                && newMove.startRow == this.startRow
                && newMove.getStartColumn() == this.getStartColumn()
                && newMove.getDestinationRow() == this.getDestinationRow()
                && newMove.getDestinationColumn() == this.getDestinationColumn();
    }

    int getStartRow(){
        return this.startRow;
    }

    int getStartColumn(){
        return this.startColumn;
    }

    int getDestinationRow(){
        return this.destinationRow;
    }

    int getDestinationColumn(){
        return this.destinationColumn;
    }

    boolean getPawnTraded(){
        return this.pawnTraded;
    }

    String getPieceForPawn(){
        return this.pieceForPawn;
    }

    int getTeamNumber(){return this.teamNumber;}
}
