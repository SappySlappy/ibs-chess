public class DummyPlayer extends PlayerBase{

    public DummyPlayer(Board board,String name, int teamNumber){
        super(board,name,teamNumber);
    }

    @Override
    Move makeAMove() {
        return null;
    }

    @Override
    Move buildMove(int startRow, int startColumn, int destinationRow, int destinationColumn) {
        return null;
    }
}
