package Metier;

import java.util.List;

import DAO.DaoCategorie;
import Entities.Categorie;

public class MetierCategorie {
    private DaoCategorie daoCat = new DaoCategorie();

    public List<Categorie> listerCat() {
        return daoCat.listerCategories();
    }

    // Add new category
    public Categorie ajouterCategorie(Categorie categorie) {
        return daoCat.saveCategorie(categorie);
    }

    // Delete category
    public boolean supprimerCategorie(int idCategorie) {
        return daoCat.deleteCategorie(idCategorie);
    }

    // Update category
    public boolean mettreAJourCategorie(int idCategorie, Categorie categorie) {
        categorie.setId_categorie(idCategorie); // Assurez-vous que l'ID est correctement défini
        return daoCat.updateCategorie(categorie);
    }

    // ... (other methods)
}
