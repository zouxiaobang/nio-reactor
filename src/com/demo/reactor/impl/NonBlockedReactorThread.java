package com.demo.reactor.impl;

import com.demo.channel.NioReactorChannel;
import com.demo.channel.ReactorChannel;
import com.demo.reactor.EventTypeFactory;
import com.demo.reactor.ReactorThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.*;
import java.util.concurrent.FutureTask;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 * 采用jdk的nio实现
 */
public abstract class NonBlockedReactorThread extends Thread implements ReactorThread {
    private final String threadName;
    private boolean isRunning;
    private final Selector selector;
    private final Queue<Runnable> registerTasks = new LinkedList<>();

    public NonBlockedReactorThread() throws IOException {
        this(Thread.currentThread().getName());
    }

    public NonBlockedReactorThread(String threadName) throws IOException {
        this.threadName = threadName;
        selector = Selector.open();
    }

    @Override
    public String getThreadName() {
        return threadName;
    }

    @Override
    public void doStart() {
        if (!isRunning) {
            isRunning = true;
            start();
        }
    }

    @Override
    public void doStop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    @Override
    public void register(int port) throws IOException {
        ServerSocketChannel selectableChannel = ServerSocketChannel.open();
        selectableChannel.configureBlocking(false);
        selectableChannel.bind(new InetSocketAddress(port));
        buildRegisterTask(selectableChannel);
    }

    @Override
    public void run() {
        while (isRunning) {
            runRegisterTask();
            runListenEvent();
        }
    }


    @Override
    public void onAccepted(ReactorChannel channel) {

    }

    @Override
    public void onRead(ReactorChannel channel) {

    }

    @Override
    public void onWritten(ReactorChannel channel) {

    }


    private void runRegisterTask() {
        Runnable registerTask;
        while ((registerTask = registerTasks.poll()) != null) {
            registerTask.run();
        }
    }

    private void runListenEvent() {
        try {
            selector.select(1000);
            handleEvents();
            selector.selectNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private NioReactorChannel toNioChannel(ReactorChannel channel) {
        if (channel instanceof NioReactorChannel) {
            return (NioReactorChannel) channel;
        }
        throw new IllegalArgumentException("NonBlockedReactorThread实现只能采用NioReactorChannel");
    }

    private void buildRegisterTask(SelectableChannel selectableChannel) {
        FutureTask<SelectionKey> registerTask = new FutureTask<>(() -> {
            SelectionKey selectionKey = selectableChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
            return selectionKey;
        });
        registerTasks.add(registerTask);
    }

    private void handleEvents() {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            iterator.remove();
            dispatchEvent(selectionKey);
        }
    }

    private void dispatchEvent(SelectionKey selectionKey) {
        if (selectionKey.isAcceptable() || selectionKey.isReadable() || selectionKey.isWritable()) {
            try {
                SelectableChannel selectableChannel = selectionKey.channel();
                selectableChannel.configureBlocking(false);
                NioReactorChannel nioReactorChannel = NioReactorChannel.ofChannel(selectableChannel);
                nioReactorChannel.setEventType(EventTypeFactory.getBy(selectionKey));
                dispatchEvent(nioReactorChannel);
                cancelListening(selectionKey, selectableChannel);
            } catch (IOException e) {
                selectionKey.cancel();
            }
        }
    }

    private void cancelListening(SelectionKey selectionKey, SelectableChannel selectableChannel) {
        if (!selectableChannel.isOpen()) {
            selectionKey.cancel();
        }
    }
}
