# Lab 3 - Connect the Dots Revisited

## Benchmarking

| Picture | dots | Indexed ArrayList | Indexed LinkedList | Iterated ArrayList | Iterated LinkedList |
|---------|------|-------------------|--------------------|--------------------|---------------------|
| Balloon | 100  | 00:00:00:110      | 00:00:32:523       | 00:00:00:080       | 00:00:00:118        |
| Balloon | 1000 | 00:00:00:141      | 00:00:32:663       | 00:00:00:118       | 00:00:00:090        |
| Skull   | 9000 | 00:00:00:158      | 00:02:23:101       | 00:00:00:093       | 00:00:00:118        |

## Time Analysis

* `removeDots()` when using **ArrayList** with **n-1** desired dots
   * O(n): `removeDots()` is a for loop nested in a while loop, but since the contents of the while loop only execute once so practically it doesn't exist.
* `removeDots()` when using **ArrayList** with **3** desired dots
   * O(n^2): `removeDots()` is a for loop inside a while loop. The code inside the for loop will execute (n-3)^2 times making the time complexity n^2.
* `removeDots()` when using **LinkedList** with **n−1** desired dots
   * O(n^2): Same reasoning as the ArrayList version but since LinkedList's search functionality requires linear time the time complexity will be n^2 rather than just n.
* `removeDots()` when using **LinkedList** with **3** desired dots
   * O(n^2): Same reasoning as the ArrayList version