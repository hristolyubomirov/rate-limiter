# Java Rate Limiter (Sliding Window Log)

## How It Works

This rate limiter uses **Sliding Window** algorithm:
1. A user makes a request -> the exact time (`Instant.now()`) is recorded in a double-ended queue (`Deque`) as a timestamp.
2. Before allowing a new request, the system calculates a `windowStart` that is used to determine which timestamps should be polled (expired) (Current Time minus the Allowed Duration).
3. It iterates through the user's queue and drops the timestamps that occurred before the (`windowStart`)  time.
4. If the size of the remaining queue is less than the maximum allowed requests - declared through (`maxRequests`) variable , the new request is added to the log and approved.
5. If the size of the queue is more than or equal to the maximum allowed requests, the request is dropped (returns false)


<img width="1082" height="534" alt="image" src="https://github.com/user-attachments/assets/601a8435-980c-4107-a76b-7242b490b6e1" />


## Example:
```java
    RateLimiter limiter = new RateLimiter(3, Duration.ofSeconds(10));
    System.out.println(limiter.allowRequest("user1")); // true
    System.out.println(limiter.allowRequest("user1")); // true
    System.out.println(limiter.allowRequest("user1")); // true
    System.out.println(limiter.allowRequest("user1")); // false

// After 10 seconds
    Thread.sleep(10000);
    System.out.println(limiter.allowRequest("user1")); // true

