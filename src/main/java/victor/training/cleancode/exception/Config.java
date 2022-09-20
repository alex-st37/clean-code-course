package victor.training.cleancode.exception;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


@Component
public class Config {

    //   @SneakyThrows // tricks the javac to ignore the checked exceptions. breaks the language.
    public Date getLastPromoDate() {
        File file = new File("config.properties");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
            return format.parse(properties.getProperty("last.promo.date"));
        } catch (IOException | ParseException e) {
            throw new MyException(MyException.ErrorCode.BAD_CONFIG);
//            throw new RuntimeException(e); // traditional way to cover the sins of youth of java.
        }
        //      return new Date();
    }


}
