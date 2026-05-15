package util;

import java.io.File;

public class UploadUtil {
    // Returns an external uploads directory outside the exploded WAR.
    // Default: project working dir + /external-uploads
    public static File getUploadsDir() {
        String userDir = System.getProperty("user.dir");
        File uploads = new File(userDir, "external-uploads");
        if (!uploads.exists())
            uploads.mkdirs();
        return uploads;
    }
}
