package test.chain;

import com.demo.handler.SendingHandler;
import com.demo.handler.TestStringHandler;
import com.demo.handler.decode.StringDecoder;
import com.demo.handler.encode.StringEncoder;
import com.demo.handler.translation.DefaultByteBufferToStringTranslator;
import com.demo.handler.translation.DefaultStringToByteBufferTranslator;
import com.demo.reactor.impl.MultipleReactor;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author zouxiaobang
 * @date 2021/1/30
 */
public class MultipleReactorServer {
    public static void main(String[] args) throws IOException {
        testMultipleReactor();
    }

    public static void testMultipleReactor() throws IOException {
        MultipleReactor multipleReactor = new MultipleReactor();
        // todo 这里需要添加两个pipeline来处理读写事件
        // todo 待优化，设置一个管道检测器，拥有重构建能力，能够直接区分读写的chain
        multipleReactor.setReactorPipeline(
                () -> Arrays.asList(new StringDecoder(new DefaultByteBufferToStringTranslator()), new TestStringHandler()),
                () -> Arrays.asList(new StringEncoder(new DefaultStringToByteBufferTranslator()), new SendingHandler()));
        multipleReactor.group();
        multipleReactor.bind(0, 8091);
    }
}
