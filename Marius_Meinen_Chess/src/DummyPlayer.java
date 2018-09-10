import java.util.ArrayList;

public class DummyPlayer extends PlayerBase{

    private ArrayList<Move> allPossibleMoves;

    public DummyPlayer(Board board,String name, int teamNumber){
        super(board,name,teamNumber);
    }

    @Override
    Move makeAMove() {
        for (int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
                PieceBase piece = this.board.getField(i,j);
                if (piece != null){
                    piece.getListOfMoves(board);

                    return null;
                }
            }
        }
        return null;
    }

    @Override
    Move buildMove(int startRow, int startColumn, int destinationRow, int destinationColumn) {
        return null;
    }
}
