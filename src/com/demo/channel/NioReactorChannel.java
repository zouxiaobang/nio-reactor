package com.demo.channel;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.reactor.EventType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class NioReactorChannel implements ReactorChannel {
    private final SelectableChannel selectableChannel;
    private EventType eventType;
    private FilterChain filterChain;
    private Ioer ioer;

    private NioReactorChannel(SelectableChannel selectableChannel) {
        this.selectableChannel = selectableChannel;
    }

    public static NioReactorChannel ofChannel(SelectableChannel selectableChannel) {
        return new NioReactorChannel(selectableChannel);
    }

    public NioReactorChannel filterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
        return this;
    }

    @Override
    public FilterChain getFilterChain() {
        return filterChain;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public Ioer getIoer() {
        if (ioer == null) {
            synchronized (NioReactorChannel.class) {
                ioer = new NioIoer();
                return ioer;
            }
        }

        return ioer;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }


    public SelectableChannel getSelectableChannel() {
        return selectableChannel;
    }

    class NioIoer implements Ioer {
        @Override
        public void read() throws IOException {
            ByteBuffer byteBuffer = getByteBufferFromChannel();
            if (byteBuffer != null) {
                doRead(byteBuffer);
            }
        }

        private void doRead(ByteBuffer byteBuffer) {
            if (filterChain != null) {
                try {
                    FilterContext filterContext = buildFilterContext(byteBuffer, FilterContext.IN_BOUND);
                    filterChain.filter(filterContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private FilterContext buildFilterContext(ByteBuffer byteBuffer, int bound) {
            FilterContext filterContext = new FilterContext();
            filterContext.setData(byteBuffer);
            filterContext.setBound(bound);
            filterContext.setReactorChannel(NioReactorChannel.this);
            return filterContext;
        }

        private ByteBuffer getByteBufferFromChannel() throws IOException {
            ByteBuffer byteBuffer;
            SocketChannel socketChannel = getSocketChannel();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                // 这里包会被拆分，后续需要对接收内容进行拼接 -- TODO 自定义ByteBuffer
                byteBuffer = ByteBuffer.allocate(1024);
                while (socketChannel.isOpen() && socketChannel.read(byteBuffer) != -1) {
                    if (byteBuffer.position() > 0) {
                        break;
                    }
                }
                if (byteBuffer.position() == 0) {
                    // 没有数据，直接返回
                    return null;
                }
                // 将byteBuffer转换为读取状态
                byteBuffer.flip();
                return byteBuffer;
            }

            return null;
        }

        private SocketChannel getSocketChannel() {
            if (selectableChannel instanceof SocketChannel) {
                return (SocketChannel) selectableChannel;
            }

            return null;
        }

        @Override
        public void write() {

        }
    }
}
