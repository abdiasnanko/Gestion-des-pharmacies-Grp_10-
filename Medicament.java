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

    // Constructeurs
    public Medicament() {}

    public Medicament(int idMedicament, String nomCommercialDCI, String formeGalenique,
                      double prix, String listeMedicament, int quantiteDisponible) {
        this.idMedicament = idMedicament;
        setNomCommercialDCI(nomCommercialDCI);
        setFormeGalenique(formeGalenique);
        setPrix(prix);
        setListeMedicament(listeMedicament);
        setQuantiteDisponible(quantiteDisponible);
    }

    // Getters
    public int getIdMedicament() { return idMedicament; }
    public String getNomCommercialDCI() { return nomCommercialDCI; }
    public String getFormeGalenique() { return formeGalenique; }
    public double getPrix() { return prix; }
    public String getListeMedicament() { return listeMedicament; }
    public int getQuantiteDisponible() { return quantiteDisponible; }

    // Setters avec validations
    public void setIdMedicament(int idMedicament) { this.idMedicament = idMedicament; }

    public void setNomCommercialDCI(String nomCommercialDCI) {
        if (nomCommercialDCI == null || nomCommercialDCI.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom commercial/DCI ne peut pas être vide.");
        }
        this.nomCommercialDCI = nomCommercialDCI;
    }

    public void setFormeGalenique(String formeGalenique) {
        if (formeGalenique == null || formeGalenique.trim().isEmpty()) {
            throw new IllegalArgumentException("La forme galénique ne peut pas être vide.");
        }
        this.formeGalenique = formeGalenique;
    }

    public void setPrix(double prix) {
        if (prix <= 0) {
            throw new IllegalArgumentException("Le prix doit être supérieur à 0.");
        }
        this.prix = prix;
    }

    public void setListeMedicament(String listeMedicament) {
        if (listeMedicament == null || listeMedicament.trim().isEmpty()) {
            throw new IllegalArgumentException("La liste du médicament ne peut pas être vide.");
        }
        this.listeMedicament = listeMedicament;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        if (quantiteDisponible < 0) {
            throw new IllegalArgumentException("La quantité disponible ne peut pas être négative.");
        }
        this.quantiteDisponible = quantiteDisponible;
    }

    @Override
    public int compareTo(Medicament autre) {
        return this.nomCommercialDCI.compareToIgnoreCase(autre.nomCommercialDCI);
    }

    @Override
    public String toString() {
        return String.format(
                "ID Médicament : %d\n" +
                "Nom (DCI)      : %s\n" +
                "Forme galénique: %s\n" +
                "Prix           : %.2f F\n" +
                "Liste          : %s\n" +
                "Qté disponible : %d",
                idMedicament, nomCommercialDCI, formeGalenique,
                prix, listeMedicament, quantiteDisponible
        );
    }

    // ================= PERSISTANCE =================
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER))) {
            oos.writeObject(liste);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Medicament> chargerTous() {
        File f = new File(FICHIER);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER))) {
            return (List<Medicament>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ================= GESTION DES ID =================
    private static int genererId() {
        List<Medicament> liste = chargerTous();
        int maxId = 0;
        for (Medicament m : liste) {
            if (m.getIdMedicament() > maxId) {
                maxId = m.getIdMedicament();
            }
        }
        return maxId + 1;
    }

    // ================= METHODES DE GESTION =================
    public static void ajouterMedicament() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== AJOUTER UN MÉDICAMENT =====\n");

        // Saisie nom
        String nom = "";
        while (nom.trim().isEmpty()) {
            System.out.print("Nom commercial/DCI : ");
            nom = scanner.nextLine();
            if (nom.trim().isEmpty()) {
                System.out.println("❌ Le nom est obligatoire.");
            }
        }

        // Saisie forme galénique
        String forme = "";
        while (forme.trim().isEmpty()) {
            System.out.print("Forme galénique (ex: comprimé, sirop, injection) : ");
            forme = scanner.nextLine();
            if (forme.trim().isEmpty()) {
                System.out.println("❌ La forme galénique est obligatoire.");
            }
        }

        // Saisie prix
        double prix = 0;
        while (prix <= 0) {
            try {
                System.out.print("Prix unitaire (FCFA) : ");
                prix = Double.parseDouble(scanner.nextLine());
                if (prix <= 0) System.out.println("❌ Le prix doit être > 0.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre valide.");
            }
        }

        // Saisie liste
        String liste = "";
        while (liste.trim().isEmpty()) {
            System.out.print("Liste (ex: Liste I, Liste II, Liste psychotrope) : ");
            liste = scanner.nextLine();
            if (liste.trim().isEmpty()) {
                System.out.println("❌ La liste est obligatoire.");
            }
        }

        // Saisie quantité
        int quantite = -1;
        while (quantite < 0) {
            try {
                System.out.print("Quantité disponible : ");
                quantite = Integer.parseInt(scanner.nextLine());
                if (quantite < 0) System.out.println("❌ La quantité ne peut pas être négative.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un entier valide.");
            }
        }

        int id = genererId();
        Medicament med = new Medicament(id, nom, forme, prix, liste, quantite);
        enregistrer(med);
        System.out.println("\n✅ Médicament ajouté avec succès ! ID généré : " + id + "\n");
    }

    public static void afficherTous() {
        List<Medicament> liste = chargerTous();
        if (liste.isEmpty()) {
            System.out.println("\nAucun médicament enregistré.\n");
            return;
        }
        Collections.sort(liste);
        System.out.println("\n===== LISTE DES MÉDICAMENTS (ordre alphabétique) =====\n");
        for (Medicament m : liste) {
            System.out.println(m);
            System.out.println("---------------------------");
        }
        System.out.println("Total : " + liste.size() + " médicament(s).\n");
    }

    public static void rechercherMedicament() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== RECHERCHER UN MÉDICAMENT =====\n");
        System.out.println("1. Rechercher par ID");
        System.out.println("2. Rechercher par nom (contient)");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1":
                System.out.print("Entrez l'ID du médicament : ");
                try {
                    int id = Integer.parseInt(scanner.nextLine());
                    afficherParId(id);
                } catch (NumberFormatException e) {
                    System.out.println("❌ ID invalide.\n");
                }
                break;
            case "2":
                System.out.print("Entrez un terme de recherche (nom) : ");
                String terme = scanner.nextLine();
                List<Medicament> resultats = rechercherParNom(terme);
                if (resultats.isEmpty()) {
                    System.out.println("\n❌ Aucun médicament trouvé contenant \"" + terme + "\".\n");
                } else {
                    System.out.println("\n🔍 " + resultats.size() + " résultat(s) :\n");
                    for (Medicament m : resultats) {
                        System.out.println(m);
                        System.out.println("---------------------------");
                    }
                }
                break;
            default:
                System.out.println("Choix invalide.\n");
        }
    }

    public static void supprimerMedicament() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEntrez l'ID du médicament à supprimer : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            List<Medicament> liste = chargerTous();
            boolean trouve = false;
            Iterator<Medicament> it = liste.iterator();
            while (it.hasNext()) {
                Medicament m = it.next();
                if (m.getIdMedicament() == id) {
                    it.remove();
                    trouve = true;
                    break;
                }
            }
            if (trouve) {
                sauvegarderListe(liste);
                System.out.println("\n✅ Médicament ID " + id + " supprimé avec succès.\n");
            } else {
                System.out.println("\n❌ Aucun médicament avec l'ID " + id + ".\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID invalide.\n");
        }
    }

    public static void afficherParId(int id) {
        List<Medicament> liste = chargerTous();
        for (Medicament m : liste) {
            if (m.getIdMedicament() == id) {
                System.out.println("\n🔍 Médicament trouvé :\n");
                System.out.println(m);
                System.out.println();
                return;
            }
        }
        System.out.println("❌ Aucun médicament trouvé avec l'ID : " + id + "\n");
    }

    public static List<Medicament> rechercherParNom(String terme) {
        List<Medicament> tous = chargerTous();
        List<Medicament> resultats = new ArrayList<>();
        for (Medicament m : tous) {
            if (m.getNomCommercialDCI().toLowerCase().contains(terme.toLowerCase())) {
                resultats.add(m);
            }
        }
        Collections.sort(resultats);
        return resultats;
    }

    // ================= MENU INTERACTIF =================
    public static void menuMedicament() {
        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        do {
            System.out.println("\n===== GESTION DES MÉDICAMENTS =====");
            System.out.println("1. Ajouter un médicament");
            System.out.println("2. Afficher tous les médicaments");
            System.out.println("3. Rechercher un médicament");
            System.out.println("4. Supprimer un médicament");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre.");
                continue;
            }

            switch (choix) {
                case 1:
                    ajouterMedicament();
                    break;
                case 2:
                    afficherTous();
                    break;
                case 3:
                    rechercherMedicament();
                    break;
                case 4:
                    supprimerMedicament();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...\n");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.\n");
            }
        } while (choix != 0);
    }
}
