package ru.skuptsov.puzzlers.job.interview.zeptoteam;

import com.google.common.util.concurrent.Striped;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

import static com.google.common.util.concurrent.Striped.lock;

/**
 * @author Sergey Kuptsov
 * @since 19/12/2016
 */
public class ChatServer {
    private static final int DEFAULT_PORT = 8080;
    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        ChatServer chatServer = new ChatServer(args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_PORT);
        chatServer.run();
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChatServerInitializer())
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            server.bind(port).sync()
                    .channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private final static class ChatService {
        private static final int MAX_LOCKS_NUMBER = 256;
        private final static Striped<Lock> userLocksPool = lock(MAX_LOCKS_NUMBER);
        private final static Striped<Lock> chatLocksPool = lock(MAX_LOCKS_NUMBER);
        private final static Map<String, User> users = new ConcurrentHashMap<>();
        private final static Map<String, Chat> chats = new ConcurrentHashMap<>();
        private final static Map<ChannelId, Chat> channelToChat = new ConcurrentHashMap<>();
        private final static Map<ChannelId, User> channelToUser = new ConcurrentHashMap<>();
        private final static Map<ChannelId, ChannelId> registeredUserChannels = new ConcurrentHashMap<>();
        private static final int MAX_GROUP_MEMBERS = 2;

        public static String connectUser(String username, String password, ChannelId channelId) {
            System.out.println("Connecting user [" + username + "]");
            String result;

            if (registeredUserChannels.get(channelId) != null) {
                result = "User already registered";
            } else {
                Lock userChannelLock = userLocksPool.get(username);
                while (true) {
                    try {
                        if (userChannelLock.tryLock()) {
                            User user;
                            if ((user = users.get(username)) != null) {
                                if (user.isPasswordValid(password)) {
                                    registeredUserChannels.put(channelId, channelId);
                                    channelToUser.put(channelId, user);
                                    result = "User login success";
                                } else {
                                    result = "User password invalid";
                                }
                            } else {
                                user = new User(username, password);
                                users.put(username, user);
                                registeredUserChannels.put(channelId, channelId);
                                channelToUser.put(channelId, user);
                                result = "New user created";
                            }
                            break;
                        }
                    } finally {
                        userChannelLock.unlock();
                    }
                }
            }

            return result;
        }

        public static String addUserToChat(Channel channel, String chatName) {
            ChannelId channelId = channel.id();
            System.out.println("Connecting user  with channel id [" + channelId + "] to chatName [" + chatName + "]");

            String result = "";
            Chat existingUserChat = channelToChat.get(channelId);
            // todo : lock on  existingUserChat == null check
            if (existingUserChat == null) {
                Lock chatLock = chatLocksPool.get(chatName);
                while (true) {
                    try {
                        if (chatLock.tryLock()) {
                            Chat chat = chats.get(chatName);
                            if (chat == null) {
                                chat = new Chat(chatName);
                                chats.put(chatName, chat);
                            }
                            if (chat.members.size() >= MAX_GROUP_MEMBERS) {
                                result = "Group has maximum numbers of users";
                            } else {
                                chat.members.add(channel);
                                channelToChat.put(channelId, chat);
                                result = "Connected";
                            }
                        }
                        break;
                    } finally {
                        chatLock.unlock();
                    }
                }
            } else {
                if (existingUserChat.chatName.equals(chatName)) {
                    return "Already connected";
                }
                Lock chatLockTarget = chatLocksPool.get(chatName);
                Lock chatLockExisting = chatLocksPool.get(existingUserChat.chatName);
                while (true) {
                    try {
                        if (chatLockTarget.tryLock() && chatLockExisting.tryLock()) {
                            Chat chat = chats.get(chatName);
                            if (chat == null) {
                                chat = new Chat(chatName);
                                chats.put(chatName, new Chat(chatName));
                            }
                            if (chat.members.size() >= MAX_GROUP_MEMBERS) {
                                result = "Group has maximum number of users";
                            } else {
                                chat.members.add(channel);
                                existingUserChat.members.remove(channel);
                                channelToChat.put(channelId, chat);
                                result = "Connected";
                            }
                        }
                        break;
                    } finally {
                        chatLockTarget.unlock();
                        chatLockExisting.unlock();
                    }
                }
            }

            return result;
        }

        public static String sendMessageToChat(ChannelId channelId, String message) {
            System.out.println("Send message with channel id [" + channelId + "]");

            if (registeredUserChannels.get(channelId) == null) {
                return "User not registered";
            }

            Chat chat;
            if ((chat = channelToChat.get(channelId)) != null) {
                for (Channel channel : chat.members) {
                    if (channel.isActive()) {
                        channel.writeAndFlush(message);
                    }
                }
            } else {
                return "User not connected to any chats";
            }

            return "";
        }

        public static String disconnectUser(Channel channel) {
            ChannelId channelId = channel.id();
            System.out.println("Disconnecting user with channel id [" + channelId + "]");

            if (registeredUserChannels.get(channelId) == null) {
                return "User not registered";
            }

            registeredUserChannels.remove(channelId);

            Lock userLock = userLocksPool.get(channelToUser.get(channelId).username);
            userLock.lock();

            Chat chat = channelToChat.get(channelId);
            if (chat != null) {
                chat.members.remove(channel);
            }
            channelToChat.remove(channelId);

            userLock.unlock();

            return "OK";
        }

        private final static class Chat {
            String chatName;
            List<Channel> members;

            public Chat(String chatName) {
                this.chatName = chatName;
                members = new CopyOnWriteArrayList<>();
            }
        }

        private static class User {
            String username;
            String passwordHash;

            public User(String username) {
                this.username = username;
            }

            public User(String username, String password) {
                this.username = username;
                this.passwordHash = toHash(password);
            }

            boolean isPasswordValid(String password) {
                return toHash(password).equals(passwordHash);
            }

            private String toHash(String password) {
                return password;
            }
        }
    }

    @ChannelHandler.Sharable
    private final static class ChatServerHandler extends SimpleChannelInboundHandler<String> {

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(ChatService.disconnectUser(ctx.channel()));
        }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            String response = "";
            if (msg.charAt(0) == '/') {
                String[] split = msg.split(" ");
                switch (split[0]) {
                    case "/connect":
                        response = ChatService.connectUser(split[1], split[2], ctx.channel().id());
                        break;
                    case "/chat":
                        response = ChatService.addUserToChat(ctx.channel(), split[1]);
                        break;
                    case "/disconnect":
                        response = ChatService.disconnectUser(ctx.channel());
                        break;
                    default:
                        response = "Incorrect message";
                        break;
                }
            } else {
                response = ChatService.sendMessageToChat(ctx.channel().id(), msg);
            }

            ctx.writeAndFlush(response);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    private static class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
        private static final int MAX_LENGTH = 100;
        private static final StringDecoder STRING_DECODER = new StringDecoder(CharsetUtil.UTF_8);
        private static final StringEncoder STRING_ENCODER = new StringEncoder(CharsetUtil.UTF_8);
        private final static ChatServerHandler CHAT_SERVER_HANDLER = new ChatServerHandler();

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline channelPipeline = ch.pipeline();
            channelPipeline.addLast("frameDecoder", new LineBasedFrameDecoder(MAX_LENGTH));
            channelPipeline.addLast("stringDecoder", STRING_DECODER);
            channelPipeline.addLast("stringEncoder", STRING_ENCODER);
            channelPipeline.addLast("chatServerHandler", CHAT_SERVER_HANDLER);
        }

    }
}