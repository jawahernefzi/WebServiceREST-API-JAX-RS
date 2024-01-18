package Test;

import java.util.List;

import DAO.DaoProduit;
import Entities.Produit;
import singleton.SingletonConnexion;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DaoProduit p = new DaoProduit();
		List<Produit> l=p.listerProduits();
		if (l != null) {
			System.out.println("Liste des produits"+l);
		} else {
			System.out.println("Erreur lors de l'affichage des produits.");
		}
	}

}
