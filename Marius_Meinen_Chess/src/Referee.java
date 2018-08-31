class Referee {

    private Board board;

    Referee(Board board){
        this.board = board;
        this.board.printBoard();
    }

    boolean checkNewMove(Move move){

        PieceBase piece = null;
        if (move.getStartRow() < 8 && move.getStartRow() >= 0 && move.getStartColumn() < 8 && move.getStartColumn() >= 0){
            piece = board.getField(move.getStartRow(),move.getStartColumn());
        }

        if (piece == null){
            return true;
        }
        if (piece.getTeamNumber() != move.getTeamNumber()){
            return true;
        }

        if (!piece.isMoveLegal(board,move)){
            return true;
        }

        this.board.executeMove(move);
        this.board.printBoard();
        return false;
    }

    boolean checkIfPiecesLeft(Player currentPlayer) {
        PieceBase piece;
        for (int i = 0; i<8;i++){
            for (int j = 0;j<8;j++){
                piece = this.board.getField(i,j);
                if (piece != null && piece.getTeamNumber() == currentPlayer.getTeamNumber()){
                    return false;
                }
            }
        }
        return true;
    }
}
