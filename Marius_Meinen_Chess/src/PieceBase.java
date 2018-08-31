import java.util.ArrayList;

public abstract class PieceBase {

    private int teamNumber;
    private String name;
    protected ArrayList<Move> possibleMoves;
    protected int row;
    protected int col;

    public PieceBase(int teamNumber, String name, int row, int col){
        this.teamNumber = teamNumber;
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public int getTeamNumber() {
        return this.teamNumber;
    }

    public String getPieceName(){
        return this.name;
    }

    public ArrayList<Move> getListOfMoves(){
        return this.possibleMoves;
    }

    public boolean isMoveLegal(Board board, Move move){
        if(move.getStartColumn() == move.getDestinationColumn() && move.getStartRow() == move.getDestinationRow()){
            return false;
        }

        if(move.getDestinationRow() < 0 || move.getStartRow() < 0 || move.getDestinationColumn() < 0 || move.getStartColumn() < 0
                || move.getDestinationRow() > 7 || move.getStartRow() > 7 || move.getDestinationColumn() > 7 || move.getStartColumn() > 7){
            return false;
        }

        if(board.getField(move.getStartRow(),move.getStartColumn()) == null){
            return false;
        }

        return true;
    }

    public void setNewField(int row, int column) {
        this.row = row;
        this.col = column;
    }
}
