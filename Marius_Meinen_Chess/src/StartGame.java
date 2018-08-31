public class StartGame {
    public static void main(String[] args) {
        Game game = new Game(new Player(new Board(),"Player A (White)", 2),new Player(new Board(),"Player B (Black)", 1) ,new Referee(new Board()));
        game.startGame();
    }
}
