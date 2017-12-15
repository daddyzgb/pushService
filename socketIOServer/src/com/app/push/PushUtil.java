package com.app.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class PushUtil {
	public  static List<SocketIOClient> clients = new ArrayList<SocketIOClient>();
	public static SocketIOServer server;//保证单例模式
	public static boolean isStarted = false;
	public static void startPushServer() {
		try {
			Configuration config = new Configuration();
			config.setHostname("192.168.16.211");//ComputerUtil.getRealIP();172.17.162.26 192.168.0.151 g
			config.setPort(6677);
			System.out.println("http://"+"192.168.16.211"+"/"+"6677");
			server = new SocketIOServer(config);
			PushListener listener = new PushListener(server);
			server.addEventListener("getmsg", Object.class, listener);
			server.addConnectListener(new ConnectListener() {
				public void onConnect(SocketIOClient client) {
					System.out.println("new sessionid=" + client.getSessionId());
					clients.add(client);
				}
			});
			server.addDisconnectListener(new DisconnectListener() {
				@Override
				public void onDisconnect(SocketIOClient client) {
					clients.remove(client);
				}
			});
			server.start();
			
			Timer timer = new Timer();  
	        timer.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                Random random = new Random();  
	                String data = "{\"x\":" +random.nextInt(100)+ ",\"y\":" +random.nextInt(100)+ "}";  
	                /*BASE64Encoder encoder = new BASE64Encoder();  
	                data = encoder.encode(data.getBytes());  */
	                System.out.println("push data...");
	                for(SocketIOClient client : clients) { 
	                    client.sendEvent("pushpoint",data);  
	                }  
	            }  
	        }, 1000, 5000);
			System.out.println("puser server started!");
		    Object object = new Object();
		    synchronized (object) {
		      object.wait();
		    }
		} catch (Exception e) {
			System.out.println("push server start ocurr exception!\n"+e.toString());
		}
	}
	public static void main(String[] args) {
		/*Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            @Override  
            public void run() {  
                Random random = new Random();  
                String data = "{\"x\":" +random.nextInt(100)+ ",\"y\":" +random.nextInt(100)+ "}";  
                BASE64Encoder encoder = new BASE64Encoder();  
                data = encoder.encode(data.getBytes());  
                for(IOClient client : clients) {  
                    client.send(formatMessage(data));  
                }  
            }  
        }, 1000, 1000); */ 
		startPushServer();
	}
}
