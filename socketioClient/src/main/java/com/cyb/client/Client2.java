package com.cyb.client;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
//https://github.com/socketio/socket.io-client-java
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月16日
 */
public class Client2 {
	public static void main(String[] args) throws URISyntaxException {
		 for(int i=0;i<1000;i++){
			 createClient(i);
		 }
	    }
	public static void createClient(final int i) throws URISyntaxException{
		IO.Options options = new IO.Options();
        options.forceNew = true;

        final OkHttpClient client = new OkHttpClient();
        options.webSocketFactory = client;
        options.callFactory = client;

        final Socket socket = IO.socket("http://192.168.16.211:6677", options);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            public void call(Object... args) {
                System.out.println("connect sucess!");
                socket.emit("login", "client"+i);//成功发送消息
                
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            public void call(Object... args) {
                System.out.println("client"+i+"disconnect 2");
                socket.close();
                socket.disconnect();
            }
        });
        socket.io().on(io.socket.engineio.client.Socket.EVENT_CLOSE, new Emitter.Listener() {
            public void call(Object... args) {
                System.out.println("client"+i+"engine close 1");
                client.dispatcher().executorService().shutdown();
                socket.close();
                socket.disconnect();
            }
        });
        socket.on("loginBack", new Emitter.Listener() {
            public void call(Object... args) {
                System.out.println("client"+i+"登录结果 "+args[0]);
            }
        });
        socket.on("pushpoint", new Emitter.Listener() {
            public void call(Object... args) {
                System.out.println("client"+i+" 获取坐标点 "+args[0]);
            }
        });
        socket.connect();
        socket.open();
	}
}
