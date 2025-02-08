package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommer.entity.AttachmentContend;

public interface AttachmentContendRepository extends JpaRepository<AttachmentContend, Integer> {
    AttachmentContend findByAttachment_Id(int attachmentId);
}