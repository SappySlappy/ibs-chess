public class Board {
    private PieceBase[][] board;

    public Board() {
        this.board = new PieceBase[8][8];
        this.initializeBlackPieces();
        this.initializeWhitePieces();
    }

    public void executeMove(Move move) {
        if (!(move.getDestinationRow() < 0 || move.getStartRow() < 0 || move.getDestinationColumn() < 0 || move.getStartColumn() < 0
                || move.getDestinationRow() > 7 || move.getStartRow() > 7 || move.getDestinationColumn() > 7 || move.getStartColumn() > 7)) {
            //set piece on new position
            this.board[move.getDestinationRow()][move.getDestinationColumn()] = this.board[move.getStartRow()][move.getStartColumn()];
            //set row and col of piece
            this.board[move.getDestinationRow()][move.getDestinationColumn()].setNewField(move.getDestinationRow(), move.getDestinationColumn());
            //set original position to null
            this.board[move.getStartRow()][move.getStartColumn()] = null;
            //Check if pawn is traded
            this.checkIfPawnIsTraded(move);
        }
    }

    private void checkIfPawnIsTraded(Move move) {
        if (move.getPawnTraded()) {
            PieceBase pieceForPawn;
            String colour = move.getTeamNumber() == 1 ? "S" : "W";
            switch (move.getPieceForPawn()) {
                case "Q":
                    pieceForPawn = new Queen(move.getTeamNumber(), colour + move.getPieceForPawn(), move.getDestinationRow(), move.getDestinationColumn());
                    this.setField(pieceForPawn, move.getDestinationRow(), move.getDestinationColumn());
                    break;
                case "T":
                    pieceForPawn = new Rook(move.getTeamNumber(), colour + move.getPieceForPawn(), move.getDestinationRow(), move.getDestinationColumn());
                    this.setField(pieceForPawn, move.getDestinationRow(), move.getDestinationColumn());
                    break;
                case "S":
                    pieceForPawn = new Knight(move.getTeamNumber(), colour + move.getPieceForPawn(), move.getDestinationRow(), move.getDestinationColumn());
                    this.setField(pieceForPawn, move.getDestinationRow(), move.getDestinationColumn());
                    break;
                case "L":
                    pieceForPawn = new Bishop(move.getTeamNumber(), colour + move.getPieceForPawn(), move.getDestinationRow(), move.getDestinationColumn());
                    this.setField(pieceForPawn, move.getDestinationRow(), move.getDestinationColumn());
                    break;
            }
        }
    }

    public PieceBase getField(int row, int column) {
        return board[row][column];
    }

    public void setField(PieceBase piece, int row, int col) {
        this.board[row][col] = piece;
    }

    private void initializeBlackPieces() {
        board[0][0] = new Rook(1, "ST", 0, 0);
        board[0][1] = new Knight(1, "SS", 0, 1);
        board[0][2] = new Bishop(1, "SL", 0, 2);
        board[0][3] = new King(1, "SK", 0, 3);
        board[0][4] = new Queen(1, "SQ", 0, 4);
        board[0][5] = new Bishop(1, "SL", 0, 5);
        board[0][6] = new Knight(1, "SS", 0, 6);
        board[0][7] = new Rook(1, "ST", 0, 7);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(1, "SP", 1, i);
        }

    }

    private void initializeWhitePieces() {
        board[7][0] = new Rook(2, "WT", 7, 0);
        board[7][1] = new Knight(2, "WS", 7, 1);
        board[7][2] = new Bishop(2, "WL", 7, 2);
        board[7][3] = new King(2, "WK", 7, 3);
        board[7][4] = new Queen(2, "WQ", 7, 4);
        board[7][5] = new Bishop(2, "WL", 7, 5);
        board[7][6] = new Knight(2, "WS", 7, 6);
        board[7][7] = new Rook(2, "WT", 7, 7);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(2, "WP", 6, i);
        }
    }
}
