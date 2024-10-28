package model ;
import java.sql.Date;

public class Facture {
    int IDfact;
    String nomclient;
    float montanttotal;
    int numfact;
    Date datevente;

    public Facture(){ super(); }

    public Facture(int IDfact,String nomclient, Date datevente, int numfact ,float montanttotal) {
        this.IDfact = IDfact;
        this.nomclient=nomclient;
        this.numfact=numfact;
        this.montanttotal = montanttotal;
        this.datevente = datevente;
    }

    public void setIDfact(int IDfact) {
        this.IDfact = IDfact;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public void setMontanttotal(float montanttotal) {
        this.montanttotal = montanttotal;
    }

    public void setNumfact(int numfact) {
        this.numfact = numfact;
    }

    public void setDatevente(Date datevente) {
        this.datevente = datevente;
    }

    public int getIDfact() {
        return IDfact;
    }

    public String getNomclient() {
        return nomclient;
    }

    public float getMontanttotal() {
        return montanttotal;
    }

    public int getNumfact() {
        return numfact;
    }

    public Date getDatevente() {
        return datevente;
    }
}