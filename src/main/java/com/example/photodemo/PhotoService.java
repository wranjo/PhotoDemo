package com.example.photodemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoService {
    private final static String URL_FAKE_API = "https://jsonplaceholder.typicode.com/photos";

    private final RestTemplate restTemplate;

    public List<PhotoDto> getAllPhoto() {
        try {
            ResponseEntity<PhotoDto[]> listPhoto = restTemplate.getForEntity(URL_FAKE_API, PhotoDto[].class);
            return Arrays.asList(Objects.requireNonNull(listPhoto.getBody()));
        } catch (RestClientException e) {
            return Collections.emptyList();
        }
    }

    public PhotoDto getPhotoById(Integer id) {
        try {
            return restTemplate.getForObject(URL_FAKE_API + "/" + id, PhotoDto.class);
        } catch (RestClientException e) {
            return new PhotoDto();
        }
    }

    public List<PhotoDto> getAllPhotoByAlbumId(Integer albumId) {
        if (albumId == null) {
            return Collections.emptyList();
        }
        ResponseEntity<PhotoDto[]> listPhoto = restTemplate.getForEntity(URL_FAKE_API + "?albumId=" + albumId, PhotoDto[].class);
        return Arrays.asList(Objects.requireNonNull(listPhoto.getBody()));
    }

    public List<PhotoDto> getAllPhotoByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return Collections.emptyList();
        }
        ResponseEntity<PhotoDto[]> listPhoto = restTemplate.getForEntity(URL_FAKE_API + "?title=" + title, PhotoDto[].class);
        return Arrays.asList(Objects.requireNonNull(listPhoto.getBody()));
    }

    public PhotoDto createPhoto(@Valid PhotoDto photo) {
        return restTemplate.postForObject(URL_FAKE_API, photo, PhotoDto.class);
    }

    public void updatePhoto(PhotoDto photoDto) {
        restTemplate.put(URL_FAKE_API + "/" + photoDto.getId(), photoDto);
        log.info("Photo {} updated", photoDto.getId());
    }

    public void deletePhoto(Integer id) {
        restTemplate.delete(URL_FAKE_API + "/" + id);
        log.info("Photo {} deleted", id);
    }

    public void getAndStorePhotoById(Integer id) {
        String photo = restTemplate.getForObject(URL_FAKE_API + "/" + id, String.class);
        writeJsonFile(photo);

        PhotoDto photoDto = restTemplate.getForObject(URL_FAKE_API + "/" + id, PhotoDto.class);
        writeXMLFile(photoDto);
    }

    private void writeJsonFile(String jsonResult) {
        try {
            FileWriter file = new FileWriter("src/main/resources/files/" + LocalDateTime.now()
                                                                                        .format(DateTimeFormatter.BASIC_ISO_DATE) + ".json");
            file.write(jsonResult);
            file.flush();
        } catch (IOException e) {
            log.error("Error while writing json file");
        }
    }

    private void writeXMLFile(PhotoDto photoDto) {
        try {
            JAXBContext context = JAXBContext.newInstance(PhotoDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            FileWriter file = new FileWriter("src/main/resources/files/" + LocalDateTime.now()
                                                                                        .format(DateTimeFormatter.BASIC_ISO_DATE) + ".xml");
            marshaller.marshal(photoDto, file);
        } catch (IOException | JAXBException e) {
            log.error("Error while writing json file");
        }
    }
}
