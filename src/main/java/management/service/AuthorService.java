package management.service;

import management.model.Author;
import management.repo.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepo authorRepo;
    private Long authorId;

    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public Author findById(Long authorId) {
        return authorRepo.findById(authorId).orElse(null);
    }

    public Author get(Long authorId) {
        return authorRepo.findById(authorId).orElse(null);
    }

    public Author save(Author author) {
        return authorRepo.save(author);
    }

    public Author update(Author author, Long authorId) {
        author.setId(authorId);
        return authorRepo.save(author);
    }

    public void delete(Long authorId) {
        authorRepo.deleteById(authorId);
    }
}
