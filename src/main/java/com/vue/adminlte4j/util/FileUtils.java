package com.vue.adminlte4j.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by bjliuyong on 2018/5/28.
 */
public class FileUtils {


    public static List<String> readAllLines(InputStream inputStream) throws IOException {
        return readAllLines(inputStream, StandardCharsets.UTF_8);
    }

    public static List<String> readAllLines(InputStream inputStream , Charset cs ) throws IOException {
        try (BufferedReader reader = newBufferedReader(inputStream, cs)) {
            List<String> result = new ArrayList<>();
            for (;;) {
                String line = reader.readLine();
                if (line == null)
                    break;
                result.add(line);
            }
            return result;
        }
    }

    public static BufferedReader newBufferedReader(InputStream inputStream, Charset cs)
        throws IOException
    {
        CharsetDecoder decoder = cs.newDecoder();
        Reader reader = new InputStreamReader(inputStream, decoder);
        return new BufferedReader(reader);
    }

    public static InputStream openInputStream(String fileName) {
        try {
            Enumeration<URL> resourceUrls = ReflectUtils.getDefaultClassLoader().getResources(fileName) ;
            while (resourceUrls.hasMoreElements()) {
                URL url = resourceUrls.nextElement();
                return url.openConnection().getInputStream() ;
            }
        } catch (IOException e) {
            throw new RuntimeException(e) ;
        }
        return null ;
    }

}
