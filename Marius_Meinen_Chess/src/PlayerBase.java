import java.util.Scanner;

public abstract class PlayerBase {


    protected Board board;
    protected String name;
    protected int teamNumber;

    public PlayerBase(Board board,String name, int teamNumber){
        this.name = name;
        this.board = board;
        this.teamNumber = teamNumber;
    }

    int getTeamNumber() {
        return this.teamNumber;
    }

    String getName() {
        return this.name;
    }

    void executeMove(Move move) {
        this.board.executeMove(move);
        if (move.getPawnTraded())
        {
            String identifier = move.getPieceForPawn();
            int row = move.getDestinationRow();
            int col = move.getDestinationColumn();
            String colour = this.teamNumber == 1 ? "S" : "W";
            switch (identifier) {
                case "Q":
                    this.board.setField(new Queen(this.teamNumber, (colour + "Q"), row, col), row, col);
                    break;
                case "T":
                    this.board.setField(new Rook(this.teamNumber, (colour + "T"), row, col), row, col);
                    break;
                case "S":
                    this.board.setField(new Knight(this.teamNumber, (colour + "S"), row, col), row, col);
                    break;
                case "L":
                    this.board.setField(new Bishop(this.teamNumber, (colour + "L"), row, col), row, col);
                    break;
                default:
                    break;
            }
        }
    }

    abstract Move makeAMove();

    abstract Move buildMove(int startRow, int startColumn, int destinationRow, int destinationColumn);
}
