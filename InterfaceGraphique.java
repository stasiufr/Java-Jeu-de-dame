package jeuDeDames;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.List;

public class InterfaceGraphique extends JFrame {
    private Jeu jeu;
    private JPanel plateauPanel;
    private JButton[][] casesButtons;
    private Piece.Point pieceSelectionnee;
    private JLabel tourLabel;
    private JLabel messageLabel;
    private Color couleurClaire = new Color(255, 206, 158);
    private Color couleurFoncee = new Color(209, 139, 71);
    
    public InterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
        this.pieceSelectionnee = null;
        
        setTitle("Jeu de Dames");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        initialiserInterface();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initialiserInterface() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Jeu");
        JMenuItem nouvellePartie = new JMenuItem("Nouvelle Partie");
        nouvellePartie.addActionListener(e -> jeu.nouvellePartie());
        menu.add(nouvellePartie);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        plateauPanel = new JPanel(new GridLayout(10, 10));
        casesButtons = new JButton[10][10];
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                button.setBackground((i + j) % 2 == 0 ? couleurClaire : couleurFoncee);
                
                final int x = j;
                final int y = i;
                button.addActionListener(e -> gererClicCase(x, y));
                
                casesButtons[j][i] = button;
                plateauPanel.add(button);
            }
        }
        
        add(plateauPanel, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        tourLabel = new JLabel("Tour du Joueur 1 (Blancs)");
        tourLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tourLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        
        messageLabel = new JLabel(" ");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
        
        infoPanel.add(tourLabel);
        infoPanel.add(messageLabel);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private void gererClicCase(int x, int y) {
        messageLabel.setText(" ");
        Case caseCliquee = jeu.getPlateau().getCase(x, y);
        
        if (pieceSelectionnee == null) {
            if (!caseCliquee.estVide() && 
                caseCliquee.getPiece().estBlanc() == jeu.getJoueurCourant().aLesBlancs()) {
                pieceSelectionnee = new Piece.Point(x, y);
                mettreEnSurbrillanceMouvementsPossibles(x, y);
            }
        } else {
            boolean mouvementEffectue = jeu.effectuerCoup(pieceSelectionnee.x, pieceSelectionnee.y, x, y);
            if (mouvementEffectue) {
                afficherPlateau();
                if (!jeu.estPriseMultipleEnCours()) {
                    tourLabel.setText("Tour du " + jeu.getJoueurCourant().toString());
                }
            }
            
            if (!jeu.estPriseMultipleEnCours() || !mouvementEffectue) {
                retirerToutesSurbrillances();
                pieceSelectionnee = null;
            } else {
                pieceSelectionnee = new Piece.Point(x, y);
                mettreEnSurbrillanceMouvementsPossibles(x, y);
            }
        }
    }
    
    private void mettreEnSurbrillanceMouvementsPossibles(int x, int y) {
        retirerToutesSurbrillances();
        casesButtons[x][y].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        
        Case caseActuelle = jeu.getPlateau().getCase(x, y);
        if (!caseActuelle.estVide()) {
            List<Piece.Point> mouvements = caseActuelle.getPiece().mouvementsPossibles(x, y, jeu.getPlateau());
            for (Piece.Point mouvement : mouvements) {
                casesButtons[mouvement.x][mouvement.y].setBorder(
                    BorderFactory.createLineBorder(Color.BLUE, 2));
            }
        }
    }
    
    private void retirerToutesSurbrillances() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                casesButtons[i][j].setBorder(null);
            }
        }
    }
    
    public void afficherPlateau() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Case caseActuelle = jeu.getPlateau().getCase(i, j);
                JButton button = casesButtons[i][j];
                button.setBackground((i + j) % 2 == 0 ? couleurClaire : couleurFoncee);
                
                if (!caseActuelle.estVide()) {
                    Piece piece = caseActuelle.getPiece();
                    if (piece instanceof Dame) {
                        button.setText(piece.estBlanc() ? "DB" : "DN");
                    } else {
                        button.setText(piece.estBlanc() ? "B" : "N");
                    }
                    button.setForeground(piece.estBlanc() ? Color.BLUE : Color.RED);
                    button.setFont(new Font("Arial", Font.BOLD, 24));
                } else {
                    button.setText("");
                }
            }
        }
    }
    
    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }
    
    public void afficherFinPartie(String message) {
        JOptionPane.showMessageDialog(this, message, "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
    }
}