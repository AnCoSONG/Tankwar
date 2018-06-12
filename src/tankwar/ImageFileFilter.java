package tankwar;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author :AnCo
 * @
 */
public class ImageFileFilter extends FileFilter {
    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()){
            return true;
        } else{
            String name = pathname.getName();
            if(
                    name.endsWith(".png")||name.endsWith(".jpg")
                    ||name.endsWith(".gif")||name.endsWith(".bmp")
                    ||name.endsWith(".tiff")||name.endsWith(".jpeg")
                    ){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Jpg、Gif、Png等图片格式";
    }
}
