package java_.db.mongo;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.bson.types.ObjectId;

/**
 * @author Sergey Kuptsov
 */
@Builder
@ToString
public class TestObject {
    @Id
    public ObjectId id;
    public String description;
    public int count;

    public TestObject() {
    }

    public TestObject(ObjectId id, String description, int count) {
        this.id = id;
        this.description = description;
        this.count = count;
    }

    public TestObject(String description, int count) {
        this.description = description;
        this.count = count;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", count=" + count +
                '}';
    }
}
