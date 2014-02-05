package com.tadeaskriz;

import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.SenderClient;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
@Path("/tasks")
@Stateless
public class MainEndpoint {

    @Inject
    Tasks tasks;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTasks(@QueryParam("offset") @DefaultValue("0") final int offset,
                              @QueryParam("limit") @DefaultValue("10") final int limit) {
        return Response.ok(tasks.getTasks(offset, limit)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getTask(@PathParam("id") final Long id) {
        return Response.ok(tasks.taskById(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTask(Task task, @Context UriInfo uriInfo) {
        task = tasks.save(task);
        sendReloadMessage();
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(task.getId())).build())
                .entity(task)
                .build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response saveTask(@PathParam("id") final Long id, final Task task) {
        task.setId(id);
        tasks.save(task);
        sendReloadMessage();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") final Long id) {
        try {
            tasks.deleteTaskById(id);

            sendReloadMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    private void sendReloadMessage() {
        JavaSender sender = new SenderClient("https://devconf2014push-detox.rhcloud.com/");

        UnifiedMessage message = new UnifiedMessage.Builder()
                .pushApplicationId("6494eed7-aff9-4910-8f44-312f59d4f89e")
                .masterSecret("94097a6f-0cce-4486-a8f2-b5df36808dba")
                .attribute("reload", "true")
                .build();

        sender.send(message);
    }

}
