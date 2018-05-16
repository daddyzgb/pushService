package com.app.push;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class LoginListener implements DataListener<Object>{
  SocketIOServer server;
  public LoginListener(SocketIOServer server){
  	this.server = server;
  }
  public void onData(SocketIOClient client, Object action, AckRequest req)  {
	  try{
		System.out.println("请求登录"+action+",client:"+client);
		client.sendEvent("loginBack", "登录成功！");
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
}