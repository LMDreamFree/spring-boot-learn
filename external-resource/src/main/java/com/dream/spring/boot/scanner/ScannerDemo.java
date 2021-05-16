package com.dream.spring.boot.scanner;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * <p>TODO</p>
 * @author @author <a href="https://github.com/LMDreamFree">Dream</a>
 * @date: 2021/5/16 20:47
 */
public class ScannerDemo {
    public static void main(String[] args) throws IOException {
        String suffix = "**/*.java";
        String userDir = System.getProperty("user.dir");
        //   String path ="/src/main/java/com/example/demo/basic/spring/";
        Class<ScannerDemo> clazz = ScannerDemo.class;
        String packageName = clazz.getPackage().getName();
        System.out.println("packageName : " + packageName);
        String path = "/external-resource/src/main/java/com/dream/spring/boot/";
        String location = userDir + path + suffix;
        System.out.println(location);
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        Resource[] resources = resourcePatternResolver.getResources(location);
        Stream.of(resources).forEach(e -> System.err.println(e.getFilename()));
        /*Stream.of(resources).forEach(e-> {
            try {
                System.out.println(IOUtils.toString(e.getInputStream()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });*/
    }

}
