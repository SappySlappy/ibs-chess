import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

class Game extends Thread{

    private PlayerBase playerA;
    private PlayerBase playerB;
    private Referee referee;
    private PlayerBase currentPlayer;
    private boolean isGameFinished;

    Game(PlayerBase playerA, PlayerBase playerB, Referee referee) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.referee = referee;
        this.isGameFinished = false;
        this.currentPlayer = this.playerA;
    }


    public Move getMoveProperty() {
        return moveProperty.get();
    }

    public ObjectProperty<Move> movePropertyProperty() {
        return moveProperty;
    }

    public void setMoveProperty(Move moveProperty) {
        this.moveProperty.set(moveProperty);
    }

    public void run(){
        while(!this.isGameFinished){
            Move move = this.currentPlayer.makeAMove();
            this.executeMove(move);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ObjectProperty<Move> moveProperty = new SimpleObjectProperty<>();

    void executeMove(Move move){
        this.getCurrentPlayer().executeMove(move);
        this.referee.executeMove(move);
        this.switchPlayer();
        this.getCurrentPlayer().executeMove(move);
    }

    Referee getReferee(){
        return this.referee;
    }

    void startGame() {

        Move currentMove = this.currentPlayer.makeAMove();
        this.isGameFinished = this.referee.checkNewMove(currentMove);

        while (!this.isGameFinished) {
            this.switchPlayer();
            this.currentPlayer.executeMove(currentMove);
            if(this.referee.checkIfPiecesLeft(this.currentPlayer)){
                break;
            }
            currentMove = this.currentPlayer.makeAMove();
            this.isGameFinished = this.referee.checkNewMove(currentMove);
        }

        System.out.print("Player " + this.currentPlayer.getName() + "lost.");
    }

    private void switchPlayer(){
        this.currentPlayer = this.currentPlayer == this.playerA ? this.playerB : this.playerA;
    }

    PlayerBase getCurrentPlayer() {
        return this.currentPlayer;
    }

}

