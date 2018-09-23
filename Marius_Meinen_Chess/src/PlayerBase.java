public abstract class PlayerBase {


    private String name;
    protected Board board;
    protected int teamNumber;

    PlayerBase(){
        this.name = "";
        this.board = new Board();
        this.teamNumber = 1;
    }

    int getTeamNumber() {
        return this.teamNumber;
    }

    void setTeamNumber(int value){
        this.teamNumber = value;
    }

    String getName() {
        return this.name;
    }

    void setName(String value) {
        this.name = value;
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
}
