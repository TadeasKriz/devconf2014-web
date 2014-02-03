package com.tadeaskriz;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
@Path("/tasks")
public class MainEndpoint {

    @Inject
    Tasks tasks;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> listTasks(@QueryParam("offset") @DefaultValue("0") final int offset,
                                @QueryParam("limit") @DefaultValue("10") final int limit) {
        return tasks.getTasks(offset, limit);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Task getTask(@PathParam("id") final Long id) {
        return tasks.taskById(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Task addTask(final Task task) {
        tasks.save(task);
        return task;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Task saveTask(@PathParam("id") final Long id, final Task task) {
        task.setId(id);
        tasks.save(task);
        return task;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") final Long id) {
        try {
            tasks.deleteTaskById(id);
        } catch (Exception e) {
            return Response.notModified().build();
        }
        return Response.noContent().build();
    }

}
