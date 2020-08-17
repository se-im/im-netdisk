package zmz.netdisk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class NetdiskApplication implements CommandLineRunner
{

    @Value("${file.upload.place}")
    private String rootPath;

    public static void main(String[] args)
    {
        SpringApplication.run(NetdiskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File f = new File(rootPath);
        if(!f.exists())
        {
            f.mkdirs();
        }
    }
}
