# Counting Inversions - Easy Guide

## What is an Inversion?
An **inversion** is when a larger number appears before a smaller number in an array.
- For indices `i < j`, if `arr[i] > arr[j]`, then `(i,j)` is an inversion pair
- It measures how "unsorted" an array is

**Example:**
```
arr = [2, 4, 1, 3, 5]
Inversions: (2,1), (4,1), (4,3)
Total count = 3
```

## Why Use Merge Sort?
- **Naive approach**: Check every pair → O(n²) time
- **Smart approach**: Use divide & conquer with merge sort → O(n log n) time

## Key Insight
When merging two sorted halves:
- If `left[i] > right[j]`, then **all remaining elements** in left half are also greater than `right[j]`
- This gives us `(mid - i + 1)` inversions at once!

## Complete Pseudocode Solution

```python
function countInversions(arr):
    temp = array of size len(arr)
    return mergeSortAndCount(arr, temp, 0, len(arr) - 1)

function mergeSortAndCount(arr, temp, left, right):
    inv_count = 0
    
    if left < right:
        mid = left + (right - left) // 2
        
        # Count inversions in left half
        inv_count += mergeSortAndCount(arr, temp, left, mid)
        
        # Count inversions in right half
        inv_count += mergeSortAndCount(arr, temp, mid + 1, right)
        
        # Count cross inversions and merge
        inv_count += mergeAndCount(arr, temp, left, mid, right)
    
    return inv_count

function mergeAndCount(arr, temp, left, mid, right):
    # Copy elements to temp array
    for i from left to right:
        temp[i] = arr[i]
    
    i = left        # Left half pointer
    j = mid + 1     # Right half pointer
    k = left        # Merged array pointer
    inv_count = 0
    
    # Merge and count inversions
    while i <= mid AND j <= right:
        if temp[i] <= temp[j]:
            arr[k] = temp[i]
            i = i + 1
        else:
            # temp[i] > temp[j] - This is an inversion!
            # All elements from i to mid are greater than temp[j]
            arr[k] = temp[j]
            j = j + 1
            inv_count += (mid - i + 1)  # KEY LINE!
        k = k + 1
    
    # Copy remaining elements from left half
    while i <= mid:
        arr[k] = temp[i]
        i = i + 1
        k = k + 1
    
    # Copy remaining elements from right half
    while j <= right:
        arr[k] = temp[j]
        j = j + 1
        k = k + 1
    
    return inv_count

# Test the solution
arr = [2, 4, 1, 3, 5]
result = countInversions(arr)
print("Number of inversions:", result)  # Output: 3
```

## Step-by-Step Example
**Array: [2, 4, 1, 3, 5]**

### Step 1: Divide
```
[2, 4, 1, 3, 5]
    ↙        ↘
[2, 4, 1]    [3, 5]
```

### Step 2: Process Left Half [2, 4, 1]
```
[2, 4, 1]
   ↙    ↘
[2, 4]  [1]

Merge [2, 4] and [1]:
- Compare 2 vs 1: 2 > 1 → inversions += (1-0+1) = 2
- Pairs found: (2,1), (4,1)
- Result: [1, 2, 4]
```

### Step 3: Process Right Half [3, 5]
```
[3, 5] - already sorted, 0 inversions
```

### Step 4: Merge Everything
```
Merge [1, 2, 4] and [3, 5]:
- Compare 1 vs 3: 1 ≤ 3 → no inversion
- Compare 2 vs 3: 2 ≤ 3 → no inversion  
- Compare 4 vs 3: 4 > 3 → inversions += (2-2+1) = 1
- Pair found: (4,3)
- Final result: [1, 2, 3, 4, 5]
```

**Total inversions: 2 + 0 + 1 = 3**

## The Magic Line Explained
```python
inv_count += (mid - i + 1)
```

When `temp[i] > temp[j]`:
- `i` is current position in left half
- `mid` is end of left half
- All elements from position `i` to `mid` are greater than `temp[j]`
- So we get `(mid - i + 1)` inversions in one shot!

## Visualize
![Visual](https://github.com/Mdsadmansakib/Design-and-Analysis-of-Algorithms/blob/main/Algorithm/Divide-and-Conquer/CI.png) 

## Complexity
- **Time**: O(n log n) - same as merge sort
- **Space**: O(n) - for temporary array

## Key Takeaways
1. Use divide and conquer approach
2. Count inversions while merging sorted halves
3. The key insight: when left > right, all remaining left elements form inversions
4. Much faster than checking every pair individually
