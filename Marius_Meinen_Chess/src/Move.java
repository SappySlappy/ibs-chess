public class Move {
    private int startRow;
    private int startColumn;
    private int destinationRow;
    private int destinationColumn;
    private boolean pawnTraded;
    private String pieceForPawn;
    private int teamNumber;

    public Move(int startRow, int startColumn, int destinationRow, int destinationColumn, boolean pawnTraded, String pieceForPawn, int teamNumber) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.pawnTraded = pawnTraded;
        this.pieceForPawn = pieceForPawn;
        this.teamNumber = teamNumber;
    }

    @Override
    public boolean equals(Object move) {
        if (getClass() != move.getClass()) {
            return false;
        }

        Move newMove = (Move) move;
        return newMove.startRow == this.startRow
                && newMove.getStartColumn() == this.getStartColumn()
                && newMove.getDestinationRow() == this.getDestinationRow()
                && newMove.getDestinationColumn() == this.getDestinationColumn();
    }

    public int getStartRow() {
        return this.startRow;
    }

    public int getStartColumn() {
        return this.startColumn;
    }

    public int getDestinationRow() {
        return this.destinationRow;
    }

    public int getDestinationColumn() {
        return this.destinationColumn;
    }

    public boolean getPawnTraded() {
        return this.pawnTraded;
    }

    public String getPieceForPawn() {
        return this.pieceForPawn;
    }

    public int getTeamNumber() {
        return this.teamNumber;
    }
}
