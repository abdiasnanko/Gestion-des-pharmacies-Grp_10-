import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean continuer = true;

        System.out.println("Pharmacie Hadja Latifa: Medicaments\n");

        while (continuer) {
            afficherMenu();
            System.out.print("Votre choix : ");

            int choix = -1;
            try {
                choix = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez saisir un chiffre.");
                continue;
            }

            switch (choix) {

            
                case 1:
                    System.out.println("\nEnregistrement d'un médicament");
                    System.out.print("ID Médicament       : ");
                    int id = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Nom commercial (DCI): ");
                    String nom = sc.nextLine().trim();

                    System.out.print("Forme galénique: ");
                    String forme = sc.nextLine().trim();

                    System.out.print("Prix: ");
                    double prix = Double.parseDouble(sc.nextLine().trim());

                    System.out.print("Liste médicament: ");
                    String liste = sc.nextLine().trim();

                    System.out.print("Quantité disponible: ");
                    int qte = Integer.parseInt(sc.nextLine().trim());

                    Medicament med = new Medicament(id, nom, forme, prix, liste, qte);
                    Medicament.enregistrer(med);
                    break;

            
                case 2:
                    System.out.print("\nEntrez le nom (ou une partie) à rechercher : ");
                    String terme = sc.nextLine().trim();
                    List<Medicament> resultats = Medicament.rechercherParNom(terme);
                    if (resultats.isEmpty()) {
                        System.out.println("Aucun médicament trouvé pour : \"" + terme + "\"");
                    } else {
                        System.out.println("\nRésultats (ordre alphabétique)");
                        for (Medicament m : resultats) {
                            System.out.println(m);
                            System.out.println();
                        }
                        System.out.println(resultats.size() + " résultat(s) trouvé(s).");
                    }
                    break;

                case 3:
                    Medicament.afficherTous();
                    break;

                case 4:
                    System.out.print("\nEntrez l'ID du médicament : ");
                    int idRecherche = Integer.parseInt(sc.nextLine().trim());
                    Medicament.afficherParId(idRecherche);
                    break;

                case 5:
                    continuer = false;
                    System.out.println("Merci");
                    break;

                default:
                    System.out.println("Choix invalide. Saisissez un nombre entre 1 et 5.");
            }
        }

        sc.close();
    }

    private static void afficherMenu() {
        System.out.println("\nMenu");
        System.out.println("\n1. Enregistrer un médicament");
        System.out.println("\n2. Rechercher par nom (A-Z)");
        System.out.println("\n3. Afficher tous (A-Z) ");
        System.out.println("\n4. Afficher par ID");
        System.out.println("\n5. Quitter");
    }
}

