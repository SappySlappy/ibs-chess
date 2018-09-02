import java.util.ArrayList;

public class King extends PieceBase {

    King(int teamNumber, String pieceIdentifier, int row, int col) {
        super(teamNumber, pieceIdentifier, row, col);
    }

    @Override
    public boolean isMoveLegal(Board board, Move move) {
        if (!(super.isMoveLegal(board, move))) {
            return false;
        }

        this.possibleMoves = new ArrayList<>();
        this.createList(board);
        return this.possibleMoves.contains(move);
    }

    private void createList(Board board) {
        PieceBase destinationField;
        if (this.row + 1 < 8) {
            destinationField = board.getField(this.row + 1, this.col);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row + 1, this.col, false, null, 0));
            }
        }

        if (this.row + 1 < 8 && this.col - 1 >= 0) {
            destinationField = board.getField(this.row + 1, this.col - 1);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row + 1, this.col - 1, false, null, 0));
            }
        }

        if (this.col - 1 >= 0) {
            destinationField = board.getField(this.row, this.col - 1);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row, this.col - 1, false, null, 0));
            }
        }

        if (this.row - 1 >= 0 && this.col - 1 >= 0) {
            destinationField = board.getField(this.row - 1, this.col - 1);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row - 1, this.col - 1, false, null, 0));
            }
        }

        if (this.row - 1 >= 0) {
            destinationField = board.getField(this.row - 1, this.col);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row - 1, this.col, false, null, 0));
            }
        }

        if (this.row - 1 >= 0 && this.col + 1 < 8) {
            destinationField = board.getField(this.row - 1, this.col + 1);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row - 1, this.col + 1, false, null, 0));
            }
        }

        if (this.col + 1 < 8) {
            destinationField = board.getField(this.row, this.col + 1);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row, this.col + 1, false, null, 0));
            }
        }

        if (this.row + 1 < 8 && this.col + 1 < 8) {
            destinationField = board.getField(this.row + 1, this.col + 1);
            if (destinationField == null || destinationField.getTeamNumber() != this.getTeamNumber()) {
                this.possibleMoves.add(new Move(this.row, this.col, this.row + 1, this.col + 1, false, null, 0));
            }
        }
    }
}
