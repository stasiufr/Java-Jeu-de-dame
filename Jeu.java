package jeuDeDames;

import javax.swing.*;
import java.util.*;

public class Jeu {
    private static Jeu instance;
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;
    private InterfaceGraphique gui;
    private boolean priseMultipleEnCours;
    private Piece.Point dernierePrisePosition;
    
    public Jeu() {
        instance = this;
        nouvellePartie();
    }
    
    public static Jeu getInstance() {
        return instance;
    }
    
    public void nouvellePartie() {
        plateau = new Plateau();
        joueur1 = new Joueur("Joueur 1", true);
        joueur2 = new Joueur("Joueur 2", false);
        joueurCourant = joueur1;
        priseMultipleEnCours = false;
        dernierePrisePosition = null;
        
        if (gui == null) {
            gui = new InterfaceGraphique(this);
        }
        demarrerJeu();
    }
    
    public void demarrerJeu() {
        plateau.initialiserPlateau();
        gui.afficherPlateau();
    }
    
    public boolean effectuerCoup(int srcX, int srcY, int destX, int destY) {
        // Vérifier si on est en prise multiple
        if (priseMultipleEnCours && (srcX != dernierePrisePosition.x || srcY != dernierePrisePosition.y)) {
            gui.afficherMessage("Vous devez continuer la prise avec la même pièce !");
            return false;
        }
        
        Case source = plateau.getCase(srcX, srcY);
        Case destination = plateau.getCase(destX, destY);
        
        if (source == null || destination == null || source.estVide()) {
            return false;
        }
        
        Piece piece = source.getPiece();
        if (piece.estBlanc() != joueurCourant.aLesBlancs()) {
            return false;
        }
        
        // Vérifier s'il y a des prises possibles
        List<Piece.Point> prisesPossibles = piece.getPrisesPossibles(srcX, srcY, plateau);
        boolean priseObligatoire = plateau.existePrisesPossibles(joueurCourant.aLesBlancs());
        
        if (priseObligatoire) {
            // Si une prise est possible, le coup doit être une prise
            boolean estPriseValide = false;
            for (Piece.Point prise : prisesPossibles) {
                if (prise.x == destX && prise.y == destY) {
                    estPriseValide = true;
                    break;
                }
            }
            
            if (!estPriseValide) {
                gui.afficherMessage("Une prise est obligatoire !");
                return false;
            }
            
            // Effectuer la prise
            int midX = (srcX + destX) / 2;
            int midY = (srcY + destY) / 2;
            plateau.getCase(midX, midY).setPiece(null);
            
            // Déplacer la pièce
            destination.setPiece(piece);
            source.setPiece(null);
            
            // Vérifier s'il y a d'autres prises possibles
            List<Piece.Point> prisesSupplementaires = piece.getPrisesPossibles(destX, destY, plateau);
            if (!prisesSupplementaires.isEmpty()) {
                priseMultipleEnCours = true;
                dernierePrisePosition = new Piece.Point(destX, destY);
                gui.afficherMessage("Prise multiple possible !");
                return true;
            }
        } else {
            // Déplacement simple
            if (!piece.peutDeplacer(srcX, srcY, destX, destY)) {
                return false;
            }
            destination.setPiece(piece);
            source.setPiece(null);
        }
        
        // Vérifier la promotion en dame
        if (piece instanceof Pion) {
            if ((piece.estBlanc() && destY == 0) || (!piece.estBlanc() && destY == 9)) {
                destination.setPiece(new Dame(piece.estBlanc()));
            }
        }
        
        // Fin du tour
        priseMultipleEnCours = false;
        dernierePrisePosition = null;
        changerJoueur();
        
        // Vérifier si la partie est terminée
        if (estPartieTerminee()) {
            String message = determinerGagnant();
            gui.afficherFinPartie(message);
        }
        
        return true;
    }
    
    private void changerJoueur() {
        joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
    }
    
    public boolean estPartieTerminee() {
        return plateau.estPartieTerminee() || !plateau.joueurPeutBouger(joueurCourant.aLesBlancs());
    }
    
    public String determinerGagnant() {
        if (!plateau.joueurPeutBouger(joueurCourant.aLesBlancs())) {
            return ((joueurCourant == joueur1) ? joueur2 : joueur1).toString() + " a gagné !";
        }
        
        int[] pieces = plateau.compterPieces();
        if (pieces[0] > pieces[1]) {
            return "Les Blancs ont gagné !";
        } else if (pieces[1] > pieces[0]) {
            return "Les Noirs ont gagné !";
        } else {
            return "Match nul !";
        }
    }
    
    public Plateau getPlateau() {
        return plateau;
    }
    
    public Joueur getJoueurCourant() {
        return joueurCourant;
    }
    
    public boolean estPriseMultipleEnCours() {
        return priseMultipleEnCours;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Jeu();
        });
    }
}