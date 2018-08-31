public class Game {

    private Player playerA;
    private Player playerB;
    private Referee referee;
    private Player currentPlayer;
    private Player notCurrentPlayer;
    private boolean isGameFinished;

    public Game(Player playerA, Player playerB, Referee referee) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.referee = referee;
        this.isGameFinished = false;
    }

    public void startGame() {
        this.currentPlayer = playerA;
        this.notCurrentPlayer = playerB;
        Move currentMove = this.currentPlayer.makeAMove();
        this.isGameFinished = referee.checkNewMove(currentMove);

        while (!this.isGameFinished) {
            this.switchPlayer();
            this.currentPlayer.executeMove(currentMove);
            if(this.referee.checkIfPiecesLeft(this.currentPlayer)){
                break;
            }
            currentMove = this.currentPlayer.makeAMove();
            this.isGameFinished = referee.checkNewMove(currentMove);
        }

        System.out.print("Player " + this.currentPlayer.getName() + "lost.");
    }

    private void switchPlayer(){
        this.notCurrentPlayer = this.currentPlayer;
        this.currentPlayer = this.currentPlayer == this.playerA ? this.playerB : this.playerA;
    }
}

