package ckj.chenkaiju;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ExchangerServers {
	
	public static void main(String[] args) {
		try {
			ManagerJM mm=new ManagerJM("服务器运行中");
			ChannelRegister register=new ChannelRegister();
			register.start();
			Selector selector=Selector.open();
			ServerSocketChannel channel=ServerSocketChannel.open();
			channel.bind(new InetSocketAddress("127.0.0.1",8088));
			System.out.print("启动http服务器");
			while(true){
				try{
					SocketChannel client=channel.accept();
					ClientThread cclient=new ClientThread(client);
					cclient.start();
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
			System.exit(0);
		} catch (IOException e) {
			
			e.printStackTrace();
			System.exit(0);
		}
	}
}
