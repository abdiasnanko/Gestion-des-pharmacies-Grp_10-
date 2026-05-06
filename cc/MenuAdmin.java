import java.util.Scanner;

public class MenuAdmin {

    private static Scanner sc = new Scanner(System.in);

    public static void afficher() {
        int choix;
        do {
            System.out.println("\n========== MENU ADMINISTRATEUR ==========");
            System.out.println("1. Gestion des médicaments");
            System.out.println("2. Gestion des pharmaciens");
            System.out.println("3. Rechercher un médicament");
            System.out.println("4. Gestion du stock");
            System.out.println("5. Gestion des factures");
            System.out.println("6. Liste des ordonnances");
            System.out.println("0. Se déconnecter");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: menuMedicaments();  break;
                case 2: menuPharmaciens();  break;
                case 3: menuRecherche();    break;
                case 4: SystemePharmacie.gestion.afficherMedicaments(); break;
                case 5: menuFactures();     break;
                case 6: SystemePharmacie.gestion.afficherOrdonnances(); break;
                case 0: System.out.println("\n Déconnexion. Retour au menu principal.\n"); break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    // ==================== MEDICAMENTS ====================
    private static void menuMedicaments() {
        int choix;
        do {
            System.out.println("\n----- Gestion des médicaments -----");
            System.out.println("1. Ajouter un médicament");
            System.out.println("2. Afficher les médicaments en stock");
            System.out.println("3. Ajouter une ordonnance");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");
            choix = lireEntier();
            switch (choix) {
                case 1: ajouterMedicament(); break;
                case 2: SystemePharmacie.gestion.afficherMedicaments(); break;
                case 3: ajouterOrdonnance(); break;
                case 0: break;
                default: System.out.println(" Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void ajouterMedicament() {

        System.out.print("Nom du médicament: ");
        String nom = sc.nextLine();

        Medicament existant = SystemePharmacie.gestion.trouverMedicamentParNom(nom);
        if (existant != null) {
            System.out.println(" Le médicament \"" + nom + "\" existe déjà en stock.");
            System.out.println(" Quantité actuelle restante : " + existant.getQuantite() + " unite(s).");
            System.out.print("Quantité à ajouter au stock: ");
            int qteAAjouter = lireEntier();
            SystemePharmacie.gestion.reapprovisionner(existant, qteAAjouter);
            return;
        }

        System.out.print("Forme galénique (comprimé, sirop, gelule...): ");
        String forme = sc.nextLine();
        System.out.print("Quantité initiale en stock: ");
        int quantite = lireEntier();
        System.out.print("Numero de l'étagere: ");
        int numEtagere = lireEntier();
        System.out.print("Type de traitement de cette etagere: ");
        String typeTraitement = sc.nextLine();

        if (SystemePharmacie.gestion.trouverEtagere(numEtagere) == null) {
            SystemePharmacie.gestion.ajouterEtagere(new Etagere(numEtagere, typeTraitement));
        }

        SystemePharmacie.gestion.ajouterMedicament(new Medicament(0, nom, forme, numEtagere, quantite));
    }

    private static void ajouterOrdonnance() {
        System.out.print("Nom du patient: ");
        String nomPat = sc.nextLine();
        System.out.print("Nom du medicament: ");
        String nomMed = sc.nextLine();
        System.out.print("Forme galenique: ");
        String forme = sc.nextLine();
        System.out.print("Dosage (500mg, 250mg...): ");
        String dosage = sc.nextLine();
        System.out.print("Posologie (3x/jr, 2x/jr...): ");
        String posologie = sc.nextLine();
        SystemePharmacie.gestion.ajouterOrdonnance(new Ordonnance(0, nomPat, nomMed, forme, dosage, posologie));
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
            System.out.println("1. Enregistrer une vente");
            System.out.println("2. Afficher les factures");
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
        if (SystemePharmacie.gestion.trouverMedicamentParNom(nom) == null) {
            System.out.println("\n Medicament introuvable dans le stock.");
            return;
        }
        System.out.print("Quantite a vendre: ");
        int qte = lireEntier();
        System.out.print("Prix unitaire (FCFA): ");
        double prix = lireDouble();
        SystemePharmacie.gestion.effectuerVente(nom, qte, prix);
    }

    // ==================== PHARMACIENS ====================
    private static void menuPharmaciens() {
        int choix;
        do {
            System.out.println("\n----- Gestion des pharmaciens -----");
            System.out.println("1. Ajouter un pharmacien");
            System.out.println("2. Retirer un pharmacien");
            System.out.println("3. Afficher les pharmaciens");
            System.out.println("4. Modifier les informations d'un pharmacien");
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
        System.out.print("Heure d'arrivee: ");
        String arrivee = sc.nextLine();
        System.out.print("Heure de depart: ");
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
        System.out.print("Nouvelle heure d'arrivee (\"" + p.getHeureArrivee() + "\"): ");
        String arr = sc.nextLine();
        if (!arr.isBlank()) p.setHeureArrivee(arr);
        System.out.print("Nouvelle heure de depart (\"" + p.getHeureDepart() + "\"): ");
        String dep = sc.nextLine();
        if (!dep.isBlank()) p.setHeureDepart(dep);
        System.out.println("\n Pharmacien modifie avec succes !");
        System.out.println(p);
    }

    private static int lireEntier() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.print(" Entree vide! veuillez entrez un nombre: ");
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
