package foo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

@Configuration
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@SpringBootApplication
public class SimpleApplication implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Final result: " + new Service().test());
    }

    @Bean
    public DataStore dataStore() {
        return new DataStore();
    }

    @Bean
    public AspceJAdvice aspceJAdvice() {
        AspceJAdvice advice = AspceJAdvice.aspectOf();
        advice.setDataStore(dataStore());
        return advice;
    }

    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        return loadTimeWeaver;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
