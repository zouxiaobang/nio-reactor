package test.chain;

import com.demo.handler.SendingHandler;
import com.demo.handler.TestStringHandler;
import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.impl.DefaultFilterChain;
import com.demo.handler.decode.StringDecoder;
import com.demo.handler.encode.StringEncoder;
import com.demo.handler.translation.DefaultByteBufferToStringTranslator;
import com.demo.handler.translation.DefaultStringToByteBufferTranslator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.StringContains;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/29
 */
public class ChainTest {
    // done chain被多线程调用，会出现线程安全问题
    // done 思考问题，加锁or对象隔离

    private static final String DATA = "Hello, World!";
    private FilterChain positiveChain;
    private FilterChain reverseChain;

    @Before
    public void initProcessors() {
        initPositiveChain();
        initReverseChain();
    }

    private void initReverseChain() {
        reverseChain = new DefaultFilterChain.Builder()
                .addProcessors(
                        new StringEncoder(new DefaultStringToByteBufferTranslator()),
                        new SendingHandler())
                .build();
    }

    private void initPositiveChain() {
        positiveChain = new DefaultFilterChain.Builder()
                .addProcessor(new StringDecoder(new DefaultByteBufferToStringTranslator()))
                .addProcessor(new TestStringHandler())
                .build();
    }

    @Test
    public void testPositiveRightProcess() throws Exception {
        // 正向正常测试
        FilterContext positiveContext = new FilterContext();
        ByteBuffer originData = ByteBuffer.wrap(DATA.getBytes());
        positiveContext.setData(originData);
        positiveContext.setBound(FilterContext.IN_BOUND);
        positiveChain.filter(positiveContext);
        Assert.assertEquals(positiveContext.getData(), DATA);
    }

    @Test
    public void testPositiveErrorProcess() {
        try {
            // 正向错误测试
            FilterContext positiveContext = new FilterContext();
            ByteBuffer originData = ByteBuffer.wrap(DATA.getBytes());
            positiveContext.setData(originData);
            positiveContext.setBound(FilterContext.OUT_BOUND);
            positiveChain.filter(positiveContext);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), new StringContains("数据流动方向异常。"));
            return;
        }
        Assert.fail("未测出错误信息，测试失败");
    }

    @Test
    public void testReverseRightProcess() throws Exception {
        // 反向正常测试
        FilterContext reverseContext = new FilterContext();
        reverseContext.setData(DATA);
        reverseContext.setBound(FilterContext.OUT_BOUND);
        reverseChain.filter(reverseContext);
        Assert.assertEquals(reverseContext.getData(), ByteBuffer.wrap(DATA.getBytes()));
    }

    @Test
    public void testReverseErrorProcess() {
        try {
            // 反向正常测试
            FilterContext reverseContext = new FilterContext();
            reverseContext.setData(DATA);
            reverseContext.setBound(FilterContext.IN_BOUND);
            reverseChain.filter(reverseContext);
            Assert.assertEquals(reverseContext.getData(), ByteBuffer.wrap(DATA.getBytes()));
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), new StringContains("数据流动方向异常。"));
            return;
        }
        Assert.fail("未测出错误信息，测试失败");
    }
}
