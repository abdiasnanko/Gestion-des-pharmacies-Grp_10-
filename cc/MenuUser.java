import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuUser {

    private static Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void afficher(String nomUtilisateur) {
        String dateConnexion = LocalDateTime.now().format(fmt);

        int choix;
        do {
            System.out.println("\n========== MENU UTILISATEUR ==========");
            System.out.println(" Connecte en tant que : " + nomUtilisateur);
            System.out.println("--------------------------------------");
            System.out.println("1. Rechercher un medicament par nom");
            System.out.println("0. Se deconnecter");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: demarrerFacture(); break;
                case 0:
                    String dateDeconnexion = LocalDateTime.now().format(fmt);
                    System.out.println("\n==========================================");
                    System.out.println(" Utilisateur    : " + nomUtilisateur);
                    System.out.println(" Connexion      : " + dateConnexion);
                    System.out.println(" Deconnexion    : " + dateDeconnexion);
                    System.out.println("==========================================\n");
                    break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== AFFICHAGE FACTURE EN COURS ====================

    private static void afficherFactureEnCours(Facture f) {
        System.out.println("\n--- Facture en cours ---");
        if (f.getLignes().isEmpty()) {
            System.out.println("  (aucun medicament pour l'instant)");
        } else {
            int i = 1;
            for (LigneFacture l : f.getLignes()) {
                System.out.println("  " + i + ". " + l);
                i++;
            }
            System.out.println("  TOTAL ACTUEL : " + f.getTotal() + " FCFA");
        }
        System.out.println("------------------------");
    }

    // ==================== CREATION FACTURE UTILISATEUR ====================

    private static void demarrerFacture() {
        Facture factureEnCours = new Facture();

        afficherFactureEnCours(factureEnCours);
        System.out.print("Entrez le nom du medicament: ");
        String nom = sc.nextLine().trim();

        Medicament trouve = SystemePharmacie.gestion.trouverMedicamentParNom(nom);
        if (trouve == null) {
            System.out.println(" Medicament introuvable dans le stock.");
            return;
        }

        System.out.println("\n Medicament trouve :");
        System.out.println(" " + trouve);
        System.out.println(" Prix unitaire : " + trouve.getPrix() + " FCFA");

        System.out.println("\nVoulez-vous ajouter ce medicament a la facture ?");
        System.out.println("1. Oui  /  2. Non");
        System.out.print("Votre choix: ");
        int repAjout = lireEntier();
        if (repAjout != 1) return;

        System.out.print("Quantite souhaitee: ");
        int qte = lireEntier();

        Medicament verif = SystemePharmacie.gestion.verifierDisponibilite(nom, qte);
        if (verif == null) return;

        factureEnCours.ajouterLigne(new LigneFacture(nom, qte, trouve.getPrix()));
        System.out.println(" " + nom + " ajoute a la facture.");

        gererFacture(factureEnCours);
    }

    // ==================== OPTIONS APRES AJOUT ====================

    private static void gererFacture(Facture factureEnCours) {
        boolean continuer = true;
        while (continuer) {
            afficherFactureEnCours(factureEnCours);
            System.out.println("1. Ajouter un autre medicament");
            System.out.println("2. Supprimer un medicament de la facture");
            System.out.println("3. Enregistrer la facture");
            System.out.println("0. Retour au menu");
            System.out.print("Votre choix: ");
            int choix = lireEntier();
            switch (choix) {
                case 1:
                    System.out.print("Entrez le nom du medicament: ");
                    String nom = sc.nextLine().trim();
                    ajouterMedicamentAFacture(factureEnCours, nom);
                    break;
                case 2:
                    supprimerLigneFacture(factureEnCours);
                    break;
                case 3:
                    if (factureEnCours.getLignes().isEmpty()) {
                        System.out.println(" Aucun medicament sur la facture. Impossible d'enregistrer.");
                    } else {
                        enregistrerFacture(factureEnCours);
                        continuer = false;
                    }
                    break;
                case 0:
                    System.out.println("\n Retour au menu utilisateur.");
                    continuer = false;
                    break;
                default:
                    System.out.println(" Choix invalide.");
            }
        }
    }

    // ==================== AJOUTER UN MEDICAMENT A LA FACTURE ====================

    private static void ajouterMedicamentAFacture(Facture factureEnCours, String nom) {
        Medicament trouve = SystemePharmacie.gestion.trouverMedicamentParNom(nom);
        if (trouve == null) {
            System.out.println(" Medicament introuvable dans le stock.");
            return;
        }

        System.out.println("\n Medicament trouve :");
        System.out.println(" " + trouve);
        System.out.println(" Prix unitaire : " + trouve.getPrix() + " FCFA");

        System.out.println("\nVoulez-vous ajouter ce medicament a la facture ?");
        System.out.println("1. Oui  /  2. Non");
        System.out.print("Votre choix: ");
        int repAjout = lireEntier();
        if (repAjout != 1) return;

        System.out.print("Quantite souhaitee: ");
        int qte = lireEntier();

        Medicament verif = SystemePharmacie.gestion.verifierDisponibilite(nom, qte);
        if (verif == null) return;

        double prix = trouve.getPrix();

        LigneFacture ligneExistante = null;
        for (LigneFacture l : factureEnCours.getLignes()) {
            if (l.getNomMedicament().equalsIgnoreCase(nom)) {
                ligneExistante = l;
                break;
            }
        }

        if (ligneExistante != null) {
            int qteTotale = ligneExistante.getQuantite() + qte;
            if (SystemePharmacie.gestion.verifierDisponibilite(nom, qteTotale) == null) {
                System.out.println(" Stock insuffisant pour cette quantite supplementaire.");
                System.out.println(" Deja sur la facture : " + ligneExistante.getQuantite() + " unite(s).");
                return;
            }
            ligneExistante.ajouterQuantite(qte);
            System.out.println(" Quantite mise a jour : "
                + ligneExistante.getQuantite() + " unite(s) de " + nom + " sur la facture.");
        } else {
            factureEnCours.ajouterLigne(new LigneFacture(nom, qte, prix));
            System.out.println(" " + nom + " ajoute a la facture.");
        }
    }

    // ==================== SUPPRIMER UNE LIGNE ====================

    private static void supprimerLigneFacture(Facture factureEnCours) {
        if (factureEnCours.getLignes().isEmpty()) {
            System.out.println(" Aucun medicament sur la facture.");
            return;
        }
        afficherFactureEnCours(factureEnCours);
        System.out.print("Entrez le numero de la ligne a supprimer: ");
        int num = lireEntier();
        if (num < 1 || num > factureEnCours.getLignes().size()) {
            System.out.println(" Numero invalide.");
            return;
        }
        String nomSupprime = factureEnCours.getLignes().get(num - 1).getNomMedicament();
        factureEnCours.getLignes().remove(num - 1);
        System.out.println(" \"" + nomSupprime + "\" retire de la facture.");
    }

    // ==================== ENREGISTRER LA FACTURE ====================

    private static void enregistrerFacture(Facture f) {
        SystemePharmacie.gestion.enregistrerFactureUtilisateur(f);
    }

    // ==================== UTILITAIRES ====================
    private static int lireEntier() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.print(" Entree vide, entrer un nombre: ");
            }
        }
    }
}
