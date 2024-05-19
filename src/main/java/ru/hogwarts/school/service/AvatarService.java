package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Uploading avatar for student with id: {}", studentId);

        Student student = studentService.getById(studentId);
        logger.info("Retrieved student: {}", student);

        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        logger.info("Avatar file path: {}", filePath);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
            logger.info("Avatar file uploaded successfully");
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.getFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDB(filePath));

        avatarRepository.save(avatar);
        logger.info("Avatar details saved in the database");
    }


    private byte[] generateDataForDB(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            String extension = getExtensions(filePath.getFileName().toString());
            ImageIO.write(preview, extension, baos);

            byte[] imageData = baos.toByteArray();

            logger.info("Generated preview image data from file: {}", filePath.getFileName());

            return imageData;
        } catch (IOException e) {
            logger.error("Error generating data for DB from file: {}", filePath.getFileName(), e);
            throw e;
        }
    }

    public Avatar findAvatar(Long studentId) {
        try {
            Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
            logger.info("Avatar found for studentId: {}", studentId);
            return avatar;
        } catch (Exception e) {
            logger.error("Error finding avatar for studentId: {}", studentId, e);
            return new Avatar();
        }
    }

    private String getExtensions(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            logger.info("Extracted extension '{}' from file name '{}'", extension, fileName);
            return extension;
        } catch (Exception e) {
            logger.error("Error extracting extension from file name: {}", fileName, e);
            return "";
        }
    }

    public Page<Avatar> getAllAvatars(Integer pageNo, Integer pageSize) {
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Avatar> avatars = avatarRepository.findAll(paging);
            logger.info("Retrieved {} avatars from page {} with page size {}", avatars.getContent().size(), pageNo, pageSize);
            return avatars;
        } catch (Exception e) {
            logger.error("Error fetching avatars from page {} with page size {}: {}", pageNo, pageSize, e.getMessage());
            return null;
        }
    }
}
