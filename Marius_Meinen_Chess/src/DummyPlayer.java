import java.util.ArrayList;

class DummyPlayer extends PlayerBase{

    private ArrayList<Move> allPossibleMoves;

    DummyPlayer(Board board, String name, int teamNumber){
        super(board,name,teamNumber);
    }

    @Override
    Move makeAMove() {
        this.allPossibleMoves = new ArrayList<>();
        for (int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
                PieceBase piece = this.board.getField(i,j);
                if (piece != null && piece.getTeamNumber()==this.teamNumber){
                    this.allPossibleMoves.addAll(piece.getListOfMoves(board));
                }
            }
        }
        if (allPossibleMoves.size() != 0) {
            Move move = allPossibleMoves.get((int) (Math.random() * this.allPossibleMoves.size()));
            PieceBase piece = this.board.getField(move.getDestinationRow(),move.getDestinationColumn());
            if (piece instanceof Pawn && (move.getDestinationRow() == 0 || move.getDestinationRow() == 7)){
                return new Move(move.getStartRow(),move.getStartColumn(),move.getDestinationRow(),move.getDestinationColumn(),true,"Q",this.teamNumber);
            }
            return move;
        }
        else {
            return null;
        }
    }
}
