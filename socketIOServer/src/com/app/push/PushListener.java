package com.app.push;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class PushListener implements DataListener<Object>{
  SocketIOServer server;
  public PushListener(SocketIOServer server){
  	this.server = server;
  }
  public void onData(SocketIOClient client, Object action, AckRequest req)  {
	  try{
		System.out.println(" "+action+",client:"+client);
		
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
}