Blocking Queue for Producer and Consumer
========================================

Design a blocking queue with a list to support Producer and Consumer design pattern where one thread inserts elements into BlockingQueue and other thread consumes it. BlockingQueue will block if either it is full or empty. BlockingQueue.take() will block if queue is empty and BlockingQueue.put() method will block if queue is full.

This problem was part of a coding challenge that I had to take when I interviewed at a company for an internship position on 5/15/2014.

The solution uses a double-ended link-list with synchronized blocks in the put() and take() methods along with wait() and notifyAll() methods to provide the implementation of a blocking queue as described by the problem statement.
