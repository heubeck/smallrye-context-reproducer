package org.acme;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        return Uni.createFrom().completionStage(Unchecked.supplier(() -> {
                    var process = new ProcessBuilder("ls").start();
                    return process.onExit();
                }))
                .chain(item -> Uni.createFrom().item(() -> "Hello Context"))
                .await().indefinitely();
    }
}