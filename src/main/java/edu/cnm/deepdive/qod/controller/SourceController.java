package edu.cnm.deepdive.qod.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.qod.model.dao.SourceRepository;
import edu.cnm.deepdive.qod.model.entity.Source;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Source.class)
@RequestMapping("/sources")
public class SourceController {

  private SourceRepository sourceRepository;

  @Autowired
  public SourceController(SourceRepository sourceRepository) {
    this.sourceRepository = sourceRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public List<Source> list() {
    return sourceRepository.findAllByOrderByName();
  }

  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public List<Source> search(@RequestParam("source") String fragment) {
    return sourceRepository.findAllByNameContainingOrderByName(fragment);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public ResponseEntity<Source> post(@RequestBody Source source) {
    sourceRepository.save(source);
    return ResponseEntity.created(source.getHref()).body(source);
  }

  @GetMapping(value = "{sourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public Source get(@PathVariable("sourceId") long sourceId) {
    return sourceRepository.findById(sourceId).get();
  }

  @DeleteMapping(value = "{sourceId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("sourceId") long sourceId) {
    sourceRepository.delete(get(sourceId));
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Source not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound() {

  }

}
