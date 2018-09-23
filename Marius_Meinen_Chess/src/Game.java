import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class Game extends Thread{

    private PlayerBase playerA;
    private PlayerBase playerB;
    private Referee referee;
    private PlayerBase currentPlayer;
    private boolean isGameFinished;
    private boolean isPaused;
    private PropertyChangeSupport propertyChangeSupport;

    Game(Referee referee) {
        this.referee = referee;
        this.isGameFinished = false;
        this.currentPlayer = this.playerA;

        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    void setPlayerA(PlayerBase value){
        this.playerA = value;
    }

    void setPlayerB(PlayerBase value){
        this.playerB = value;
    }

    void addPropertyChangeListener(PropertyChangeListener listener){
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    synchronized public void run(){
        this.isPaused = false;
        while(!this.isGameFinished){
            synchronized (this) {
                while (isPaused) {
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Move move = this.currentPlayer.makeAMove();
                this.propertyChangeSupport.firePropertyChange("newMove",null,move);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        }
    }

    void pauseThread(){
        this.isPaused = true;
    }

    synchronized void resumeThread(){
        this.isPaused = false;
        notify();
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

