package model;

public class fournisseurs {

	int id ;
	String nom ;
	String address ;
	String tel ;

	public fournisseurs(int id, String nom, String address, String tel) {
		super();
		this.id = id;
		this.nom = nom;
		this.address = address;
		this.tel = tel;
	}
	public int getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public String getTel() {
		return tel;
	}
	public String getAddress() {
		return address;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
