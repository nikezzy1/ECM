package ls.ecm;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("ls.ecm.mapper")
@EnableTransactionManagement//开启事务
public class EcmApplication {

    private static Logger logger = LoggerFactory.getLogger("EcmApplication");

    public static void main(String[] args) {
        SpringApplication.run(EcmApplication.class, args);
        logger.info("系统启动成功");
    }

}
