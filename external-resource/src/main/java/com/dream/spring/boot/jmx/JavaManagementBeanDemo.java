package com.dream.spring.boot.jmx;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

/**
 * <p>
 * {@link ManagementFactory}
 * </p>
 *
 * @author @author <a href="https://github.com/LMDreamFree">Dream</a>
 * @date 2021/5/16 20:47
 * @className JavaManagementBeanDemo
 */
public class JavaManagementBeanDemo {
    public static void main(String[] args) throws MalformedObjectNameException, ReflectionException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanException, IOException {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println(memoryMXBean.getHeapMemoryUsage().getUsed());
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(operatingSystemMXBean.getName());
        ObjectName name = new ObjectName("com.dream.spring.boot.jmx.Customized:type=customized");
        ManagementFactory.getPlatformMBeanServer().createMBean("com.dream.spring.boot.jmx.Customized", name);
        System.in.read();
    }
}
