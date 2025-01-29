package jeuDeDames;

import java.util.*;

public class Plateau {
    private Case[][] cases;
    public static final int TAILLE = 10;
    
    public Plateau() {
        cases = new Case[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cases[i][j] = new Case(i, j);
            }
        }
    }
    
    public void initialiserPlateau() {
        // Placement des pions noirs (en haut)
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if ((x + y) % 2 == 1) {
                    cases[x][y].setPiece(new Pion(false));
                }
            }
        }
        
        // Placement des pions blancs (en bas)
        for (int y = 6; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if ((x + y) % 2 == 1) {
                    cases[x][y].setPiece(new Pion(true));
                }
            }
        }
    }
    
    public Case getCase(int x, int y) {
        if (x >= 0 && x < TAILLE && y >= 0 && y < TAILLE) {
            return cases[x][y];
        }
        return null;
    }
    
    public boolean existePrisesPossibles(boolean estBlanc) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Case caseActuelle = cases[i][j];
                if (!caseActuelle.estVide() && 
                    caseActuelle.getPiece().estBlanc() == estBlanc &&
                    !caseActuelle.getPiece().getPrisesPossibles(i, j, this).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean joueurPeutBouger(boolean estBlanc) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Case caseActuelle = cases[i][j];
                if (!caseActuelle.estVide() && 
                    caseActuelle.getPiece().estBlanc() == estBlanc && 
                    !caseActuelle.getPiece().mouvementsPossibles(i, j, this).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean estPartieTerminee() {
        boolean piecesNoires = false;
        boolean piecesBlanches = false;
        
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (!cases[i][j].estVide()) {
                    if (cases[i][j].getPiece().estBlanc()) {
                        piecesBlanches = true;
                    } else {
                        piecesNoires = true;
                    }
                    if (piecesBlanches && piecesNoires) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public int[] compterPieces() {
        int[] compte = new int[2]; // [blanches, noires]
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (!cases[i][j].estVide()) {
                    Piece piece = cases[i][j].getPiece();
                    if (piece.estBlanc()) {
                        compte[0]++;
                    } else {
                        compte[1]++;
                    }
                }
            }
        }
        return compte;
    }
}