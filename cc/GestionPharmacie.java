import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestionPharmacie {

    private ArrayList<Pharmacien>  pharmaciens  = new ArrayList<>();
    private ArrayList<Medicament>  medicaments  = new ArrayList<>();
    private ArrayList<Etagere>     etageres     = new ArrayList<>();
    private ArrayList<Facture>     factures     = new ArrayList<>();
    private ArrayList<Ordonnance>  ordonnances  = new ArrayList<>();

    private int nextIdPharmacien  = 1;
    private int nextIdMedicament  = 1;
    private int nextNumOrdonnance = 1;

    // ==================== PHARMACIENS ====================

    public void ajouterPharmacien(Pharmacien p) {
        p.setId(nextIdPharmacien++);
        pharmaciens.add(p);
        System.out.println("\n Pharmacien ajoute avec succes !");
        System.out.println(p);
    }

    public void retirerPharmacien(int id) {
        for (int i = 0; i < pharmaciens.size(); i++) {
            if (pharmaciens.get(i).getId() == id) {
                System.out.println("\n Pharmacien \"" + pharmaciens.get(i).getNom() + "\" retire.");
                pharmaciens.remove(i);
                return;
            }
        }
        System.out.println("\n Aucun pharmacien trouve avec l'ID " + id + ".");
    }

    public void afficherPharmaciens() {
        if (pharmaciens.isEmpty()) {
            System.out.println("\n  Aucun pharmacien enregistre.");
            return;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("\n========== LISTE DES PHARMACIENS ==========");
        System.out.println("Date : " + LocalDateTime.now().format(fmt));
        System.out.println("--------------------------------------------");
        for (Pharmacien p : pharmaciens) System.out.println(p);
        System.out.println("Total : " + pharmaciens.size() + " pharmacien(s).");
    }

    public Pharmacien trouverPharmacien(int id) {
        for (Pharmacien p : pharmaciens)
            if (p.getId() == id) return p;
        return null;
    }

    public boolean pharmaciensVide() { return pharmaciens.isEmpty(); }

    // ==================== ETAGERES ====================

    public void ajouterEtagere(Etagere e) {
        etageres.add(e);
        System.out.println("\n Etagere enregistree : " + e);
    }

    public Etagere trouverEtagere(int numero) {
        for (Etagere e : etageres)
            if (e.getNumeroEtagere() == numero) return e;
        return null;
    }

    public void afficherEtageres() {
        if (etageres.isEmpty()) {
            System.out.println("\n  Aucune etagere enregistree.");
            return;
        }
        System.out.println("\n--- Liste des etageres ---");
        for (Etagere e : etageres) System.out.println(e);
    }

    // ==================== MEDICAMENTS ====================

    public void ajouterMedicament(Medicament m) {
        m.setId(nextIdMedicament++);
        medicaments.add(m);
        System.out.println("\n Medicament ajoute avec succes !");
        System.out.println(m);
    }

    public void reapprovisionner(Medicament m, int quantiteAAjouter) {
        m.ajouterQuantite(quantiteAAjouter);
        System.out.println("\n Stock mis a jour avec succes !");
        System.out.println(" " + m.getNom() + " : quantite restante = " + m.getQuantite() + " unite(s).");
    }

    public void afficherMedicaments() {
        if (medicaments.isEmpty()) {
            System.out.println("\n  Aucun medicament en stock.");
            return;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("\n========== STOCK DES MEDICAMENTS ==========");
        System.out.println("Date : " + LocalDateTime.now().format(fmt));
        System.out.println("--------------------------------------------");
        for (Medicament m : medicaments) {
            System.out.println("ID: " + m.getId() + " | Nom: " + m.getNom() + " | Forme: " + m.getFormeGalenique() + " | Etagere N : " + m.getNumeroEtagere() + " | Qte initiale: " + m.getQuantiteInitiale() + " | Qte restante: " + m.getQuantite());
        }
        System.out.println("--------------------------------------------");
        System.out.println("Total references en stock : " + medicaments.size() + " medicament(s).");
    }

    public Medicament trouverMedicamentParNom(String nom) {
        for (Medicament m : medicaments)
            if (m.getNom().equalsIgnoreCase(nom)) return m;
        return null;
    }

    public void rechercherParNom(String nom) {
        boolean trouve = false;
        System.out.println("\n--- Resultats pour \"" + nom + "\" ---");
        for (Medicament m : medicaments) {
            if (m.getNom().toLowerCase().contains(nom.toLowerCase())) {
                System.out.println(m);
                Etagere e = trouverEtagere(m.getNumeroEtagere());
                if (e != null) System.out.println("   Etagere : " + e);
                trouve = true;
            }
        }
        if (!trouve) System.out.println("  Aucun medicament trouve avec ce nom.");
    }

    public void rechercherParEtagere(int numeroEtagere) {
        boolean trouve = false;
        Etagere e = trouverEtagere(numeroEtagere);
        System.out.println("\n--- Medicaments sur l'etagere N " + numeroEtagere + " ---");
        if (e != null) System.out.println("  " + e);
        for (Medicament m : medicaments) {
            if (m.getNumeroEtagere() == numeroEtagere) {
                System.out.println(m);
                trouve = true;
            }
        }
        if (!trouve) System.out.println("  Aucun medicament sur cette etagere.");
    }

    public int effectuerVente(String nomMedicament, int quantiteVendue, double prixUnitaire) {
        Medicament m = trouverMedicamentParNom(nomMedicament);
        if (m == null) {
            System.out.println("\n Medicament introuvable dans le stock.");
            return 1;
        }
        if (quantiteVendue > m.getQuantite()) {
            System.out.println("\n Quantite insuffisante en stock.");
            System.out.println("   Stock disponible : " + m.getQuantite() + " unite(s).");
            return 2;
        }
        m.setQuantite(m.getQuantite() - quantiteVendue);
        Facture f = new Facture(nomMedicament, quantiteVendue, prixUnitaire);
        factures.add(f);
        System.out.println("\n Vente effectuee avec succes !");
        System.out.println(f);
        System.out.println(" Stock restant pour " + m.getNom() + " : " + m.getQuantite() + " unite(s).");
        return 0;
    }

    public Medicament verifierDisponibilite(String nom, int quantite) {
        Medicament m = trouverMedicamentParNom(nom);
        if (m == null) {
            System.out.println("\n Medicament introuvable dans le stock.");
            return null;
        }
        if (quantite > m.getQuantite()) {
            System.out.println("\n Quantite insuffisante en stock.");
            System.out.println("   Stock disponible : " + m.getQuantite() + " unite(s).");
            return null;
        }
        return m;
    }

    public void enregistrerFactureUtilisateur(Facture facture) {
        for (LigneFacture ligne : facture.getLignes()) {
            Medicament m = trouverMedicamentParNom(ligne.getNomMedicament());
            if (m != null) {
                m.setQuantite(m.getQuantite() - ligne.getQuantite());
            }
        }
        factures.add(facture);
        System.out.println("\n========== FACTURE ENREGISTREE ==========");
        System.out.println(facture);
        System.out.println("==========================================");
    }

    // ==================== FACTURES ====================

    public void ajouterFacture(Facture f) {
        factures.add(f);
        System.out.println("\n Facture enregistree !");
        System.out.println(f);
    }

    public void afficherFactures() {
        if (factures.isEmpty()) {
            System.out.println("\n  Aucune facture enregistree.");
            return;
        }
        double totalGeneral = 0;
        System.out.println("\n========== LISTE DES FACTURES ==========");
        for (Facture f : factures) {
            System.out.println(f);
            System.out.println("----------------------------------------");
            totalGeneral += f.getTotal();
        }
        System.out.println("TOTAL GENERAL TOUTES FACTURES : " + totalGeneral + " FCFA");
    }

    // ==================== ORDONNANCES ====================

    public void ajouterOrdonnance(Ordonnance o) {
        o.setNumeroOrdonnance(nextNumOrdonnance++);
        ordonnances.add(o);
        System.out.println("\n Ordonnance enregistree !");
        System.out.println(o);
    }

    public void afficherOrdonnances() {
        if (ordonnances.isEmpty()) {
            System.out.println("\n  Aucune ordonnance enregistree.");
            return;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("\n========== LISTE DES ORDONNANCES ==========");
        System.out.println("Date : " + LocalDateTime.now().format(fmt));
        System.out.println("--------------------------------------------");
        for (Ordonnance o : ordonnances) System.out.println(o);
        System.out.println("Total : " + ordonnances.size() + " ordonnance(s).");
    }
}
