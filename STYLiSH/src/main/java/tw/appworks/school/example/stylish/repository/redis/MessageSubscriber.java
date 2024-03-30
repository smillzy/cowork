package tw.appworks.school.example.stylish.repository.redis;

public interface MessageSubscriber {

    void receiveMessage(String message);

}
