package com.pointlion.sys.mvc.admin.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.pointlion.sys.mvc.admin.login.SessionUtil;


@ServerEndpoint(value="/admin/friendchat/{friend}",configurator=GetHttpSessionConfigurator.class)
public class ChatForFriend {

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String , ChatForFriend> webSocketMap = new HashMap<String , ChatForFriend>();//所有登陆系统的用户都将会存放到set中,方便给特定用户推送消息
    //定义一个记录客户端的聊天昵称
//    private final String nickname;
//    public  ChatForFriend() {
//    	nickname = "当前用户";
//    }
    
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String me;
    /*
     *	使用@Onopen注解的表示当客户端链接成功后的回掉。参数Session是可选参数
     	这个Session是WebSocket规范中的会话，表示一次会话。并非HttpSession
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
    	HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.session = session;
        me = httpSession.getAttribute(SessionUtil.usernameKey).toString();//获取登录用户--"我"的用户id
    	
        webSocketMap.put(me , this);//系统登陆的时候,将"我"重新放入set
        
    }
    
    /*0
     *	使用@OnMessage注解的表示当客户端发送消息后的回掉，第一个参数表示用户发送的数据。参数Session是可选参数，与OnOpen方法中的session是一致的
     */
    @OnMessage
    public void onMessage(@PathParam("friend") String friend,String message,Session session) throws IOException{
    	ChatForFriend o = webSocketMap.get(friend);//目标"朋友"的对象
    	if(o!=null){//给朋友推送在线消息
    		o.session.getBasicRemote().sendText(message);
    	}
    	//聊天信息入库,记录聊天消息未读
    	
    }
    
	/*
	*	用户断开链接后的回调，注意这个方法必须是客户端调用了断开链接方法后才会回调
	*/
    @OnClose
    public void onClose() {
    	webSocketMap.remove(me);
    }
    
    //	对用户的消息可以做一些过滤请求，如屏蔽关键字等等。。。
    public static String filter(String message){
        if(message==null){
            return null;
        }
        return message;
    }

}
