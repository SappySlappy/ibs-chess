import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChessMainGui extends Application {

    private MenuBar menuBar;
    private ToolBar toolBar;
    private BorderPane root;
    private Game game;

    public static void main(String[] args) {     //throws Exception
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {      //trows Exception
        this.game = new Game(new Player(new Board(), "Player A", 2), new Player(new Board(), "Player B", 1), new Referee(new Board()));
        this.CreateMenuBar();
        this.CreateToolBar();
        this.CreateLayout();

        Scene scene = new Scene(this.root, 800, 800);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void CreateMenuBar() {
        this.menuBar = new MenuBar();

        Menu gameMenu = new Menu("_Game");
        Menu playerAMenu = new Menu("Player _A");
        Menu playerBMenu = new Menu("Player _B");

        MenuItem openMenuItem = new MenuItem("_Open");
        MenuItem printMenuItem = new MenuItem("_Print");
        MenuItem startMenuItem = new MenuItem("_Start");
        MenuItem stopMenuItem = new MenuItem("_Stop");
        MenuItem closeMenuItem = new MenuItem("_Close");
        RadioMenuItem isPlayerAHumanMenuItem = new RadioMenuItem("_Human");
        RadioMenuItem isPlayerBHumanMenuItem = new RadioMenuItem("_Human");

        openMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
        printMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+P"));
        startMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
        stopMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+H"));
        closeMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+C"));

        openMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("Images/Open-file-icon.png").toString(), 15, 15, false, true)));
        printMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("Images/print.png").toString(), 15, 15, false, true)));

        isPlayerAHumanMenuItem.setSelected(true);
        isPlayerBHumanMenuItem.setSelected(true);

        playerAMenu.getItems().add(isPlayerAHumanMenuItem);
        playerBMenu.getItems().add(isPlayerBHumanMenuItem);
        gameMenu.getItems().addAll(openMenuItem, printMenuItem, new SeparatorMenuItem(), startMenuItem, stopMenuItem, new SeparatorMenuItem(), closeMenuItem);

        this.menuBar.getMenus().addAll(gameMenu, playerAMenu, playerBMenu);
    }

    private void CreateToolBar() {
        this.toolBar = new ToolBar();

        Button OpenButton = new Button();
        Button SaveButton = new Button();
        Button StartButton = new Button();
        Button StopButton = new Button();

        OpenButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/Open-file-icon.png").toExternalForm(), 30, 30, true, false)));
        SaveButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/blue-save-disk-icon.png").toString(), 30, 30, true, false)));
        StartButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/start_icon.png").toString(), 30, 30, true, false)));
        StopButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/stop_icon.png").toString(), 30, 30, true, false)));

        OpenButton.setTooltip(new Tooltip("Opens a saved game."));
        SaveButton.setTooltip(new Tooltip("Saves the current game."));
        StartButton.setTooltip(new Tooltip("Starts a new game."));
        StopButton.setTooltip(new Tooltip("Stops the current game."));

        this.toolBar.getItems().addAll(OpenButton, SaveButton, StartButton, StopButton);
    }

    private void CreateLayout() {
        this.root = new BorderPane();

        Label topLabel = new Label("Player B");
        Label botLabel = new Label("Player A");
        Label infoLabel = new Label("Start a new game or load an old one.");

        VBox topVBox = new VBox();
        VBox botVBox = new VBox();
        HBox infoLabelBox = new HBox();

        VBox.setMargin(topLabel, new Insets(10, 10, 0, 10));
        HBox.setMargin(infoLabel, new Insets(0, 10, 10, 10));

        topVBox.getChildren().addAll(this.menuBar, this.toolBar, topLabel);
        infoLabelBox.getChildren().addAll(infoLabel);
        botVBox.getChildren().addAll(botLabel, infoLabelBox);
        GamePane gameFieldPane = new GamePane(this.game.getRefereeBoard());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        //scrollPane.setContent(gameFieldPane);

        StackPane stackpane = new StackPane(gameFieldPane);
        scrollPane.setContent(stackpane);

        // https://stackoverflow.com/questions/30687994/how-to-center-the-content-of-a-javafx-8-scrollpane
        stackpane.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        stackpane.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getHeight(), scrollPane.viewportBoundsProperty()));

        this.root.setTop(topVBox);
        this.root.setCenter(scrollPane);
        this.root.setBottom(botVBox);

        BorderPane.setAlignment(gameFieldPane, Pos.CENTER);
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        BorderPane.setAlignment(botLabel, Pos.CENTER);
        infoLabelBox.setAlignment(Pos.BOTTOM_LEFT);
        topVBox.setAlignment(Pos.CENTER);
        botVBox.setAlignment(Pos.CENTER);
    }
}
