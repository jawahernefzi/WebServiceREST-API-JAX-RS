package controlleur;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Entities.Categorie;
import Entities.Produit;
import Metier.MetierCategorie;
import Metier.MetierProduit;

@Path("/api")
public class ServiceStock {

    private MetierProduit metierProd;
    private MetierCategorie metierCat;

    public ServiceStock() {
        // Initialisation de votre MetierProduit et MetierCategorie (à adapter selon votre structure)
        this.metierProd = new MetierProduit();
        this.metierCat = new MetierCategorie();
    }

    @Path("/produits")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produit> lister() {
        return metierProd.listerProduits();
    }

    @Path("/produits/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Produit getProduit(@PathParam("code") int code) {
        return metierProd.getProduit(code);
    }

    @Path("/produits")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterProduit(Produit p) {
        Produit createdProduit = metierProd.ajouterProduit(p);

        if (createdProduit != null) {
            // Return a response with 201 Created status and the details of the created product
            return Response.status(Response.Status.CREATED).entity(createdProduit).build();
        } else {
            // Handle the case where the product couldn't be added
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de l'ajout du produit.").build();
        }
    }
    
    @Path("/produits/{code}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean modifierProduit(@PathParam("code") int code, Produit p) {
        return metierProd.mettreAJourProduit(code, p);
    }

    @Path("/produits/{code}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response supprimerProduit(@PathParam("code") int code) {
        boolean deleted = metierProd.supprimerProduit(code);
        if (deleted) {
            return Response.ok("Produit supprimé avec succès.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Produit non trouvé.").build();
        }
    }

    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categorie> listerCat() {
        return metierCat.listerCat();
    }

    @Path("/categories")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterCategorie(Categorie categorie) {
        Categorie createdCategorie = metierCat.ajouterCategorie(categorie);

        if (createdCategorie != null) {
            // Return a response with 201 Created status and the details of the created category
            return Response.status(Response.Status.CREATED).entity(createdCategorie).build();
        } else {
            // Handle the case where the category couldn't be added
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de l'ajout de la catégorie.").build();
        }
    }

    @Path("/categories/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean modifierCategorie(@PathParam("id") int id, Categorie categorie) {
        return metierCat.mettreAJourCategorie(id, categorie);
    }

    @Path("/categories/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response supprimerCategorie(@PathParam("id") int id) {
        boolean deleted = metierCat.supprimerCategorie(id);
        if (deleted) {
            return Response.ok("Catégorie supprimée avec succès.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Catégorie non trouvée.").build();
        }
    }
}
