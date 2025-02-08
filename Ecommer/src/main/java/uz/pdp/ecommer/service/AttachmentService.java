package uz.pdp.ecommer.service;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.ecommer.entity.Attachment;
import uz.pdp.ecommer.entity.AttachmentContend;
import uz.pdp.ecommer.repo.AttachmentContendRepository;
import uz.pdp.ecommer.repo.AttachmentRepository;

import java.io.IOException;

@MultipartConfig
@Service
public class AttachmentService {


    private final AttachmentContendRepository attachmentContendRepository;
    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentContendRepository attachmentContendRepository, AttachmentRepository attachmentRepository) {
        this.attachmentContendRepository = attachmentContendRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public void getFileForPhoto(int attachmentId, HttpServletResponse response) throws IOException {
        AttachmentContend attachmentContend = attachmentContendRepository.findByAttachment_Id(attachmentId);
        response.getOutputStream().write(attachmentContend.getContent());
    }

    public int save(MultipartFile file) throws IOException {
        Attachment attachment = Attachment.builder()
                .filename(file.getOriginalFilename())
                .build();
        attachmentRepository.save(attachment);
        AttachmentContend contend = AttachmentContend.builder()
                .attachment(attachment)
                .content(file.  getBytes()).build();
        attachmentContendRepository.save(contend);
        return attachment.getId();
    }
}
