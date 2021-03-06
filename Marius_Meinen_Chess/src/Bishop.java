import java.util.ArrayList;

public class Bishop extends PieceBase {

    Bishop(int teamNumber, String pieceIdentifier, int row, int col) {
        super(teamNumber, pieceIdentifier, row, col);
    }

    @Override
    public boolean isMoveLegal(Board board, Move move) {
        if (!super.isMoveLegal(board, move)) {
            return false;
        }

        this.setPossibleMoves(new ArrayList<>());
        this.createList(board);
        return this.getPossibleMoves().contains(move);
    }

    @Override
    protected void createList(Board board) {
        this.setPossibleMoves(new ArrayList<>());
        int destinationCol = this.col + 1;
        checkMovementToRightDown(board, destinationCol, this.row + 1);

        destinationCol = this.col + 1;
        checkMovementToRightUp(board, destinationCol, this.row - 1);

        destinationCol = this.col - 1;
        checkMovementToLeftDown(board, destinationCol, this.row + 1);

        destinationCol = this.col - 1;
        checkMovementToLeftUp(board, destinationCol, this.row - 1);

    }

    private void checkMovementToRightDown(Board board, int destinationCol, int startRow) {
        if (startRow < 8 && startRow >= 0) {
            for (int i = startRow; i < 8; i++) {
                if (destinationCol < 8) {
                    if (board.getField(i, destinationCol) == null) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                        break;
                    } else {
                        break;
                    }
                    destinationCol++;
                }
            }
        }
    }

    private void checkMovementToRightUp(Board board, int destinationCol, int startRow) {
        if (startRow < 8 && startRow >= 0) {
            for (int i = startRow; i >= 0; i--) {
                if (destinationCol < 8) {
                    if (board.getField(i, destinationCol) == null) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                        break;
                    } else {
                        break;
                    }
                    destinationCol++;
                }
            }
        }
    }

    private void checkMovementToLeftUp(Board board, int destinationCol, int startRow) {
        if (startRow < 8 && startRow >= 0) {
            for (int i = startRow; i >= 0; i--) {
                if (destinationCol >= 0) {
                    if (board.getField(i, destinationCol) == null) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                        break;
                    } else {
                        break;
                    }
                    destinationCol--;
                }
            }
        }
    }

    private void checkMovementToLeftDown(Board board, int destinationCol, int startRow) {
        if (startRow < 8 && startRow >= 0) {
            for (int i = startRow; i < 8; i++) {
                if (destinationCol >= 0) {
                    if (board.getField(i, destinationCol) == null) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.getPossibleMoves().add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                        break;
                    } else {
                        break;
                    }
                    destinationCol--;
                }
            }
        }
    }
}
