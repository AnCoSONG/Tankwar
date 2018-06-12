package tankwar;

import java.io.File;
import javax.swing.filechooser.FileFilter;
@Deprecated
public class TankWarMapFileFilter extends FileFilter{
    @Override
    public boolean accept(File f) {
        boolean returnVal = false;
        if(f.isDirectory()){
            returnVal = true;
        }else {
            String name = f.getName();
            if(name.endsWith(".twm")){
                returnVal = true;
            }
        }
        return returnVal;
    }

    @Override
    public String getDescription() {
        return ".twm(TankWarMap坦克大战地图)";
    }

}
