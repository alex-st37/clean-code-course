package victor.training.cleancode.fp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import victor.training.cleancode.fp.support.OrderRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

import static org.jooq.lambda.Unchecked.consumer;

@Slf4j
@RequiredArgsConstructor
public class FileExportService_Loan {

   public interface VictorConsumer<T> {

      void accept(T t) throws Exception;
   }
   private final OrderRepo orderRepo;

   public void exportOrders() throws IOException {
      File file = new File("target/orders.csv");
      log.info("Starting export into {} ...", file.getAbsolutePath());
      long t0 = System.currentTimeMillis();
      try (Writer writer = new FileWriter(file)) {

         writer.write("order_id;date\n");

         orderRepo.findByActiveTrue()
                 .map(o -> o.getId() + ";" + o.getCreationDate() + "\n")
                 .forEach(consumer(writer::write));

         log.info("Export DONE");
      } catch (Exception e) {
         log.error("Export FAILED!", e); // TERROR-Driven Development
         // imagine... sendErrorEmail(e);
         throw e;
      } finally {
         long t1 = System.currentTimeMillis();
         log.info("Export completed in {} seconds ", (t1 - t0) / 1000);
      }
   }


   // UTIL
   static public <T> Consumer<T> wrap(VictorConsumer<T> vc) {
      return x -> {
         try {
            vc.accept(x);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      };
   }

}

