package ckj.chenkaiju;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import net.sf.json.JSONObject;

public class ExchangerClient {

	static String reaRemotHost="39.106.34.156";
	
	static int reaRemotlPort=8089;
	
	static String imgHost="ckj";
	
	static String imgHostPass="ckj";
	
	static String realLocalAddress="127.0.0.1:8080";
	
	static String realLocalHost="127.0.0.1";
	
	static int realLocalPort=8080;
	
	static int i=0;
	
	public static void main(String[] args) {
		try {
			SocketChannel channel = SocketChannel.open();
			channel.connect(new InetSocketAddress(reaRemotHost,reaRemotlPort));
			channel.configureBlocking(false);
			while(channel.isConnected()){
				JSONObject protol=new JSONObject();
				protol.put("imgHost", imgHost);
				protol.put("imgHostPass", imgHostPass);
				String protolstr=protol.toString();
				ByteBuffer buf=ByteBuffer.allocate(1024);
				buf.put(protolstr.getBytes());
				buf.flip();
				while(buf.hasRemaining()){
					channel.write(buf);
				}
				StringBuilder builder=new StringBuilder();
				ByteBuffer resbuf=ByteBuffer.allocate(1024);
				resbuf.clear();
				int relen=-1;
				boolean iswrite=false;
				while(true){
					while((relen=channel.read(resbuf))>0){
						resbuf.flip();
						while(resbuf.hasRemaining()){
							builder.append((char)resbuf.get());
						}
						iswrite=true;
						resbuf.clear();
					}
					if(iswrite){
						break;
					}
				}
				String res=builder.toString();
				System.out.println(res);
				JSONObject re=JSONObject.fromObject(res);
				if(re.optBoolean("result")){
					while(true){
						try{
							StringBuilder bui=new StringBuilder();
							ByteBuffer bf=ByteBuffer.allocate(1024);
							bf.clear();
							while(channel.read(bf)>0){
								bf.flip();
								while(bf.hasRemaining()){
									bui.append((char)bf.get());
								}
								bf.clear();
							}
							String req=bui.toString();
							if(!req.equals("")){
								i++;
								System.out.println("服务器新数据到达"+i);
								System.out.println(req);
								SocketChannel deschannel = SocketChannel.open();
								deschannel.connect(new InetSocketAddress(realLocalHost,realLocalPort));
								ByteBuffer desbf=ByteBuffer.allocate(1024);
								desbf.clear();
								desbf.put(req.getBytes());
								desbf.flip();
								while(desbf.hasRemaining()){
									deschannel.write(desbf);
								}
								deschannel.shutdownOutput();
								desbf.clear();
								while(deschannel.read(desbf)>0){
									desbf.flip();
									while(desbf.hasRemaining()){
										channel.write(desbf);
									}
									desbf.clear();
								}
								System.out.println("回复服务器新数据完毕");
							}
							
						}catch(Exception e){
							e.printStackTrace();
							break;
							
						}
					}
				}
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
