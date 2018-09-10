import java.util.ArrayList;

public class Pawn extends PieceBase {

    Pawn(int teamNumber, String pieceIdentifier, int row, int col) {
        super(teamNumber, pieceIdentifier, row, col);
    }

    @Override
    public boolean isMoveLegal(Board board, Move move) {
        if (!super.isMoveLegal(board, move)) {
            return false;
        }

        this.setPossibleMoves(new ArrayList<>());
        return this.checkBasicMovement(board, move);
    }

    @Override
    protected void createList(Board board){
        this.setPossibleMoves(new ArrayList<>());
        if (this.getTeamNumber() == 1) {
            this.createListForBlackPawn(board);
        }
        else {
            createListForWhitePawn(board);
        }
    }

    private boolean checkBasicMovement(Board board, Move move) {
        PieceBase pawn = board.getField(move.getStartRow(), move.getStartColumn());
        if (pawn.getTeamNumber() == 1) {
            if (this.checkForBlackPawns(move)) {
                this.createListForBlackPawn(board);
                for (Move moveFromList : this.getPossibleMoves()) {
                    if (moveFromList.equals(move)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            if (this.checkForWhitePawn(move)) {
                this.createListForWhitePawn(board);
                for (Move moveFromList : this.getPossibleMoves()) {
                    if (moveFromList.equals(move)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private void createListForBlackPawn(Board board) {
        if (this.row == 1) {
            if (board.getField(row + 1, col) == null && board.getField(row + 2, col) == null) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row + 2, this.col, false, null, 0));
            }
        }

        if (this.row + 1 < 8) {
            if (board.getField(this.row + 1, this.col) == null) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row + 1, this.col, false, null, 0));
            }

            if (col + 1 < 8 && board.getField(this.row + 1, this.col + 1) != null
                    && board.getField(this.row + 1, this.col + 1).getTeamNumber() != this.getTeamNumber()) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row + 1, this.col + 1, false, null, 0));
            }

            if (col - 1 >= 0 && board.getField(this.row + 1, this.col - 1) != null
                    && board.getField(this.row + 1, this.col - 1).getTeamNumber() != this.getTeamNumber()) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row + 1, this.col - 1, false, null, 0));
            }
        }
    }

    private void createListForWhitePawn(Board board) {
        if (this.row == 6) {
            if (board.getField(row - 1, col) == null && board.getField(row - 2, col) == null) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row - 2, this.col, false, null, 0));
            }
        }

        if (this.row - 1 >= 0) {
            if (board.getField(this.row - 1, this.col) == null) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row - 1, this.col, false, null, 0));
            }

            if (col + 1 < 8 && board.getField(this.row - 1, this.col + 1) != null
                    && board.getField(this.row - 1, this.col + 1).getTeamNumber() != this.getTeamNumber()) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row - 1, this.col + 1, false, null, 0));
            }

            if (col - 1 >= 0 && board.getField(this.row - 1, this.col - 1) != null
                    && board.getField(this.row - 1, this.col - 1).getTeamNumber() != this.getTeamNumber()) {
                this.getPossibleMoves().add(new Move(this.row, this.col, this.row - 1, this.col - 1, false, null, 0));
            }
        }
    }


    // Checks that pawn only moves down
    private boolean checkForWhitePawn(Move move) {

        return move.getStartRow() > move.getDestinationRow();
    }

    // Checks that pawn only moves up
    private boolean checkForBlackPawns(Move move) {

        return move.getStartRow() < move.getDestinationRow();
    }
}
