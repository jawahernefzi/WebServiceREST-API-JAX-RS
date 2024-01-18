package Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import Entities.Categorie;
import Entities.Produit;

public class ClientTestRest {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        Scanner scanner = new Scanner(System.in);
        int choix = -1;

        while (choix != 0) {
            System.out.println("Menu:");
            System.out.println("1. Afficher les produits");
            System.out.println("2. Ajouter un produit");
            System.out.println("3. Editer un produit");
            System.out.println("4. Supprimer un produit");
            System.out.println("5. Afficher les catégories");
            System.out.println("6. Ajouter une catégorie");
            System.out.println("7. Editer une catégorie");
            System.out.println("8. Supprimer une catégorie");
            System.out.println("0. Quitter");

            System.out.print("Choisissez une option : ");

            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
            } else {
                System.out.println("Option invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Vider le scanner pour éviter une boucle infinie
                continue;
            }

            switch (choix) {
                case 1:
                    afficherProduits();
                    break;
                case 2:
                    ajouterProduit();
                    break;
                case 3:
                    editerProduit();
                    break;
                case 4:
                    supprimerProduit();
                    break;
                case 5:
                    afficherCategories();
                    break;
                case 6:
                    ajouterCategorie();
                    break;
                case 7:
                    editerCategorie();
                    break;
                case 8:
                    supprimerCategorie();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");
            }
        }

        // Fermez le scanner après la boucle
        scanner.close();
    }

    private static void afficherProduits() throws JsonParseException, JsonMappingException, IOException {
        Client c = Client.create(new DefaultClientConfig());
        String uriString = "http://localhost:8084/Stock/api/produits/";
        WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() == 404) {
            System.out.println("Resource not found: " + response.getEntity(String.class));
        } else {
            String corprep = response.getEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Produit[] tabprod = mapper.readValue(corprep, Produit[].class);

            System.out.println("Liste des produits : ");
            for (Produit p : tabprod) {
                System.out.println("Code: " + p.getCode() + ", Libelle: " + p.getLibelle() + ", Prix: " + p.getPrix());
            }
        }
    }

    private static void ajouterProduit() throws IOException {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Entrez le libellé du produit : ");
            String libelle = scanner.next();

            double prix = 0.0;
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Entrez le prix du produit : ");
                    prix = Double.parseDouble(scanner.next());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Veuillez entrer un nombre valide pour le prix.");
                }
            }

            Produit newProduit = new Produit();
            newProduit.setLibelle(libelle);
            newProduit.setPrix(prix);

            Client c = Client.create(new DefaultClientConfig());
            String uriString = "http://localhost:8084/Stock/api/produits/";
            WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

            ClientResponse response = resource.type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, new ObjectMapper().writeValueAsString(newProduit));

            if (response.getStatus() == 201) {
                String responseBody = response.getEntity(String.class);
                Produit createdProduit = new ObjectMapper().readValue(responseBody, Produit.class);
                System.out.println("Produit ajouté avec succès. Détails du nouveau produit : " + createdProduit);
            } else if (response.getStatus() == 204) {
                System.out.println("Produit ajouté avec succès. (No Content)");
            } else {
                System.out.println("Erreur lors de l'ajout du produit : " + response.getEntity(String.class));
            }

        } finally {
            scanner.close();
        }
    }

    private static void editerProduit() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le code du produit à éditer : ");
        int code = scanner.nextInt();

        System.out.print("Entrez le nouveau libellé du produit : ");
        String newLibelle = scanner.next();
        System.out.print("Entrez le nouveau prix du produit : ");
        double newPrix = scanner.nextDouble();

        Client c = Client.create(new DefaultClientConfig());
        String uriString = "http://localhost:8084/Stock/api/produits/" + code;
        WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

        Produit updatedProduit = new Produit();
        updatedProduit.setLibelle(newLibelle);
        updatedProduit.setPrix(newPrix);

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, new ObjectMapper().writeValueAsString(updatedProduit));

        if (response.getStatus() == 200) {
            System.out.println("Produit édité avec succès.");
        } else {
            System.out.println("Erreur lors de l'édition du produit : " + response.getEntity(String.class));
        }
    }

    private static void supprimerProduit() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le code du produit à supprimer : ");
        int code = scanner.nextInt();

        Client c = Client.create(new DefaultClientConfig());
        String uriString = "http://localhost:8084/Stock/api/produits/" + code;
        WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

        ClientResponse response = resource.delete(ClientResponse.class);

        if (response.getStatus() == 200) {
            System.out.println("Produit supprimé avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression du produit : " + response.getEntity(String.class));
        }
    }

    private static void afficherCategories() throws JsonParseException, JsonMappingException, IOException {
        Client c = Client.create(new DefaultClientConfig());
        String uriString = "http://localhost:8084/Stock/api/categories";
        WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() == 404) {
            System.out.println("Resource not found: " + response.getEntity(String.class));
        } else {
            String corprep = response.getEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            Categorie[] tabcat = mapper.readValue(corprep, Categorie[].class);

            System.out.println("Liste des catégories : ");
            for (Categorie cat : tabcat) {
                System.out.println("ID: " + cat.getId_categorie() + ", Nom: " + cat.getNom() + ", Description: " + cat.getDescription());
            }
        }
    }

    private static void ajouterCategorie() throws IOException {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Entrez le nom de la catégorie : ");
            String nom = scanner.next();

            System.out.print("Entrez la description de la catégorie : ");
            String description = scanner.next();

            Categorie newCategorie = new Categorie();
            newCategorie.setNom(nom);
            newCategorie.setDescription(description);

            Client c = Client.create(new DefaultClientConfig());
            String uriString = "http://localhost:8084/Stock/api/categories";
            WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

            ClientResponse response = resource.type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, new ObjectMapper().writeValueAsString(newCategorie));

            if (response.getStatus() == 201) {
                String responseBody = response.getEntity(String.class);
                Categorie createdCategorie = new ObjectMapper().readValue(responseBody, Categorie.class);
                System.out.println("Catégorie ajoutée avec succès. Détails de la nouvelle catégorie : " + createdCategorie);
            } else if (response.getStatus() == 204) {
                System.out.println("Catégorie ajoutée avec succès. (No Content)");
            } else {
                System.out.println("Erreur lors de l'ajout de la catégorie : " + response.getEntity(String.class));
            }

        } finally {
            scanner.close();
        }
    }

    private static void editerCategorie() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez l'ID de la catégorie à éditer : ");
        int id = scanner.nextInt();

        System.out.print("Entrez le nouveau nom de la catégorie : ");
        String newNom = scanner.next();
        System.out.print("Entrez la nouvelle description de la catégorie : ");
        String newDescription = scanner.next();

        Client c = Client.create(new DefaultClientConfig());
        String uriString = "http://localhost:8084/Stock/api/categories/" + id;
        WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

        Categorie updatedCategorie = new Categorie();
        updatedCategorie.setNom(newNom);
        updatedCategorie.setDescription(newDescription);

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, new ObjectMapper().writeValueAsString(updatedCategorie));

        if (response.getStatus() == 200) {
            System.out.println("Catégorie éditée avec succès.");
        } else {
            System.out.println("Erreur lors de l'édition de la catégorie : " + response.getEntity(String.class));
        }
    }

    private static void supprimerCategorie() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez l'ID de la catégorie à supprimer : ");
        int id = scanner.nextInt();

        Client c = Client.create(new DefaultClientConfig());
        String uriString = "hhttp://localhost:8084/Stock/api/categories/" + id;
        WebResource resource = c.resource(UriBuilder.fromUri(uriString).build());

        ClientResponse response = resource.delete(ClientResponse.class);

        if (response.getStatus() == 200) {
            System.out.println("Catégorie supprimée avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression de la catégorie : " + response.getEntity(String.class));
        }
    }
}
