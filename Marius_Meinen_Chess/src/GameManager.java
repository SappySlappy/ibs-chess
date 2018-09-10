import javafx.stage.Stage;

class GameManager {

        private Game game;
        private ChessMainGui mainGui;

    GameManager(){
        this.game = new Game(new Player(new Board(), "Player A", 2),
                new Player(new Board(), "Player B", 1),
                new Referee(new Board()));
        this.game.start();
    }

    Game getCurrentGame(){
        return this.game;
    }

    PlayerBase getCurrentPlayer(){
        return this.game.getCurrentPlayer();
    }

    boolean getIsGameFinished(int teamNumber) {
        return this.game.getReferee().isGameFinished(teamNumber);
    }

    void setMainGui(ChessMainGui gui){
        this.mainGui = gui;
    }

    void start(){
        this.mainGui.start(new Stage());
    }

    void closeWindow(){
        this.mainGui.closeWindow();
    }
}
