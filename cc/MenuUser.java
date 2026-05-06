import java.util.Scanner;

public class MenuUser {

    private static Scanner sc = new Scanner(System.in);

    public static void afficher(String nomUtilisateur) {
        int choix;
        do {
            System.out.println("\n========== MENU UTILISATEUR ==========");
            System.out.println("Connecte en tant que : " + nomUtilisateur);
            System.out.println("--------------------------------------");
            System.out.println("1. Rechercher un medicament par nom");
            System.out.println("0. Se deconnecter");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: demarrerFacture(); break;
                case 0: System.out.println("\n Deconnexion. Retour au menu principal.\n"); break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== CREATION FACTURE UTILISATEUR ====================

    private static void demarrerFacture() {
        // La facture qui accumule toutes les lignes
        Facture factureEnCours = new Facture();
        boolean factureVide = true;

        boolean continuer = true;
        while (continuer) {

            System.out.print("\nEntrez le nom du medicament: ");
            String nom = sc.nextLine();

            Medicament trouve = SystemePharmacie.gestion.trouverMedicamentParNom(nom);
            if (trouve == null) {
                System.out.println(" Medicament introuvable dans le stock.");
                if (factureVide) {
                    System.out.println(" Aucun medicament ajoute. Retour au menu.");
                    return;
                }
                System.out.println("\n Que voulez-vous faire ?");
                System.out.println("1. Rechercher un autre medicament");
                System.out.println("2. Enregistrer la facture avec les medicaments deja ajoutes");
                System.out.println("0. Annuler et retour au menu");
                System.out.print("Votre choix: ");
                int rep = lireEntier();
                if (rep == 1) continue;
                if (rep == 2) { enregistrerFacture(factureEnCours); return; }
                return;
            }

            System.out.println("\n Medicament trouve :");
            System.out.println(" " + trouve);

            System.out.println("\nVoulez-vous ajouter ce medicament a la facture ?");
            System.out.println("1. Oui");
            System.out.println("2. Non");
            System.out.print("Votre choix: ");
            int repAjout = lireEntier();

            if (repAjout == 1) {
                System.out.print("Quantite souhaitee: ");
                int qte = lireEntier();

                Medicament verif = SystemePharmacie.gestion.verifierDisponibilite(nom, qte);
                if (verif == null) {
                    System.out.println("\n Que voulez-vous faire ?");
                    System.out.println("1. Rechercher un autre medicament");
                    if (!factureVide) System.out.println("2. Enregistrer la facture avec les medicaments deja ajoutes");
                    System.out.println("0. Annuler et retour au menu");
                    System.out.print("Votre choix: ");
                    int repErr = lireEntier();
                    if (repErr == 1) continue;
                    if (repErr == 2 && !factureVide) { enregistrerFacture(factureEnCours); return; }
                    return;
                }

                System.out.print("Prix unitaire (FCFA): ");
                double prix = lireDouble();

                factureEnCours.ajouterLigne(new LigneFacture(nom, qte, prix));
                factureVide = false;
                System.out.println(" Medicament ajoute a la facture.");
            }

            System.out.println("\n========================================");
            System.out.println("1. Ajouter un autre medicament");
            System.out.println("2. Enregistrer la facture");
            System.out.print("Votre choix: ");
            int suite = lireEntier();

            if (suite == 2) {
                if (factureVide) {
                    System.out.println(" Aucun medicament ajoute. Facture annulee.");
                    return;
                }
                enregistrerFacture(factureEnCours);
                continuer = false;
            }
        }
    }

    private static void enregistrerFacture(Facture f) {
        SystemePharmacie.gestion.enregistrerFactureUtilisateur(f);
    }

    private static int lireEntier() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.print(" Entree invalide, entrez un nombre: ");
            }
        }
    }

    private static double lireDouble() {
        while (true) {
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.print(" Entree invalide, entrez un nombre: ");
            }
        }
    }
}
