package com.aitrich;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/hello")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExampleResource {

    @Inject
    Repository repository;
	

    @GET
    public Multi<UserEntity> findAllUser() {
        return repository.findAll().stream();
    }
    
    @GET
    @Path("{id}")
    public Uni<UserEntity> getUserById(@PathParam Long id) {
    	
    	System.out.println("Entered  "+id);

        return repository.findById(id);
    }
    
	@POST
	@Transactional
	public Uni<Response> saveUser(UserEntity user)
	{
		repository.persist(user);
		return Uni.createFrom().item(Response.status(Response.Status.CREATED).entity(user).build());
	}
	
    
    @DELETE
    @Path("{id}")
    public Uni<Response> deleteUserById(@PathParam Long id) {

        return Uni.createFrom().item(Response.status(Response.Status.OK).entity(repository.deleteById(id)).build());
    }
    
    
    @PUT
	public Response updateUser(UserEntity user)
	{
    	repository.update("", user);
		System.out.println("updateUser "+user);
		return Response.status(Response.Status.OK).entity(user).build();
	}
	
    
}