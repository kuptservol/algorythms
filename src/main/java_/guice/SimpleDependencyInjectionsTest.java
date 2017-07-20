package java_.guice;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class SimpleDependencyInjectionsTest {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BillingModule());

        CreditCardProcessor creditCardProcessor = injector.getInstance(CreditCardProcessor.class);
        CreditCardProcessor creditCardProcessor2 = injector.getInstance(CreditCardProcessor.class);

        // not singleton by default!!
        System.out.println(creditCardProcessor != creditCardProcessor2);

        creditCardProcessor.process();
    }

    @BindingAnnotation
    @Target({FIELD, PARAMETER, METHOD})
    @Retention(RUNTIME)
    public @interface Sber {
    }

    @BindingAnnotation
    @Target({FIELD, PARAMETER, METHOD})
    @Retention(RUNTIME)
    public @interface Raiff {
    }

    @ImplementedBy(BankUtilsImpl.class)
    public interface BankUtils {

    }

    public interface BillingManager {
        void manage();
    }

    public static class BankUtilsImpl implements BankUtils {

    }

    public static class BillingManagerSber implements BillingManager {

        final String city;

        public BillingManagerSber(String city) {
            this.city = city;
        }

        @Override
        public void manage() {
            System.out.println("Manage Sber " + city);
        }
    }

    public static class BillingManagerRaiff implements BillingManager {

        @Override
        public void manage() {
            System.out.println("Manage Raiff");
        }
    }

    public static class BillingManagerTinkoff implements BillingManager {

        @Override
        public void manage() {
            System.out.println("Manage Tinkoff");
        }
    }

    //    @Singleton
    public static class CreditCardProcessor {

        @Inject
        @Sber
//        @Named("Tinkoff")
        private BillingManager billingManager;

        @Inject
        private BankUtils bankUtils;

        public void process() {
            billingManager.manage();
        }
    }

    public static class BillingModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(CreditCardProcessor.class);
            bind(BillingManager.class)
                    .annotatedWith(Names.named("Tinkoff"))
                    .to(BillingManagerTinkoff.class);
            bind(String.class)
                    .annotatedWith(Names.named("city"))
                    .toInstance("Moscow");
        }

        @Provides
        @Singleton
        @Sber
        public BillingManager billingManagerSber(@Named("city") String city) {
            return new BillingManagerSber(city);
        }

        @Provides
        @Singleton
        @Raiff
        public BillingManager billingManagerRaiff() {
            return new BillingManagerRaiff();
        }
    }
}
