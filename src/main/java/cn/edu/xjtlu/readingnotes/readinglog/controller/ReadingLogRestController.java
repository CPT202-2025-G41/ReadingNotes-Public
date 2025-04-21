package cn.edu.xjtlu.readingnotes.readinglog.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.xjtlu.readingnotes.readinglog.ReadingLog;
import cn.edu.xjtlu.readingnotes.readinglog.ReadingLogRepo;
import cn.edu.xjtlu.readingnotes.util.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/log")
public class ReadingLogRestController {

    @Autowired
    private ReadingLogRepo repo;

    @PostMapping(params = "action=create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createLog(
            @AuthenticationPrincipal UserInfo principal,
            @RequestParam Map<String,String> params) {
        final Long userId = principal.getId();
        ReadingLog log = new ReadingLog(
            params.get("title"),
            params.get("author"),
            LocalDate.parse(params.get("date")),
            Integer.parseInt(params.get("spentTime")),
            params.get("note"),
            userId);
        ReadingLog savedLog = repo.save(log);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/log/" + savedLog.getId()));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
    
    @PutMapping("/{logId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateLog(
            @AuthenticationPrincipal UserInfo principal,
            @PathVariable Long logId,
            @RequestParam Map<String,String> params)
            throws Exception {
        final Long userId = principal.getId();
        ReadingLog target = repo.findById(logId).orElseThrow(() -> new Exception("Log not found."));
        if (target.getUserId() != userId) {
            throw new Exception("Unauthorized.");
        }
        ReadingLog log = new ReadingLog(
            params.get("title"),
            params.get("author"),
            LocalDate.parse(params.get("date")),
            Integer.parseInt(params.get("spentTime")),
            params.get("note"),
            userId);
        repo.save(log);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/log"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    
    @DeleteMapping("/{logId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteLog(
            HttpServletRequest request,
            @AuthenticationPrincipal UserInfo principal,
            @PathVariable Long logId)
            throws Exception {
        ReadingLog log = repo.findById(logId).orElseThrow(() -> new Exception("Log not found"));
        if (request.isUserInRole("ROLE_ADMIN") || log.getUserId() == principal.getId()) {
            repo.deleteById(logId);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/log"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
        throw new Exception("Unauthorized.");
    }
}
