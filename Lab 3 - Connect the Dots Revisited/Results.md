# Benchmark Results

## Time for [balloon.dot](http://msoe.us/taylor/cs2852/balloon.dot) with **100* desired dots

```
Indexed ArrayList:   00:00:00:146
Indexed LinkedList:  00:00:48:398
Iterated ArrayList:  00:00:00:131
Iterated LinkedList: 00:00:00:208
```

## Time for [balloon.dot](http://msoe.us/taylor/cs2852/balloon.dot) with **1000* desired dots

```
Indexed ArrayList:   00:00:00:173
Indexed LinkedList:  00:00:47:823
Iterated ArrayList:  00:00:00:172
Iterated LinkedList: 00:00:00:126
```

## Time for [skull.dot](http://msoe.us/taylor/cs2852/skull.dot) with **9000* desired dots

```
Indexed ArrayList:   00:00:00:218
Indexed LinkedList:  00:05:04:035
Iterated ArrayList:  00:00:00:241
Iterated LinkedList: 00:00:00:459
```

# Asymptotic Time Complexity Analysis for `removeDots()`

## O( n ) &mdash; when using `ArrayList` with **n-1** desired dots

Justification: `removeDots()` is a for loop nested in a while loop, but since the contents of the while loop only execute once so practically it doesn't exist.

## O( n^2 ) &mdash; when using `ArrayList` with **3** desired dots

Justification: `removeDots()` is a for loop inside a while loop. The code inside the for loop will execute (n-3)^2 times making the time complexity n^2.

## O( n^2 ) &mdash; when using `LinkedList` with **n-1** desired dots

Justification: Same reasoning as the ArrayList version but since LinkedList's search functionality requires linear time the time complexity will be n^2 rather than just n.

## O( n^2 ) &mdash; when using `LinkedList` with **3** desired dots

Justification: Same reasoning as the ArrayList version