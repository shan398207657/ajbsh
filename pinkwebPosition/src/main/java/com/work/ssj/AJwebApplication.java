package com.work.ssj;

import com.work.ssj.datasources.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(basePackages={"com.work.ssj.*.*.mapper"})
@EnableTransactionManagement
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
@EnableAsync
public class AJwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AJwebApplication.class, args);
    }

}
