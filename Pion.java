package jeuDeDames;

import java.util.*;

public class Pion extends Piece {
    public Pion(boolean estBlanc) {
        super(estBlanc);
    }
    
    @Override
    public boolean peutDeplacer(int srcX, int srcY, int destX, int destY) {
        int direction = estBlanc() ? -1 : 1;
        int diffY = destY - srcY;
        int diffX = Math.abs(destX - srcX);
        
        // Déplacement simple en diagonale vers l'avant
        return diffX == 1 && diffY == direction;
    }
    
    @Override
    protected List<Point> getDeplacementsSimples(int x, int y, Plateau plateau) {
        List<Point> deplacements = new ArrayList<>();
        int direction = estBlanc() ? -1 : 1;
        
        // Vérifier les deux diagonales avant
        for (int dx : new int[]{-1, 1}) {
            int newX = x + dx;
            int newY = y + direction;
            
            if (estDansLimites(newX, newY) && plateau.getCase(newX, newY).estVide()) {
                deplacements.add(new Point(newX, newY));
            }
        }
        
        return deplacements;
    }
    
    @Override
    protected List<Direction> getDirectionsPossibles() {
        List<Direction> directions = new ArrayList<>();
        
        // Les pions peuvent prendre dans les quatre directions
        directions.add(new Direction(1, 1));
        directions.add(new Direction(-1, 1));
        directions.add(new Direction(1, -1));
        directions.add(new Direction(-1, -1));
        
        return directions;
    }
}