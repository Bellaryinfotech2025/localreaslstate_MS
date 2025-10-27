package in.bellaryinfotech.ServiceImpl;

import in.bellaryinfotech.model.FileEntity;
import in.bellaryinfotech.Repository.FileRepository;
import in.bellaryinfotech.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileRepository fileRepository;

    // ✅ Dynamic directory paths from application.properties
    @Value("${file.upload.image-dir}")
    private String IMAGE_DIR;

    @Value("${file.upload.video-dir}")
    private String VIDEO_DIR;

    @Override
    public FileEntity uploadFile(MultipartFile imageFile, MultipartFile videoFile,
                                 String title, String location, String area, String areaInCents,
                                 String price, String features) throws Exception {

        LOG.info("Starting upload: title={}, location={}, area={}, price={}", title, location, area, price);

        if ((imageFile == null || imageFile.isEmpty()) && (videoFile == null || videoFile.isEmpty())) {
            throw new Exception("At least one file (image or video) must be provided");
        }

        // ✅ Ensure directories exist
        new File(IMAGE_DIR).mkdirs();
        new File(VIDEO_DIR).mkdirs();

        String imageUrl = null;
        String videoUrl = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_DIR, imageFileName); // ✅ Safe for all OS
            imageFile.transferTo(imagePath.toFile());
            imageUrl = "/api/files/view/image/" + imageFileName;
        }

        if (videoFile != null && !videoFile.isEmpty()) {
            String videoFileName = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
            Path videoPath = Paths.get(VIDEO_DIR, videoFileName);
            videoFile.transferTo(videoPath.toFile());
            videoUrl = "/api/files/view/video/" + videoFileName;
        }

        FileEntity entity = new FileEntity(
                imageFile != null ? imageFile.getOriginalFilename() : null,
                imageFile != null ? imageFile.getContentType() : null,
                imageUrl,
                videoFile != null ? videoFile.getOriginalFilename() : null,
                videoFile != null ? videoFile.getContentType() : null,
                videoUrl,
                title, location, area, areaInCents, price, features
        );

        FileEntity saved = fileRepository.save(entity);
        LOG.info("File uploaded successfully with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public List<FileEntity> getAllFiles() {
        LOG.info("Fetching all uploaded files...");
        List<FileEntity> files = fileRepository.findAll();
        LOG.info("Total files fetched: {}", files.size());
        return files;
    }

    @Override
    public FileEntity getFileById(Long id) throws Exception {
        LOG.info("Fetching file with ID: {}", id);
        return fileRepository.findById(id)
                .orElseThrow(() -> new Exception("File not found with ID: " + id));
    }

    @Override
    public FileEntity updateFile(Long id, MultipartFile imageFile, MultipartFile videoFile,
                                 String title, String location, String area, String areaInCents,
                                 String price, String features) throws Exception {

        FileEntity existing = fileRepository.findById(id)
                .orElseThrow(() -> new Exception("File not found for update with ID: " + id));

        new File(IMAGE_DIR).mkdirs();
        new File(VIDEO_DIR).mkdirs();

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_DIR, imageFileName);
            imageFile.transferTo(imagePath.toFile());
            existing.setName(imageFile.getOriginalFilename());
            existing.setType(imageFile.getContentType());
            existing.setImageUrl("/api/files/view/image/" + imageFileName);
        }

        if (videoFile != null && !videoFile.isEmpty()) {
            String videoFileName = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
            Path videoPath = Paths.get(VIDEO_DIR, videoFileName);
            videoFile.transferTo(videoPath.toFile());
            existing.setVideoName(videoFile.getOriginalFilename());
            existing.setVideoType(videoFile.getContentType());
            existing.setVideoUrl("/api/files/view/video/" + videoFileName);
        }

        existing.setTitle(title);
        existing.setLocation(location);
        existing.setArea(area);
        existing.setAreaInCents(areaInCents);
        existing.setPrice(price);
        existing.setFeatures(features);

        FileEntity updated = fileRepository.save(existing);
        LOG.info("File updated successfully with ID: {}", updated.getId());
        return updated;
    }

    @Override
    public void deleteFileById(Long id) throws Exception {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new Exception("File not found with ID: " + id));
        fileRepository.delete(file);
        LOG.info("File deleted with ID: {}", id);
    }

    @Override
    public void deleteAllFiles() {
        fileRepository.deleteAll();
        LOG.info("All files deleted successfully.");
    }
}
