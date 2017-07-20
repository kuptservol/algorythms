package java_.db.mongo;

import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.testng.annotations.Test;

public class MongoDbConnectorTest {

    @Test
    public void test() {
        MongoClient mongoClient = new MongoClient();

        MongoDatabase database = mongoClient.getDatabase("java_");

        System.out.println(database);

        MongoCollection<Document> testCollection = database.getCollection("TestCollection");

        System.out.println(testCollection);

        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));


        testCollection.insertOne(doc);

        System.out.println(testCollection.count());
    }

    @Test
    public void mongojack() {
        MongoClient mongoClient = new MongoClient();
        DB testDB = mongoClient.getDB("testDB");
        DBCollection testObjectCollection = testDB.getCollection("testObject");

        JacksonDBCollection<TestObject, ObjectId> coll = JacksonDBCollection.wrap(testObjectCollection,
                TestObject.class,
                ObjectId.class);

        TestObject testObject = new TestObject("description", 1);

        WriteResult<TestObject, ObjectId> result = coll.insert(testObject);
        ObjectId id = result.getSavedId();
        TestObject savedObject = coll.findOneById(id);

        System.out.println(savedObject);
    }
}
