public class Etagere {

    private int numeroEtagere;
    private String typeTraitement;

    public Etagere() {}

    public Etagere(int numeroEtagere, String typeTraitement) {
        this.numeroEtagere   = numeroEtagere;
        this.typeTraitement  = typeTraitement;
    }

    public int getNumeroEtagere()  { return numeroEtagere; }
    public String getTypeTraitement() { return typeTraitement; }

    public void setNumeroEtagere(int n)       { this.numeroEtagere  = n; }
    public void setTypeTraitement(String t)   { this.typeTraitement = t; }

    public String toString() {
        return "Étagère N°" + numeroEtagere + " | Type de traitement: " + typeTraitement;
    }
}
