import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.*;

public class RateLimiter {

    private final int maxRequests;
    private final Duration durationWindow;
    private final ConcurrentHashMap<String, Deque<Instant>> requests = new ConcurrentHashMap<>();

    public RateLimiter(int maxRequests, Duration durationWindow) {
        this.maxRequests = maxRequests;
        this.durationWindow = durationWindow;
    }

    public boolean allowRequest(String userId) {
        Instant now = Instant.now();
        Instant windowStart = now.minus(durationWindow);
        requests.putIfAbsent(userId, new ArrayDeque<>());
        Deque<Instant> timestamps = requests.computeIfAbsent(userId, k -> new ArrayDeque<>());

        synchronized (timestamps) {
            while ((!timestamps.isEmpty()) && (!timestamps.peek().isAfter(windowStart))) {
                timestamps.poll();
            }

            if (timestamps.size() >= maxRequests) {
                return false;
            }

            timestamps.add(now);
            return true;
        }
}
}