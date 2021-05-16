package com.dream.spring.boot.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author @author <a href="https://github.com/LMDreamFree">Dream</a>
 * @date 2021/5/16 20:55
 * @className Customized
 */
public class Customized implements CustomizedMBean {
    private static final OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN = ManagementFactory.getOperatingSystemMXBean();

    @Override
    public String operatingSystemName() {
        return OPERATING_SYSTEM_MX_BEAN.getName();
    }

    @Override
    public String version() {
        return OPERATING_SYSTEM_MX_BEAN.getVersion();
    }
}
