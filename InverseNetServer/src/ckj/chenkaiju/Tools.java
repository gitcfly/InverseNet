package ckj.chenkaiju;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Tools {
	
	
	public static boolean transformTo(SocketChannel from,SocketChannel to){
		
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.clear();
			int len=-1;
			boolean iswrite=false;
			try {
				while(true){
					while((len=from.read(buf))>0){
						buf.flip();
						while(buf.hasRemaining()){
							to.write(buf);
						}
						buf.clear();
						iswrite=true;
					}
					if(iswrite)
						break;
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		
		
	}
	
	public static String getHost(String requestStr){
		String host="";
		if(!requestStr.equals("")){
			String[] request=requestStr.split("\r\n");
			for(int i=0;i<request.length;i++){
				String line=request[i];
				if(line.startsWith("GET")||line.startsWith("POST")){
					line=line.substring(line.indexOf("/"));
					String deshost=line.substring(line.indexOf("/")+1,line.indexOf(" HTTP"));
					if(deshost.indexOf("/")>0){
						deshost=deshost.substring(0,deshost.indexOf("/"));
					}
					host=deshost;
				}
			}
		}
		return host;
		
	}
	
	public static String getRequestStr(SocketChannel client){
		String requestStr="";
		StringBuilder bu=new StringBuilder();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.clear();
		int len=-1;
		boolean iswrite=false;
		try {
			while(true){
				while((len=client.read(buf))>0){
					buf.flip();
					while(buf.hasRemaining()){
						bu.append((char)buf.get());
					}
					buf.clear();
					iswrite=true;
				}
				if(iswrite){
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		requestStr=bu.toString();
		return requestStr;
		
	}
	

}
