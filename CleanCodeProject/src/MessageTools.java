import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

/**
 * Created by andrejka on 16.2.16.
 */
public class MessageTools {
    private ArrayList<Message> allMessages = new ArrayList<Message>();

    public void addMessage() {
        Scanner scanner = new Scanner(System.in);
        String authorAdd = scanner.nextLine();
        String messageAdd = scanner.nextLine();
        allMessages.add(new Message(UUID.randomUUID().toString(), messageAdd, authorAdd, System.currentTimeMillis()));
    }

    public void toFile(String fileName) throws IOException {
        Writer writer = new FileWriter(fileName);
        Gson gson = new GsonBuilder().create();
        gson.toJson(allMessages, writer);
        writer.close();
    }

    public void loadFromFile(String fileName) throws IOException {
        allMessages.clear();
        Reader reader = new InputStreamReader(new FileInputStream(fileName));
        Gson gson = new GsonBuilder().create();
        Message[] messageAdd = gson.fromJson(reader, Message[].class);
        for (Message i : messageAdd) {
            allMessages.add(i);
        }
        reader.close();
    }

    public void watchAll() {
        ArrayList<Message> messagesCopy = new ArrayList<>(allMessages.size());
        for (Message i : allMessages) {
            messagesCopy.add(i);
        }
        Collections.sort(messagesCopy);
        for (Message i : messagesCopy) {
            System.out.println(i.toString());
        }
    }

    public void removeById(String id) {
        for (Message i : allMessages) {
            if (i.getId().equals(id)) {
                allMessages.remove(i);
                break;
            }
        }
    }

    public ArrayList<Message> searchAuthor(String author) {
        ArrayList<Message> properAuthor = new ArrayList<>();
        for (Message i : allMessages) {
            if (i.getAuthor().equals(author)) {
                properAuthor.add(i);
            }
        }
        return properAuthor;
    }

    public ArrayList<Message> searchExpression(String expression) {
        ArrayList<Message> properExpression = new ArrayList<>();
        for (Message i : allMessages) {
            if (i.getMessage().contains(expression)) {
                properExpression.add(i);
            }
        }
        return properExpression;
    }

    public ArrayList<Message> searchRegExpression(String regExpression) {
        ArrayList<Message> properRegExpression = new ArrayList<>();
        for (Message i : allMessages) {
            if (i.getMessage().matches(regExpression)) {
                properRegExpression.add(i);
            }
        }
        return properRegExpression;
    }

    public ArrayList<Message> historySendingTime(Date lowerDate, Date upperDate) {
        ArrayList<Message> properTimestamp = new ArrayList<>();
        for (Message i : allMessages) {
            if ((i.getDate().after(lowerDate)) && (i.getDate().before(upperDate))) {
                properTimestamp.add(i);
            }
        }
        return properTimestamp;
    }

}
