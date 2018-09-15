import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class Game extends Thread{

    private PlayerBase playerA;
    private PlayerBase playerB;
    private Referee referee;
    private PlayerBase currentPlayer;
    private boolean isGameFinished;
    private PropertyChangeSupport propertyChangeSupport;

    Game(PlayerBase playerA, PlayerBase playerB, Referee referee) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.referee = referee;
        this.isGameFinished = false;
        this.currentPlayer = this.playerA;

        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    void addPropertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void run(){
        while(!this.isGameFinished){
            Move move = this.currentPlayer.makeAMove();
            this.propertyChangeSupport.firePropertyChange("newMove",null,move);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void executeMove(Move move){
        this.getCurrentPlayer().executeMove(move);
        this.referee.executeMove(move);
        this.switchPlayer();
        this.getCurrentPlayer().executeMove(move);
        this.referee.isGameFinished(this.currentPlayer.getTeamNumber());
    }

    Referee getReferee(){
        return this.referee;
    }

    private void switchPlayer(){
        this.currentPlayer = this.currentPlayer == this.playerA ? this.playerB : this.playerA;
    }

    PlayerBase getCurrentPlayer() {
        return this.currentPlayer;
    }

}

