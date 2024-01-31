package management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import management.mappers.RestResponseMapper;
import management.model.Author;
import management.model.Book;
import management.model.Publisher;
import management.request.BookRequest;
import management.service.AuthorService;
import management.service.BookService;
import management.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static management.constants.Messages.*;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    public BookController(BookService bookService, ModelMapper modelMapper, PublisherService publisherService, AuthorService authorService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllBooks() {
        try {
            List<Book> bookList = this.bookService.findAll();
            if (bookList.isEmpty()) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, bookList, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable @NotNull Long id) {

        try {
            Book book = bookService.get(id);
            if (book == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, book, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, RECORDS_RECEIVED);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@RequestBody @Valid BookRequest bookRequest) {
        try {
            Book book = modelMapper.map(bookRequest, Book.class);
            Author author = authorService.findById(bookRequest.getAuthorId());
            if (author == null) {
                return RestResponseMapper.map(FAIL, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            book.setAuthor(author);
            Publisher publisher = publisherService.get(bookRequest.getPublisherId());
            if (publisher == null) {
                return RestResponseMapper.map(FAIL, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            book.setPublisher(publisher);
            book = this.bookService.save(book);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, book, RECORD_CREATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<Object> updateBookById(@PathVariable @NotNull Long id, @Valid @RequestBody BookRequest bookRequest) {
        try {
            Book book = this.bookService.get(id);
            if (book == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            book = modelMapper.map(bookRequest, Book.class);
            Author author = authorService.findById(bookRequest.getAuthorId());
            if (author == null) {
                return RestResponseMapper.map(FAIL, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            book.setAuthor(author);
            Publisher publisher = publisherService.get(bookRequest.getPublisherId());
            if (publisher == null) {
                return RestResponseMapper.map(FAIL, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            book.setPublisher(publisher);
            book = bookService.update(book, id);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, book, RECORD_UPDATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable @NotNull Long id) {
        try {
            Book book = bookService.get(id);
            if (book == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            bookService.delete(id);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, "The book with ID " + id + " has been deleted successfully.", RECORD_DELETED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);

        }
    }
}
