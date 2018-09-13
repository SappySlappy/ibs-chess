import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class Referee  {

    private Board board;
    private PropertyChangeSupport propertyChangeSupport;

    Referee(Board board) {
        this.board = board;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    void addPropertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    boolean checkIfPiecesLeft(PlayerBase currentPlayer) {
        PieceBase piece;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piece = this.board.getField(i, j);
                if (piece != null && piece.getTeamNumber() == currentPlayer.getTeamNumber()) {
                    return false;
                }
            }
        }
        return true;
    }

    void executeMove(Move move) {
        this.board.executeMove(move);
        if (move.getPawnTraded())
        {
            String identifier = move.getPieceForPawn();
            int row = move.getDestinationRow();
            int col = move.getDestinationColumn();
            String colour = move.getTeamNumber() == 1 ? "S" : "W";
            switch (identifier) {
                case "Q":
                    this.board.setField(new Queen(move.getTeamNumber(), (colour + "Q"), row, col), row, col);
                    break;
                case "T":
                    this.board.setField(new Rook(move.getTeamNumber(), (colour + "T"), row, col), row, col);
                    break;
                case "S":
                    this.board.setField(new Knight(move.getTeamNumber(), (colour + "S"), row, col), row, col);
                    break;
                case "L":
                    this.board.setField(new Bishop(move.getTeamNumber(), (colour + "L"), row, col), row, col);
                    break;
                default:
                    break;
            }
        }
        this.propertyChangeSupport.firePropertyChange("refBoard",1,1);
    }

    public Board getBoard() {
        return this.board;
    }

    boolean isGameFinished(int teamNumber) {
        for (int i = 0;i<8;i++){
            for (int j = 0;j<8;j++){
                PieceBase piece = this.board.getField(i,j);
                if (piece != null && piece.getTeamNumber() == teamNumber){
                    return false;
                }
            }
        }

        return true;
    }
}
