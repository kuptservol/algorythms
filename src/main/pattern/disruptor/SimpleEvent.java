package pattern.disruptor;

import com.google.common.base.Objects;

/**
 * @author Sergey Kuptsov
 * @since 19/04/2016
 */
public class SimpleEvent {

    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}
