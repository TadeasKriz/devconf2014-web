package com.tadeaskriz;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
@Path("/tasks")
public class MainEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> listTasks(@PathParam("offset") final int offset, @PathParam("limit") final int limit) {
        Tasks tasks = new Tasks();
        return tasks.getTasks(offset, limit);
    }
    

}
