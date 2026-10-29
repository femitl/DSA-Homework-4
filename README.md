# DSA-Homework-4
Part A: Big-O table
<img width="470" height="552" alt="Screenshot 2026-10-29 at 3 27 19 AM" src="https://github.com/user-attachments/assets/beae38da-b219-4682-ae12-e08dfa1a40c8" />



Part B: Defend two choices

1. Library (HashMap from ID to playlist node)

I chose a HashMap to store each song's ID and its corresponding node in the playlist. This lets me find a song directly without searching through the entire playlist. Since I store the node itself, I can also access its prev and next pointers, which makes finding and removing songs O(1). It also makes getting the next song in the playlist efficient.

If I used a regular array or list, I would have to search through the elements to find a song, which would take O(n) time. Removing an element from an array could also require shifting the remaining elements. I decided to use Java's built-in HashMap instead of implementing my own because it already handles hashing and collisions, and its operations take O(1) time on average. Although performance can be worse in certain cases, a HashMap is a good choice for quickly accessing songs by ID.

2. Up Next (ArrayDeque)

I chose an ArrayDeque to store songs in the Up Next queue because I need to add songs to both the front and the back and remove the next song from the front. ArrayDeque supports these operations efficiently, taking O(1) amortized time.

Using a regular array would be less efficient because adding or removing elements at the front would require shifting the other elements, which takes O(n) time. I could have implemented my own deque using a linked list, but using Java's built-in ArrayDeque saves time and avoids having to write and debug extra code. Since ArrayDeque uses a resizable array, some operations can take longer when the array needs to grow, but the average cost remains O(1) amortized.

One design decision I made was to have removeSong remove the song from the library without searching through the Up Next queue. Instead, if that song is still in the queue, next() skips it when it reaches the front. This avoids having to search through the queue during removal and keeps removeSong at O(1).

