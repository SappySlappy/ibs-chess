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

    public void run(){
        while(!this.isGameFinished){
            Move move = this.currentPlayer.makeAMove();
            this.currentPlayer.executeMove(move);
            this.referee.executeMove(move);
            this.switchPlayer();
        }
    }

    void executeMove(Move move){
        this.getCurrentPlayer().executeMove(move);
        this.referee.executeMove(move);
        this.switchPlayer();
        this.getCurrentPlayer().executeMove(move);
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

