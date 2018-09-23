import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
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

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ChessMainGui extends Application {

    private final File programmDirectory = new File("C:\\Users\\Marius\\IdeaProjects\\ibs-chess\\Marius_Meinen_Chess\\programm");
    private MenuBar menuBar;
    private ToolBar toolBar;
    private BorderPane root;
    private GameManager gameManager;
    private Stage primaryStage;
    private Thread gameThread;
    private ArrayList<String> listOfProgramNames;
    private boolean gameStarted;

    private Button StartButton;
    private Button StopButton;

    private Menu playerAMenu;
    private Menu playerBMenu;

    private MenuItem startMenuItem;
    private MenuItem stopMenuItem;

    public static void main(String[] args) {     //throws Exception
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {      //trows Exception
        this.primaryStage = primaryStage;
        this.CreateMenuBar();
        this.CreateToolBar();
        this.gameManager = new GameManager();
        this.gameManager.setMainGui(this);
        this.CreateLayout();
        this.gameStarted = false;

        Scene scene = new Scene(this.root, 700, 700);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void closeWindow(){
        this.primaryStage.close();
    }


    private void CreateMenuBar() {
        this.menuBar = new MenuBar();

        Menu gameMenu = new Menu("_Game");
        playerAMenu = new Menu("Player _A");
        playerBMenu = new Menu("Player _B");

        MenuItem newMenuItem = new MenuItem("_New");
        MenuItem printMenuItem = new MenuItem("_Print");
        this.startMenuItem = new MenuItem("_Start");
        this.stopMenuItem = new MenuItem("_Stop");
        MenuItem closeMenuItem = new MenuItem("_Close");
        ToggleGroup playerToggleGroupA = new ToggleGroup();
        ToggleGroup playerToggleGroupB = new ToggleGroup();
        RadioMenuItem isPlayerAHumanMenuItem = new RadioMenuItem("_Human");
        isPlayerAHumanMenuItem.setToggleGroup(playerToggleGroupA);
        RadioMenuItem isPlayerBHumanMenuItem = new RadioMenuItem("_Human");
        isPlayerBHumanMenuItem.setToggleGroup(playerToggleGroupB);
        newMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));
        printMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+P"));
        startMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
        stopMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+H"));
        closeMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+C"));

        newMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("Images/Open-file-icon.png").toString(), 15, 15, false, true)));
        printMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("Images/print.png").toString(), 15, 15, false, true)));

        isPlayerAHumanMenuItem.setSelected(true);
        isPlayerBHumanMenuItem.setSelected(true);

        playerAMenu.getItems().add(isPlayerAHumanMenuItem);
        playerBMenu.getItems().add(isPlayerBHumanMenuItem);

        this.loadPlayerProgramNames();
        for (String name:this.listOfProgramNames){
            RadioMenuItem playerKiA = new RadioMenuItem(name);
            playerKiA.setToggleGroup(playerToggleGroupA);
            RadioMenuItem playerKiB = new RadioMenuItem(name);
            playerKiB.setToggleGroup(playerToggleGroupB);
            playerAMenu.getItems().add(playerKiA);
            playerBMenu.getItems().add(playerKiB);
        }
        gameMenu.getItems().addAll(newMenuItem, printMenuItem, new SeparatorMenuItem(), startMenuItem, stopMenuItem, new SeparatorMenuItem(), closeMenuItem);

        stopMenuItem.setDisable(true);

        newMenuItem.addEventHandler(ActionEvent.ACTION, e -> this.start(new Stage()));
        closeMenuItem.addEventHandler(ActionEvent.ACTION, e -> Platform.exit());
        startMenuItem.addEventHandler(ActionEvent.ACTION, event -> this.startDummyPlayer());
        stopMenuItem.addEventHandler(ActionEvent.ACTION, event -> this.stopDummyPlayer());

        this.menuBar.getMenus().addAll(gameMenu, playerAMenu, playerBMenu);
    }

    private void loadPlayerProgramNames() {
            listOfProgramNames = new ArrayList<>();
            File[] listOfFiles = this.programmDirectory.listFiles(file -> file.getName().endsWith(".jar"));
            for (File file:listOfFiles){
                listOfProgramNames.add(getProgramClassname(file.getAbsolutePath()));
            }
    }

    static String getProgramClassname(String jarPathAndFilename) {
        try {
            URL fileURL = new URL("file:" + jarPathAndFilename);
            URL u = new URL("jar", "", fileURL + "!/");
            JarURLConnection uc = (JarURLConnection) u.openConnection();
            Attributes attr = uc.getMainAttributes();
            return attr != null ? attr.getValue("Player")
                    : null;
        } catch (IOException exc) {
            return null;
        }
    }


    private void CreateToolBar() {
        this.toolBar = new ToolBar();

        Button OpenButton = new Button();
        Button SaveButton = new Button();
        this.StartButton = new Button();
        this.StopButton = new Button();

        StopButton.setDisable(true);

        OpenButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/Open-file-icon.png").toExternalForm(), 30, 30, true, false)));
        SaveButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/blue-save-disk-icon.png").toString(), 30, 30, true, false)));
        StartButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/start_icon.png").toString(), 30, 30, true, false)));
        StopButton.setGraphic(new ImageView(new Image(getClass().getResource("Images/stop_icon.png").toString(), 30, 30, true, false)));

        OpenButton.setTooltip(new Tooltip("Opens a saved game."));
        SaveButton.setTooltip(new Tooltip("Saves the current game."));
        StartButton.setTooltip(new Tooltip("Starts a new game."));
        StopButton.setTooltip(new Tooltip("Stops the current game."));

        StartButton.addEventHandler(ActionEvent.ACTION, event -> this.startDummyPlayer());
        StopButton.addEventHandler(ActionEvent.ACTION, event -> this.stopDummyPlayer());

        this.toolBar.getItems().addAll(OpenButton, SaveButton, StartButton, StopButton);
    }

    private void startDummyPlayer(){
        StartButton.setDisable(true);
        startMenuItem.setDisable(true);
        StopButton.setDisable(false);
        stopMenuItem.setDisable(false);
        if (!gameStarted){
            this.gameManager.setPlayer(getSelectedPlayer(this.playerAMenu), this.getSelectedPlayer(this.playerBMenu));
            this.gameStarted = true;
        }
        if (gameThread == null){
            this.gameThread = this.gameManager.getCurrentGame();
            this.gameThread.setDaemon(true);
            gameThread.start();
        }
            synchronized (gameThread) {
                this.gameManager.getCurrentGame().resumeThread();
            }

    }

    private PlayerBase getSelectedPlayer(Menu menu) {
         String selectedProgram = "";
        for (MenuItem item:menu.getItems()){
            if (item instanceof RadioMenuItem && ((RadioMenuItem) item).isSelected()){
                RadioMenuItem selected = (RadioMenuItem) item;
                selectedProgram = selected.getText();
            }
        }

        if (!selectedProgram.isEmpty()){
            try {
                JARClassLoader cl = new JARClassLoader(this.programmDirectory+"/"+selectedProgram);
                Class<?> programClass = cl.loadClass(selectedProgram);
                return (PlayerBase) programClass.newInstance();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void stopDummyPlayer(){
        StopButton.setDisable(true);
        stopMenuItem.setDisable(true);
        StartButton.setDisable(false);
        startMenuItem.setDisable(false);
        synchronized (gameThread){
            this.gameThread.interrupt();
            this.gameManager.getCurrentGame().pauseThread();
        }
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
        GamePane gameFieldPane = new GamePane(this.gameManager);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        StackPane stackpane = new StackPane(gameFieldPane);
        scrollPane.setContent(stackpane);

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
