package com.vue.adminlte4j.support;

import java.io.IOException;
import java.nio.file.FileVisitOption;
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
    private List<FileEntry> dirFileEntries = new ArrayList<>() ;
    private static String projectDir = System.getProperty("user.dir") ;
    private static Path sp , tp ;

    public FileEntry listen(Path parentPath , String fromPathName){
        return  listen(Paths.get(parentPath.toString() , fromPathName)) ;
    }

    public FileEntry listen(Path parentPath , Path fromPath){
        return  listen(Paths.get(parentPath.toString() , fromPath.toString())) ;
    }

    public FileEntry listen(Path fromPath){
        FileEntry fileEntry = new FileEntry().from(fromPath) ;
        fileEntries.add(fileEntry) ;
        return  fileEntry ;
    }

    public FileEntry listenAbsolute(Path fromPath) {
        FileEntry fileEntry = new FileEntry().fromAbsolute(fromPath) ;
        fileEntries.add(fileEntry) ;
        return  fileEntry ;
    }

    @Override public void run() {

        for(int i = 0 ; i < fileEntries.size() ;i++) {
            System.out.println("listen file change" + fileEntries.get(i));
        }

        boolean deal = false ;

        while(true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0 ; i < fileEntries.size() ;i++) {
                try {
                    if(fileEntries.get(i).type() == FileEntryType.DIR) {
                        if(deal)
                            continue;
                        addFileEntries(fileEntries.get(i)) ;
                    }
                    onChange(fileEntries.get(i)) ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            deal = true  ;
        }

    }

    private void addFileEntries(FileEntry fileEntry) throws IOException {
        Files.walk(fileEntry.srcPath, FileVisitOption.FOLLOW_LINKS).forEach(path -> {
            if(path.toFile().isFile()) {
                Path relativePath = fileEntry.srcPath.relativize(path) ;
                listenAbsolute(path).toAbsolute(Paths.get(fileEntry.targetPath.toString() , relativePath.toString())) ;
            }
        });
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

    public static class FileEntry {

        Path srcPath ;
        Path targetPath ;
        long lastModified ;
        FileEntryType type = FileEntryType.FILE ; //单文件 1，目录结构

        public FileEntry(){

        }
        public FileEntry from(Path parentPath , Path fromPath) {
            return  from( Paths.get(parentPath.toString()  , fromPath.toString())) ;
        }

        public FileEntry from(Path fromPath) {
            return  fromAbsolute(Paths.get(projectDir.toString() , fromPath.toString())) ;
        }

        public FileEntry fromAbsolute(Path srcPath ) {
            this.srcPath = srcPath ;
            return  this ;
        }

        public FileEntry to(Path parentPath , String toPath) {
            return to(parentPath , Paths.get(toPath)) ;
        }

        public FileEntry to(Path parentPath , Path toPath) {
            return to(Paths.get(parentPath.toString(), toPath.toString())) ;
        }

        public FileEntry to(Path toPath) {
            return toAbsolute(Paths.get(projectDir.toString() , toPath.toString())) ;
        }



        public FileEntry toAbsolute(Path targetPath ) {
            this.targetPath = targetPath;
            this.lastModified = srcPath.toFile().lastModified() ;
            return this ;
        }

        public boolean isChange(){
            long l = this.lastModified ;
            this.lastModified = srcPath.toFile().lastModified() ;
            return l != this.lastModified ;
        }

        public FileEntry type(FileEntryType type) {
            this.type = type ;
            return  this ;
        }

        public FileEntryType type() {
            return this.type ;
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

    public enum FileEntryType {

        FILE  , DIR

    }

}
