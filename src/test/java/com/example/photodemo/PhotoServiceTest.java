package com.example.photodemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {

    private final RestTemplate restTemplateSpy = spy(RestTemplate.class);
    private final PhotoService photoService = new PhotoService(restTemplateSpy);

    @Test
    void testGetAllPhoto() {
        List<PhotoDto> list = photoService.getAllPhoto();
        assertThat(list, is(not(empty())));
        assertThat(list.size(), is(5000));
    }

    @Test
    void testGetPhotoByIdNotFound() {
        PhotoDto photoDto = photoService.getPhotoById(100000);
        assertThat(photoDto.getId(), is(nullValue()));
    }

    @Test
    void testGetPhotoByIdSuccessfully() {
        PhotoDto photoDto = photoService.getPhotoById(1);
        assertThat(photoDto, is(notNullValue()));
    }

    @Test
    void testGetAllPhotoByAlbumIdNotFound() {
        List<PhotoDto> list = photoService.getAllPhotoByAlbumId(200);
        assertThat(list, is(empty()));
    }

    @ParameterizedTest
    @NullSource
    void testGetAllPhotoByAlbumIdEntryWrongFormat(Integer albumId) {
        List<PhotoDto> list = photoService.getAllPhotoByAlbumId(albumId);
        assertThat(list, is(empty()));
    }

    @Test
    void testGetAllPhotoByAlbumIdSuccessfully() {
        List<PhotoDto> list = photoService.getAllPhotoByAlbumId(50);
        assertThat(list, is(not(empty())));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testGetAllPhotoByTitleEntryWrongFormat(String title) {
        List<PhotoDto> list = photoService.getAllPhotoByTitle(title);
        assertThat(list, is(empty()));
    }

    @Test
    void testGetAllPhotoByTitleNotFound() {
        List<PhotoDto> list = photoService.getAllPhotoByTitle("BEBECAFE LOADS");
        assertThat(list, is(empty()));
    }

    @Test
    void testGetAllPhotoByTitleSuccessfully() {
        List<PhotoDto> list = photoService.getAllPhotoByTitle("repudiandae iusto deleniti rerum");
        assertThat(list, is(not(empty())));
    }


    @Test
    void testCreatePhotoSuccessfully() {
        PhotoDto photo = new PhotoDto();
        photo.setAlbumId(1);
        photo.setTitle("photocreation");
        photo.setUrl("https://via.placeholder.com/600/92222f");
        photo.setThumbnailUrl("https://via.placeholder.com/150/92222f");

        PhotoDto photoCreated = photoService.createPhoto(photo);
        assertThat(photoCreated.getId(), is(notNullValue()));
    }
}
