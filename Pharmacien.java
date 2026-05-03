import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Pharmacien {

    // ===== ATTRIBUTS =====
    private int idPharmacien;
    private String nomPharmacien;           // max 25 caractères
    private LocalDateTime heureArrivee;
    private LocalDateTime heureDepart;

    // ===== CONSTRUCTEUR =====
    public Pharmacien(int idPharmacien, String nomPharmacien,
                      LocalDateTime heureArrivee, LocalDateTime heureDepart) {
        this.idPharmacien = idPharmacien;
        setNomPharmacien(nomPharmacien);  // validation via setter
        this.heureArrivee = heureArrivee;
        this.heureDepart = heureDepart;
    }

    // ===== GETTERS =====
    public int getIdPharmacien() { return idPharmacien; }
    public String getNomPharmacien() { return nomPharmacien; }
    public LocalDateTime getHeureArrivee() { return heureArrivee; }
    public LocalDateTime getHeureDepart() { return heureDepart; }

    // ===== SETTERS =====
    public void setNomPharmacien(String nomPharmacien) {
        if (nomPharmacien == null || nomPharmacien.length() > 25) {
            throw new IllegalArgumentException("Le nom du pharmacien doit faire au maximum 25 caractères.");
        }
        this.nomPharmacien = nomPharmacien;
    }
    public void setHeureArrivee(LocalDateTime heureArrivee) { this.heureArrivee = heureArrivee; }
    public void setHeureDepart(LocalDateTime heureDepart) { this.heureDepart = heureDepart; }

    // ===== LISTE GLOBALE ET COMPTEUR =====
    private static ArrayList<Pharmacien> listePharmaciens = new ArrayList<>();
    private static int compteurId = 1;

    // ===== MÉTHODES AJOUTÉES POUR LA PERSISTANCE =====
    public static void setListePharmaciens(ArrayList<Pharmacien> liste) {
        listePharmaciens = liste;
        // Recalculer le compteur d'ID
        int maxId = 0;
        for (Pharmacien p : liste) {
            if (p.getIdPharmacien() > maxId) maxId = p.getIdPharmacien();
        }
        compteurId = maxId + 1;
    }

    public static ArrayList<Pharmacien> getListePharmaciens() {
        return listePharmaciens;
    }

    // ===== METHODE POUR SAISIR UN LocalDateTime =====
    private static LocalDateTime saisirDateTime(Scanner scanner, String description) {
        System.out.println("Saisie de " + description + " :");
        System.out.print("  Année (AAAA) : ");
        int annee = scanner.nextInt();
        System.out.print("  Mois (1-12) : ");
        int mois = scanner.nextInt();
        System.out.print("  Jour (1-31) : ");
        int jour = scanner.nextInt();
        System.out.print("  Heure (0-23) : ");
        int heure = scanner.nextInt();
        System.out.print("  Minute (0-59) : ");
        int minute = scanner.nextInt();
        scanner.nextLine(); // vider buffer
        return LocalDateTime.of(annee, mois, jour, heure, minute);
    }

    // ===== AJOUTER UN PHARMACIEN =====
    public static void ajouterPharmacien() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== AJOUTER UN PHARMACIEN =====\n");

        System.out.print("Nom du pharmacien (max 25 caractères) : ");
        String nom = scanner.nextLine();
        if (nom.length() > 25) {
            System.out.println("❌ Erreur : le nom dépasse 25 caractères. Opération annulée.\n");
            return;
        }

        LocalDateTime arrivee = saisirDateTime(scanner, "l'heure d'arrivée");
        LocalDateTime depart = saisirDateTime(scanner, "l'heure de départ");

        Pharmacien p = new Pharmacien(compteurId++, nom, arrivee, depart);
        listePharmaciens.add(p);

        System.out.println("\n✅ Pharmacien ajouté avec succès ! ID attribué : " + p.getIdPharmacien() + "\n");
    }

    // ===== AFFICHER TOUS LES PHARMACIENS =====
    public static void afficherPharmaciens() {
        System.out.println("\n===== LISTE DES PHARMACIENS =====\n");
        if (listePharmaciens.isEmpty()) {
            System.out.println("Aucun pharmacien enregistré.\n");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Pharmacien p : listePharmaciens) {
            System.out.println("---------------------------");
            System.out.println("ID            : " + p.getIdPharmacien());
            System.out.println("Nom           : " + p.getNomPharmacien());
            System.out.println("Heure arrivée : " + p.getHeureArrivee().format(formatter));
            System.out.println("Heure départ  : " + p.getHeureDepart().format(formatter));
        }
        System.out.println("---------------------------\n");
    }

    // ===== RECHERCHER UN PHARMACIEN PAR NOM =====
    public static void rechercherPharmacien() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEntrez le nom du pharmacien à rechercher : ");
        String recherche = scanner.nextLine();

        boolean trouve = false;
        for (Pharmacien p : listePharmaciens) {
            if (p.getNomPharmacien().equalsIgnoreCase(recherche)) {
                System.out.println("\n✅ Pharmacien trouvé :");
                System.out.println("ID : " + p.getIdPharmacien() + " | Nom : " + p.getNomPharmacien());
                trouve = true;
            }
        }
        if (!trouve) System.out.println("\n❌ Aucun pharmacien trouvé avec ce nom.\n");
    }

    // ===== SUPPRIMER UN PHARMACIEN =====
    public static void supprimerPharmacien() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEntrez l'ID du pharmacien à supprimer : ");
        int idCible = scanner.nextInt();

        Pharmacien aSupprimer = null;
        for (Pharmacien p : listePharmaciens) {
            if (p.getIdPharmacien() == idCible) {
                aSupprimer = p;
                break;
            }
        }
        if (aSupprimer != null) {
            listePharmaciens.remove(aSupprimer);
            System.out.println("\n✅ Pharmacien ID " + idCible + " supprimé avec succès.\n");
        } else {
            System.out.println("\n❌ Aucun pharmacien avec cet ID.\n");
        }
    }

    // ===== MENU PHARMACIEN =====
    public static void menuPharmacien() {
        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        do {
            System.out.println("\n===== GESTION DES PHARMACIENS =====");
            System.out.println("1. Ajouter un pharmacien");
            System.out.println("2. Afficher tous les pharmaciens");
            System.out.println("3. Rechercher un pharmacien");
            System.out.println("4. Supprimer un pharmacien");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: ajouterPharmacien(); break;
                case 2: afficherPharmaciens(); break;
                case 3: rechercherPharmacien(); break;
                case 4: supprimerPharmacien(); break;
                case 0: System.out.println("Retour...\n"); break;
                default: System.out.println("Choix invalide.\n");
            }
        } while (choix != 0);
    }
}
