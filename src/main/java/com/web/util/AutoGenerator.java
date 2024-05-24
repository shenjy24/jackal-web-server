package com.web.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.util.Collections;

/**
 * MyBatis Plus 自动实体代码生成器
 *
 * @author shenjy
 * @time 2024/5/24 21:45
 */
public class AutoGenerator {
    public static void main(String[] args) {
        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/seata?serverTimezone=GMT%2B8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("shenjy") // 设置作者
                            .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                            .dateType(DateType.SQL_PACK);

                })
                .packageConfig(builder -> {
                    builder.parent("com.web.repository")
                            .entity("entity")
                            .mapper("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    Paths.get(System.getProperty("user.dir")) + "/src/main/resource/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("account_tbl")
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .enableRestStyle()
                            .serviceBuilder()
                            .disableService()
                            .disableServiceImpl();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
