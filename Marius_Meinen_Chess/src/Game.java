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
    }

    void startGame() {
        this.currentPlayer = this.playerA;
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

    Board getRefereeBoard(){
        return this.referee.getBoard();
    }

    private void switchPlayer(){
        this.currentPlayer = this.currentPlayer == this.playerA ? this.playerB : this.playerA;
    }
}

