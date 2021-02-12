package com.example.photodemo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "photo")
@XmlType(propOrder = {"id", "albumId", "title", "url", "thumbnailUrl"})
public class PhotoDto {

    @NotNull
    private Integer albumId;

    private Integer id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String url;

    @NotNull
    @NotEmpty
    private String thumbnailUrl;

    @XmlElement(name = "albumId")
    public void setAlbumId(Integer id) {
        this.albumId = id;
    }

    @XmlAttribute
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "url")
    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElement(name = "thumbnailUrl")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
