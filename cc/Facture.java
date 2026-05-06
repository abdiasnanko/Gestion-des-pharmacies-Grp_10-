import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Facture {

    private int numeroFacture;
    private String dateVente;
    private ArrayList<LigneFacture> lignes;

    private static int compteur = 1;

    public Facture() {
        this.numeroFacture = compteur++;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.dateVente = LocalDateTime.now().format(fmt);
        this.lignes    = new ArrayList<>();
    }

    public Facture(String nomMedicament, int quantite, double prixUnitaire) {
        this();
        this.lignes.add(new LigneFacture(nomMedicament, quantite, prixUnitaire));
    }

    public void ajouterLigne(LigneFacture ligne) {
        lignes.add(ligne);
    }

    public double getTotal() {
        double total = 0;
        for (LigneFacture l : lignes) total += l.getSousTotal();
        return total;
    }

    public int getNumeroFacture() { return numeroFacture; }
    public String getDateVente()     { return dateVente; }
    public ArrayList<LigneFacture> getLignes() { return lignes; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Facture N ").append(numeroFacture)
          .append(" | Date: ").append(dateVente).append("\n");
        for (LigneFacture l : lignes) {
            sb.append(l).append("\n");
        }
        sb.append("  TOTAL A PAYER : ").append(getTotal()).append(" FCFA");
        return sb.toString();
    }
}
