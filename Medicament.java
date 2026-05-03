import java.io.*;
import java.util.*;

public class Medicament implements Comparable<Medicament>, Serializable {

    private static final long serialVersionUID = 1L;

    private int idMedicament;
    private String nomCommercialDCI;
    private String formeGalenique;
    private double prix;
    private String listeMedicament;
    private int quantiteDisponible;

    public Medicament() {}

    public Medicament(int idMedicament, String nomCommercialDCI, String formeGalenique,
                    double prix, String listeMedicament, int quantiteDisponible) {
        this.idMedicament = idMedicament;
        this.nomCommercialDCI = nomCommercialDCI;
        this.formeGalenique = formeGalenique;
        this.prix = prix;
        this.listeMedicament = listeMedicament;
        this.quantiteDisponible = quantiteDisponible;
    }


    public int getIdMedicament() { return idMedicament; }
    public void setIdMedicament(int idMedicament) { this.idMedicament = idMedicament; }

    public String getNomCommercialDCI() { return nomCommercialDCI; }
    public void setNomCommercialDCI(String nomCommercialDCI) { this.nomCommercialDCI = nomCommercialDCI; }

    public String getFormeGalenique() { return formeGalenique; }
    public void setFormeGalenique(String formeGalenique) { this.formeGalenique = formeGalenique; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public String getListeMedicament() { return listeMedicament; }
    public void setListeMedicament(String listeMedicament) { this.listeMedicament = listeMedicament; }

    public int getQuantiteDisponible() { return quantiteDisponible; }
    public void setQuantiteDisponible(int quantiteDisponible) { this.quantiteDisponible = quantiteDisponible; }

    @Override
    public int compareTo(Medicament autre) {
        return this.nomCommercialDCI.compareToIgnoreCase(autre.nomCommercialDCI);
    }

    @Override
    public String toString() {
        return String.format(
            "\n" +
            "ID Médicament : %-20d \n" +
            "Nom (DCI) : %-20s \n" +
            "Forme galénique : %-20s \n" +
            "Prix : %-19.2f F\n" +
            "Liste : %-20s \n" +
            "Qté disponible : %-20d \n" +
            idMedicament, nomCommercialDCI, formeGalenique,
            prix, listeMedicament, quantiteDisponible
        );
    }

    private static final String FICHIER = "medicaments.dat";


    public static void enregistrer(Medicament med) {
        List<Medicament> liste = chargerTous();
    
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getIdMedicament() == med.getIdMedicament()) {
                liste.set(i, med); // mise à jour
                sauvegarderListe(liste);
                System.out.println("✔ Médicament mis à jour avec succès.");
                return;
            }
        }
        liste.add(med);
        sauvegarderListe(liste);
        System.out.println("✔ Médicament enregistré avec succès.");
    }

    private static void sauvegarderListe(List<Medicament> liste) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FICHIER))) {
            oos.writeObject(liste);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Medicament> chargerTous() {
        File f = new File(FICHIER);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FICHIER))) {
            return (List<Medicament>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public static List<Medicament> rechercherParNom(String terme) {
        List<Medicament> tous = chargerTous();
        List<Medicament> resultats = new ArrayList<>();
        for (Medicament m : tous) {
            if (m.getNomCommercialDCI().toLowerCase()
                    .contains(terme.toLowerCase())) {
                resultats.add(m);
            }
        }
        Collections.sort(resultats);
        return resultats;
    }

    
    public static void afficherTous() {
        List<Medicament> liste = chargerTous();
        if (liste.isEmpty()) {
            System.out.println("Aucun médicament enregistré.");
            return;
        }
        Collections.sort(liste);
        System.out.println("\nListe des medicaments (ordre alphabétique)");
        for (Medicament m : liste) {
            System.out.println(m);
            System.out.println();
        }
        System.out.println("Total : " + liste.size() + " médicament(s).");
    }


    public static void afficherParId(int id) {
        List<Medicament> liste = chargerTous();
        for (Medicament m : liste) {
            if (m.getIdMedicament() == id) {
                System.out.println("\nMedicaments trouvé");
                System.out.println(m);
                return;
            }
        }
        System.out.println("Aucun médicament trouvé avec l'ID : " + id);
    }
}
