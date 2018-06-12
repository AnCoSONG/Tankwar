package tankwar;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class XMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()){
            return true;
        }else{
            if(f.getName().endsWith(".xml")){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public String getDescription() {
        return "(.xml)XML可标记文本文件";
    }
}
