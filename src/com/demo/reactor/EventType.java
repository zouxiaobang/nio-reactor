package com.demo.reactor;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public enum EventType {
    /**
     * 暂时只给出“接收就绪、读取就绪、写就绪”3个事件
     */
    ACCEPT,
    READ,
    WRITE
}
