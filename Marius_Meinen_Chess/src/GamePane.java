import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class GamePane extends Region {
    private GraphicsContext gc;
    private int cellSpace;
    private PieceBase dragPiece;
    private GameManager gameManager;
    private boolean isPawnTraded;
    private String pieceForPawn;
    private Stage tradePawnPopUpWindow;
    private Canvas canvas;
    private Image dragImage;
    private Image boardImage;

    private Image blackRook;
    private Image blackKnight;
    private Image blackBishop;
    private Image blackKing;
    private Image blackQueen;
    private Image blackPawn;

    private Image whiteRook;
    private Image whiteKnight;
    private Image whiteBishop;
    private Image whiteKing;
    private Image whiteQueen;
    private Image whitePawn;

    GamePane(GameManager gameManager) {
        this.gameManager = gameManager;
        this.cellSpace = 60;
        this.setPrefSize(cellSpace * 10, cellSpace * 10);
        this.setMinSize(cellSpace * 10, cellSpace * 10);
        this.setMaxSize(cellSpace * 10, cellSpace * 10);
        this.loadChessImages();
        this.canvas = new Canvas(10 * this.cellSpace, 10 * this.cellSpace);
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        this.drawBoard(null);
        this.addCanvasHandler(canvas);
        this.createTradePawnWindow();
    }

    private void addCanvasHandler(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.DRAG_DETECTED, event -> {
            getDragPiece(event);
            if (dragPiece != null && dragPiece.getTeamNumber() != gameManager.getCurrentGame().getCurrentPlayer().getTeamNumber()) {
                dragPiece = null;
            } else if (this.dragPiece != null) {
                redrawBoard();
                WritableImage canvasImage = this.canvas.snapshot(new SnapshotParameters(), new WritableImage(this.cellSpace * 10, this.cellSpace * 10));
                this.boardImage = canvas.snapshot(new SnapshotParameters(), canvasImage);
                this.fillAllPossibleFields(dragPiece);
            }
            event.consume();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (this.dragPiece != null) {
                clearCanvas();
                this.gc.drawImage(this.boardImage, 0, 0, 600, 600);
                this.fillNotPossibleField(dragPiece, event);
                this.drawDraggedPiece(event);
            }
            event.consume();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (dragPiece != null) {
                executeDragAndDropOnBoard(event);
            }
            event.consume();
            dragPiece = null;
            redrawBoard();
        });
    }

    private void loadChessImages() {
        this.blackRook = new Image(getClass().getResource("Images/Black_Rook.png").toString(), 40, 40, false, true);
        this.blackKnight = new Image(getClass().getResource("Images/Black_Knight.png").toString(), 40, 40, false, true);
        this.blackBishop = new Image(getClass().getResource("Images/Black_Bishop.png").toString(), 40, 40, false, true);
        this.blackKing = new Image(getClass().getResource("Images/Black_King.png").toString(), 40, 40, false, true);
        this.blackQueen = new Image(getClass().getResource("Images/Black_Queen.png").toString(), 40, 40, false, true);
        this.blackPawn = new Image(getClass().getResource("Images/Black_Pawn.png").toString(), 40, 40, false, true);

        this.whiteRook = new Image(getClass().getResource("Images/White_Rook.png").toString(), 40, 40, false, true);
        this.whiteKnight = new Image(getClass().getResource("Images/White_Knight.png").toString(), 40, 40, false, true);
        this.whiteBishop = new Image(getClass().getResource("Images/White_Bishop.png").toString(), 40, 40, false, true);
        this.whiteKing = new Image(getClass().getResource("Images/White_King.png").toString(), 40, 40, false, true);
        this.whiteQueen = new Image(getClass().getResource("Images/White_Queen.png").toString(), 40, 40, false, true);
        this.whitePawn = new Image(getClass().getResource("Images/White_Pawn.png").toString(), 40, 40, false, true);

    }

    private void drawBoard(PieceBase piece) {
        this.createGameFieldPane();
        this.drawPieces(piece);
        this.drawSideText();
    }

    private void drawSideText() {
        String text;
        int halfCellSpace = this.cellSpace / 2;
        for (int i = 8; i > 0; i--) {
            text = i + "";
            this.gc.setFill(Color.BLACK);
            this.gc.fillText(text, halfCellSpace, this.cellSpace * i + halfCellSpace);
            this.gc.fillText(text, this.cellSpace * 9 + halfCellSpace, this.cellSpace * i + halfCellSpace);
        }

        this.gc.fillText("A", cellSpace + halfCellSpace, halfCellSpace);
        this.gc.fillText("B", cellSpace * 2 + halfCellSpace, halfCellSpace);
        this.gc.fillText("C", cellSpace * 3 + halfCellSpace, halfCellSpace);
        this.gc.fillText("D", cellSpace * 4 + halfCellSpace, halfCellSpace);
        this.gc.fillText("E", cellSpace * 5 + halfCellSpace, halfCellSpace);
        this.gc.fillText("F", cellSpace * 6 + halfCellSpace, halfCellSpace);
        this.gc.fillText("G", cellSpace * 7 + halfCellSpace, halfCellSpace);
        this.gc.fillText("H", cellSpace * 8 + halfCellSpace, halfCellSpace);

        this.gc.fillText("A", cellSpace + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("B", cellSpace * 2 + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("C", cellSpace * 3 + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("D", cellSpace * 4 + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("E", cellSpace * 5 + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("F", cellSpace * 6 + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("G", cellSpace * 7 + halfCellSpace, cellSpace * 9 + halfCellSpace);
        this.gc.fillText("H", cellSpace * 8 + halfCellSpace, cellSpace * 9 + halfCellSpace);
    }


    private void createGameFieldPane() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.gc.rect((this.cellSpace * i + this.cellSpace), (this.cellSpace * j + this.cellSpace), this.cellSpace, this.cellSpace);
                this.gc.setFill((i + j) % 2 == 0 ? Color.BROWN : Color.WHITE);
                this.gc.fillRect((this.cellSpace * i + this.cellSpace), (this.cellSpace * j + this.cellSpace), this.cellSpace, this.cellSpace);
            }
        }
        this.gc.setStroke(Color.BLACK);
        this.gc.stroke();

    }

    private void drawPieces(PieceBase doNotDrawPiece) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceBase piece = this.gameManager.getCurrentGame().getReferee().getBoard().getField(i, j);
                if (piece != null) {
                    if (piece instanceof Queen) {
                        this.drawImage(i, j, piece, doNotDrawPiece, this.blackQueen, this.whiteQueen);
                    }

                    if (piece instanceof King) {
                        this.drawImage(i, j, piece, doNotDrawPiece, this.blackKing, this.whiteKing);
                    }

                    if (piece instanceof Bishop) {
                        this.drawImage(i, j, piece, doNotDrawPiece, this.blackBishop, this.whiteBishop);
                    }

                    if (piece instanceof Knight) {
                        this.drawImage(i, j, piece, doNotDrawPiece, this.blackKnight, this.whiteKnight);
                    }

                    if (piece instanceof Rook) {
                        this.drawImage(i, j, piece, doNotDrawPiece, this.blackRook, this.whiteRook);
                    }

                    if (piece instanceof Pawn) {
                        this.drawImage(i, j, piece, doNotDrawPiece, this.blackPawn, this.whitePawn);
                    }
                }
            }
        }
    }

    private void drawImage(int i, int j, PieceBase piece, PieceBase doNotDrawPiece, Image blackPieceImage, Image whitePieceImage) {
        if (piece.getTeamNumber() == 1) {
            if (!piece.equals(doNotDrawPiece)) {
                this.gc.drawImage(blackPieceImage, (this.cellSpace * j + this.cellSpace), (this.cellSpace * i + this.cellSpace), this.cellSpace, this.cellSpace);
            } else {
                this.dragImage = blackPieceImage;
            }
        } else if (!piece.equals(doNotDrawPiece)) {
            this.gc.drawImage(whitePieceImage, (this.cellSpace * j + this.cellSpace), (this.cellSpace * i + this.cellSpace), this.cellSpace, this.cellSpace);
        } else {
            this.dragImage = whitePieceImage;
        }
    }

    private void createTradePawnWindow() {
        this.tradePawnPopUpWindow = new Stage();
        this.tradePawnPopUpWindow.setTitle(this.gameManager.getCurrentPlayer().getName() + " can trade a pawn.");
        VBox vBox = new VBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton queen = new RadioButton("Queen");
        queen.setToggleGroup(group);
        queen.setSelected(true);
        RadioButton knight = new RadioButton("Knight");
        knight.setToggleGroup(group);
        RadioButton rook = new RadioButton("Rook");
        rook.setToggleGroup(group);
        RadioButton bishop = new RadioButton("Bishop");
        bishop.setToggleGroup(group);
        Button acceptTrade = new Button("Trade Pawn");
        acceptTrade.setOnAction(event1 -> {
            this.isPawnTraded = true;
            if (queen.isSelected()) {
                this.pieceForPawn = "Q";
            }
            if (rook.isSelected()) {
                this.pieceForPawn = "T";
            }
            if (knight.isSelected()) {
                this.pieceForPawn = "S";
            }
            if (bishop.isSelected()) {
                this.pieceForPawn = "L";
            }

            this.tradePawnPopUpWindow.close();
        });
        VBox.setMargin(queen, new Insets(10, 10, 10, 10));
        VBox.setMargin(knight, new Insets(0, 10, 10, 10));
        VBox.setMargin(rook, new Insets(0, 10, 10, 10));
        VBox.setMargin(bishop, new Insets(0, 10, 10, 10));
        VBox.setMargin(acceptTrade, new Insets(0, 10, 10, 10));
        vBox.getChildren().addAll(queen, knight, rook, bishop, acceptTrade);
        Scene tradeScene = new Scene(vBox, 200, 150);
        tradePawnPopUpWindow.setScene(tradeScene);
        tradePawnPopUpWindow.initModality(Modality.APPLICATION_MODAL);
        tradePawnPopUpWindow.initStyle(StageStyle.UNDECORATED);
    }

    private void getDragPiece(MouseEvent event) {
        int col = (int) event.getX() / this.cellSpace - 1;
        int row = (int) event.getY() / this.cellSpace - 1;
        if (col >= 0 && col < 8 && row >= 0 && row < 8 && this.dragPiece == null) {
            this.dragPiece = this.gameManager.getCurrentGame().getReferee().getBoard().getField(row, col);
        }
    }

    private void redrawBoard() {
        if (this.dragPiece != null) {
            clearCanvas();
            this.drawBoard(dragPiece);
            this.fillAllPossibleFields(dragPiece);
            this.drawSideText();
        }
    }

    private void clearCanvas() {
        this.gc.clearRect(0, 0, 10 * this.cellSpace, 10 * this.cellSpace);
    }


    private void drawDraggedPiece(MouseEvent event) {
        if (dragPiece.getTeamNumber() == 1) {
            this.gc.drawImage(this.dragImage, event.getX() - 30, event.getY() - 30, this.cellSpace, this.cellSpace);
        } else {
            this.gc.drawImage(this.dragImage, event.getX() - 30, event.getY() - 30, this.cellSpace, this.cellSpace);
        }
    }

    private void executeDragAndDropOnBoard(MouseEvent event) {
        this.isPawnTraded = false;
        this.pieceForPawn = null;
        int col = (int) event.getX() / this.cellSpace - 1;
        int row = (int) event.getY() / this.cellSpace - 1;
        Move move2 = new Move(this.dragPiece.getStartRow(), this.dragPiece.getStartCol(), row, col, this.isPawnTraded, this.pieceForPawn, this.dragPiece.getTeamNumber());
        if (this.dragPiece instanceof Pawn && (row == 0 || row == 7) && this.dragPiece.getPossibleMoves().contains(move2)) {
            this.tradePawnPopUpWindow.showAndWait();
        }
        Move move = new Move(this.dragPiece.getStartRow(), this.dragPiece.getStartCol(), row, col, this.isPawnTraded, this.pieceForPawn, this.dragPiece.getTeamNumber());
        if (this.dragPiece.getPossibleMoves().contains(move)) {
            this.gameManager.getCurrentGame().executeMove(move);
            this.drawBoard(null);
        }
        this.dragPiece = null;
        this.drawBoard(null);

        if (this.gameManager.getIsGameFinished(this.gameManager.getCurrentPlayer().getTeamNumber())) {
            this.gameManager.closeWindow();
            this.gameManager.start();
        }
    }


    private void fillAllPossibleFields(PieceBase dragPiece) {
        for (Move move : dragPiece.getListOfMoves(this.gameManager.getCurrentGame().getReferee().getBoard())) {
            this.gc.setFill(new Color(0.2, 0.6, 0.2, 0.5));
            this.gc.fillRect((this.cellSpace * move.getDestinationColumn() + this.cellSpace), (this.cellSpace * move.getDestinationRow() + this.cellSpace), this.cellSpace, this.cellSpace);
        }

        this.gc.setFill(new Color(0, 0, 1, 0.5));
        this.gc.fillRect((this.cellSpace * this.dragPiece.getStartCol() + this.cellSpace), (this.cellSpace * this.dragPiece.getStartRow() + this.cellSpace), this.cellSpace, this.cellSpace);
    }

    private void fillNotPossibleField(PieceBase dragPiece, MouseEvent event) {
        int col = (int) event.getX() / this.cellSpace - 1;
        int row = (int) event.getY() / this.cellSpace - 1;

        if (!this.dragPiece.getPossibleMoves().contains(new Move(dragPiece.getStartRow(), dragPiece.getStartCol(), row, col, false, null, 0)) && !(row == this.dragPiece.getStartRow() && col == this.dragPiece.getStartCol()) && row < 8 && row >= 0 && col < 8 && col >= 0) {
            this.gc.setFill(new Color(1, 0, 0, 0.5));
            this.gc.fillRect(this.cellSpace * col + this.cellSpace, this.cellSpace * row + this.cellSpace, this.cellSpace, this.cellSpace);
        }
    }
}
