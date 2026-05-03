import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pharmacie {

    // ==================== AUTHENTIFICATION (tableaux statiques) ====================
    private static void passUser() {
        String[] tab = new String[6];
        String[] pass = new String[6];
        // ID Olle 
        tab[0] = "Olle";
        pass[0] = "0499";
        // ID Nana 
        tab[1] = "Nana";
        pass[1] = "0500";
        // ID Nadia
        tab[2] = "Nadia";
        pass[2] = "0438";
        // ID Ndam
        tab[3] = "Ndam";
        pass[3] = "0498";
        // ID Ibrahima 
        tab[4] = "Ibrahima";
        pass[4] = "449";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Votre ID de connexion utilisateur: ");
        String id = scanner.nextLine();
        System.out.print("Votre Mot de passe utilisateur: ");
        String passwd = scanner.nextLine();

        int i = 0;
        boolean success = false;
        while (i < tab.length) {
            if (tab[i] != null && pass[i] != null) {
                if (tab[i].equals(id) && pass[i].equals(passwd)) {
                    System.out.println("\nBienvenue " + tab[i] + "\n");
                    horodatage();
                    success = true;
                    // Lancer le menu utilisateur
                    menuUtilisateur();
                    break;
                }
            }
            i++;
        }
        if (!success) {
            System.out.println("\nIdentifiant ou mot de passe incorrect \n");
        }
    }

    private static void passAdmin() {
        String[] tabAdmin = new String[6];
        String[] passwdAdmin = new String[6];
        tabAdmin[0] = "Nanko";
        passwdAdmin[0] = "0421";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Votre ID administrateur: ");
        String id = scanner.nextLine();
        System.out.print("Votre Mot de passe administrateur: ");
        String pwd = scanner.nextLine();

        int i = 0;
        boolean success = false;
        while (i < tabAdmin.length) {
            if (tabAdmin[i] != null && passwdAdmin[i] != null) {
                if (tabAdmin[i].equals(id) && passwdAdmin[i].equals(pwd)) {
                    System.out.println("\nBienvenue " + tabAdmin[i] + "\n");
                    horodatage();
                    success = true;
                    // Lancer le menu administrateur
                    menuAdministrateur();
                    break;
                }
            }
            i++;
        }
        if (!success) {
            System.out.println("\nIdentifiant ou mot de passe incorrect \n");
        }
    }

    // ==================== ESPACE UTILISATEUR ====================
    private static void menuUtilisateur() {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n===== ESPACE UTILISATEUR =====");
            System.out.println("1. Rechercher un médicament");
            System.out.println("0. Se déconnecter");
            System.out.print("Choix : ");
            choix = sc.nextInt(); sc.nextLine();
            switch (choix) {
                case 1 -> Medicament.rechercherMedicament();
                case 0 -> System.out.println("Déconnexion...");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== ESPACE ADMINISTRATEUR ====================
    private static void menuAdministrateur() {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n===== ESPACE ADMINISTRATEUR =====");
            System.out.println("1. Gestion des médicaments (CRUD complet)");
            System.out.println("2. Gestion des pharmaciens (CRUD complet)");
            System.out.println("0. Se déconnecter");
            System.out.print("Choix : ");
            choix = sc.nextInt(); sc.nextLine();
            switch (choix) {
                case 1 -> Medicament.menuMedicament();
                case 2 -> {
                    chargerPharmaciens();
                    Pharmacien.menuPharmacien();
                    sauvegarderPharmaciens();
                }
                case 0 -> System.out.println("Déconnexion...");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== PERSISTANCE PHARMACIENS ====================
    private static void chargerPharmaciens() {
        ArrayList<Pharmacien> liste = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try (BufferedReader br = new BufferedReader(new FileReader("pharmaciens.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String nom = parts[1];
                    LocalDateTime arrivee = LocalDateTime.parse(parts[2], formatter);
                    LocalDateTime depart = LocalDateTime.parse(parts[3], formatter);
                    liste.add(new Pharmacien(id, nom, arrivee, depart));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Fichier pharmaciens.txt introuvable, création d'une liste vide.");
        }
        Pharmacien.setListePharmaciens(liste);
    }

    private static void sauvegarderPharmaciens() {
        ArrayList<Pharmacien> liste = Pharmacien.getListePharmaciens();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("pharmaciens.txt"))) {
            for (Pharmacien p : liste) {
                String ligne = p.getIdPharmacien() + ";" + p.getNomPharmacien() + ";"
                        + p.getHeureArrivee().format(formatter) + ";" + p.getHeureDepart().format(formatter);
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur sauvegarde pharmaciens.");
        }
    }

    // ==================== MENU PRINCIPAL ====================
    private static void horodatage() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Date/heure connexion : " + now.format(fmt) + "\n");
    }

    private static void optionMenu() {
        System.out.println("===== PHARMACIE HADJA LATIFA =====\n");
        System.out.println("1. Connexion utilisateur");
        System.out.println("2. Connexion administrateur");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
    }

    private static void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            optionMenu();
            choix = sc.nextInt(); sc.nextLine();
            switch (choix) {
                case 1 -> passUser();
                case 2 -> passAdmin();
                case 0 -> {
                    System.out.print("Confirmer quitter ? (1 oui / 2 non) : ");
                    int conf = sc.nextInt();
                    if (conf == 1) {
                        System.out.println("Au revoir !");
                        return;
                    }
                }
                default -> System.out.println("Choix invalide.\n");
            }
        } while (true);
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        chargerPharmaciens();      // charge la liste statique de Pharmacien

        // Création de médicaments par défaut si le fichier est vide
        List<Medicament> test = Medicament.chargerTous();
        if (test.isEmpty()) {
            System.out.println("Création de médicaments par défaut...");
            Medicament m1 = new Medicament(1, "Doliprane", "Comprimé", 2.5, "Liste I", 100);
            Medicament m2 = new Medicament(2, "Amoxicilline", "Gélule", 4.2, "Liste II", 50);
            Medicament m3 = new Medicament(3, "Ventoline", "Inhalateur", 5.8, "Liste I", 30);
            Medicament.enregistrer(m1);
            Medicament.enregistrer(m2);
            Medicament.enregistrer(m3);
        }

        menuPrincipal();
        sauvegarderPharmaciens();   // sauvegarde finale des pharmaciens
    }
}
