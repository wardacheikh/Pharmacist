package model ;

public class LigneVente {
    int ID_ligne_vente;
    String Nommedic;
    float Prixunit;
    int QuantiteVendu;

    float Montanttotal;

    int Numfact;

    public LigneVente(){
        super();
    }
    public LigneVente(int ID_ligne_vente, String nommedic, float prixunit, int quantiteVendu, float montanttotal, int numfact) {
        this.ID_ligne_vente = ID_ligne_vente;
        Nommedic = nommedic;
        Prixunit = prixunit;
        QuantiteVendu = quantiteVendu;
        Montanttotal = montanttotal;
        Numfact = numfact;
    }

    public void setID_ligne_vente(int ID_ligne_vente) {
        this.ID_ligne_vente = ID_ligne_vente;
    }

    public void setNommedic(String nommedic) {
        Nommedic = nommedic;
    }

    public void setPrixunit(float prixunit) {
        Prixunit = prixunit;
    }

    public void setQuantiteVendu(int quantiteVendu) {
        QuantiteVendu = quantiteVendu;
    }

    public void setMontanttotal(float montanttotal) {
        Montanttotal = montanttotal;
    }

    public void setNumfact(int numfact) {
        Numfact = numfact;
    }

    public int getID_ligne_vente() {
        return ID_ligne_vente;
    }

    public String getNommedic() {
        return Nommedic;
    }

    public float getPrixunit() {
        return Prixunit;
    }

    public int getQuantiteVendu() {
        return QuantiteVendu;
    }

    public float getMontanttotal() {
        return Montanttotal;
    }

    public int getNumfact() {
        return Numfact;
    }
}
