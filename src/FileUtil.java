import java.io.File;

public class FileUtil {
    public static boolean checkFileExistence(String path){
        File f = new File(path);
        return (f.exists() && f.isFile());
    }
}
