import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MusicLibrary {

    private final ArrayList<MediaItem> items = new ArrayList<>();

    public void addItem(MediaItem item) {
        if (item == null) throw new IllegalArgumentException("item must not be null");
        items.add(item);
    }

    public List<MediaItem> getItems() {
        return List.copyOf(items);
    }

    public List<MediaItem> searchByTitle(String keyword) {
        if (keyword == null) keyword = "";
        String k = keyword.toLowerCase();
        ArrayList<MediaItem> result = new ArrayList<>();
        for (MediaItem i : items) {
            if (i.getTitle().toLowerCase().contains(k)) result.add(i);
        }
        return result;
    }

    public List<MediaItem> filterByMinDuration(int minSeconds) {
        ArrayList<MediaItem> result = new ArrayList<>();
        for (MediaItem i : items) {
            if (i.getDuration() >= minSeconds) result.add(i);
        }
        return result;
    }

    public void sortByDuration() {
        items.sort(Comparator.comparingInt(MediaItem::getDuration));
    }

    public void printAll() {
        for (MediaItem i : items) {
            System.out.println("  " + i.getInfo()); // полиморфизм: вызов реализуется по фактическому типу
        }
    }
}
