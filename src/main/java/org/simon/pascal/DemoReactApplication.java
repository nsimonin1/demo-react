package org.simon.pascal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.simon.pascal.service.PersonHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class DemoReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoReactApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(PersonHandler personHandler) {
		return nest(path("/person"),
				nest(accept(APPLICATION_JSON),
						route(GET("/{id}"), personHandler::getPerson)
								.andRoute(method(HttpMethod.GET), personHandler::listPeople).andRoute(
										POST("/").and(contentType(APPLICATION_JSON)), personHandler::createPerson)));

	}
}
