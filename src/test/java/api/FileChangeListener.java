package api;

import com.vue.adminlte4j.util.EnvUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/3/19.
 */
public class FileChangeListener extends Thread  {

    private List<FileEntry> fileEntries = new ArrayList<>() ;
    String projectDir = System.getProperty("user.dir") ;
    Path sp , tp ;

    public FileEntry listen(Path parentPath , String fromPathName){
        return  listen(parentPath , Paths.get(fromPathName)) ;
    }

    public FileEntry listen(Path parentPath , Path fromPath){
        FileEntry fileEntry = new FileEntry().from(parentPath , fromPath) ;
        fileEntries.add(fileEntry) ;
        return  fileEntry ;
    }

    @Override public void run() {

        for(int i = 0 ; i < fileEntries.size() ;i++) {
            System.out.println("listen file change" + fileEntries.get(i));
        }

        while(true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0 ; i < fileEntries.size() ;i++) {
                try {
                    onChange(fileEntries.get(i)) ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void  onChange(FileEntry fileEntry) throws Exception{
        if(!fileEntry.isChange())
            return;

        Files.copy(fileEntry.srcPath , fileEntry.targetPath , StandardCopyOption.REPLACE_EXISTING);
        System.out.println("#############file copy " + fileEntry);
    }



    public static void main(String ...args) {
        FileChangeListener fileChangeListener = new FileChangeListener() ;

        Path distLibPath = Paths.get("src" , "main" ,"static" , "dist" , "lib") ;
        Path targetLibPath = Paths.get("target" , "classes" , "META-INF" , "resources" , "lib") ;


        fileChangeListener.listen(distLibPath , "base.js").to(targetLibPath , "base.js") ;
        fileChangeListener.start();
    }

    class FileEntry {

        Path srcPath ;
        Path targetPath ;
        long lastModified ;
        int type = 0 ; //单文件 1，目录结构

        public FileEntry(){

        }
        public FileEntry from(Path parentPath , Path fromPath) {
            sp = parentPath ;
            return  from( Paths.get(parentPath.toString()  , fromPath.toString())) ;
        }

        public FileEntry from(Path fromPath) {
            this.srcPath = Paths.get(projectDir.toString() , fromPath.toString()) ;
            return  this ;
        }

        public FileEntry to(Path parentPath , String toPath) {
            return to(parentPath , Paths.get(toPath)) ;
        }

        public FileEntry to(Path parentPath , Path toPath) {
            tp = parentPath ;
            return to(Paths.get(parentPath.toString(), toPath.toString())) ;
        }

        public FileEntry to(Path toPath) {
            this.targetPath = Paths.get(projectDir.toString() , toPath.toString()) ;
            this.lastModified = srcPath.toFile().lastModified() ;
            return this ;
        }

        public boolean isChange(){
            long l = this.lastModified ;
            this.lastModified = srcPath.toFile().lastModified() ;
            return l != this.lastModified ;
        }

        public FileEntry type(int type) {
            this.type = type ;
            return  this ;
        }

        @Override public String toString() {
            final StringBuilder sb = new StringBuilder("FileEntry{");
            sb.append("srcPath=").append(srcPath);
            sb.append(", targetPath=").append(targetPath);
            sb.append(", lastModified=").append(lastModified);
            sb.append(", type=").append(type);
            sb.append('}');
            return sb.toString();
        }
    }


}
