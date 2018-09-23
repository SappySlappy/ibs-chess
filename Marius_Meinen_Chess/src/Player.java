import java.util.Scanner;

class Player extends PlayerBase{

    Player() {
        super();
    }

    Move makeAMove() {
        return null;
    }

    private String tradePawn(int row, int col) {
        System.out.println("The pawn on row " + row + ", column " + col + " can be traded.");
        System.out.print("What piece do you want (Q,T,S,L): ");
        Scanner scanner = new Scanner(System.in);
        String identifier = scanner.next().toUpperCase();

        while (!(identifier.equals("Q") || identifier.equals("T") || identifier.equals("S") || identifier.equals("L"))) {
            identifier = scanner.next().toUpperCase();
        }

        String colour = this.teamNumber == 1 ? "S" : "W";
        switch (identifier) {
            case "Q":
                this.board.setField(new Queen(this.teamNumber, (colour + "Q"), row, col), row, col);
                return "Q";
            case "T":
                this.board.setField(new Rook(this.teamNumber, (colour + "T"), row, col), row, col);
                return "T";
            case "S":
                this.board.setField(new Knight(this.teamNumber, (colour + "S"), row, col), row, col);
                return "S";
            case "L":
                this.board.setField(new Bishop(this.teamNumber, (colour + "L"), row, col), row, col);
                return "L";
            default:
                return null;
        }
    }
}
