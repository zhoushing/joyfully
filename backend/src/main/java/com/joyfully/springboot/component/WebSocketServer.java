package com.joyfully.springboot.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网络套接字服务器
 *
 * @author marx
 * @date 2021/11/11
 */
@ServerEndpoint(value = "/imserver/{nickName}")
@Component
public class WebSocketServer {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger("chatRoomLogger");

    /**
     * 会话映射 Map
     */
    public static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 连接成功的方法
     *
     * @param session  会话
     * @param nickName 用户昵称
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickName") String nickName) {
        SESSION_MAP.put(nickName, session);
        logger.info("有新用户加入, nickName={}, 当前在线人数为：{}", nickName, SESSION_MAP.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (String key : SESSION_MAP.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("nickName", key);
            /* {"nickName", "zhang", "nickName": "admin"}*/
            array.add(jsonObject);
        }
        /*{"users":[{"nickName": "zhang"},{"nickName": "admin"}]}*/
        // 后台发送消息给所有的客户端
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    /**
     * 连接关闭调用的方法
     *
     * @param session  会话
     * @param nickName 用户昵称
     */
    @OnClose
    public void onClose(Session session, @PathParam("nickName") String nickName) {
        SESSION_MAP.remove(nickName);
        logger.info("一个连接关闭, 移除username={}的用户 session, 当前在线人数为：{}", nickName, SESSION_MAP.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickName") String nickName) {
        logger.info("服务端收到用户 nickName={}的消息：{}", nickName, message);
        JSONObject object = JSONUtil.parseObj(message);

        // to表示发送给哪个用户，比如 admin
        String toUsername = object.getStr("to");
        // 发送的消息文本，比如 hello
        String text = object.getStr("text");
        // 例如  {"to": "admin", "text": "聊天文本"}

        // 根据 to用户名来获取 session，再通过session发送消息文本
        Session toSession = SESSION_MAP.get(toUsername);

        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}

            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", nickName);
            jsonObject.set("text", text);
            this.sendMessage(jsonObject.toString(), toSession);
            logger.info("发送给用户 nickName={}, 消息：{}", toUsername, jsonObject.toString());
        }
        else {
            logger.info("发送失败, 未找到用户 nickName={}的session", toUsername);
        }
    }

    /**
     * 发生错误时
     *
     * @param session 会话
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 服务端发送消息给客户端
     *
     * @param message   消息
     * @param toSession 会话
     */
    private void sendMessage(String message, Session toSession) {
        try {
            logger.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            logger.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     *
     * @param message 消息
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : SESSION_MAP.values()) {
                logger.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            logger.error("服务端发送消息给客户端失败", e);
        }
    }

}
