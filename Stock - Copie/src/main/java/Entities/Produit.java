package Entities;

public class Produit {
	 private int code;
	    private String libelle;
	    private double prix;
	    private Categorie id_categorie;
	    
		public Produit() {
			super();
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}

		public double getPrix() {
			return prix;
		}

		public void setPrix(double prix) {
			this.prix = prix;
		}

		public Categorie getId_categorie() {
			return id_categorie;
		}

		public void setId_categorie(Categorie id_categorie) {
			this.id_categorie = id_categorie;
		}

		public Produit(int code, String libelle, double prix, Categorie id_categorie) {
			super();
			this.code = code;
			this.libelle = libelle;
			this.prix = prix;
			this.id_categorie = id_categorie;
		}

		@Override
		public String toString() {
			return "Produit [code=" + code + ", libelle=" + libelle + ", prix=" + prix + ", id_categorie="
					+ id_categorie + "]";
		}

}
