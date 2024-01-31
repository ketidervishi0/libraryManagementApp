package management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import management.mappers.RestResponseMapper;
import management.model.Publisher;
import management.request.PublisherRequest;
import management.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static management.constants.Messages.*;

@RestController
@RequestMapping(value = "/publisher")

public class PublisherController {

    private final PublisherService publisherService;
    private final ModelMapper modelMapper;

    public PublisherController(PublisherService publisherService, ModelMapper modelMapper) {
        this.publisherService = publisherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllPublishers() {
        try {
            List<Publisher> publisherList = this.publisherService.findAll();
            if (publisherList.isEmpty()) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, publisherList, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getPublisherById(@PathVariable @NotNull Long id) {

        try {
            Publisher publisher = publisherService.get(id);
            if (publisher == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, publisher, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, RECORDS_RECEIVED);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addPublisher(@RequestBody @Valid PublisherRequest publisherRequest) {
        try {
            Publisher publisher = modelMapper.map(publisherRequest, Publisher.class);
            publisher = this.publisherService.addPublisher(publisher);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, publisher, RECORD_CREATED);
        } catch (Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Object> deletePublisherById(@PathVariable @NotNull Long id) {
        try {
            Publisher publisher = publisherService.get(id);
            if (publisher == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            publisherService.deletePublisher(id);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, "The publisher with ID " + id + " has been deleted successfully.", RECORD_DELETED);
        } catch (
                Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

}