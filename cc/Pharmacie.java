import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Pharmacie {

  private static void optionMenu(){
    System.out.println ("===== PHARMACIE HADJA LATIFA ===== \n ");
    System.out.println("1. Connexion en tant qu'utilisateur \n ");
    System.out.println("2. Connexion en tant qu'administrateur \n ");
    System.out.println("0. Quitter l'application \n");
    System.out.print("Faites votre choix: ");
  }

  private static void horodatage(){
    LocalDateTime maintenant = LocalDateTime.now();
    DateTimeFormatter formatComplet = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    System.out.println("Date et heure de connexion : " + maintenant.format(formatComplet) +"\n ");
  }

  private static void passUser(){
    String [] tab = new String[6];
    String [] pass = new String[6];
    tab[0]= "Olle";    pass[0]= "0499";
    tab[1]= "Nana";    pass[1]= "0500";
    tab[2]= "Nadia";   pass[2]= "0438";
    tab[3]= "Ndam";    pass[3]= "0499";
    tab[4]= "Ibrahima";pass[4]= "449";

    System.out.print("Votre ID de connexion utilisateur: ");
    Scanner scanner = new Scanner(System.in);
    String id = scanner.nextLine();
    System.out.print("Votre Mot de passe utilisateur: ");
    String passwd = scanner.nextLine();

    int i = 0;
    boolean success = false;
    while (i < tab.length) {
      if (tab[i] != null && pass[i] != null){
        if (tab[i].equals(id) && pass[i].equals(passwd)) {
          System.out.println("\n Bienvenue "+ tab[i]+"\n");
          horodatage();
          success = true;
          MenuUser.afficher(tab[i]);
        }
      }
      i++;
    }
    if (!success) {
      System.out.println("\n Identifiant ou mot de passe incorrect \n");
    }
  }

  private static void passAdmin(){
    boolean success = false;
    String [] tabAdmin = new String[6];
    tabAdmin[0]= "Nanko";
    String [] passwdAdmin = new String[6];
    passwdAdmin[0]= "0421";

    System.out.print("Votre ID administrateur: ");
    Scanner scanner = new Scanner(System.in);
    String id = scanner.nextLine();
    System.out.print("Votre Mot de passe administrateur: ");
    String pwd = scanner.nextLine();

    int i = 0;
    while (i < tabAdmin.length) {
      if (tabAdmin[i] != null && passwdAdmin[i] != null) {
        if (tabAdmin[i].equals(id) && passwdAdmin[i].equals(pwd)) {
          System.out.println("\n Bienvenue "+ tabAdmin[i]+"\n");
          horodatage();
          success = true;
          MenuAdmin.afficher();
        }
      }
      i++;
    }
    if (!success) {
      System.out.println("\n Identifiant ou mot de passe incorrect \n");
    }
  }

  private static void menu(){
    int confirmation;
    Scanner scanner = new Scanner(System.in);

    boolean continuer = true;
    while (continuer) {
      optionMenu();
      int numero;
      try {
        numero = Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println(" Erreur ! Faites un choix valide ! [0-2] \n ");
        continue;
      }

      if (numero >= 3) {
        System.out.println(" Erreur ! Faites un choix valide ! [0-2] \n ");
        continue;
      }

      switch (numero) {
        case 1:
          System.out.println ("\n   ===== PHARMACIE HADJA LATIFA ===== ");
          System.out.println("\n=====CONNEXION EN TANT QU'UTILISATEUR=====\n ");
          passUser();
          break;
        case 2:
          System.out.println ("\n     ===== PHARMACIE HADJA LATIFA ===== ");
          System.out.println("\n=====CONNEXION EN TANT QU'ADMINISTRATEUR=====\n ");
          passAdmin();
          break;
        case 0:
          System.out.print(" Voulez vous vraiment quitter ? \n 1. Oui \n 2. Non \n veuillez confirmer: ");
          try {
            confirmation = Integer.parseInt(scanner.nextLine().trim());
          } catch (NumberFormatException e) {
            confirmation = 2;
          }
          if (confirmation == 1) {
            System.out.println("Au revoir !\n ");
            continuer = false;
          }
          break;
        default:
          break;
      }
    }
  }

  public static void main (String [] args){
    menu();
  }
}
