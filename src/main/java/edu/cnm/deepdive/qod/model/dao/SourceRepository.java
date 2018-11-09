package edu.cnm.deepdive.qod.model.dao;

import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface SourceRepository extends CrudRepository<Source, UUID> {

  List<Source> findAllByOrderByName();

  List<Source> findAllByNameContainingOrderByName(String fragment);


}
