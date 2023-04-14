package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExampleRepository extends CrudRepository<Example, Long> {

    List<Example> findByLastname(String lastName);

    Example findById(long id);

}
