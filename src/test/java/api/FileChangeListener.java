package api;

import com.vue.adminlte4j.util.EnvUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Created by bjliuyong on 2018/3/19.
 */
public class FileChangeListener extends Thread  {

    private ArrayList<Path> listenerPaths = new ArrayList<>() ;
    private ArrayList<Long> lastModified  = new ArrayList<>() ;


    String userDir = System.getProperty("user.dir") ;
    Path path = EnvUtils.getJavaResourcesPath();
    private Path webPath = Paths.get(path.getParent().toString() , "static") ;
    private Path distLibPath = Paths.get(webPath.toString() , "dist" , "lib") ;
    private Path targetLibPath = Paths.get(userDir , "target" , "classes" , "META-INF" , "resources" , "lib") ;

    public FileChangeListener() {

        Path baseJs = Paths.get( "base.js") ;
        Path vueAdminlteJs = Paths.get("vue-adminlte","dist","js","vue-adminlte.min.js") ;
        listenerPaths.add(baseJs);
        listenerPaths.add(Paths.get("lib.js"));
        listenerPaths.add(vueAdminlteJs);

        for(int i = 0 ; i < listenerPaths.size() ; i++) {
            lastModified.add(distLibPath.resolve(listenerPaths.get(i)).toFile().lastModified());
        }

        System.out.println(vueAdminlteJs);
    }

    @Override public void run() {

        while(true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0 ; i < listenerPaths.size() ; i++) {
                long l = distLibPath.resolve(listenerPaths.get(i)).toFile().lastModified();
                if(l > lastModified.get(i)) {
                    lastModified.set(i , onChange(listenerPaths.get(i))) ;
                }
            }

        }

    }

    private long  onChange(Path path) {
        try {
            Files.copy(distLibPath.resolve(path) , targetLibPath.resolve(path) , StandardCopyOption.REPLACE_EXISTING);
            System.out.println("#############file copy src:    " + distLibPath.resolve(path));
            System.out.println("#############file copy target: " + targetLibPath.resolve(path));
            return targetLibPath.resolve(path).toFile().lastModified();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return distLibPath.resolve(path).toFile().lastModified();
    }

    public static void main(String ...args) {
        FileChangeListener fileChangeListener = new FileChangeListener() ;
        fileChangeListener.run();
    }

    class FileEntry {



    }

    class FileType {

    }
}
