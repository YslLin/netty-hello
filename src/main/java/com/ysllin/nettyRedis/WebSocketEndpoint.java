package com.ysllin.nettyRedis;

import com.ysllin.nettyRedis.utils.SpringContextHolder;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketEndpoint {

    /**
     * 路径标识：目前使用token来代表
     */
    public static final String IDENTIFIER = "identifier";
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("*** WebSocket opened from sessionId " + session.getId() + " , identifier = " + identifier);
        connect(identifier, session);
    }

    public void connect(String identifier, Session session) {
        try {
            if (StringUtil.isNullOrEmpty(identifier)) {
                return;
            }

            WebSocketManager websocketManager = getWebSocketManager();

            //像刷新这种，id一样，session不一样，后面的覆盖前面的
            websocketManager.put(identifier, session);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected WebSocketManager getWebSocketManager() {
        return SpringContextHolder.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }
}
