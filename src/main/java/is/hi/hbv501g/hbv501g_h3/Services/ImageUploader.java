package is.hi.hbv501g.hbv501g_h3.Services;

import java.io.File;
import java.io.IOException;

public interface ImageUploader {
    /**
     * Uploads an image to an image hosting service.
     *
     * @param imageFile The image file to upload.
     * @return The URL of the uploaded image.
     * @throws IOException If an error occurs during the upload.
     */
    String uploadImage(File imageFile) throws IOException;
}
