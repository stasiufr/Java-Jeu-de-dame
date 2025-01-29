package jeuDeDames;

import java.util.*;

public class Dame extends Piece {
    public Dame(boolean estBlanc) {
        super(estBlanc);
    }
    
    @Override
    public boolean peutDeplacer(int srcX, int srcY, int destX, int destY) {
        return Math.abs(destX - srcX) == Math.abs(destY - srcY);
    }
    
    @Override
    public List<Point> getPrisesPossibles(int x, int y, Plateau plateau) {
        List<Point> prises = new ArrayList<>();
        
        for (Direction dir : getDirectionsPossibles()) {
            int currentX = x + dir.dx;
            int currentY = y + dir.dy;
            
            // Parcourir la diagonale jusqu'à trouver une pièce
            while (estDansLimites(currentX, currentY)) {
                Case caseCourante = plateau.getCase(currentX, currentY);
                
                if (!caseCourante.estVide()) {
                    if (caseCourante.getPiece().estBlanc() != this.estBlanc()) {
                        // Vérifier les cases après la pièce adverse
                        int nextX = currentX + dir.dx;
                        int nextY = currentY + dir.dy;
                        
                        while (estDansLimites(nextX, nextY)) {
                            Case caseApres = plateau.getCase(nextX, nextY);
                            if (caseApres.estVide()) {
                                prises.add(new Point(nextX, nextY));
                            } else {
                                break;
                            }
                            nextX += dir.dx;
                            nextY += dir.dy;
                        }
                    }
                    break;
                }
                currentX += dir.dx;
                currentY += dir.dy;
            }
        }
        
        return prises;
    }
    
    @Override
    protected List<Point> getDeplacementsSimples(int x, int y, Plateau plateau) {
        List<Point> deplacements = new ArrayList<>();
        
        for (Direction dir : getDirectionsPossibles()) {
            int currentX = x + dir.dx;
            int currentY = y + dir.dy;
            
            while (estDansLimites(currentX, currentY)) {
                Case caseCourante = plateau.getCase(currentX, currentY);
                if (caseCourante.estVide()) {
                    deplacements.add(new Point(currentX, currentY));
                } else {
                    break;
                }
                currentX += dir.dx;
                currentY += dir.dy;
            }
        }
        
        return deplacements;
    }
    
    @Override
    protected List<Direction> getDirectionsPossibles() {
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction(1, 1));
        directions.add(new Direction(1, -1));
        directions.add(new Direction(-1, 1));
        directions.add(new Direction(-1, -1));
        return directions;
    }
}