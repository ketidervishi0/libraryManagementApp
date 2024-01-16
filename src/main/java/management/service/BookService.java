package management.service;

import management.model.Book;
import management.repo.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    public Book get(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepo.save(book);
    }

    public Book update(Book book, Long id) {
        book.setId(id);
        return bookRepo.save(book);
    }

    public void delete(Long id) {
        bookRepo.deleteById(id);
    }
}

