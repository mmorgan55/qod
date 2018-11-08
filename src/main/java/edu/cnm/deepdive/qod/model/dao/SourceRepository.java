package edu.cnm.deepdive.qod.model.dao;

import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SourceRepository extends CrudRepository<Source, Long> {

  List<Source> findAllByOrderByName();

  List<Source> findAllByNameContainingOrderByName(String fragment);


}
