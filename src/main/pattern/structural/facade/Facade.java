package pattern.structural.facade;

/**
 * Created by Сергей on 20.04.2016.
 */
public class Facade {

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacadeImpl(new Db(), new Cache());

        User user = new User();
        userFacade.createUser(user);
        userFacade.deleteUser(user);
    }

    private interface UserFacade {
        User createUser(User user);

        void deleteUser(User user);
    }

    private final static class UserFacadeImpl implements UserFacade {

        private final Db db;
        private final Cache cache;

        private UserFacadeImpl(Db db, Cache cache) {
            this.db = db;
            this.cache = cache;
        }

        @Override
        public User createUser(User user) {
            User createdUser = db.save(user);
            cache.persist(user);
            return createdUser;
        }

        @Override
        public void deleteUser(User user) {
            db.remove(user);
            cache.invalidate(user);
        }
    }

    private final static class Db {


        public User save(User user)
        {
            System.out.println("Save user");
            return user;
        }

        public void remove(User user) {
            System.out.println("remove" + user);
        }
    }

    private final static class Cache {

        public void invalidate(User user) {
            System.out.println("invalidate" + user);
        }

        public void persist(User user) {
            System.out.println("persist" + user);
        }
    }

    private final static class User {}
}
