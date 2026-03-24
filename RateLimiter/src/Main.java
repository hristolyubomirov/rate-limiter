import javax.management.timer.Timer;

void main(String[] args) throws InterruptedException {
    RateLimiter limiter = new RateLimiter(3, Duration.ofSeconds(10));
    System.out.println(limiter.allowRequest("user1")); // true
    System.out.println(limiter.allowRequest("user1")); // true
    System.out.println(limiter.allowRequest("user1")); // true
    System.out.println(limiter.allowRequest("user1")); // false

// After 10 seconds
    Thread.sleep(10000);
    System.out.println(limiter.allowRequest("user1")); // true
}