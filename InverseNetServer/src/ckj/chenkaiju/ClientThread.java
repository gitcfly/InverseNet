package ckj.chenkaiju;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientThread extends Thread{
	SocketChannel client;
	
	public ClientThread(SocketChannel client){
		this.client=client;
	}
	@Override
	public void run() {
		try{
			client.configureBlocking(false);
			String requestStr=Tools.getRequestStr(client);
	    	String host=Tools.getHost(requestStr);
	    	if(!host.equals("")){
	    		System.out.println("原始请求：\n"+requestStr);
	    		if(requestStr.indexOf("/"+host+"/")>0){
		    		requestStr=requestStr.replaceFirst("/"+host+"/", "/");
		    	}if((requestStr.indexOf("/"+host+"/")<0)&&(requestStr.indexOf("/"+host)>0)){
		    		requestStr=requestStr.replaceFirst("/"+host, "/");
		    	}
		    	System.out.println("变换请求：\n"+requestStr);
	    		SocketChannel server=ChannelRegister.servers.get(host);
	    		if(server!=null){
	    			ByteBuffer bu=ByteBuffer.allocate(1024);
	    			bu.put(requestStr.getBytes());
	    			bu.flip();
	    			while(bu.hasRemaining()){
	    				server.write(bu);
	    			}
	    			Tools.transformTo(server, client);
	    			client.shutdownOutput();
	    			client.close();
	    			System.out.println("给浏览器发送数据完毕");
	    		}else{
	    			String res="您访问的资源不存在。。。";
		    		ByteBuffer bu=ByteBuffer.allocate(1024);
					bu.put(res.getBytes());
					bu.flip();
					while(bu.hasRemaining()){
						client.write(bu);
					}
					client.shutdownOutput();
					client.close();
					System.out.println("给浏览器发送数据完毕,您访问的资源不存在。。。");
	    		}
	    	}else{
	    		String res="您访问的资源不存在。。。";
	    		ByteBuffer bu=ByteBuffer.allocate(1024);
				bu.put(res.getBytes());
				bu.flip();
				while(bu.hasRemaining()){
					client.write(bu);
				}
				client.shutdownOutput();
				client.close();
				System.out.println("给浏览器发送数据完毕,您访问的资源不存在。。。");
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

}
