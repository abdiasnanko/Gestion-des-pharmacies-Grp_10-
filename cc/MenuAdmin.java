import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuAdmin {

    private static Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void afficher(String nomAdmin) {
        String dateConnexion = LocalDateTime.now().format(fmt);

        int choix;
        do {
            System.out.println("\n========== MENU ADMINISTRATEUR ==========");
            System.out.println("1. Gestion des medicaments");
            System.out.println("2. Gestion des pharmaciens");
            System.out.println("3. Rechercher un medicament");
            System.out.println("4. Gestion du stock");
            System.out.println("5. Gestion des factures");
            System.out.println("0. Se deconnecter");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: menuMedicaments();  break;
                case 2: menuPharmaciens();  break;
                case 3: menuRecherche();    break;
                case 4: SystemePharmacie.gestion.afficherMedicaments(); break;
                case 5: menuFactures();     break;
                case 0:
                    String dateDeconnexion = LocalDateTime.now().format(fmt);
                    System.out.println("\n==========================================");
                    System.out.println(" Administrateur : " + nomAdmin);
                    System.out.println(" Connexion      : " + dateConnexion);
                    System.out.println(" Deconnexion    : " + dateDeconnexion);
                    System.out.println("==========================================\n");
                    break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== MEDICAMENTS ====================
    private static void menuMedicaments() {
        int choix;
        do {
            System.out.println("\n----- Gestion des medicaments -----");
            System.out.println("1. Ajouter un medicament");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: ajouterMedicament(); break;
                case 0: break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void ajouterMedicament() {
        System.out.print("Nom du medicament: ");
        String nom = sc.nextLine();

        Medicament existant = SystemePharmacie.gestion.trouverMedicamentParNom(nom);
        if (existant != null) {
            System.out.println(" Le medicament \"" + nom + "\" existe deja en stock.");
            System.out.println(" Quantite actuelle restante : " + existant.getQuantite() + " unite(s).");
            System.out.print("Quantite a ajouter au stock: ");
            int qteAAjouter = lireEntier();
            SystemePharmacie.gestion.reapprovisionner(existant, qteAAjouter);
            return;
        }

        System.out.print("Forme galenique (comprime, sirop, gelule...): ");
        String forme = sc.nextLine();
        System.out.print("Quantite initiale en stock: ");
        int quantite = lireEntier();
        System.out.print("Prix du medicament (FCFA): ");
        double prix = lireDouble();
        System.out.print("Numero de l'etagere: ");
        int numEtagere = lireEntier();
        System.out.print("Type de traitement de cette etagere: ");
        String typeTraitement = sc.nextLine();

        if (SystemePharmacie.gestion.trouverEtagere(numEtagere) == null) {
            SystemePharmacie.gestion.ajouterEtagere(new Etagere(numEtagere, typeTraitement));
        }

        SystemePharmacie.gestion.ajouterMedicament(
            new Medicament(0, nom, forme, numEtagere, quantite, prix));
    }

    // ==================== RECHERCHE ====================
    private static void menuRecherche() {
        int choix;
        do {
            System.out.println("\n----- Rechercher un medicament -----");
            System.out.println("1. Rechercher par nom");
            System.out.println("2. Rechercher par numero d'etagere");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1:
                    System.out.print("Entrez le nom (ou une partie): ");
                    String nom = sc.nextLine();
                    SystemePharmacie.gestion.rechercherParNom(nom);
                    break;
                case 2:
                    System.out.print("Entrez le numero de l'etagere: ");
                    int num = lireEntier();
                    SystemePharmacie.gestion.rechercherParEtagere(num);
                    break;
                case 0: break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== FACTURES ====================
    private static void menuFactures() {
        int choix;
        do {
            System.out.println("\n----- Gestion des factures -----");
            System.out.println("1. Enregistrer une vente (nouvelle facture)");
            System.out.println("2. Afficher toutes les factures");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: enregistrerVente(); break;
                case 2: SystemePharmacie.gestion.afficherFactures(); break;
                case 0: break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void enregistrerVente() {
        System.out.print("Nom du medicament a vendre: ");
        String nom = sc.nextLine();
        Medicament m = SystemePharmacie.gestion.trouverMedicamentParNom(nom);
        if (m == null) {
            System.out.println("\n Medicament introuvable dans le stock.");
            return;
        }
        System.out.println(" Prix unitaire : " + m.getPrix() + " FCFA");
        System.out.print("Quantite a vendre: ");
        int qte = lireEntier();
        SystemePharmacie.gestion.effectuerVente(nom, qte, m.getPrix());
    }

    // ==================== PHARMACIENS ====================
    private static void menuPharmaciens() {
        int choix;
        do {
            System.out.println("\n----- Gestion des pharmaciens -----");
            System.out.println("1. Ajouter un pharmacien");
            System.out.println("2. Retirer un pharmacien");
            System.out.println("3. Afficher les pharmaciens");
            System.out.println("4. Modifier un pharmacien");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: ajouterPharmacien();  break;
                case 2: retirerPharmacien();  break;
                case 3: SystemePharmacie.gestion.afficherPharmaciens(); break;
                case 4: modifierPharmacien(); break;
                case 0: break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void ajouterPharmacien() {
        System.out.print("Nom du pharmacien: ");
        String nom = sc.nextLine();
        System.out.print("Heure d'arrivee ");
        String arrivee = sc.nextLine();
        System.out.print("Heure de depart ");
        String depart = sc.nextLine();
        SystemePharmacie.gestion.ajouterPharmacien(new Pharmacien(0, nom, arrivee, depart));
    }

    private static void retirerPharmacien() {
        if (SystemePharmacie.gestion.pharmaciensVide()) {
            System.out.println(" Aucun pharmacien enregistre.");
            return;
        }
        SystemePharmacie.gestion.afficherPharmaciens();
        System.out.print("ID du pharmacien a retirer: ");
        int id = lireEntier();
        SystemePharmacie.gestion.retirerPharmacien(id);
    }

    private static void modifierPharmacien() {
        if (SystemePharmacie.gestion.pharmaciensVide()) {
            System.out.println(" Aucun pharmacien enregistre.");
            return;
        }
        SystemePharmacie.gestion.afficherPharmaciens();
        System.out.print("ID du pharmacien a modifier: ");
        int id = lireEntier();
        Pharmacien p = SystemePharmacie.gestion.trouverPharmacien(id);
        if (p == null) {
            System.out.println(" Pharmacien introuvable.");
            return;
        }
        System.out.print("Nouveau nom (taper Entree pour garder la meme donnee\"" + p.getNom() + "\"): ");
        String nom = sc.nextLine();
        if (!nom.isBlank()) p.setNom(nom);
        System.out.print("Nouvelle heure d'arrivee \"" + p.getHeureArrivee() + "\"): ");
        String arr = sc.nextLine();
        if (!arr.isBlank()) p.setHeureArrivee(arr);
        System.out.print("Nouvelle heure de depart \"" + p.getHeureDepart() + "\"): ");
        String dep = sc.nextLine();
        if (!dep.isBlank()) p.setHeureDepart(dep);
        System.out.println("\n Pharmacien modifie avec succes !");
        System.out.println(p);
    }

    // ==================== UTILITAIRES ====================
    private static int lireEntier() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.print(" Entree invalide, entrez la donnée souhaitée: ");
            }
        }
    }

    private static double lireDouble() {
        while (true) {
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.print(" Entree invalide, entrez la donnée souhaitée: ");
            }
        }
    }
}
