package jeuDeDames;

import java.util.*;

public abstract class Piece {
    private boolean estBlanc;
    
    public Piece(boolean estBlanc) {
        this.estBlanc = estBlanc;
    }
    
    public boolean estBlanc() {
        return estBlanc;
    }
    
    public abstract boolean peutDeplacer(int srcX, int srcY, int destX, int destY);
    
    public List<Point> mouvementsPossibles(int x, int y, Plateau plateau) {
        List<Point> mouvements = new ArrayList<>();
        
        // Si des prises sont possibles, on ne retourne que les prises
        List<Point> prises = getPrisesPossibles(x, y, plateau);
        if (!prises.isEmpty()) {
            return prises;
        }
        
        // Sinon, on retourne les déplacements simples
        return getDeplacementsSimples(x, y, plateau);
    }
    
    public List<Point> getPrisesPossibles(int x, int y, Plateau plateau) {
        List<Point> prises = new ArrayList<>();
        
        // Vérifier les prises dans toutes les directions
        for (Direction dir : getDirectionsPossibles()) {
            int destX = x + 2 * dir.dx;
            int destY = y + 2 * dir.dy;
            int midX = x + dir.dx;
            int midY = y + dir.dy;
            
            if (estDansLimites(destX, destY)) {
                Case caseMilieu = plateau.getCase(midX, midY);
                Case caseDestination = plateau.getCase(destX, destY);
                
                if (!caseMilieu.estVide() && 
                    caseMilieu.getPiece().estBlanc() != this.estBlanc() &&
                    caseDestination.estVide()) {
                    prises.add(new Point(destX, destY));
                }
            }
        }
        
        return prises;
    }
    
    protected abstract List<Point> getDeplacementsSimples(int x, int y, Plateau plateau);
    
    protected abstract List<Direction> getDirectionsPossibles();
    
    protected boolean estDansLimites(int x, int y) {
        return x >= 0 && x < Plateau.TAILLE && y >= 0 && y < Plateau.TAILLE;
    }
    
    protected static class Point {
        public final int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    protected static class Direction {
        public final int dx, dy;
        
        public Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}