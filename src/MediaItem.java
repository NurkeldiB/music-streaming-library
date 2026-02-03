public abstract class MediaItem {

    private String title;
    private int duration;

    public MediaItem(String title, int duration) {
        setTitle(title);
        setDuration(duration);
    }

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        this.title = title.trim();
    }

    public int getDuration() {
        return duration;
    }

    public final void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be > 0");
        }
        this.duration = duration;
    }

    public abstract String getInfo();
}