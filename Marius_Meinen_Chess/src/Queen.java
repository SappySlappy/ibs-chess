import java.util.ArrayList;

public class Queen extends PieceBase {

    Queen(int teamNumber, String pieceIdentifier, int row, int col) {
        super(teamNumber, pieceIdentifier, row, col);
    }

    @Override
    public boolean isMoveLegal(Board board, Move move) {
        if (!super.isMoveLegal(board, move)) {
            return false;
        }

        this.possibleMoves = new ArrayList<>();
        PieceBase piece = new Bishop(this.getTeamNumber(), "", this.row, this.col);
        if (!piece.isMoveLegal(board, move)) {
            piece = new Rook(this.getTeamNumber(), "", this.row, this.col);
            return piece.isMoveLegal(board, move);
        }

        return true;
    }

    @Override
    protected void createList(Board board){
        this.possibleMoves = new ArrayList<>();
        PieceBase piece = new Bishop(this.getTeamNumber(), "", this.row, this.col);
        this.possibleMoves = piece.getListOfMoves(board);
        piece = new Rook(this.getTeamNumber(), "", this.row, this.col);
        this.possibleMoves.addAll(piece.getListOfMoves(board));
    }

}
