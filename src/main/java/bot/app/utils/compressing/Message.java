package bot.app.utils.compressing;

import java.io.IOException;
import java.io.Serializable;

public class Message implements Serializable{
    private String className;
    private String data;

    public Message() {}

    public <T> Message(T originalObject) throws IOException {
        this.className = originalObject.getClass().getName();
        this.data = StringSerialization.toString(originalObject);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static <T> String compose(T object) throws IOException {
        Message m = new Message(object);
        return StringSerialization.toString(m);
    }

    public static Object decompose(String s) throws IOException, ClassNotFoundException {
        Message m = (Message) StringSerialization.fromString(s, Message.class);
        return StringSerialization.fromString(m.data, Class.forName(m.className));
    }
}
