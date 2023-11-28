package ua.udunt.lex.faker;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;
import ua.udunt.lex.util.LibUtil;

import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

@Slf4j
public class LexFaker extends Faker {

    public UkranianProvider ukranian() {
        return getProvider(UkranianProvider.class, UkranianProvider::new, this);
    }

    public static class UkranianProvider extends AbstractProvider<BaseProviders> {

        private final BaseProviders faker;

        protected UkranianProvider(BaseProviders faker) {
            super(faker);
            this.faker = faker;
            try {
                faker.addPath(Locale.ENGLISH, Paths.get(Objects.requireNonNull(getClass()
                        .getClassLoader().getResource("faker/merchants.yml")).toURI()));
                faker.addPath(Locale.ENGLISH, Paths.get(Objects.requireNonNull(getClass()
                        .getClassLoader().getResource("faker/streets.yml")).toURI()));
                faker.addPath(Locale.ENGLISH, Paths.get(Objects.requireNonNull(getClass()
                        .getClassLoader().getResource("faker/cities.yml")).toURI()));
                faker.addPath(Locale.ENGLISH, Paths.get(Objects.requireNonNull(getClass()
                        .getClassLoader().getResource("faker/male_names.yml")).toURI()));
                faker.addPath(Locale.ENGLISH, Paths.get(Objects.requireNonNull(getClass()
                        .getClassLoader().getResource("faker/female_names.yml")).toURI()));
                faker.addPath(Locale.ENGLISH, Paths.get(Objects.requireNonNull(getClass()
                        .getClassLoader().getResource("faker/lastnames.yml")).toURI()));
            } catch (Exception e) {
                log.error("", e);
            }
        }

        public String merchant() {
            return resolve("shopping.merchants", null);
        }

        private String street() {
            return resolve("address.streets", null);
        }

        private String city() {
            return resolve("address.cities", null);
        }

        public String address() {
            return street() + ", " + faker.address().streetAddressNumber() + ", " + city();
        }

        private String femaleName() {
            return resolve("female.names", null);
        }

        private String maleName() {
            return resolve("male.names", null);
        }

        private String lastname() {
            return resolve("name.lastnames", null);
        }

        public String fullName(String sex) {
            if (!LibUtil.isNullOrEmpty(sex)) {
                switch (sex) {
                    case "Male" -> {
                        return maleName() + " " + lastname();
                    }
                    case "Female" -> {
                        return femaleName() + " " + lastname();
                    }
                    default -> {
                    }
                }
            }
            return null;
        }

    }

}
