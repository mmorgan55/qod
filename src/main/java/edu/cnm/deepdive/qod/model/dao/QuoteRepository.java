package edu.cnm.deepdive.qod.model.dao;

import edu.cnm.deepdive.qod.model.entity.Quote;
import edu.cnm.deepdive.qod.model.entity.Source;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, UUID> {

  List<Quote> findAllByTextContainingOrderByText(String fragment);

  List<Quote> findAllByOrderByText();

  List<Quote> findAllBySourceContainingOrderBySourceAscTextAsc(String fragment);

  List<Quote> findAllBySourceOrderByText(Source source);

  Optional<Quote> findFirstBySourceAndId(Source source, UUID quoteId);

//  List<Quote> findAllBySourceContainingAndTextContainingOrderBySourceAscTextAsc(
//      String source, String text);

  @Query(value = "SELECT * FROM Quote ORDER BY RANDOM() FETCH FIRST 1 ROW ONLY", nativeQuery = true)
  Optional<Quote> findRandom();
}
