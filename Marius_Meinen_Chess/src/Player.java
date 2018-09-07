import java.util.Scanner;

class Player {

    private Board board;
    private String name;
    private int teamNumber;

    Player(Board board, String name, int teamNumber) {
        this.name = name;
        this.board = board;
        this.teamNumber = teamNumber;
    }

    int getTeamNumber() {
        return this.teamNumber;
    }

    void executeMove(Move move) {
        this.board.executeMove(move);
        if (move.getPawnTraded())
        {
            String identifier = move.getPieceForPawn();
            int row = move.getDestinationRow();
            int col = move.getDestinationColumn();
            String colour = this.teamNumber == 1 ? "S" : "W";
            switch (identifier) {
                case "Q":
                    this.board.setField(new Queen(this.teamNumber, (colour + "Q"), row, col), row, col);
                    break;
                case "T":
                    this.board.setField(new Rook(this.teamNumber, (colour + "T"), row, col), row, col);
                    break;
                case "S":
                    this.board.setField(new Knight(this.teamNumber, (colour + "S"), row, col), row, col);
                    break;
                case "L":
                    this.board.setField(new Bishop(this.teamNumber, (colour + "L"), row, col), row, col);
                    break;
                default:
                    break;
            }
        }
    }

    String getName() {
        return this.name;
    }

    Move makeAMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(name);
        System.out.print("From row: ");
        int startRow = scanner.nextInt();
        System.out.print("From column: ");
        int startColumn = scanner.nextInt();
        System.out.print("To row: ");
        int destinationRow = scanner.nextInt();
        System.out.print("To column: ");
        int destinationColumn = scanner.nextInt();

        return this.buildMove(startRow, startColumn, destinationRow, destinationColumn);
    }

    private Move buildMove(int startRow, int startColumn, int destinationRow, int destinationColumn) {
        PieceBase pawn;
        String tradePiece;
        Move move;

        if (startRow < 8 && startRow >= 0 && startColumn < 8 && startColumn >= 0) {
            pawn = this.board.getField(startRow, startColumn);
            if (pawn != null && pawn.getPieceName().endsWith("P")) {
                if (pawn.getTeamNumber() == 1 && destinationRow == 7) {
                    tradePiece = this.tradePawn(destinationRow, destinationColumn);
                    move = new Move(startRow, startColumn, destinationRow, destinationColumn, true, tradePiece, teamNumber);
                    this.executeMove(move);
                } else if (pawn.getTeamNumber() == 2 && destinationRow == 0) {
                    tradePiece = this.tradePawn(destinationRow, destinationColumn);
                    move = new Move(startRow, startColumn, destinationRow, destinationColumn, true, tradePiece, teamNumber);
                    this.executeMove(move);
                } else {
                    move = new Move(startRow, startColumn, destinationRow, destinationColumn, false, null, teamNumber);
                    this.executeMove(move);
                }
            } else {
                move = new Move(startRow, startColumn, destinationRow, destinationColumn, false, null, teamNumber);
                this.executeMove(move);
            }
        } else {
            move = new Move(startRow, startColumn, destinationRow, destinationColumn, false, null, teamNumber);
        }

        return move;
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
