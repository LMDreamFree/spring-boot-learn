package com.dream.spring.boot.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.PropertySource;

import java.io.Serializable;


/**
 * <p>
 * 自定义endpoint {@link Endpoint}
 * </p>
 *
 * @author lim
 * @date 2021/5/1 22:39
 * @className ArchitectureEndpoint
 */
@Endpoint(id = "architecture")
public class ArchitectureEndpoint {


    @ReadOperation
    public String architecture() {
        return "Hello,World";
    }

    public class EnvironmentDecorate<T extends PropertySource> extends PropertySource<T> implements Serializable {
        private final T source;

        public EnvironmentDecorate(String name, T source) {
            super(name, source);
            this.source = source;
        }

        @Override
        public Object getProperty(String name) {
            return source.getProperty(name);
        }
    }
}
