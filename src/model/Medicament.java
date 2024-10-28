package model;

import java.sql.Date;

public class Medicament {
    int ID;
    String nom;
    Date dateExpir;
    String Description;
    float prixunit;
    int quantite;
    String laboratoire;

    public Medicament() {
        super();
    }


    public Medicament(int ID, String nom, String Description, float prixunit, int quantite, String laboratoire , Date dateExpir) {
        this.ID = ID;
        this.nom = nom;
        this.dateExpir = dateExpir;
        this.Description = Description;
        this.prixunit = prixunit;
        this.quantite = quantite;
        this.laboratoire = laboratoire;
    }



    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateExpir(Date dateExpir) {
        this.dateExpir = dateExpir;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrixunit(float prixunit) {
        this.prixunit = prixunit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setLaboratoire(String laboratoire) {
        this.laboratoire = laboratoire;
    }

    public int getID() {
        return ID;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateExpir() {
        return dateExpir;
    }

    public String getDescription() {
        return Description;
    }

    public float getPrixunit() {
        return prixunit;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getLaboratoire() {
        return laboratoire;
    }
}
