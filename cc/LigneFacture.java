public class LigneFacture {

    private String nomMedicament;
    private int quantite;
    private double prixUnitaire;

    public LigneFacture(String nomMedicament, int quantite, double prixUnitaire) {
        this.nomMedicament = nomMedicament;
        this.quantite      = quantite;
        this.prixUnitaire  = prixUnitaire;
    }

    public String getNomMedicament() { return nomMedicament; }
    public int getQuantite() { return quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public double getSousTotal() { return quantite * prixUnitaire; }

    public void ajouterQuantite(int q) {
        this.quantite += q;
    }

    public String toString() {
        return "  - " + nomMedicament + " | Qte: " + quantite + " | Prix unit.: " + prixUnitaire + " FCFA" + " | Sous-total: " + getSousTotal() + " FCFA";
    }
}
