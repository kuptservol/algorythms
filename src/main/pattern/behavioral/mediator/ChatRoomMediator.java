package pattern.behavioral.mediator;

import java.util.Scanner;

/**
 * @author Sergey Kuptsov
 * @since 21/05/2016
 */
public class ChatRoomMediator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("print username");

        User user = new User(scanner.next());
        System.out.println("now you can start chating");

        ChatRoom chatRoom = new ChatRoom();

        while (scanner.hasNextLine()) {
            chatRoom.sendMessage(user, scanner.nextLine());
        }
    }

    private static class User {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static class ChatRoom {
        public void sendMessage(User user, String message) {
            System.out.println(user.getName() + ":" + message);
        }
    }
}
