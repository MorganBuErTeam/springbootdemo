package com.test.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.Getter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Title: NettyTCPClient.java
 * Description: tcp/ip通讯基本类
 * @author ying.chen
 * @date 2019年1月21日
 */
public class NettyTCPClient {

	private ChannelFuture channelFuture = null;
	private SocketChannel socketChannel = null;//Socket
	//	private String readResult;// 读取的结果容器
	private int connCount;// 连接次数
	private Date stopTime;// 断开时间
	private Date connTime;// 连接时间
	private String state;//小车状态
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	private String result;

	public String getResult(){
		return result;
	}
	public void setResult(String write){
		result=write;
	}


	public String getState() {
		return state;
	}

	public int getConnCount() {
		return connCount;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public Date getConnTime() {
		return connTime;
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}


	static Logger logger = LoggerFactory.getLogger(NettyTCPClient.class);
	private String serverIP;//ip
	private int port;//端口

	public NettyTCPClient(String serverIP,int port) {
		super();
		this.serverIP = serverIP;
		this.port = port;
		startRead();//建立连接  TODO tcp/ip建立连接
	}

	/**
	 *
	 * Title: startRead
	 * Description:读取小车的实时信息
	 */
	public void startRead(){
		Thread read = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						connect();
						state = "已连接";
						logger.info("Netty客户端已连接");
					} catch (Exception e) {
//						workerGroup.shutdownGracefully();
						state = "未连接";
						logger.debug("小车:" + serverIP + "重新连接。。。" );
					}finally {
						try {
							TimeUnit.MILLISECONDS.sleep(1000);
						} catch (InterruptedException e) {
						}//延迟1秒
					}
				}
			}
		},"小车:" + serverIP + "的Netty客户端");
		read.start();
	}

	/**
	 *
	 * Title: connect
	 * Description:  建立连接
	 * @throws Exception
	 */
	public void connect() throws Exception {
		Bootstrap bootstrap = new Bootstrap();//netty的组件容器，用于把其他各个部分连接起来；如果是TCP的Server端，则为ServerBootstrap
		bootstrap.group(workerGroup)//配置各组件
				.channel(NioSocketChannel.class)
				.remoteAddress(serverIP, port)
				.handler(new ChildChannelHandler());
		channelFuture = bootstrap.connect().sync();//开启监听
		socketChannel = (SocketChannel) channelFuture.channel();
		channelFuture.channel().closeFuture().sync();
	}

	public void write(byte[] bs){
		socketChannel.writeAndFlush(bs);
	}

	/**
	 * 编码器
	 */
	public class RpcEncoder extends MessageToByteEncoder {

		@Override
		public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
			out.writeBytes((byte[])in);
			ReferenceCountUtil.release(in);
		}
	}

	public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(final SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new RpcEncoder()).addLast(new ClientRequestResponseHander());
		}
	}

	/**
	 *
	 * Title: convertByteBufToString
	 * Description: 将数组转换成字符串
	 * @param buf ByteBuf
	 * @return String
	 */
	private String convertByteBufToString(ByteBuf buf) {
		String str = null;
		if(buf.hasArray()) { // 处理堆缓冲区
			str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
		} else { // 处理直接缓冲区以及复合缓冲区
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(), bytes);//new String(bytes, CharsetUtil.UTF_8)
			str = new String(bytes,CharsetUtil.UTF_8);
		}
		return str;
	}


	/**
	 * 读取数据
	 */
	public class ClientRequestResponseHander extends ChannelHandlerAdapter {

		/**
		 * 读数据调用
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			try {
//        		 ByteBuf buffer = (ByteBuf) msg;
//                 readResult = convertByteBufToString(buffer);
//                 logger.info(convertByteBufToString(buffer));

				ByteBuf byteBuf = (ByteBuf) msg;
				byte[] bytes = new byte[byteBuf.readableBytes()];
				byteBuf.readBytes(bytes);
				String message = new String(bytes,"utf-8");
				logger.info("客户端接受的消息: " + message);
				result = message;
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			logger.error("小车:" + serverIP + "服务端断开。。。" );
			super.channelInactive(ctx);
		}

		/**
		 * 异常调用
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.error("小车:" + serverIP + "发生异常了···异常信息:" + cause.getMessage());
		}
	}
}
