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

        this.possibleMoves = new ArrayList<>();
        this.createList(board);
        return this.possibleMoves.contains(move);
    }

    private void createList(Board board) {
        //to bottom right
        int destinationCol = this.col + 1;
        checkMovementToRightDown(board, destinationCol, this.row + 1);

        //to top right
        destinationCol = this.col + 1;
        checkMovementToRightUp(board, destinationCol, this.row - 1);

        //to bottom left
        destinationCol = this.col - 1;
        checkMovementToLeftDown(board, destinationCol, this.row + 1);
        //to top left
        destinationCol = this.col - 1;
        checkMovementToLeftUp(board, destinationCol, this.row - 1);

    }

    private void checkMovementToRightDown(Board board, int destinationCol, int startRow) {
        if (startRow < 8 && startRow >= 0) {
            for (int i = startRow; i < 8; i++) {
                if (destinationCol < 8) {
                    if (board.getField(i, destinationCol) == null) {
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
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
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
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
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
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
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
                    } else if (board.getField(i, destinationCol) != null && board.getField(i, destinationCol).getTeamNumber() != this.getTeamNumber()) {
                        this.possibleMoves.add(new Move(this.row, this.col, i, destinationCol, false, null, 0));
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
