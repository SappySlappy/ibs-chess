import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

class GamePane extends Region {
    private Canvas canvas;
    private GraphicsContext gc;
    private int cellSpace;
    PieceBase dragPiece;
    private GameManager gameManager;

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
        this.canvas = new Canvas(700, 700);
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        this.drawBoard(null,null);

        this.canvas.addEventHandler(MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse drag detected");
                getDragPiece(event);
                if (dragPiece != null && dragPiece.getTeamNumber() != gameManager.getCurrentGame().getCurrentPlayer().getTeamNumber()){
                    dragPiece = null;
                }

            }
        });

        this.canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse dragged");
                redrawBoard(event);
                event.consume();
            }
        });

        this.canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse released");
                if (dragPiece != null) {
                    executeDragAndDropOnBoard(event);
                }
                dragPiece = null;
                redrawBoard(null);
            }
        });
    }

    private void executeDragAndDropOnBoard(MouseEvent event) {
        int col =(int) event.getX()/this.cellSpace-1;
        int row =(int) event.getY()/this.cellSpace-1;
        Move move = new Move(dragPiece.getStartRow(),dragPiece.getStartCol(),row,col,false,null,dragPiece.getTeamNumber());
        if (this.dragPiece.possibleMoves.contains(move))
        {
            gameManager.getCurrentGame().executeMove(move);
            updateBoard(null,null);
        }
        this.dragPiece = null;
        updateBoard(null,null);
    }

    private void drawBoard(PieceBase piece,MouseEvent e){
        this.createGameFieldPane();
        this.drawPieces(piece,e);
        this.drawSideText();
    }

    private void getDragPiece(MouseEvent event) {
        int col =(int) event.getX()/this.cellSpace-1;
        int row =(int) event.getY()/this.cellSpace-1;
        if (col >= 0 && col<8 && row >= 0 && row<8 && dragPiece == null){
            dragPiece = this.gameManager.getCurrentGame().getReferee().getBoard().getField(row,col);
        }
    }


    private void redrawBoard(MouseEvent e){
        if (dragPiece != null){
            this.updateBoard(dragPiece,e);
            this.fillAllPossibleFields(dragPiece, e);
            this.drawSideText();
        }
    }

    private void fillAllPossibleFields(PieceBase dragPiece, MouseEvent event) {
        for(Move move : dragPiece.getListOfMoves(this.gameManager.getCurrentGame().getReferee().getBoard()))
        {
            this.gc.rect((this.cellSpace * move.getDestinationColumn() + this.cellSpace), (this.cellSpace * move.getDestinationRow() + this.cellSpace), this.cellSpace, this.cellSpace);
            this.gc.setFill(new Color(0.2,0.6,0.2,0.5));
            this.gc.fillRect((this.cellSpace * move.getDestinationColumn() + this.cellSpace), (this.cellSpace * move.getDestinationRow() + this.cellSpace), this.cellSpace, this.cellSpace);
        }

        int col =(int) event.getX()/this.cellSpace-1;
        int row =(int) event.getY()/this.cellSpace-1;

        if (!this.dragPiece.possibleMoves.contains(new Move(dragPiece.getStartRow(),dragPiece.getStartCol(),row,col,false,null,0)) && row < 8 && row >= 0&& col < 8 && col >= 0)
        {
            this.gc.setFill(new Color(1,0,0,0.5));
            this.gc.fillRect(this.cellSpace*col + this.cellSpace,this.cellSpace*row+this.cellSpace,this.cellSpace,this.cellSpace);
        }
    }


    private void updateBoard(PieceBase clickedPawn, MouseEvent e)
    {
        this.drawBoard(clickedPawn,e);

    }

    private void drawSideText() {
        String text;
        int halfCellSpace = this.cellSpace / 2;
        for (int i = 8; i > 0; i--) {
            this.gc.clearRect(0,i*cellSpace,cellSpace,cellSpace);
            this.gc.clearRect(cellSpace*9,i*cellSpace,cellSpace,cellSpace);
            this.gc.clearRect(i*cellSpace,cellSpace*9,cellSpace,cellSpace);
            this.gc.clearRect(i*cellSpace,0,cellSpace,cellSpace);
            text = i + "";
            gc.setFill(Color.BLACK);
            this.gc.fillText(text, halfCellSpace, cellSpace * i + halfCellSpace);
            this.gc.fillText(text, cellSpace * 9 + halfCellSpace, cellSpace * i + halfCellSpace);
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

    private void drawPieces(PieceBase doNotDrawPiece, MouseEvent e) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceBase piece = this.gameManager.getCurrentGame().getReferee().getBoard().getField(i, j);
                if (piece != null) {
                    if (piece instanceof Queen) {
                        this.drawImage(i,j,piece,doNotDrawPiece,e,this.blackQueen,this.whiteQueen);
                    }

                    if (piece instanceof King) {
                        this.drawImage(i,j,piece,doNotDrawPiece,e,this.blackKing,this.whiteKing);
                    }

                    if (piece instanceof Bishop) {
                        this.drawImage(i,j,piece,doNotDrawPiece,e,this.blackBishop,this.whiteBishop);
                    }

                    if (piece instanceof Knight) {
                        this.drawImage(i,j,piece,doNotDrawPiece,e,this.blackKnight,this.whiteKnight);
                    }

                    if (piece instanceof Rook) {
                        this.drawImage(i,j,piece,doNotDrawPiece,e,this.blackRook,this.whiteRook);
                    }

                    if (piece instanceof Pawn) {
                        this.drawImage(i,j,piece,doNotDrawPiece,e,this.blackPawn,this.whitePawn);
                    }
                }
            }
        }
    }

    private void drawImage(int i, int j,PieceBase piece, PieceBase doNotDrawPiece,MouseEvent e, Image blackPieceImage, Image whitePieceImage){
        if (piece.getTeamNumber() == 1) {
            if (!piece.equals(doNotDrawPiece)){
                this.gc.drawImage(blackPieceImage, (this.cellSpace * j + this.cellSpace), (this.cellSpace * i + this.cellSpace), this.cellSpace, this.cellSpace);
            }
            else{
                this.gc.drawImage(blackPieceImage,  e.getX()-30, e.getY()-30, this.cellSpace, this.cellSpace);
            }
        } else if (!piece.equals(doNotDrawPiece)){
            this.gc.drawImage(whitePieceImage, (this.cellSpace * j + this.cellSpace), (this.cellSpace * i + this.cellSpace), this.cellSpace, this.cellSpace);
        }
        else{
            this.gc.drawImage(whitePieceImage, e.getX()-30, e.getY()-30, this.cellSpace, this.cellSpace);
        }
    }
}
