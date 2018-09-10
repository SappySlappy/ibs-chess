import java.util.ArrayList;

public class Rook extends PieceBase {

    Rook(int teamNumber, String pieceIdentifier, int row, int col) {
        super(teamNumber, pieceIdentifier, row, col);
        this.setPossibleMoves(new ArrayList<>());
    }

    @Override
    public boolean isMoveLegal(Board board, Move move) {
        if (!super.isMoveLegal(board, move)) {
            return false;
        }

        this.setPossibleMoves(new ArrayList<>());
        this.createList(board);
        return getPossibleMoves().contains(move);

    }

    @Override
    protected void createList(Board board) {
        this.setPossibleMoves(new ArrayList<>());
        // moves to bottom
        if (row + 1 < 8) {
            for (int i = this.row + 1; i < 8; i++) {
                if (board.getField(i, this.col) == null) {
                    getPossibleMoves().add(new Move(this.row, this.col, i, this.col, false, null, 0));
                } else if (board.getField(i, this.col).getTeamNumber() != this.getTeamNumber()) {
                    getPossibleMoves().add(new Move(this.row, this.col, i, this.col, false, null, 0));
                    break;
                } else {
                    break;
                }
            }
        }
        // moves to top
        if (row - 1 >= 0) {
            for (int i = this.row - 1; i >= 0; i--) {
                if (board.getField(i, this.col) == null) {
                    getPossibleMoves().add(new Move(this.row, this.col, i, this.col, false, null, 0));
                } else if (board.getField(i, this.col).getTeamNumber() != this.getTeamNumber()) {
                    getPossibleMoves().add(new Move(this.row, this.col, i, this.col, false, null, 0));
                    break;
                } else {
                    break;
                }
            }
        }
        //moves to right
        if (col + 1 < 8) {
            for (int i = this.col + 1; i < 8; i++) {
                if (board.getField(this.row, i) == null) {
                    getPossibleMoves().add(new Move(this.row, this.col, this.row, i, false, null, 0));
                } else if (board.getField(this.row, i).getTeamNumber() != this.getTeamNumber()) {
                    getPossibleMoves().add(new Move(this.row, this.col, this.row, i, false, null, 0));
                    break;
                } else {
                    break;
                }
            }
        }
        //moves to left
        if (col - 1 >= 0) {
            for (int i = this.col - 1; i >= 0; i--) {
                if (board.getField(this.row, i) == null) {
                    getPossibleMoves().add(new Move(this.row, this.col, this.row, i, false, null, 0));
                } else if (board.getField(this.row, i).getTeamNumber() != this.getTeamNumber()) {
                    getPossibleMoves().add(new Move(this.row, this.col, this.row, i, false, null, 0));
                    break;
                } else {
                    break;
                }
            }
        }
    }
}
