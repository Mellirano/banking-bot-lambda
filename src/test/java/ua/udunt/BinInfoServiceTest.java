package ua.udunt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import ua.udunt.lex.service.BinInfoService;

@Slf4j
public class BinInfoServiceTest {

    @Test
    public void getBinInfoTest() {
        BinInfoService binInfoService = BinInfoService.getInstance();
        log.info("Bin info: {}", binInfoService.getBinInfo("5168752021610608"));
    }

}
