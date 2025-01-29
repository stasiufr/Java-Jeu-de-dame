package jeuDeDames;

public class Case {
    private int x;
    private int y;
    private Piece piece;
    private boolean estNoire;
    
    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
        this.estNoire = ((x + y) % 2 == 1);
    }
    
    public boolean estVide() {
        return piece == null;
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    public Piece getPiece() {
        return piece;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean estNoire() {
        return estNoire;
    }
}