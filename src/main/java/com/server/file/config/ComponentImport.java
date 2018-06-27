package com.server.file.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author ygl
 * @create 2018-06-04
 * @DESCRIPTION
 * 解决jmx重复注册bean的问题
 * @EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
 **/
@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ComponentImport {
}
