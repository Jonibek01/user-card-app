package uz.app.lesson2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.app.lesson2.dto.ResponseMessage;
import uz.app.lesson2.entity.User;


@RestController
@RequestMapping("/info")
public class InfoController {

    @GetMapping("/my")
    public ResponseEntity<?> getMyInfo() {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new ResponseMessage("This endpoint provides information about the user.", true ,principal));
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNews() {
        return ResponseEntity.ok("Latest news and updates will be posted here.");
    }
    @GetMapping("/events")
    public ResponseEntity<?> getEvents() {
        return ResponseEntity.ok("Upcoming events and activities will be listed here.");
    }
    @GetMapping("/updates")
    public ResponseEntity<?> getUpdates() {
        return ResponseEntity.ok("Recent updates and changes will be shared here.");
    }
    @GetMapping("/announcements")
    public ResponseEntity<?> getAnnouncements() {
        return ResponseEntity.ok("Important announcements will be posted here.");
    }
}
