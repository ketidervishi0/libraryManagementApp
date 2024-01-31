package management.service;

import management.model.Author;
import management.repo.AuthorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepo authorRepo;

    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public Author findById(Long id) {
        return authorRepo.findById(id).orElse(null);
    }

    public Author get(Long id) {
        return authorRepo.findById(id).orElse(null);
    }

    public Author save(Author author) {
        return authorRepo.save(author);
    }

    public Author update(Author author, Long id) {
        author.setId(id);
        return authorRepo.save(author);
    }

    public void delete(Long id) {
        authorRepo.deleteById(id);
    }
}
