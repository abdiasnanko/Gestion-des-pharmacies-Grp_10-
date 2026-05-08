import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pharmacien {

    private int id;
    private String nom;
    private String heureArrivee;
    private String heureDepart;
    private String dateEnregistrement; 


    public Pharmacien(int id, String nom, String heureArrivee, String heureDepart) {
        this.id = id;
        this.nom = nom;
        this.heureArrivee  = heureArrivee;
        this.heureDepart  = heureDepart;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.dateEnregistrement = LocalDateTime.now().format(fmt);
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getHeureArrivee() { return heureArrivee; }
    public String getHeureDepart() { return heureDepart; }
    public String getDateEnregistrement() { return dateEnregistrement; }

    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom  = nom; }
    public void setHeureArrivee(String h) { this.heureArrivee = h; }
    public void setHeureDepart(String h) { this.heureDepart  = h; }

    public String toString() {
        return "ID: " + id + " | Nom: " + nom + " | Arrivee: " + heureArrivee + " | Depart: " + heureDepart + " | Date ajout: " + dateEnregistrement;
    }
}
