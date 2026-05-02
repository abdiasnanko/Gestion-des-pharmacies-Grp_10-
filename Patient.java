import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient {

    // ===== ATTRIBUTS =====
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String dateNaissance;

    // ===== CONSTRUCTEUR =====
    public Patient(int id, String nom, String prenom, String telephone, String adresse, String dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    // ===== GETTERS =====
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
    public String getDateNaissance() { return dateNaissance; }

    // ===== SETTERS =====
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    // ===== LISTE GLOBALE DES PATIENTS =====
    private static List<Patient> listePatients = new ArrayList<>();
    private static int compteurId = 1;

    // ===== AJOUTER UN PATIENT =====
    public static void ajouterPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== AJOUTER UN PATIENT =====\n");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();

        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();

        System.out.print("Date de naissance (jj/mm/aaaa) : ");
        String dateNaissance = scanner.nextLine();

        Patient p = new Patient(compteurId++, nom, prenom, telephone, adresse, dateNaissance);
        listePatients.add(p);

        System.out.println("\n✅ Patient ajouté avec succès ! ID attribué : " + p.getId() + "\n");
    }

    // ===== AFFICHER TOUS LES PATIENTS =====
    public static void afficherPatients() {
        System.out.println("\n===== LISTE DES PATIENTS =====\n");
        if (listePatients.isEmpty()) {
            System.out.println("Aucun patient enregistré.\n");
            return;
        }
        for (Patient p : listePatients) {
            System.out.println("---------------------------");
            System.out.println("ID           : " + p.getId());
            System.out.println("Nom          : " + p.getNom());
            System.out.println("Prénom       : " + p.getPrenom());
            System.out.println("Téléphone    : " + p.getTelephone());
            System.out.println("Adresse      : " + p.getAdresse());
            System.out.println("Date Naiss.  : " + p.getDateNaissance());
        }
        System.out.println("---------------------------\n");
    }

    // ===== RECHERCHER UN PATIENT PAR NOM =====
    public static void rechercherPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEntrez le nom du patient à rechercher : ");
        String recherche = scanner.nextLine();

        boolean trouve = false;
        for (Patient p : listePatients) {
            if (p.getNom().equalsIgnoreCase(recherche)) {
                System.out.println("\n✅ Patient trouvé :");
                System.out.println("ID : " + p.getId() + " | " + p.getPrenom() + " " + p.getNom() +
                        " | Tél : " + p.getTelephone());
                trouve = true;
            }
        }
        if (!trouve) System.out.println("\n❌ Aucun patient trouvé avec ce nom.\n");
    }

    // ===== SUPPRIMER UN PATIENT =====
    public static void supprimerPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEntrez l'ID du patient à supprimer : ");
        int idCible = scanner.nextInt();

        Patient aSupprimer = null;
        for (Patient p : listePatients) {
            if (p.getId() == idCible) {
                aSupprimer = p;
                break;
            }
        }
        if (aSupprimer != null) {
            listePatients.remove(aSupprimer);
            System.out.println("\n✅ Patient ID " + idCible + " supprimé avec succès.\n");
        } else {
            System.out.println("\n❌ Aucun patient avec cet ID.\n");
        }
    }

    // ===== MENU PATIENT (appelé depuis Pharmacie.java) =====
    public static void menuPatient() {
        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        do {
            System.out.println("\n===== GESTION DES PATIENTS =====");
            System.out.println("1. Ajouter un patient");
            System.out.println("2. Afficher tous les patients");
            System.out.println("3. Rechercher un patient");
            System.out.println("4. Supprimer un patient");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine(); // vider le buffer

            switch (choix) {
                case 1: ajouterPatient(); break;
                case 2: afficherPatients(); break;
                case 3: rechercherPatient(); break;
                case 4: supprimerPatient(); break;
                case 0: System.out.println("Retour...\n"); break;
                default: System.out.println("Choix invalide.\n");
            }
        } while (choix != 0);
    }
}