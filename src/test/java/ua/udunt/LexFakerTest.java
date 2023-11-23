package ua.udunt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import ua.udunt.lex.faker.LexFaker;

@Slf4j
public class LexFakerTest {

    @Test
    public void fakerTest() {
        LexFaker faker = new LexFaker();
        log.info("Merchant: {}", faker.ukranian().merchant());
    }

}
