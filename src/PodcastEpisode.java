import java.util.Objects;

public class PodcastEpisode extends MediaItem {

    private String channelName;

    public PodcastEpisode(String title, int duration, String channelName) {
        super(title, duration);
        setChannelName(channelName);
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        if (channelName == null || channelName.isBlank()) {
            throw new IllegalArgumentException("channelName must not be blank");
        }
        this.channelName = channelName.trim();
    }

    @Override
    public String getInfo() {
        return "Podcast: " + getTitle() + " [" + channelName + "], " + getDuration() + " sec";
    }

    @Override
    public String toString() {
        return "PodcastEpisode{title='" + getTitle() + "', channelName='" + channelName + "', duration=" + getDuration() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PodcastEpisode)) return false;
        PodcastEpisode that = (PodcastEpisode) o;
        return Objects.equals(getTitle().toLowerCase(), that.getTitle().toLowerCase())
                && Objects.equals(channelName.toLowerCase(), that.channelName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle().toLowerCase(), channelName.toLowerCase());
    }
}