import java.util.Scanner;


public class Pharmacie {
  
  private static void passUser(){
    //section dediee aux ID utilisateurs
    boolean success = false; 

    String [] tab = new String[6];
    tab[1]= "Olle";

    String [] pass = new String[6];
    pass[1]= "0499";

    System.out.print("Votre ID de connexion utilisateur: ");
    Scanner scanner = new Scanner(System.in);
        String id=scanner.nextLine();
    System.out.print("Votre Mot de passe utilisateur: ");
        String passwd=scanner.nextLine();
    for (int i=0; i<=1; i++) {
      if (tab[i].equals(id) && pass[i].equals(passwd)) {
        System.out.println("\n Bienvenue "+ tab[i]+"\n");
        success = true;}}
      if (!success) {
            System.out.println("\n Identifiant ou mot de passe incorrect \n");
          }
  }



private static void passAdmin(){
    //section dediee aux ID Administrateurs
    boolean success = false; 
    String [] tabAdmin = new String[6];
    tabAdmin[0]= "Nanko";

    String [] passwdAdmin = new String[6];
    passwdAdmin[0]= "4567";

    System.out.print("Votre ID administrateur: ");
    Scanner scanner = new Scanner(System.in);
        String id=scanner.nextLine();
    System.out.print("Votre Mot de passe administrateur: ");
        String pwd=scanner.nextLine();
    for (int j=0; j<=1; j++) {
      if (tabAdmin[j].equals(id) && passwdAdmin[j].equals(pwd)) {
        System.out.println("\n Bienvenue "+ tabAdmin[j]+"\n");
        success = true;}}
      if (!success) {
            System.out.println("\n Identifiant ou mot de passe incorrect \n");
          }
  }

  public static void main (String [] args){
  System.out.println ("===== PHARMACIE HADJA LATIFA ===== \n ");
  System.out.println("1. Connexion en tant qu'utilisateur \n ");
  System.out.println("2. Connexion en tant qu'administrateur \n ");
  System.out.println("3. Quitter l'application \n");

  System.out.print("Faites votre choix: ");

  Scanner scanner = new Scanner(System.in);
  int numero = scanner.nextInt();

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
    case 3: 
          System.out.println("\n Au revoir !\n ");  
    break;
    default:
      break;
  }
  }
} 