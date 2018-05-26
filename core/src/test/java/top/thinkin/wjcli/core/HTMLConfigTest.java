package top.thinkin.wjcli.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import top.thinkin.wjcli.util.StrUtil;

public class HTMLConfigTest {
    HTMLConfig config;

    @Before
    public void cteate() {
        config = HTMLConfig.cteate();
        Assert.assertNotNull(config);
    }


    @Test
    public void html() throws Exception {
        config.build();
        Assert.assertTrue(!StrUtil.isEmpty(config.html()));
    }
}