package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entities.Categorie;
import singleton.SingletonConnexion;

public class DaoCategorie {
    // Méthode pour lister toutes les catégories
    public List<Categorie> listerCategories() {
        Connection con = SingletonConnexion.getConnexion();
        List<Categorie> listeCategories = new ArrayList<>();

        String sql = "select id_categorie, nom, description from Categorie ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId_categorie(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("nom"));
                categorie.setDescription(rs.getString("description"));
                listeCategories.add(categorie);
            }

            return listeCategories;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listeCategories;
    }

    // Méthode pour ajouter une nouvelle catégorie
    public Categorie saveCategorie(Categorie categorie) {
        Connection con = SingletonConnexion.getConnexion();
        String sql = "insert into Categorie (nom, description) values (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, categorie.getNom());
            ps.setString(2, categorie.getDescription());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    categorie.setId_categorie(generatedKeys.getInt(1));
                }
                System.out.println("Catégorie ajoutée avec succès. Détails de la nouvelle catégorie : " + categorie);
                return categorie;
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

    // Méthode pour supprimer une catégorie
    public boolean deleteCategorie(int idCategorie) {
        Connection con = SingletonConnexion.getConnexion();
        String sql = "delete from Categorie where id_categorie=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCategorie);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Catégorie supprimée avec succès. ID de la catégorie : " + idCategorie);
                return true;
            } else {
                System.out.println("Catégorie non trouvée avec l'ID : " + idCategorie);
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

    // Méthode pour mettre à jour une catégorie
    public boolean updateCategorie(Categorie categorie) {
        Connection con = SingletonConnexion.getConnexion();
        String sql = "update Categorie set nom=?, description=? where id_categorie=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, categorie.getNom());
            ps.setString(2, categorie.getDescription());
            ps.setInt(3, categorie.getId_categorie());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Catégorie mise à jour avec succès. Détails mis à jour de la catégorie : " + categorie);
                return true;
            } else {
                System.out.println("Catégorie non trouvée avec l'ID : " + categorie.getId_categorie());
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
}
