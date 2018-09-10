import javafx.stage.Stage;

public class GameManager {

        private Game game;
        private ChessMainGui mainGui;

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

    public boolean getIsGameFinished(int teamNumber) {
        return this.game.getReferee().isGameFinished(teamNumber);
    }

    public void setMainGui(ChessMainGui gui){
        this.mainGui = gui;
    }

    public void start(){
        this.mainGui.start(new Stage());
    }

    public void closeWindow(){
        this.mainGui.closeWindow();
    }
}
