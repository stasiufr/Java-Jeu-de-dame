package jeuDeDames;

public class Joueur {
    private String nom;
    private boolean aLesBlancs;
    private int score;
    
    public Joueur(String nom, boolean aLesBlancs) {
        this.nom = nom;
        this.aLesBlancs = aLesBlancs;
        this.score = 0;
    }
    
    public boolean aLesBlancs() {
        return aLesBlancs;
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getScore() {
        return score;
    }
    
    public void incrementerScore() {
        score++;
    }
    
    @Override
    public String toString() {
        return nom + " (" + (aLesBlancs ? "Blancs" : "Noirs") + ")";
    }
}