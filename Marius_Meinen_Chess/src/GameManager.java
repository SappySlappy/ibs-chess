public class GameManager {

        private Game game;

    public GameManager(){
        this.game = new Game(new Player(new Board(), "Player A", 2),
                new Player(new Board(), "Player B", 1),
                new Referee(new Board()));
    }

    public Game getCurrentGame(){
        return this.game;
    }

    public Player getCurrentPlayer(){
        return this.game.getCurrentPlayer();
    }
}
