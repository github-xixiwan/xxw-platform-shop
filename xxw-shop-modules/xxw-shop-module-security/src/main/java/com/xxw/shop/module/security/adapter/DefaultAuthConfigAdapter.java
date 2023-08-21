package com.xxw.shop.module.security.adapter;

import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultAuthConfigAdapter implements AuthConfigAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthConfigAdapter.class);

    public DefaultAuthConfigAdapter() {
        logger.info("not implement other AuthConfigAdapter, use DefaultAuthConfigAdapter... all url need auth...");
    }

    /**
     * 内部直接调用接口，无需登录权限
     */
    private static final String FEIGN_INSIDER_URI = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/**";

    /**
     * 外部直接调用接口，无需登录权限 unwanted auth
     */
    private static final String EXTERNAL_URI = "/**/ua/**";

    /**
     * favicon
     */
    private static final String FAVICON = "/favicon.ico";

    /**
     * knife4j
     */
    private static final String DOC_URI = "/doc.html";

    /**
     * swagger
     */
    private static final String SWAGGER_URI = "/v3/api-docs/**";

    /**
     * webjars
     */
    private static final String WEBJARS_URI = "/webjars/**";

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/*");
    }

    @Override
    public List<String> excludePathPatterns(String... paths) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(FAVICON);
        arrayList.add(DOC_URI);
        arrayList.add(WEBJARS_URI);
        arrayList.add(SWAGGER_URI);
        arrayList.add(FEIGN_INSIDER_URI);
        arrayList.add(EXTERNAL_URI);
        arrayList.addAll(Arrays.asList(paths));
        return arrayList;
    }
}
