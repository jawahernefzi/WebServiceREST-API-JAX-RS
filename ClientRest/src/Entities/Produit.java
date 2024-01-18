package Entities;

public class Produit {
    private int code;
    private String libelle;
    private double prix;

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

    public Produit(int code, String libelle, double prix) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.prix = prix;
    }

    public Produit() {
        super();
    }
}
