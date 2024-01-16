package management.controller;

import management.mappers.RestResponseMapper;
import management.model.Publisher;
import management.request.PublisherRequest;
import management.service.PublisherService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static management.constants.Messages.*;
import static management.constants.Messages.SERVER_ERROR;

@RestController
@RequestMapping(value = "/publisher")

public class PublisherController {

    private final PublisherService publisherService;
    private final ModelMapper modelMapper;

    public PublisherController(PublisherService publisherService, ModelMapper modelMapper) {
        this.publisherService = publisherService;
        this.modelMapper = modelMapper;
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

    @GetMapping("/getById/{publisherId}")
    public ResponseEntity<Object> getPublisherById(@PathVariable @NotNull Long publisherId) {

        try {
            Publisher publisher = publisherService.get(publisherId);
            if (publisher == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, RECORDS_RECEIVED);
            }
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, publisher, RECORDS_RECEIVED);
        } catch (Exception ex) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, RECORDS_RECEIVED);
        }
    }

    @DeleteMapping("/deleteById/{publisherId}")
    public ResponseEntity<Object> deletePublisherById(@PathVariable @NotNull Long publisherId) {
        try {
            Publisher publisher = publisherService.get(publisherId);
            if (publisher == null) {
                return RestResponseMapper.map(SUCCESS, HttpStatus.NOT_FOUND, null, NOT_FOUND);
            }
            publisherService.deletePublisher(publisherId);
            return RestResponseMapper.map(SUCCESS, HttpStatus.OK, null, RECORD_DELETED);
        } catch (
                Exception e) {
            return RestResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

}