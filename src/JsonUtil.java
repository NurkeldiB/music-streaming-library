import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static byte[] utf8(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static String songToJson(SongDto dto) {
        return """
                {"id":%d,"title":"%s","duration":%d,"artist":{"id":%d,"name":"%s","genre":"%s"}}
                """.formatted(
                dto.id(),
                escape(dto.title()),
                dto.duration(),
                dto.artistId(),
                escape(dto.artistName()),
                escape(dto.artistGenre())
        ).trim();
    }

    // Очень простой парсер JSON-объекта вида {"title":"x","duration":123,"artistId":1}
    // Без вложенных объектов и массивов — для учебного REST достаточно.
    public static Map<String, String> parseFlatObject(String json) {
        HashMap<String, String> map = new HashMap<>();
        if (json == null) return map;

        String s = json.trim();
        if (s.startsWith("{")) s = s.substring(1);
        if (s.endsWith("}")) s = s.substring(0, s.length() - 1);

        // split по запятым верхнего уровня (тут без вложенности — ок)
        String[] parts = s.split(",");
        for (String p : parts) {
            String part = p.trim();
            if (part.isEmpty()) continue;
            String[] kv = part.split(":", 2);
            if (kv.length != 2) continue;

            String key = stripQuotes(kv[0].trim());
            String val = kv[1].trim();
            val = stripQuotes(val);
            map.put(key, val);
        }
        return map;
    }

    private static String stripQuotes(String s) {
        String t = s.trim();
        if (t.startsWith("\"") && t.endsWith("\"") && t.length() >= 2) {
            return t.substring(1, t.length() - 1);
        }
        return t;
    }
}
