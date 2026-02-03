import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RestServer {

    public static void start(Connection con, int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/songs", ex -> handleSongs(con, ex));

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server: " + e.getMessage(), e);
        }
    }

    private static void handleSongs(Connection con, HttpExchange ex) throws IOException {
        try {
            String method = ex.getRequestMethod().toUpperCase();
            URI uri = ex.getRequestURI();

            Long id = queryLong(uri, "id");

            if ("GET".equals(method)) {
                if (id == null) {
                    List<SongDto> all = Db.getAllSongs(con);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    for (int i = 0; i < all.size(); i++) {
                        if (i > 0) sb.append(",");
                        sb.append(JsonUtil.songToJson(all.get(i)));
                    }
                    sb.append("]");
                    sendJson(ex, 200, sb.toString());
                } else {
                    SongDto dto = Db.getSongById(con, id);
                    if (dto == null) {
                        sendJson(ex, 404, "{\"error\":\"song not found\"}");
                    } else {
                        sendJson(ex, 200, JsonUtil.songToJson(dto));
                    }
                }
                return;
            }

            if ("POST".equals(method)) {
                String body = readBody(ex);
                Map<String, String> obj = JsonUtil.parseFlatObject(body);

                String title = obj.getOrDefault("title", "").trim();
                int duration = Integer.parseInt(obj.getOrDefault("duration", "0"));
                long artistId = Long.parseLong(obj.getOrDefault("artistId", "0"));

                long newId = Db.insertSong(con, title, duration, artistId);
                SongDto created = Db.getSongById(con, newId);
                sendJson(ex, 201, JsonUtil.songToJson(created));
                return;
            }

            if ("PUT".equals(method)) {
                if (id == null) {
                    sendJson(ex, 400, "{\"error\":\"missing id query param\"}");
                    return;
                }

                String body = readBody(ex);
                Map<String, String> obj = JsonUtil.parseFlatObject(body);

                String title = obj.getOrDefault("title", "").trim();
                int duration = Integer.parseInt(obj.getOrDefault("duration", "0"));
                long artistId = Long.parseLong(obj.getOrDefault("artistId", "0"));

                boolean ok = Db.updateSong(con, id, title, duration, artistId);
                if (!ok) {
                    sendJson(ex, 404, "{\"error\":\"song not found\"}");
                } else {
                    sendJson(ex, 200, JsonUtil.songToJson(Db.getSongById(con, id)));
                }
                return;
            }

            if ("DELETE".equals(method)) {
                if (id == null) {
                    sendJson(ex, 400, "{\"error\":\"missing id query param\"}");
                    return;
                }
                boolean ok = Db.deleteSong(con, id);
                if (!ok) sendJson(ex, 404, "{\"error\":\"song not found\"}");
                else sendJson(ex, 200, "{\"status\":\"deleted\"}");
                return;
            }

            sendJson(ex, 405, "{\"error\":\"method not allowed\"}");

        } catch (NumberFormatException e) {
            sendJson(ex, 400, "{\"error\":\"bad number format\"}");
        } catch (SQLException e) {
            sendJson(ex, 500, "{\"error\":\"db error\"}");
        } catch (Exception e) {
            sendJson(ex, 500, "{\"error\":\"server error\"}");
        }
    }

    private static Long queryLong(URI uri, String key) {
        String q = uri.getRawQuery();
        if (q == null || q.isBlank()) return null;
        String[] parts = q.split("&");
        for (String p : parts) {
            String[] kv = p.split("=", 2);
            if (kv.length == 2 && kv[0].equals(key)) {
                try {
                    return Long.parseLong(kv[1]);
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }
        }
        return null;
    }

    private static String readBody(HttpExchange ex) throws IOException {
        try (InputStream is = ex.getRequestBody()) {
            byte[] bytes = is.readAllBytes();
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    private static void sendJson(HttpExchange ex, int status, String json) throws IOException {
        byte[] bytes = JsonUtil.utf8(json);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        ex.sendResponseHeaders(status, bytes.length);
        ex.getResponseBody().write(bytes);
        ex.close();
    }
}