/**
 * 
 */
package org.simon.pascal.repository;

import org.simon.pascal.domain.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author simon.pascal.ngos
 *
 */
public interface PersonRepository {
	Mono<Person> getPerson(int id);

	Flux<Person> allPeople();

	Mono<Void> savePerson(Mono<Person> person);
}
