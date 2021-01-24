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

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public void read() throws IOException {
        ByteBuffer byteBuffer = getByteBufferFromChannel();
        if (byteBuffer != null && filterChain != null) {
            FilterContext filterContext = buildFilterContext(byteBuffer);
            filterChain.filter(filterContext);
        }
    }

    private FilterContext buildFilterContext(ByteBuffer byteBuffer) {
        FilterContext filterContext = new FilterContext();
        filterContext.setOriginalData(byteBuffer);
        return filterContext;
    }

    @Override
    public void write() {

    }

    public SelectableChannel getSelectableChannel() {
        return selectableChannel;
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
}
