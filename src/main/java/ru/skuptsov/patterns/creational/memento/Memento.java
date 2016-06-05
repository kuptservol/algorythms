package ru.skuptsov.patterns.creational.memento;

import org.joda.time.DateTime;

import java.util.TreeMap;

import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

/**
 * @author Sergey Kuptsov
 * @since 05/06/2016
 */
public class Memento {

    public static void main(String[] args) {
        EditorOriginator editorOriginator = new EditorOriginator();

        editorOriginator.saveText("Text_original");

        System.out.println(editorOriginator.getText());

        SnapshotOwner snapshotOwner = new SnapshotOwner();

        snapshotOwner.addSnapshot(now(UTC), editorOriginator.makeSnapshot());

        editorOriginator.saveText("Text_changed");

        System.out.println(editorOriginator.getText());

        editorOriginator.undoToSnapshot(snapshotOwner.undo());

        System.out.println(editorOriginator.getText());
    }

    private static final class EditorMemento {
        private String text;

        public EditorMemento(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private final static class EditorOriginator {

        private String text;

        public String getText() {
            return text;
        }

        public void saveText(String text) {
            this.text = text;
        }

        public EditorMemento makeSnapshot() {
            return new EditorMemento(text);
        }

        public void undoToSnapshot(EditorMemento editorMemento) {
            this.text = editorMemento.text;
        }
    }

    private final static class SnapshotOwner {
        private TreeMap<DateTime, EditorMemento> snapshots = new TreeMap<>();

        public void addSnapshot(DateTime time, EditorMemento editorMemento) {
            snapshots.put(time, editorMemento);
        }

        public EditorMemento get(DateTime dateTime) {
            return snapshots.get(dateTime);
        }

        public EditorMemento undo() {
            return snapshots.floorEntry(now(UTC)).getValue();
        }
    }
}
