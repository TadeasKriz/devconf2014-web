package com.tadeaskriz;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
@Path("/tasks")
public class MainEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> listTasks(@QueryParam("offset") @DefaultValue("0") final int offset,
                                @QueryParam("limit") @DefaultValue("10") final int limit) {
        Tasks tasks = new Tasks();
        return tasks.getTasks(offset, limit);
    }


}
