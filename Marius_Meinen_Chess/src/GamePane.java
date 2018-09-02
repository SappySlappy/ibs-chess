import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

class GamePane extends Region {
    private GraphicsContext gc;
    private int cellSpace;

    private Board board;
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

    GamePane(Board board){
        this.board = board;
        this.cellSpace = 60;
        this.setPrefSize(cellSpace*10,cellSpace*10);
        this.setMinSize(cellSpace*10,cellSpace*10);
        this.setMaxSize(cellSpace*10,cellSpace*10);
        this.loadChessImages();
        this.createGameFieldPane();
        this.drawPieces();
        this.drawSideText();
    }

    private void drawSideText() {
        String text;
        int halfCellSpace = this.cellSpace/2;
        for(int i = 8;i>0;i--){
            text = i+"";
            this.gc.fillText(text,halfCellSpace, cellSpace*i+halfCellSpace);
            this.gc.fillText(text,cellSpace*9+halfCellSpace, cellSpace*i+halfCellSpace);
        }

        this.gc.fillText("A",cellSpace+halfCellSpace, halfCellSpace);
        this.gc.fillText("B",cellSpace*2+halfCellSpace, halfCellSpace);
        this.gc.fillText("C",cellSpace*3+halfCellSpace, halfCellSpace);
        this.gc.fillText("D",cellSpace*4+halfCellSpace, halfCellSpace);
        this.gc.fillText("E",cellSpace*5+halfCellSpace, halfCellSpace);
        this.gc.fillText("F",cellSpace*6+halfCellSpace, halfCellSpace);
        this.gc.fillText("G",cellSpace*7+halfCellSpace, halfCellSpace);
        this.gc.fillText("H",cellSpace*8+halfCellSpace, halfCellSpace);

        this.gc.fillText("A",cellSpace+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("B",cellSpace*2+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("C",cellSpace*3+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("D",cellSpace*4+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("E",cellSpace*5+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("F",cellSpace*6+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("G",cellSpace*7+halfCellSpace, cellSpace *9+halfCellSpace);
        this.gc.fillText("H",cellSpace*8+halfCellSpace, cellSpace *9+halfCellSpace);
    }

    private void loadChessImages(){
        this.blackRook = new Image(getClass().getResource("Images/Black_Rook.png").toString(), 40,40,false,true);
        this.blackKnight = new Image(getClass().getResource("Images/Black_Knight.png").toString(), 40,40,false,true);
        this.blackBishop = new Image(getClass().getResource("Images/Black_Bishop.png").toString(), 40,40,false,true);
        this.blackKing = new Image(getClass().getResource("Images/Black_King.png").toString(), 40,40,false,true);
        this.blackQueen = new Image(getClass().getResource("Images/Black_Queen.png").toString(), 40,40,false,true);
        this.blackPawn = new Image(getClass().getResource("Images/Black_Pawn.png").toString(), 40,40,false,true);

        this.whiteRook = new Image(getClass().getResource("Images/White_Rook.png").toString(), 40,40,false,true);
        this.whiteKnight = new Image(getClass().getResource("Images/White_Knight.png").toString(), 40,40,false,true);
        this.whiteBishop = new Image(getClass().getResource("Images/White_Bishop.png").toString(), 40,40,false,true);
        this.whiteKing = new Image(getClass().getResource("Images/White_King.png").toString(), 40,40,false,true);
        this.whiteQueen = new Image(getClass().getResource("Images/White_Queen.png").toString(), 40,40,false,true);
        this.whitePawn = new Image(getClass().getResource("Images/White_Pawn.png").toString(), 40,40,false,true);
    }

    private void createGameFieldPane() {
        Canvas canvas = new Canvas(700, 700);
        this.gc = canvas.getGraphicsContext2D();

        for (int i = 0; i <8 ;i++){
            for (int j = 0;j<8;j++){
                this.gc.rect((this.cellSpace*i+this.cellSpace),(this.cellSpace*j+this.cellSpace),this.cellSpace,this.cellSpace);
                this.gc.setFill((i+j)%2 == 0 ? Color.BROWN : Color.WHITE);
                this.gc.fillRect((this.cellSpace*i+this.cellSpace),(this.cellSpace*j+this.cellSpace),this.cellSpace,this.cellSpace);
            }
        }
        this.gc.setStroke(Color.BLACK);
        this.gc.stroke();
        this.getChildren().add(canvas);
    }

    private void drawPieces(){
        for (int i = 0; i <8 ;i++){
            for (int j = 0;j<8;j++){
                PieceBase piece = this.board.getField(i,j);
                if (piece != null){
                    if (piece instanceof Queen){
                        if (piece.getTeamNumber() == 1){
                            this.gc.drawImage(this.blackQueen,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                        else {
                            this.gc.drawImage(this.whiteQueen,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                    }

                    if (piece instanceof King){
                        if (piece.getTeamNumber() == 1){
                            this.gc.drawImage(this.blackKing,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                        else {
                            this.gc.drawImage(this.whiteKing,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                    }

                    if (piece instanceof Bishop){
                        if (piece.getTeamNumber() == 1){
                            this.gc.drawImage(this.blackBishop,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                        else {
                            this.gc.drawImage(this.whiteBishop,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                    }

                    if (piece instanceof Knight){
                        if (piece.getTeamNumber() == 1){
                            this.gc.drawImage(this.blackKnight,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                        else {
                            this.gc.drawImage(this.whiteKnight,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                    }

                    if (piece instanceof Rook){
                        if (piece.getTeamNumber() == 1){
                            this.gc.drawImage(this.blackRook,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                        else {
                            this.gc.drawImage(this.whiteRook,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                    }

                    if (piece instanceof Pawn){
                        if (piece.getTeamNumber() == 1){
                            this.gc.drawImage(this.blackPawn,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                        else {
                            this.gc.drawImage(this.whitePawn,(this.cellSpace*j+this.cellSpace),(this.cellSpace*i+this.cellSpace),this.cellSpace,this.cellSpace);
                        }
                    }
                }
            }
        }
    }
}
