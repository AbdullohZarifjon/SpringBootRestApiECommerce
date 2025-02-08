package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommer.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}