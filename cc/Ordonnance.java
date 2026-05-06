import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ordonnance {

    private int numeroOrdonnance;
    private String nomPatient;
    private String nomMedicament;
    private String formeGalenique;
    private String dosage;
    private String posologie;
    private String dateOrdonnance;

    public Ordonnance() {}

    public Ordonnance(int numeroOrdonnance, String nomPatient, String nomMedicament,
                      String formeGalenique, String dosage, String posologie) {
        this.numeroOrdonnance = numeroOrdonnance;
        this.nomPatient = nomPatient;
        this.nomMedicament = nomMedicament;
        this.formeGalenique = formeGalenique;
        this.dosage = dosage;
        this.posologie = posologie;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.dateOrdonnance = LocalDateTime.now().format(fmt);
    }

    public int getNumeroOrdonnance() { return numeroOrdonnance; }
    public String getNomPatient()    { return nomPatient; }
    public String getNomMedicament()    { return nomMedicament; }
    public String getFormeGalenique()   { return formeGalenique; }
    public String getDosage()           { return dosage; }
    public String getPosologie()        { return posologie; }
    public String getDateOrdonnance()   { return dateOrdonnance; }

    public void setNumeroOrdonnance(int n)   { this.numeroOrdonnance = n; }
    public void setNomPatient(String s)   { this.nomPatient = s; }
    public void setNomMedicament(String s)   { this.nomMedicament = s; }
    public void setFormeGalenique(String s)  { this.formeGalenique = s; }
    public void setDosage(String s)          { this.dosage = s; }
    public void setPosologie(String s)       { this.posologie = s; }

    public String toString() {
    return "Ordonnance N° " + numeroOrdonnance + " | Date: " + dateOrdonnance + " | NomPatient: " +nomPatient+ " | Medicament: " + nomMedicament + " | Forme: " + formeGalenique + " | Dosage: " + dosage + " | Posologie: " + posologie;
    }
}
