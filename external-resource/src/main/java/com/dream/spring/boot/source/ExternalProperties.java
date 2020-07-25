package com.dream.spring.boot.source;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.Map;

import static com.dream.spring.boot.source.ExternalProperties.SOURCE_PREFIX;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author lim
 * @date 2020/7/25 17:02
 * @className ExternalProperties
 */
@ConfigurationProperties(prefix = SOURCE_PREFIX)
public class ExternalProperties {
    public static final String SOURCE_PREFIX="source";
    private String name;
    private String fileName;
    private String group;
    private Environment environment;
    private Map<String,SourceDetailProperties> detail;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Map<String, SourceDetailProperties> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, SourceDetailProperties> detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
