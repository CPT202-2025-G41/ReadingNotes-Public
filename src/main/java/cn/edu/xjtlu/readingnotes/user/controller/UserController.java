package cn.edu.xjtlu.readingnotes.user.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.xjtlu.readingnotes.storage.StorageService;
import cn.edu.xjtlu.readingnotes.user.User;
import cn.edu.xjtlu.readingnotes.user.UserRepo;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StorageService storageService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userRepo.findById(id).orElseThrow());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(
        @PathVariable Long id,
        @RequestPart String username,
        @RequestPart String oldPassword,
        @RequestPart(required = false) String password,
        @RequestPart String email,
        @RequestPart(required = false) MultipartFile avatar) {
        
        User existing = userRepo.findById(id).orElseThrow();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/profile"));
        
        // Verify old password
        if (!passwordEncoder.matches(oldPassword, existing.getPassword())) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    
        // Added field checksum save logic
        if (!username.isBlank()) {
            existing.setUsername(username);
        }
        if (password != null && !password.isBlank()) {
            existing.setPassword(passwordEncoder.encode(password));
        }
        if (!email.isBlank()) {
            existing.setEmail(email);
        }
        if (avatar != null) {
            MediaType type = MediaTypeFactory.getMediaType(avatar.getResource()).orElseThrow();
            if (type.equals(MediaType.IMAGE_JPEG)) {
                storageService.store(avatar, id + ".jpg");
                existing.setAvatar(id + ".jpg");
            } else if (type.equals(MediaType.IMAGE_PNG)) {
                storageService.store(avatar, id + ".png");
                existing.setAvatar(id + ".png");
            }
        }
        
        userRepo.save(existing);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    } 
}