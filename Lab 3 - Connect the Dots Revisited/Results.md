# Lab 3 - Connect the Dots Revisited

## Benchmarking

| Picture | dots | Indexed ArrayList | Indexed LinkedList | Iterated ArrayList | Iterated LinkedList |
|---------|------|-------------------|--------------------|--------------------|---------------------|
| Balloon | 100  | 00:00:00:110      | 00:00:32:523       | 00:00:00:080       | 00:00:00:118        |
| Balloon | 1000 | 00:00:00:141      | 00:00:32:663       | 00:00:00:118       | 00:00:00:090        |
| Skull   | 9000 | 00:00:00:158      | 00:02:23:101       | 00:00:00:093       | 00:00:00:118        |

## Time Analysis

* `removeDots()` when using **ArrayList** with **n-1** desired dots
   * $O(n^2)$: `removeDots()` is a for loop nested in a while loop.
* `removeDots()` when using **ArrayList** with **3** desired dots
   * $O(n)$: Some Reasoning
* `removeDots()` when using **LinkedList** with **n−1** desired dots
   * $O(n)$: Some Reasoning
* `removeDots()` when using **LinkedList** with **3** desired dots
   * $O(n)$: Some Reasoning