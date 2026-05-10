public class Medicament {

    private int id;
    private String nom;
    private String formeGalenique;
    private int numeroEtagere;
    private int quantiteInitiale;
    private int quantiteRestante;
    private double prix;    

    public Medicament(int id, String nom, String formeGalenique, int numeroEtagere, int quantite, double prix) {
        this.id = id;
        this.nom = nom;
        this.formeGalenique= formeGalenique;
        this.numeroEtagere = numeroEtagere;
        this.quantiteInitiale = quantite;
        this.quantiteRestante = quantite;
        this.prix = prix;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getFormeGalenique() { return formeGalenique; }
    public int getNumeroEtagere() { return numeroEtagere; }
    public int getQuantiteInitiale() { return quantiteInitiale; }
    public int getQuantite() { return quantiteRestante; }
    public double getPrix() { return prix; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setFormeGalenique(String forme) { this.formeGalenique = forme; }
    public void setNumeroEtagere(int n) { this.numeroEtagere  = n; }
    public void setQuantite(int q) { this.quantiteRestante = q; }
    public void setPrix(double p) { this.prix = p; }

    public void ajouterQuantite(int q) {
        this.quantiteInitiale += q;
        this.quantiteRestante += q;
    }

    public String toString() {
        return "ID: " + id + " | Nom: " + nom + " | Forme galenique: " + formeGalenique + " | Etagere N : "  + numeroEtagere + " | Prix: " + prix + " FCFA" + " | Qte initiale: " + quantiteInitiale +0 " | Qte restante: " + quantiteRestante;
    }
}
