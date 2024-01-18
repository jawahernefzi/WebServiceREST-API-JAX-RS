package singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexion {
    private static Connection connexion = null;

    public static Connection getConnexion() {
        if (connexion == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/gestion_stock?useUnicode=true&characterEncoding=UTF-8";
                String utilisateur = "root";
                String motDePasse = "";
                connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
                System.out.println("Connexion à la base de données réussie.");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            }
        }
        return connexion;
    }
}


