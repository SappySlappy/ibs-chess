class Game {

    private Player playerA;
    private Player playerB;
    private Referee referee;
    private Player currentPlayer;
    private boolean isGameFinished;

    Game(Player playerA, Player playerB, Referee referee) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.referee = referee;
        this.isGameFinished = false;
        this.currentPlayer = this.playerA;
    }

    public void executeMove(Move move){
        this.getCurrentPlayer().executeMove(move);
        this.referee.executeMove(move);
        this.switchPlayer();
        this.getCurrentPlayer().executeMove(move);
    }

    public Referee getReferee(){
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

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
}

