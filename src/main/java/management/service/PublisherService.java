package management.service;

import management.model.Publisher;
import management.repo.PublisherRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepo publisherRepo;

    public PublisherService(PublisherRepo publisherRepo) {
        this.publisherRepo = publisherRepo;
    }

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    public void deletePublisher(Long publisherId) {
        publisherRepo.deleteById(publisherId);
    }

    public Publisher get(Long publisherId) {
        return publisherRepo.findById(publisherId).orElse(null);
    }

    public List<Publisher> findAll() {
        return publisherRepo.findAll();
    }
}
