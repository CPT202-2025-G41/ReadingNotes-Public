package cn.edu.xjtlu.readingnotes.readinglog.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.xjtlu.readingnotes.readinglog.ReadingLog;
import cn.edu.xjtlu.readingnotes.readinglog.ReadingLogService;
import cn.edu.xjtlu.readingnotes.util.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/log")
public class ReadingLogViewController {

    @Autowired
    private ReadingLogService readingLogService;

    private Supplier<Exception> supplier = () -> new Exception("Log not found");

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String viewLogs(
            HttpServletRequest request,
            @AuthenticationPrincipal UserInfo principal,
            Model model) throws Exception {
        List<ReadingLog> logs;
        if (request.isUserInRole("ROLE_ADMIN")) {
            logs = readingLogService.getLogs(null);
        } else {
            logs = readingLogService.getLogs(principal.getId());
        }
        model.addAttribute("logs", logs);
        return "logs";
    }
    
    @GetMapping(params = "action=create")
    @PreAuthorize("hasRole('USER')")
    public String createLog(Model model) {
        model.addAttribute("local", true);
        return "log-edit";
    }

    @GetMapping(params = "action=filter")
    @PreAuthorize("hasRole('USER')")
    public String showFilters() {
        return "filter";
    }

    @GetMapping(params = "action=review")
    @PreAuthorize("hasRole('ADMIN')")
    public String reviewLogs() {
        return "filter";
    }

    @PostMapping(params = "action=filter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PreAuthorize("hasRole('USER')")
    public String getUserReadingLogsByFilters(
            @AuthenticationPrincipal UserInfo principal,
            @RequestParam Map<String,String> formData,
            Model model) {
        final Long userId = principal.getId();
        model.addAttribute("logs", readingLogService.filterLogs(userId, formData));
        model.addAttribute("filters", formData);
        return "filter";
    }

    @PostMapping(params = "action=review", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public String getReadingLogsByFilters(
            @RequestParam Map<String,String> formData,
            Model model) {
        model.addAttribute("logs", readingLogService.filterLogs(formData));
        model.addAttribute("filters", formData);
        return "filter";
    }

    @GetMapping(path = "/{logId}", params = "action=edit")
    @PreAuthorize("hasRole('USER')")
    public String editLog(
            @AuthenticationPrincipal UserInfo principal,
            @PathVariable Long logId,
            Model model) throws Exception {
        ReadingLog log = readingLogService.getLog(logId).orElseThrow(supplier);
        if (principal.getId() != log.getUserId()) {
            throw new Exception("Unauthorized.");
        }
        model.addAttribute("log", log);
        return "log-edit";
    }
    
    @GetMapping("/{logId}")
    @PreAuthorize("isAuthenticated()")
    public String viewLog(
            HttpServletRequest request,
            @AuthenticationPrincipal UserInfo principal,
            @PathVariable Long logId,
            Model model)
            throws Exception {
        ReadingLog log = readingLogService.getLog(logId).orElseThrow(supplier);
        if (request.isUserInRole("ROLE_ADMIN") || log.getUserId() == principal.getId()) {
            model.addAttribute("log", log);
            return "log";
        } else {
            throw new Exception("Unauthorized.");
        }
    }
    
    @GetMapping(params = "action=progress")
    @PreAuthorize("hasRole('USER')")
    public String viewProgress(
            @AuthenticationPrincipal UserInfo principal,
            Model model) {
        List<Object[]> progress =
            readingLogService.getTitleProgressList(principal.getId());
        model.addAttribute("titles", progress);
        return "progress";
    }
    
}
