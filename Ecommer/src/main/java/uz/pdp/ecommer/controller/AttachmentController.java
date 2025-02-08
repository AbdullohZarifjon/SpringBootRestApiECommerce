package uz.pdp.ecommer.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.ecommer.entity.Attachment;
import uz.pdp.ecommer.entity.AttachmentContend;
import uz.pdp.ecommer.repo.AttachmentContendRepository;
import uz.pdp.ecommer.repo.AttachmentRepository;
import uz.pdp.ecommer.service.AttachmentService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@MultipartConfig
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/{attachmentId}")
    public void getFile(@PathVariable int attachmentId, HttpServletResponse response) throws IOException {
        attachmentService.getFileForPhoto(attachmentId, response);
    }

    @PostMapping
    public int upload(@RequestParam MultipartFile file) throws IOException {
        return attachmentService.save(file);
    }
}
