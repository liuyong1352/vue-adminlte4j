import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by bjliuyong on 2017/12/26.
 */
public class Model {

    public static void main(String ... args) {
        String currentDir = System.getProperty("user.dir") ;


        Path path = Paths.get(currentDir  , "src" , "main" , "java") ;
        System.out.println(path);
    }

}
