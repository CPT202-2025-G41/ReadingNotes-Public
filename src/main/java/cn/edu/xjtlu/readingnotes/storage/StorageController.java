package cn.edu.xjtlu.readingnotes.storage;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/avatar")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/{filename}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> getMethodName(@PathVariable String filename) {
        Resource resource = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
            .contentType(MediaTypeFactory.getMediaType(resource).orElseThrow())
            .body(resource);
    }
    
}
