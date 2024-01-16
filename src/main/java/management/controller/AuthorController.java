package management.controller;


import management.mappers.RestResponseMapper;
import management.model.Author;
import management.request.AuthorRequest;
import management.service.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static management.constants.Messages.*;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    public AuthorController(AuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllAuthors() {
        try {
            List<Author> authorList = this.authorService.findAll();
            if (authorList.isEmpty()) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, authorList, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{authorId}")
    public ResponseEntity<Object> getAuthorById(@PathVariable @NotNull Long authorId) {

        try {
            Author author = authorService.get(authorId);
            if (author == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, author, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, RECORDS_RECEIVED);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        try {
            Author author = modelMapper.map(authorRequest, Author.class);
            author = this.authorService.save(author);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, author, RECORD_CREATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @PutMapping("/updateById/{authorId}")
    public ResponseEntity<Object> updateAuthorById(@PathVariable @NotNull Long authorId, @Valid @RequestBody AuthorRequest authorRequest) {
        try {
            Author author = this.authorService.get(authorId);
            if (author == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }

            author = modelMapper.map(authorRequest, Author.class);
            author = authorService.update(author, authorId);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, author, RECORD_UPDATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{authorId}")
    public ResponseEntity<Object> deleteAuthorById(@PathVariable @NotNull Long authorId) {
        try {
            Author author = authorService.get(authorId);
            if (author == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            authorService.delete(authorId);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, null, RECORD_DELETED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);

        }
    }

}
