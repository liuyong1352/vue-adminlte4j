package com.vue.adminlte4j.support;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bjliuyong on 2018/3/19.
 */
public class FileChangeListener extends Thread  {

    private Set<FileEntry> fileEntries = new HashSet<>() ;
    private static String projectDir = System.getProperty("user.dir") ;
    private static Path sp , tp ;

    public static final String SPRING_BOOT = "spring_boot" ;

    public static FileChangeListener INSTANCE = new FileChangeListener() ;

    private FileChangeListener() {
        this.setDaemon(true);
    }

    public static FileChangeListener getInstance() {
        return  INSTANCE ;
    }

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

    public FileChangeListener autoConfig(String type) {
        Path javaMetaInfResPath = Paths.get("src" , "main" ,"resources" , "META-INF" , "resources" ) ;
        Path classMetaInfResPath = Paths.get("target" , "classes"  , "META-INF" , "resources" ) ;
        listen(javaMetaInfResPath)
            .to(classMetaInfResPath).type(FileChangeListener.FileEntryType.DIR);

        listen(Paths.get("src" , "test" ,"resources", "static" ))
            .to(Paths.get("target" , "test-classes" , "static"))
            .type(FileChangeListener.FileEntryType.DIR);

        listen(Paths.get("src" , "main" ,"resources", "static" ))
            .to(Paths.get("target" , "classes" , "static"))
            .type(FileChangeListener.FileEntryType.DIR);
        return  this ;
    }

    @Override public void run() {

        List<FileEntry> copies = new ArrayList<>();
        copies.addAll(fileEntries) ;
        copies.forEach(fileEntry-> System.out.println("listen file change" + fileEntry) );

        copies.forEach(o -> {
            try {
                if(o.type() == FileEntryType.DIR )
                    addFileEntries(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        boolean isInterrupted = false ;
        while(!isInterrupted) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                isInterrupted = true ;
                e.printStackTrace();
            }

            fileEntries.forEach(f -> {
                try {
                    onChange(f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private void addFileEntries(FileEntry fileEntry) throws IOException {
        if(!fileEntry.srcPath.toFile().exists())
            return;
        Files.walk(fileEntry.srcPath, FileVisitOption.FOLLOW_LINKS).forEach(path -> {
            /*if(path.toFile().isFile() && path.toString().endsWith(".html")) {
                Path relativePath = fileEntry.srcPath.relativize(path) ;
                listenAbsolute(path).toAbsolute(Paths.get(fileEntry.targetPath.toString() , relativePath.toString())) ;
            }*/

            Path relativePath = fileEntry.srcPath.relativize(path) ;
            Path target = Paths.get(fileEntry.targetPath.toString() , relativePath.toString()) ;
            listenAbsolute(path)
                .toAbsolute(target)
                .type(path.toFile().isFile() ? FileEntryType.FILE : FileEntryType.DIR) ;
        });
    }

    private void  onChange(FileEntry fileEntry) throws Exception{
        if(!fileEntry.isChange())
            return;
        if (fileEntry.type() == FileEntryType.DIR) {
            Files.walk(fileEntry.srcPath, FileVisitOption.FOLLOW_LINKS).forEach(path -> {
                Path relativePath = fileEntry.srcPath.relativize(path) ;
                Path target = Paths.get(fileEntry.targetPath.toString() , relativePath.toString()) ;
                if(!target.toFile().exists()) {
                    try {
                        Files.copy(path , target, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println(LocalDateTime.now().toString() + "#####File Copy " + path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listenAbsolute(path)
                        .toAbsolute(target)
                        .type(path.toFile().isFile() ? FileEntryType.FILE : FileEntryType.DIR) ;

                }
            });
        } else {
            if(fileEntry.srcPath.toFile().exists()) {
                Files.copy(fileEntry.srcPath, fileEntry.targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(LocalDateTime.now().toString() + "#####File Copy " + fileEntry);
            } else {
                fileEntry.targetPath.toFile().delete() ;
                System.out.println(LocalDateTime.now().toString() + "#####File Delete " + fileEntry.targetPath);
            }

        }
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

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof FileEntry))
                return false;

            FileEntry entry = (FileEntry) o;

            return srcPath.equals(entry.srcPath);
        }

        @Override public int hashCode() {
            return srcPath.hashCode();
        }
    }

    public enum FileEntryType {

        FILE  , DIR

    }

}
