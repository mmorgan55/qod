package edu.cnm.deepdive.qod.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.qod.model.dao.QuoteRepository;
import edu.cnm.deepdive.qod.model.dao.SourceRepository;
import edu.cnm.deepdive.qod.model.entity.Quote;
import edu.cnm.deepdive.qod.model.entity.Source;
import edu.cnm.deepdive.qod.view.Flat;
import edu.cnm.deepdive.qod.view.Nested;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Quote.class)
@RequestMapping("/sources/{sourceId}/quotes")
public class SourceQuoteController {

  private SourceRepository sourceRepository;
  private QuoteRepository quoteRepository;

  @Autowired
  public SourceQuoteController(SourceRepository sourceRepository,
      QuoteRepository quoteRepository) {
    this.sourceRepository = sourceRepository;
    this.quoteRepository = quoteRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Flat.class)
  public List<Quote> list(@PathVariable("sourceId") long sourceId) {
    Source source = sourceRepository.findById(sourceId).get();
    return quoteRepository.findAllBySourceOrderByText(source);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public ResponseEntity<Quote> post(
      @PathVariable("sourceId") long sourceId, @RequestBody Quote quote) {
    Source source = sourceRepository.findById(sourceId).get();
    quote.setSource(source);
    quoteRepository.save(quote);
    return ResponseEntity.created(quote.getHref()).body(quote);
  }

  @GetMapping(value = "{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Flat.class)
  public Quote get(@PathVariable("sourceId") long sourceId,
      @PathVariable("quoteId") long quoteId) {
    Source source = sourceRepository.findById(sourceId).get();
    return quoteRepository.findFirstBySourceAndId(source, quoteId).get();
  }

  @DeleteMapping(value = "{quoteId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("sourceId") long sourceId, @PathVariable("quoteId") long quoteId) {
    quoteRepository.delete(get(sourceId, quoteId));
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Source or quote not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {

  }

}
