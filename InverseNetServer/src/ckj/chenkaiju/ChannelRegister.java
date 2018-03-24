package ckj.chenkaiju;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class ChannelRegister extends Thread{
	
	public static HashMap<String,SocketChannel> servers=new HashMap<String,SocketChannel>();
	
	
	@Override
	public void run() {
		try {
			ServerSocketChannel serChnnel=ServerSocketChannel.open();
			serChnnel.bind(new InetSocketAddress(8089));
			System.out.println("启动注册服务器");
			while(true){
				try{
					SocketChannel clientChnnel=serChnnel.accept();
					clientChnnel.configureBlocking(false);
					while(true){
						StringBuilder bu=new StringBuilder();
						ByteBuffer buf = ByteBuffer.allocate(1024);
						buf.clear();
						int len=-1;
						while((len=clientChnnel.read(buf))>0){
							buf.flip();
							while(buf.hasRemaining()){
								bu.append((char)buf.get());
							}
							buf.clear();
						}
						String js=bu.toString();
						if(!js.equals("")){
							JSONObject protocal=JSONObject.fromObject(js);
							String imgHost=protocal.optString("imgHost");
							String imgHostPass=protocal.optString("imgHostPass");
							JSONObject result=new JSONObject();
							result.put("result",true);
							String res=result.toString();
							ByteBuffer rebuf = ByteBuffer.allocate(1024);
							rebuf.clear();
							rebuf.put(res.getBytes());
							rebuf.flip();
							while(rebuf.hasRemaining()){
								clientChnnel.write(rebuf);
							}
							servers.put(imgHost,clientChnnel);
							System.out.println("回复完毕");
							break;
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
