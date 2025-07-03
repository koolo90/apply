package com.brocode.apply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    /**
     * @param person - person to process
     * @return greeting
     */
    @Override
    public Person process(Person person) {
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName, person.age());

        log.info("Converting ({}) into ({})", person, transformedPerson);

        return transformedPerson;
    }
}
