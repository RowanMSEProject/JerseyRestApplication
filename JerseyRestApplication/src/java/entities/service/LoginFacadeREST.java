/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.service;

import entities.Login;
import entities.Userroles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author mse
 */
@Path("entities.login")
public class LoginFacadeREST extends AbstractFacade<Login> {
    
    //@PersistenceContext(unitName = "JerseyRestApplicationPU")
    private EntityManager em = Persistence.createEntityManagerFactory("JerseyRestApplicationPU").createEntityManager();

    public LoginFacadeREST() {
        super(Login.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Login entity) {
        super.create(entity);
    }
    
    /**
     * Add a new user to the database
     * Add the necessary fields, leaving rest blank for new user to fill in  (not implemented yet)
     * the password must return true when passed into checkPassword()
     * In order to add role field, a new role instance is made
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param role 
     */
    @POST
    @Path("/create")
    @Consumes({"application/x-www-form-urlencoded", "application/xml", "application/json"})
    public void createUser(@FormParam("firstName") String firstName, 
                           @FormParam("lastName") String lastName,
                           @FormParam("username") String username,
                           @FormParam("password") String password,
                           @FormParam("role") String role) {
        if (checkPassword(password)) {
            List<Login> users = em.createNamedQuery("Login.findAll").getResultList();
            int id = users.get(users.size() - 1).getUserid();
            id++;
            Login newUser = new Login(id, username, password, firstName, lastName);
            int roleID = Integer.parseInt(role);
            Userroles newRole = new Userroles(roleID);
            newUser.setRoleid(newRole);
            super.create(newUser);
        } else {
            throw new WebApplicationException(Response.status(400).entity("Password must contain capital letter and number and be 8 characters long <a href=\"https://localhost:8100/JerseyRestApplication/createUserForm.html\">Create User</a>").build());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Login entity) {
        super.edit(entity);
    }
    
    /**
     * Update password of a user given userID and old password
     * the new password must return true when passed into checkPassword()
     * @param userid
     * @param oldPswd
     * @param newPswd 
     */
    @POST
    @Path("/updatePswd")
    @Consumes({"application/x-www-form-urlencoded", "application/xml", "application/json"})
    public void updatePswd(@FormParam("username") String userid,
            @FormParam("oldPassword") String oldPswd,
            @FormParam("newPassword") String newPswd) {
        if (checkPassword(newPswd)) {
            String query = "UPDATE Login SET password='" + newPswd + "' WHERE userid=" + userid + " and "
                    + "password='" + oldPswd + "'";
            super.executeQuery(query);
        } else {
            throw new WebApplicationException(Response.status(400).entity("New Password must contain capital letter and number and be 8 characters long <a href=\"https://localhost:8100/JerseyRestApplication/updatePassword.html\">Create User</a>").build());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    /**
     * delete a user from the database based off userID input
     * @param userid 
     */
    @POST
    @Path("/rm")
    @Consumes("application/x-www-form-urlencoded")
    public void delete(@FormParam("userid") String userid) {
        //Old way we deleted users before new db added
        //String query = "DELETE FROM Skillsforusers sfu WHERE sfu.login.userid=" + userid;
        //super.executeQuery(query);
        //query = "DELETE FROM Login l WHERE l.userid=" + userid;
        //super.executeQuery(query);
          int id = Integer.parseInt(userid);
          super.testRemove(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Login find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    /**
     * Create a table of information about users from Login table
     * @return 
     */
    @GET
    @Path("users/table")
    @Produces("text/html")
    public String getUsersTable() {
        if (em != null) {
            List<Login> results = em.createQuery("SELECT L FROM Login L").getResultList();
            String answer = "<h2> Users </h2>" + "<br><table border='1'> <tr>";
            answer = answer + "<th>USERID</th><th>FIRST NAME</th><th>LAST NAME</th><th>USERNAME</th><th>PASSWORD</th><th>ROLE</th></tr>";
            for (Login result : results) {
                answer = answer + "<tr><td>" + result.getUserid() + "</td>";
                answer = answer + "<td>" + result.getFirstname() + "</td>";
                answer = answer + "<td>" + result.getLastname() + "</td>";
                answer = answer + " <td>" + result.getUsername() + "</td>";
                answer = answer + " <td>" + result.getPassword() + "</td>";
                answer = answer + " <td>" + result.getRoleid().getDescription() + "</td></tr>";
            }
            answer = answer + "</table>";
            return answer;
        } else {
            throw new WebApplicationException(Response.status(400)
                    .entity("Null entity manager")
                    .build());
        }
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Login> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Login> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Check the password to make sure it is 8 characters long 
     * and includes a capital letter, lowercase letter, and number
     * @param password
     * @return 
     */
    private boolean checkPassword(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isAtLeast8 = password.length() >= 8;//Checks for at least 8 characters
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
                break;
            }
        }
        return hasUppercase && hasLowercase && isAtLeast8 && hasDigit;
    }
    
}
