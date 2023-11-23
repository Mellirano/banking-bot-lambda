package ua.udunt.lex.faker;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;
import ua.udunt.lex.util.LibUtil;

import java.nio.file.Paths;
import java.util.Locale;

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
            faker.addPath(Locale.ENGLISH, Paths.get("src/main/resources/faker/merchants.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/main/resources/faker/streets.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/main/resources/faker/cities.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/main/resources/faker/male_names.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/main/resources/faker/female_names.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/main/resources/faker/lastnames.yml"));
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
