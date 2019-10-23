package puzzler.interview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class UserActivityInWindow {
    private final Map<Integer, Integer> userToWindowEventsCount = new HashMap<>();
    private final LinkedList<UserTimedEvent> timeUserEvents = new LinkedList<>();

    private final Map<Integer, Integer> countToUsersCount = new HashMap<>();

    public void registerEvent(int userId, int time) {
        timeUserEvents.addLast(new UserTimedEvent(time, userId));
        userToWindowEventsCount.compute(userId, (user, count) -> {
            if (count == null) {
                return 1;
            } else {
                return count + 1;
            }
        });

        int currentUserCount = userToWindowEventsCount.get(userId);
        updateCountForUser(currentUserCount, currentUserCount - 1);

        clearOutdated(time);
    }

    private void updateCountForUser(int to, int from) {
        countToUsersCount.compute(from, (count, urs) -> {
            if (urs == null) {
                return 1;
            } else {
                return urs + 1;
            }
        });

        if (to > 0) {
            if (countToUsersCount.containsKey(to)) {
                Integer prevCount = countToUsersCount.get(to);
                if (prevCount == 1) {
                    countToUsersCount.remove(to);
                } else {
                    countToUsersCount.put(to, prevCount - 1);
                }
            } else {
                countToUsersCount.put(to, 1);
            }
        }
    }

    public int query(int userId, int time) {
        clearOutdated(time);

        return userToWindowEventsCount.getOrDefault(userId, 0);
    }

    int queryByCount(int count, int time) {
        if (count <= 0) {
            return 0;
        }

        clearOutdated(time);

        return countToUsersCount.getOrDefault(count, 0);
    }

    private void clearOutdated(int to) {
        to = to - 300;
        UserTimedEvent firstUserTimedEvent = timeUserEvents.peekFirst();
        int current = firstUserTimedEvent == null ? Integer.MAX_VALUE : firstUserTimedEvent.time;
        while (current <= to) {
            UserTimedEvent next = timeUserEvents.pollFirst();
            int userId = next.userId;
            int currentUserCount = userToWindowEventsCount.get(userId);
            userToWindowEventsCount.compute(userId, (usr, count) -> count - 1);
            if (userToWindowEventsCount.get(userId) == 0) {
                userToWindowEventsCount.remove(userId);
            }
            updateCountForUser(currentUserCount-1, currentUserCount);
            firstUserTimedEvent = timeUserEvents.peekFirst();
            current = firstUserTimedEvent == null ? Integer.MAX_VALUE : firstUserTimedEvent.time;
        }
        // delete users with 0 events
    }

    private final class UserTimedEvent {
        private final int time;
        private final int userId;

        private UserTimedEvent(int time, int userId) {
            this.time = time;
            this.userId = userId;
        }
    }

    public static void main(String[] args) {
        UserActivityInWindow activity = new UserActivityInWindow();

        assertEquals(activity.query(1, 0), 0);

        activity.registerEvent(1, 0);
        assertEquals(activity.queryByCount(1, 1), 1);
        activity.registerEvent(1, 1);
        assertEquals(activity.queryByCount(1, 1), 0);
        assertEquals(activity.queryByCount(2, 1), 1);
        activity.registerEvent(1, 1);
        activity.registerEvent(3, 1);
        activity.registerEvent(2, 10);
        assertEquals(activity.queryByCount(1, 2), 2);
        assertEquals(activity.queryByCount(3, 2), 1);

        assertEquals(activity.query(1, 50), 3);
        assertEquals(activity.query(2, 50), 1);
        assertEquals(activity.query(3, 50), 1);
        assertEquals(activity.queryByCount(1, 50), 2);

        assertEquals(activity.query(1, 305), 0);
        assertEquals(activity.query(3, 305), 0);
        assertEquals(activity.query(2, 305), 1);
        assertEquals(activity.queryByCount(1, 50), 2);

        activity.registerEvent(2, 600);
        assertEquals(activity.query(2, 601), 1);
        assertEquals(activity.queryByCount(1, 601), 1);

        assertEquals(activity.query(2, 1000000), 0);
        assertEquals(activity.queryByCount(1, 1000000), 0);
    }
}
