package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import is.hi.hbv501g.hbv501g_h3.Services.ImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryUploader implements ImageUploader {

    private final Cloudinary cloudinary;

    public CloudinaryUploader(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret) {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        this.cloudinary = new Cloudinary(config);
    }

    @Override
    public String uploadImage(File imageFile) throws IOException {
        Map<String, Object> uploadResult = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());

        return (String) uploadResult.get("url");
    }
}
