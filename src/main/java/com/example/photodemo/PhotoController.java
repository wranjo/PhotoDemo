package com.example.photodemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("listPhoto", photoService.getAllPhoto());
        return "index";
    }

    @GetMapping("/view")
    public String viewPhoto(Model model, @RequestParam(value = "photoId") Integer photoId) {
        model.addAttribute("photo", photoService.getPhotoById(photoId));
        return "photo";
    }

    @PostMapping("/create")
    public String createPhoto(Model model, PhotoDto photo) {
        PhotoDto photoCreated = photoService.createPhoto(photo);
        model.addAttribute("photo", photoCreated);
        return "photo";
    }

    @GetMapping("/edit")
    public String editPhoto(Model model,
                            @RequestParam(value = "photoId") Integer photoId) {
        model.addAttribute("photo", photoService.getPhotoById(photoId));
        return "editPhoto";
    }

    @PostMapping("/save")
    public String savePhoto(Model model, PhotoDto photoDto) {
        photoService.updatePhoto(photoDto);
        model.addAttribute("photo", photoService.getPhotoById(photoDto.getId()));
        return "photo";
    }

    @PostMapping("/delete")
    public String deletePhoto(Model model,
                              @RequestParam(value = "photoId") Integer photoId) {
        photoService.deletePhoto(photoId);
        model.addAttribute("listPhoto", photoService.getAllPhoto());
        return "index";
    }
}
