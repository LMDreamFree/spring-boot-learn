package com.dream.rpc.config;

import com.alibaba.fastjson.JSON;
import com.dream.rpc.annotation.EnableRpc;
import com.dream.rpc.annotation.EnableRpcServices;
import com.dream.rpc.factorybean.RpcServiceFactoryBean;
import feign.Contract;
import feign.MethodMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * {@link ImportBeanDefinitionRegistrar}
 * </p>
 *
 * @author cloud
 * @date 2021/7/3 22:29
 * @className EnableRpcRegistrar
 * @see org.springframework.cloud.openfeign.FeignClientsRegistrar
 */
public final class  EnableRpcRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    private ResourceLoader resourceLoader;
    private final MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
    private Contract contract = new SpringMvcContract();

    private BeanFactory beanFactory;


    private static final Logger LOGGER = LoggerFactory.getLogger(EnableRpcRegistrar.class);

    public EnableRpcRegistrar() {
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.beanDefinitionRegistry = registry;
        if (beanDefinitionRegistry instanceof BeanFactory) {
             beanFactory = (BeanFactory) this.beanDefinitionRegistry;
        }
        Map<String, Object> metaData = importingClassMetadata.getAnnotationAttributes(EnableRpcServices.class.getName());
        LOGGER.info("获取'@EnableRpcServices'注解元信息:{}", JSON.toJSONString(metaData));
        String[] value = (String[]) metaData.get("value");
        LOGGER.info("获取'@EnableRpcServices' value 信息 :{}", JSON.toJSONString(value));
        final Set<BeanDefinition> beanDefinitions = new HashSet<>();
        if (!ObjectUtils.isEmpty(value)) {
            // 扫描指定包信息
            ClassPathBeanDefinitionScanner scanner = getScanner();
            scanner.resetFilters(false);
            scanner.setResourceLoader(this.resourceLoader);
            scanner.addIncludeFilter(new AnnotationTypeFilter(EnableRpc.class));
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(value[0]);
            beanDefinitions.addAll(candidateComponents);
        }
        // 处理beanDefinition
        handlerBeanDefinition(beanDefinitions, importingClassMetadata);
    }

    private void handlerBeanDefinition(Set<BeanDefinition> beanDefinitions, AnnotationMetadata annotationMetadata) {
        String className = annotationMetadata.getClassName();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                String beanClassName = beanDefinition.getBeanClassName();
                Class targetClazz = ClassUtils.resolveClassName(beanClassName, null);
                RpcServiceFactoryBean factoryBean = new RpcServiceFactoryBean();
                factoryBean.setClazz(targetClazz);
                String serviceName = null;
                // 获取注解上的value值
                try {
                    MetadataReader metadataReader = readerFactory.getMetadataReader(beanClassName);
                    Map<String, Object> annotationAttributes = metadataReader.getAnnotationMetadata().getAnnotationAttributes(EnableRpc.class.getName());
                    serviceName = (String) annotationAttributes.get("value");
                } catch (IOException e) {
                    LOGGER.error("获取['" + beanClassName + "']失败:{}", e.getMessage(), e);
                }

                List<MethodMetadata> methodMetadata = contract.parseAndValidateMetadata(targetClazz);
                for (MethodMetadata md : methodMetadata) {
                    Method method = md.method();
                    String requestUrl = md.template().url();
                    Class<?> returnType = method.getReturnType();
                    ClientInvocationHandler invocationHandler = new ClientInvocationHandler(requestUrl, serviceName, returnType);
                    MethodRegistry.addRegistry(method,
                            invocationHandler);
                    factoryBean.setInvocationHandler(invocationHandler);
                    invocationHandler.setBeanFactory(beanFactory);
                }
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(targetClazz, () -> {
                    try {
                        return factoryBean.getObject();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                builder.setLazyInit(true);
                BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(), className);
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, beanDefinitionRegistry);
            }
        }

    }


    private ClassPathBeanDefinitionScanner getScanner() {
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
        return classPathBeanDefinitionScanner;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
