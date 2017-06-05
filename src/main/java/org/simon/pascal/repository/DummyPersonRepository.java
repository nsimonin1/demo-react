/**
 * 
 */
package org.simon.pascal.repository;

import java.util.HashMap;
import java.util.Map;

import org.simon.pascal.domain.Person;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author simon.pascal.ngos
 *
 */
@Component
public class DummyPersonRepository implements PersonRepository {
	private final Map<Integer, Person> people = new HashMap<>();

	public DummyPersonRepository() {
		this.people.put(1, new Person("John Doe", 42));
		this.people.put(2, new Person("Jane Doe", 36));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.simon.pascal.repository.PersonRepository#getPerson(int)
	 */
	@Override
	public Mono<Person> getPerson(int age) {

		return Mono.justOrEmpty(people.get(age));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.simon.pascal.repository.PersonRepository#allPeople()
	 */
	@Override
	public Flux<Person> allPeople() {
		return Flux.fromIterable(people.values());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.simon.pascal.repository.PersonRepository#savePerson(reactor.core.
	 * publisher.Mono)
	 */
	@Override
	public Mono<Void> savePerson(Mono<Person> personMono) {
		return personMono.doOnNext(person -> {
			final int id = people.size() + 1;
			people.put(id, person);
			System.out.format("Saved %s with id %d%n", person, id);
		}).thenEmpty(Mono.empty());
	}

}
