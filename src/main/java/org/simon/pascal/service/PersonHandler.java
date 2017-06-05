/**
 * 
 */
package org.simon.pascal.service;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.simon.pascal.domain.Person;
import org.simon.pascal.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author simon.pascal.ngos
 *
 */
@Component
public class PersonHandler {
	private final PersonRepository repository;

	public PersonHandler(PersonRepository repository) {
		this.repository = repository;
	}

	public Mono<ServerResponse> getPerson(ServerRequest request) {
		final int personId = Integer.valueOf(request.pathVariable("id"));
		final Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		final Mono<Person> personMono = this.repository.getPerson(personId);
		return personMono.flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(person)))
				.switchIfEmpty(notFound);
	}

	public Mono<ServerResponse> createPerson(ServerRequest request) {
		final Mono<Person> person = request.bodyToMono(Person.class);
		return ServerResponse.ok().build(this.repository.savePerson(person));
	}

	public Mono<ServerResponse> listPeople(ServerRequest request) {
		final Flux<Person> people = this.repository.allPeople();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, Person.class);
	}
}
