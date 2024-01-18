package Metier;

import DAO.DaoProduit;
import Entities.Produit;

import java.util.List;

public class MetierProduit {
    private DaoProduit daoProduit;

    public MetierProduit() {
        // Initialisation de votre DAO (à adapter selon votre structure)
        this.daoProduit = new DaoProduit();
    }

    // Méthode pour lister tous les produits
    public List<Produit> listerProduits() {
        return daoProduit.listerProduits();
    }

    // Méthode pour récupérer un produit par son code
    public Produit getProduit(int code) {
        // Ajoutez ici la logique métier avant d'appeler le DAO si nécessaire
        return daoProduit.getProduit(code);
    }

    // Méthode pour ajouter un nouveau produit
    public Produit ajouterProduit(Produit produit) {
        // Ajoutez ici la logique métier avant d'appeler le DAO si nécessaire
        return daoProduit.saveProduit(produit);
    }

    // Méthode pour supprimer un produit
    public boolean supprimerProduit(int codeProduit) {
        // Ajoutez ici la logique métier avant d'appeler le DAO si nécessaire
        return daoProduit.deleteProduit(codeProduit);
    }

    // Méthode pour mettre à jour un produit
    public boolean mettreAJourProduit(int codeProduit, Produit produit) {
        // Ajoutez ici la logique métier avant d'appeler le DAO si nécessaire
        produit.setCode(codeProduit); // Assurez-vous que le code est correctement défini
        return daoProduit.updateProduit(produit);
    }

    // Vous pouvez ajouter d'autres méthodes métier selon vos besoins
}
