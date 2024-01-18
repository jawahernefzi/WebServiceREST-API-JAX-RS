package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entities.Categorie;
import Entities.Produit;
import singleton.SingletonConnexion;

public class DaoProduit {
    // Méthode pour lister tous les produits
    public List<Produit> listerProduits() {
        Connection con = SingletonConnexion.getConnexion();
        List<Produit> listeProduits = new ArrayList<>();

        String sql = "select code, libelle, prix, id_categorie from Produit ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produit produit = new Produit();
                produit.setCode(rs.getInt("code"));
                produit.setLibelle(rs.getString("libelle"));
                produit.setPrix(rs.getDouble("prix"));

                // Vous devrez récupérer la catégorie associée à partir de la base de données
                // C'est ici que vous devriez peut-être utiliser la classe DaoCategorie
                // Pour l'instant, je suppose que vous avez une méthode getCategorieById dans DaoCategorie
                int idCategorie = rs.getInt("id_categorie");
                Categorie categorie = getCategorieById(idCategorie);
                produit.setId_categorie(categorie);

                listeProduits.add(produit);
            }

            return listeProduits;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listeProduits;
    }

    // Méthode pour ajouter un nouveau produit
    public Produit saveProduit(Produit produit) {
        Connection con = SingletonConnexion.getConnexion();
        String sql = "insert into Produit (libelle, prix, id_categorie) values (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, produit.getLibelle());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getId_categorie().getId_categorie());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    produit.setCode(generatedKeys.getInt(1));
                }
                System.out.println("Produit ajouté avec succès. Détails du nouveau produit : " + produit);
                return produit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Méthode pour supprimer un produit
    public boolean deleteProduit(int codeProduit) {
        Connection con = SingletonConnexion.getConnexion();
        String sql = "delete from Produit where code=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codeProduit);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produit supprimé avec succès. Code du produit : " + codeProduit);
                return true;
            } else {
                System.out.println("Produit non trouvé avec le code : " + codeProduit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Méthode pour mettre à jour un produit
    public boolean updateProduit(Produit produit) {
        Connection con = SingletonConnexion.getConnexion();
        String sql = "update Produit set libelle=?, prix=?, id_categorie=? where code=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, produit.getLibelle());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getId_categorie().getId_categorie());
            ps.setInt(4, produit.getCode());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produit mis à jour avec succès. Détails mis à jour du produit : " + produit);
                return true;
            } else {
                System.out.println("Produit non trouvé avec le code : " + produit.getCode());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Méthode pour récupérer une catégorie par ID (à adapter selon votre implémentation)
    private Categorie getCategorieById(int idCategorie) {
        // Implémentation de la méthode pour récupérer une catégorie par son ID
        // Vous devez l'adapter en fonction de votre structure de code
        // Vous pourriez avoir besoin d'une instance de DaoCategorie pour cela
        return null;
    }
}
