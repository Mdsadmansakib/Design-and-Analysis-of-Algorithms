# Complete Guide to Greedy Algorithms


## Introduction

Greedy algorithms are a class of algorithms that make the locally optimal choice at each step, hoping to find a global optimum. The fundamental principle is to choose the best option available at each moment without considering future consequences.

**Think of it like this:** Imagine you're climbing a mountain in dense fog. At each step, you can only see a few feet ahead, so you always choose the path that goes upward the most. This is the greedy approach - always make the best immediate choice.

**Key Characteristics:**
- **Local Optimization**: Make the best choice at each step
- **No Backtracking**: Never reconsider previous choices
- **Efficiency**: Often simple and fast to implement
- **Not Always Optimal**: Don't always guarantee the best overall solution
- **Pattern Recognition**: Work well for problems with specific structures

**When Greedy Works:**
- When local optimal choices lead to global optimal solutions
- Problems with the "greedy choice property"
- When you need fast, approximate solutions
- Optimization problems with optimal substructure

---

## Easy Problems on Greedy Algorithm

### 1. Fractional Knapsack Problem
**Problem Statement:** You have a knapsack of capacity W and items with weights and values. You can take fractions of items. Maximize the total value.

**Real-World Example:** You're a gold miner with a bag that holds 10kg. You find gold nuggets of different sizes and purities. You want to maximize the value of gold you take.

**Greedy Strategy:** Always pick the item with the highest value-to-weight ratio first. If you can't fit the whole item, take as much as possible.

**Why This Works:** Items with higher value/weight ratios give you more "bang for your buck" in terms of knapsack space.

**Step-by-Step Example:**
- Items: {(weight=2, value=10), (weight=3, value=5), (weight=5, value=15), (weight=7, value=7)}
- Ratios: {5, 1.67, 3, 1}
- Knapsack capacity: 8

**Detailed Pseudocode:**
```
ALGORITHM FractionalKnapsack(items[], capacity):
    // Step 1: Calculate value-to-weight ratio for each item
    FOR each item i in items:
        item[i].ratio = item[i].value / item[i].weight
    
    // Step 2: Sort items by ratio in descending order
    SORT items by ratio in descending order
    
    // Step 3: Greedily pick items
    total_value = 0
    remaining_capacity = capacity
    
    FOR each item in sorted_items:
        IF remaining_capacity >= item.weight:
            // Take the whole item
            total_value = total_value + item.value
            remaining_capacity = remaining_capacity - item.weight
        ELSE:
            // Take fraction of the item
            fraction = remaining_capacity / item.weight
            total_value = total_value + (item.value * fraction)
            remaining_capacity = 0
            BREAK  // Knapsack is full
    
    RETURN total_value
```

### 2. Minimum Rotations for Circular Lock
**Problem Statement:** You have a circular lock with digits 0-9. To change from one digit to another, you can rotate clockwise or counterclockwise. Find the minimum rotations needed.

**Real-World Example:** Think of a combination lock on a bike. To go from 3 to 7, you can go clockwise (3→4→5→6→7 = 4 moves) or counterclockwise (3→2→1→0→9→8→7 = 6 moves). Choose the shorter path.

**Greedy Strategy:** For each digit position, calculate both clockwise and counterclockwise distances, then choose the minimum.

**Why This Works:** Since the lock is circular, there are only two possible paths between any two digits. The greedy choice is always the shorter path.

**Detailed Pseudocode:**
```
ALGORITHM MinimumRotations(current[], target[]):
    total_rotations = 0
    
    FOR i = 0 to length(current) - 1:
        // Calculate direct difference
        direct_diff = absolute_value(current[i] - target[i])
        
        // Calculate circular difference (going the other way around)
        circular_diff = 10 - direct_diff
        
        // Choose the minimum of the two
        min_rotations = minimum(direct_diff, circular_diff)
        
        total_rotations = total_rotations + min_rotations
        
        PRINT "Position", i, ": from", current[i], "to", target[i], 
              "requires", min_rotations, "rotations"
    
    RETURN total_rotations
```

**Example Walkthrough:**
- Current: [1, 5, 3], Target: [8, 2, 7]
- Position 0: 1→8, direct=7, circular=3, choose 3
- Position 1: 5→2, direct=3, circular=7, choose 3  
- Position 2: 3→7, direct=4, circular=6, choose 4
- Total: 3+3+4 = 10 rotations

### 3. Maximum Composite Numbers to Make N
**Problem Statement:** Find the maximum number of composite numbers (numbers ≥ 4 that aren't prime) that sum to N.

**Real-World Example:** You want to break down a number into as many "building blocks" as possible, where each block must be a composite number.

**Greedy Strategy:** Use the smallest composite number (4) as much as possible to maximize the count.

**Why This Works:** Since 4 is the smallest composite number, using it maximizes the number of terms in the sum.

**Detailed Pseudocode:**
```
ALGORITHM MaxCompositeNumbers(n):
    // Handle impossible cases
    IF n < 4:
        RETURN -1  // Cannot form any composite numbers
    
    // Special cases where we can't use only 4s
    IF n equals 5:
        RETURN -1  // 5 = 4 + 1, but 1 is not composite
    IF n equals 7:
        RETURN -1  // 7 = 4 + 3, but 3 is not composite
    
    // Handle cases where n mod 4 leaves remainder
    IF n mod 4 equals 0:
        RETURN n / 4  // Use all 4s
    ELSE IF n mod 4 equals 1:
        // n = 4k + 1, use (k-2) fours and two 6s
        // Example: 9 = 4 + 4 + 1, but 1 is not composite
        // So: 9 = 6 + 3, but 3 is not composite
        // Better: 9 = 6 + 3, still doesn't work
        // Actually: 9 = 4 + 4 + 1 doesn't work, so use 9 = 6 + 3 doesn't work
        // We use: 9 = 4 + 4 + 1, we need 1 more, so 9 = 6 + 3 (not valid)
        // Correct approach: 9 = 4 + 4 + 1, we can't do this
        // Let's use 9 = 6 + 3, but 3 is prime
        // Better: 9 = 4 + 4 + 1 is impossible
        // So for n = 4k + 1, it's impossible when k < 2
        IF n < 9:
            RETURN -1
        ELSE:
            RETURN (n - 6) / 4 + 1  // Use one 6 and rest 4s
    ELSE IF n mod 4 equals 2:
        // n = 4k + 2, use (k-1) fours and one 6
        RETURN (n - 6) / 4 + 1
    ELSE: // n mod 4 equals 3
        // n = 4k + 3, use (k-2) fours and two 6s
        IF n < 15:
            RETURN -1
        ELSE:
            RETURN (n - 12) / 4 + 2  // Use two 6s and rest 4s
```

### 4. Smallest Subset with Sum Greater Than Rest
**Problem Statement:** Find the smallest subset of an array whose sum is greater than the sum of remaining elements.

**Real-World Example:** You have a bag of coins, and you want to take the fewest coins such that their total value is more than the coins left behind.

**Greedy Strategy:** Always take the largest remaining coin first. This minimizes the number of coins needed.

**Why This Works:** Larger elements contribute more to the subset sum, so we need fewer of them to exceed half the total sum.

**Detailed Pseudocode:**
```
ALGORITHM SmallestSubsetGreaterSum(array[]):
    // Step 1: Calculate total sum
    total_sum = 0
    FOR each element in array:
        total_sum = total_sum + element
    
    // Step 2: Sort array in descending order
    SORT array in descending order
    
    // Step 3: Greedily add largest elements
    subset_sum = 0
    count = 0
    
    FOR i = 0 to length(array) - 1:
        subset_sum = subset_sum + array[i]
        count = count + 1
        
        remaining_sum = total_sum - subset_sum
        
        PRINT "Added", array[i], "Subset sum:", subset_sum, 
              "Remaining sum:", remaining_sum
        
        IF subset_sum > remaining_sum:
            RETURN count
    
    RETURN count  // This shouldn't happen for valid input
```

**Example Walkthrough:**
- Array: [3, 1, 7, 9, 2, 5, 4], Total sum = 31
- Sorted: [9, 7, 5, 4, 3, 2, 1]
- Step 1: Add 9, subset_sum = 9, remaining = 22 (9 ≤ 22)
- Step 2: Add 7, subset_sum = 16, remaining = 15 (16 > 15) ✓
- Answer: 2 elements

### 5. Assign Cookies Problem
**Problem Statement:** You have children with different greed factors and cookies of different sizes. Assign cookies to maximize the number of satisfied children.

**Real-World Example:** You're a parent with different sized cookies trying to satisfy as many children as possible. Each child has a minimum cookie size requirement.

**Greedy Strategy:** Sort both children and cookies. Try to satisfy the least greedy child first with the smallest possible cookie.

**Why This Works:** By satisfying less greedy children first, we save larger cookies for more demanding children.

**Detailed Pseudocode:**
```
ALGORITHM AssignCookies(children_greed[], cookies_size[]):
    // Step 1: Sort both arrays
    SORT children_greed in ascending order
    SORT cookies_size in ascending order
    
    // Step 2: Use two pointers to match
    child_pointer = 0
    cookie_pointer = 0
    satisfied_children = 0
    
    WHILE child_pointer < length(children_greed) AND 
          cookie_pointer < length(cookies_size):
        
        current_child_greed = children_greed[child_pointer]
        current_cookie_size = cookies_size[cookie_pointer]
        
        IF current_cookie_size >= current_child_greed:
            // Cookie can satisfy this child
            satisfied_children = satisfied_children + 1
            child_pointer = child_pointer + 1
            PRINT "Child with greed", current_child_greed, 
                  "satisfied with cookie size", current_cookie_size
        
        // Move to next cookie regardless
        cookie_pointer = cookie_pointer + 1
    
    RETURN satisfied_children
```

**Example Walkthrough:**
- Children greed: [1, 2, 3], Cookies: [1, 1]
- Both sorted: [1, 2, 3] and [1, 1]
- Child 0 (greed=1) gets cookie 0 (size=1) ✓
- Child 1 (greed=2) cannot get cookie 1 (size=1) ✗
- Result: 1 satisfied child

### 6. Buy Maximum Stocks Problem
**Problem Statement:** On day i, you can buy at most i stocks at price[i]. Given a budget, maximize the number of stocks you can buy.

**Real-World Example:** A stock market with daily purchase limits. You want to buy as many stocks as possible within your budget.

**Greedy Strategy:** Always buy from the cheapest days first to maximize the total number of stocks.

**Why This Works:** Buying cheaper stocks first leaves more budget for additional purchases.

**Detailed Pseudocode:**
```
ALGORITHM BuyMaximumStocks(prices[], budget):
    // Step 1: Create array of (price, max_quantity) pairs
    stock_options = []
    FOR i = 0 to length(prices) - 1:
        day = i + 1  // Day 1, 2, 3, ...
        stock_options.append((prices[i], day))
    
    // Step 2: Sort by price in ascending order
    SORT stock_options by price in ascending order
    
    // Step 3: Greedily buy stocks
    total_stocks = 0
    remaining_budget = budget
    
    FOR each (price, max_quantity) in stock_options:
        // Calculate how many stocks we can buy
        affordable_quantity = remaining_budget / price
        stocks_to_buy = minimum(affordable_quantity, max_quantity)
        
        IF stocks_to_buy > 0:
            total_stocks = total_stocks + stocks_to_buy
            remaining_budget = remaining_budget - (stocks_to_buy * price)
            
            PRINT "Day", max_quantity, ": bought", stocks_to_buy, 
                  "stocks at price", price, "each"
    
    RETURN total_stocks
```

**Example Walkthrough:**
- Prices: [10, 7, 19], Budget: 45
- Stock options: [(10,1), (7,2), (19,3)]
- Sorted: [(7,2), (10,1), (19,3)]
- Buy 2 stocks at price 7 each: budget = 45-14 = 31
- Buy 1 stock at price 10: budget = 31-10 = 21
- Buy 1 stock at price 19: budget = 21-19 = 2
- Total: 2+1+1 = 4 stocks

### 7. Maximum Consecutive Difference Sum
**Problem Statement:** Rearrange an array to maximize the sum of absolute differences between consecutive elements.

**Real-World Example:** Arranging numbers on a circle to maximize the "roughness" or variation between adjacent numbers.

**Greedy Strategy:** Alternate between smallest and largest remaining elements to create maximum differences.

**Why This Works:** Placing extreme values next to each other creates the largest possible differences.

**Detailed Pseudocode:**
```
ALGORITHM MaxConsecutiveDiffSum(array[]):
    // Step 1: Sort the array
    SORT array in ascending order
    
    // Step 2: Rearrange alternating between extremes
    left_pointer = 0
    right_pointer = length(array) - 1
    rearranged = []
    take_from_right = TRUE
    
    WHILE left_pointer <= right_pointer:
        IF take_from_right:
            rearranged.append(array[right_pointer])
            right_pointer = right_pointer - 1
        ELSE:
            rearranged.append(array[left_pointer])
            left_pointer = left_pointer + 1
        
        take_from_right = NOT take_from_right
    
    // Step 3: Calculate sum of absolute differences
    total_diff = 0
    FOR i = 1 to length(rearranged) - 1:
        diff = absolute_value(rearranged[i] - rearranged[i-1])
        total_diff = total_diff + diff
        PRINT "Difference between", rearranged[i-1], "and", 
              rearranged[i], "is", diff
    
    RETURN total_diff
```

**Example Walkthrough:**
- Array: [4, 2, 1, 8]
- Sorted: [1, 2, 4, 8]
- Rearranged: [8, 1, 4, 2] (alternating max-min)
- Differences: |8-1|=7, |1-4|=3, |4-2|=2
- Total: 7+3+2 = 12

### 8. Minimum and Maximum Cost to Buy All Candies
**Problem Statement:** When you buy one candy, you get up to k candies free. Find minimum and maximum cost to buy all candies.

**Real-World Example:** A candy store offer: "Buy 1, get 2 free!" You want to find the cheapest and most expensive way to get all candies.

**Greedy Strategy:** 
- **Minimum cost**: Buy cheapest candies first (get expensive ones free)
- **Maximum cost**: Buy expensive candies first (get cheap ones free)

**Detailed Pseudocode:**
```
ALGORITHM MinMaxCandyCost(prices[], k):
    n = length(prices)
    
    // Calculate minimum cost
    SORT prices in ascending order
    min_cost = 0
    candies_bought = 0
    candies_needed = n
    
    WHILE candies_needed > 0:
        // Buy one candy
        min_cost = min_cost + prices[candies_bought]
        candies_bought = candies_bought + 1
        candies_needed = candies_needed - 1
        
        // Get up to k free candies
        free_candies = minimum(k, candies_needed)
        candies_needed = candies_needed - free_candies
        
        PRINT "Bought candy at price", prices[candies_bought-1], 
              "got", free_candies, "free candies"
    
    // Calculate maximum cost
    SORT prices in descending order
    max_cost = 0
    candies_bought = 0
    candies_needed = n
    
    WHILE candies_needed > 0:
        // Buy one candy
        max_cost = max_cost + prices[candies_bought]
        candies_bought = candies_bought + 1
        candies_needed = candies_needed - 1
        
        // Get up to k free candies
        free_candies = minimum(k, candies_needed)
        candies_needed = candies_needed - free_candies
    
    RETURN min_cost, max_cost
```

### 9. Minimum Currency Notes
**Problem Statement:** Given denominations [1, 2, 5, 10, 20, 50, 100, 500, 1000], find minimum number of notes to make amount N.

**Real-World Example:** An ATM dispensing cash with the fewest number of bills.

**Greedy Strategy:** Always use the largest denomination possible first.

**Why This Works:** Using larger denominations reduces the total count of notes needed.

**Detailed Pseudocode:**
```
ALGORITHM MinimumNotes(amount):
    denominations = [1000, 500, 100, 50, 20, 10, 5, 2, 1]
    notes_used = []
    total_notes = 0
    remaining_amount = amount
    
    FOR each denomination in denominations:
        IF remaining_amount >= denomination:
            // Calculate how many notes of this denomination we need
            notes_count = remaining_amount / denomination
            
            // Add this many notes to our result
            FOR i = 1 to notes_count:
                notes_used.append(denomination)
                total_notes = total_notes + 1
            
            // Update remaining amount
            remaining_amount = remaining_amount % denomination
            
            PRINT "Used", notes_count, "notes of denomination", denomination
        
        IF remaining_amount equals 0:
            BREAK
    
    RETURN notes_used, total_notes
```

**Example Walkthrough:**
- Amount: 1234
- Use 1 note of 1000: remaining = 234
- Use 0 notes of 500: remaining = 234  
- Use 2 notes of 100: remaining = 34
- Use 0 notes of 50: remaining = 34
- Use 1 note of 20: remaining = 14
- Use 1 note of 10: remaining = 4
- Use 0 notes of 5: remaining = 4
- Use 2 notes of 2: remaining = 0
- Total: 7 notes

### 10. Maximum Equal Sum of Three Stacks
**Problem Statement:** Remove elements from the top of three stacks to make their sums equal. Find the maximum possible equal sum.

**Real-World Example:** Three towers of coins where you can only remove from the top. Make all towers have the same total value.

**Greedy Strategy:** Always remove elements from the stack with the highest sum until all sums are equal.

**Why This Works:** We want to maximize the final sum, so we only remove from the tallest stack to bring it down to the others' level.

**Detailed Pseudocode:**
```
ALGORITHM MaxEqualSum(stack1[], stack2[], stack3[]):
    // Step 1: Calculate initial sums
    sum1 = sum_of_array(stack1)
    sum2 = sum_of_array(stack2)
    sum3 = sum_of_array(stack3)
    
    // Step 2: Create pointers for each stack (top elements)
    top1 = 0
    top2 = 0  
    top3 = 0
    
    // Step 3: Remove elements from tallest stack
    WHILE sum1 != sum2 OR sum2 != sum3 OR sum1 != sum3:
        // Find the maximum sum
        max_sum = maximum(sum1, sum2, sum3)
        
        IF sum1 equals max_sum:
            // Remove top element from stack1
            IF top1 < length(stack1):
                PRINT "Removing", stack1[top1], "from stack1"
                sum1 = sum1 - stack1[top1]
                top1 = top1 + 1
            ELSE:
                RETURN 0  // Stack1 is empty, impossible to equalize
        
        ELSE IF sum2 equals max_sum:
            // Remove top element from stack2
            IF top2 < length(stack2):
                PRINT "Removing", stack2[top2], "from stack2"
                sum2 = sum2 - stack2[top2]
                top2 = top2 + 1
            ELSE:
                RETURN 0  // Stack2 is empty, impossible to equalize
        
        ELSE: // sum3 equals max_sum
            // Remove top element from stack3
            IF top3 < length(stack3):
                PRINT "Removing", stack3[top3], "from stack3"
                sum3 = sum3 - stack3[top3]
                top3 = top3 + 1
            ELSE:
                RETURN 0  // Stack3 is empty, impossible to equalize
    
    RETURN sum1  // All sums are equal now
```

**Example Walkthrough:**
- Stack1: [3, 2, 1, 1, 1], sum = 8
- Stack2: [4, 3, 2], sum = 9  
- Stack3: [1, 1, 4, 1], sum = 7
- Remove 4 from stack2: sum = 5
- Remove 3 from stack1: sum = 5
- Remove 1,1 from stack3: sum = 5
- Equal sum: 5

---

## Medium Problems on Greedy Algorithm

### 1. Activity Selection Problem
**Problem Statement:** Given activities with start and end times, select the maximum number of activities that don't overlap.

**Real-World Example:** You're scheduling meetings in a conference room. Each meeting has a start and end time. You want to fit as many meetings as possible without conflicts.

**Greedy Strategy:** Always select the activity that ends earliest among the remaining activities. This leaves the most room for future activities.

**Why This Works:** By finishing activities early, we maximize the time available for subsequent activities. Activities that end later "block" more future possibilities.

**Detailed Pseudocode:**
```
ALGORITHM ActivitySelection(activities[]):
    // Step 1: Sort activities by end time
    SORT activities by end_time in ascending order
    
    // Step 2: Select activities greedily
    selected_activities = []
    last_end_time = -infinity
    
    FOR each activity in sorted_activities:
        current_start = activity.start_time
        current_end = activity.end_time
        
        // Check if this activity starts after the last selected activity ends
        IF current_start >= last_end_time:
            selected_activities.append(activity)
            last_end_time = current_end
            
            PRINT "Selected activity: Start =", current_start, 
                  "End =", current_end
        ELSE:
            PRINT "Skipped activity: Start =", current_start, 
                  "End =", current_end, "(conflicts with previous)"
    
    RETURN selected_activities
```

**Example Walkthrough:**
- Activities: [(1,3), (2,5), (4,6), (6,7), (5,8), (8,9)]
- Sorted by end time: [(1,3), (2,5), (4,6), (6,7), (5,8), (8,9)]
- Select (1,3): last_end = 3
- Skip (2,5): starts at 2 < 3
- Select (4,6): starts at 4 ≥ 3, last_end = 6
- Select (6,7): starts at 6 ≥ 6, last_end = 7
- Skip (5,8): starts at 5 < 7
- Select (8,9): starts at 8 ≥ 7
- Result: 4 activities selected

### 2. Jump Game Problem
**Problem Statement:** Given an array where each element represents the maximum jump length from that position, determine if you can reach the last index.

**Real-World Example:** You're playing a board game where each square tells you the maximum number of steps you can take from that position. Can you reach the end?

**Greedy Strategy:** Keep track of the furthest position you can reach at each step. If you can always reach the current position, continue; otherwise, it's impossible.

**Why This Works:** We don't need to find the optimal path, just determine if ANY path exists. Tracking the maximum reachable position is sufficient.

**Detailed Pseudocode:**
```
ALGORITHM CanJump(nums[]):
    max_reachable = 0
    n = length(nums)
    
    FOR i = 0 to n-1:
        // If current position is beyond what we can reach, return false
        IF i > max_reachable:
            PRINT "Cannot reach position", i
            RETURN false
        
        // Update the furthest position we can reach
        current_max_jump = i + nums[i]
        max_reachable = maximum(max_reachable, current_max_jump)
        
        PRINT "At position", i, "with jump", nums[i], 
              "can reach up to position", max_reachable
        
        // Early termination: if we can reach the last index
        IF max_reachable >= n-1:
            PRINT "Can reach the end!"
            RETURN true
    
    RETURN max_reachable >= n-1
```

**Example Walkthrough:**
- Array: [2, 3, 1, 1, 4]
- Position 0: jump=2, max_reachable = 0+2 = 2
- Position 1: jump=3, max_reachable = max(2, 1+3) = 4
- Position 2: jump=1, max_reachable = max(4, 2+1) = 4
- Position 3: jump=1, max_reachable = max(4, 3+1) = 4
- Position 4: We can reach it (4 ≥ 4), so return true

### 3. Job Sequencing Problem
**Problem Statement:** Given jobs with deadlines and profits, schedule jobs to maximize total profit. Each job takes 1 unit of time.

**Real-World Example:** You're a freelancer with multiple project offers. Each project has a deadline and payment. You want to maximize your earnings.

**Greedy Strategy:** Sort jobs by profit in descending order. For each job, try to schedule it as late as possible (but before its deadline) to leave room for other jobs.

**Why This Works:** Higher-profit jobs are prioritized, and scheduling them as late as possible maximizes flexibility for other jobs.

**Detailed Pseudocode:**
```
ALGORITHM JobSequencing(jobs[]):
    // Step 1: Sort jobs by profit in descending order
    SORT jobs by profit in descending order
    
    // Step 2: Find maximum deadline to determine time slots
    max_deadline = 0
    FOR each job in jobs:
        max_deadline = maximum(max_deadline, job.deadline)
    
    // Step 3: Create time slots array
    time_slots = array of size max_deadline, initialized to null
    total_profit = 0
    scheduled_jobs = []
    
    // Step 4: Schedule jobs greedily
    FOR each job in sorted_jobs:
        // Try to schedule this job as late as possible
        FOR time_slot = minimum(job.deadline, max_deadline) - 1 down to 0:
            IF time_slots[time_slot] is null:
                // Schedule this job
                time_slots[time_slot] = job
                total_profit = total_profit + job.profit
                scheduled_jobs.append(job)
                
                PRINT "Scheduled job", job.id, "with profit", job.profit, 
                      "at time slot", time_slot
                BREAK
    
    PRINT "Total profit:", total_profit
    RETURN scheduled_jobs, total_profit
```

**Example Walkthrough:**
- Jobs: [(id=1, deadline=2, profit=100), (id=2, deadline=1, profit=19), 
         (id=3, deadline=2, profit=27), (id=4, deadline=1, profit=25)]
- Sorted by profit: [(1,2,100), (3,2,27), (4,1,25), (2,1,19)]
- Time slots: [null, null]
- Schedule job 1 at slot 1 (deadline=2): slots=[null, job1]
- Schedule job 3 at slot 0 (deadline=2): slots=[job3, job1]
- Job 4 (deadline=1): slot 0 taken, cannot schedule
- Job 2 (deadline=1): slot 0 taken, cannot schedule
- Result: Jobs 1 and 3 scheduled, profit = 127

### 4. Egyptian Fraction Problem
**Problem Statement:** Express a fraction as a sum of distinct unit fractions (fractions with numerator 1).

**Real-World Example:** Ancient Egyptians only used unit fractions. Express 4/13 as a sum like 1/4 + 1/18 + 1/468.

**Greedy Strategy:** Always subtract the largest possible unit fraction that doesn't exceed the current fraction.

**Why This Works:** Using the largest possible unit fraction at each step minimizes the number of terms needed.

**Detailed Pseudocode:**
```
ALGORITHM EgyptianFraction(numerator, denominator):
    result_fractions = []
    
    WHILE numerator > 0:
        // Find the smallest integer x such that 1/x <= numerator/denominator
        // This means x >= denominator/numerator
        x = ceiling(denominator / numerator)
        
        result_fractions.append(1/x)
        PRINT "Added unit fraction: 1/", x
        
        // Subtract 1/x from numerator/denominator
        // numerator/denominator - 1/x = (numerator*x - denominator)/(denominator*x)
        numerator = numerator * x - denominator
        denominator = denominator * x
        
        PRINT "Remaining fraction:", numerator, "/", denominator
        
        // Simplify the fraction if possible
        gcd_value = gcd(numerator, denominator)
        numerator = numerator / gcd_value
        denominator = denominator / gcd_value
    
    RETURN result_fractions
```

**Example Walkthrough:**
- Fraction: 4/13
- Step 1: x = ceil(13/4) = 4, subtract 1/4
  - Remaining: (4×4 - 13)/(13×4) = 3/52
- Step 2: x = ceil(52/3) = 18, subtract 1/18
  - Remaining: (3×18 - 52)/(52×18) = 2/936 = 1/468
- Step 3: x = ceil(468/1) = 468, subtract 1/468
  - Remaining: 0
- Result: 4/13 = 1/4 + 1/18 + 1/468

### 5. Merge Overlapping Intervals
**Problem Statement:** Given a collection of intervals, merge all overlapping intervals.

**Real-World Example:** You have multiple time ranges for when you're available. Merge overlapping availability into single time blocks.

**Greedy Strategy:** Sort intervals by start time. Merge each interval with the previous one if they overlap.

**Why This Works:** Sorting ensures we process intervals in chronological order, making it easy to detect and merge overlaps.

**Detailed Pseudocode:**
```
ALGORITHM MergeIntervals(intervals[]):
    IF intervals is empty:
        RETURN []
    
    // Step 1: Sort intervals by start time
    SORT intervals by start_time in ascending order
    
    // Step 2: Merge overlapping intervals
    merged = []
    current_interval = intervals[0]
    
    FOR i = 1 to length(intervals) - 1:
        next_interval = intervals[i]
        
        // Check if current and next intervals overlap
        IF current_interval.end >= next_interval.start:
            // Merge the intervals
            current_interval.end = maximum(current_interval.end, next_interval.end)
            PRINT "Merged intervals:", current_interval, "and", next_interval
        ELSE:
            // No overlap, add current interval to result
            merged.append(current_interval)
            current_interval = next_interval
            PRINT "Added interval:", current_interval
    
    // Add the last interval
    merged.append(current_interval)
    
    RETURN merged
```

**Example Walkthrough:**
- Intervals: [[1,3], [2,6], [8,10], [15,18]]
- Sorted: [[1,3], [2,6], [8,10], [15,18]]
- Merge [1,3] and [2,6]: [1,6] (since 3 ≥ 2)
- [1,6] and [8,10]: no overlap (6 < 8)
- [8,10] and [15,18]: no overlap (10 < 15)
- Result: [[1,6], [8,10], [15,18]]

### 6. Minimum Fibonacci Terms with Sum K
**Problem Statement:** Express a number K as a sum of the minimum number of Fibonacci numbers.

**Real-World Example:** You want to pay an amount using Fibonacci-valued coins (1, 1, 2, 3, 5, 8, 13...). Use the fewest coins possible.

**Greedy Strategy:** Always use the largest Fibonacci number that doesn't exceed the remaining amount.

**Why This Works:** Using larger Fibonacci numbers reduces the total count needed due to their exponential growth pattern.

**Detailed Pseudocode:**
```
ALGORITHM MinFibonacciTerms(K):
    // Step 1: Generate Fibonacci numbers up to K
    fibonacci = [1, 1]
    WHILE fibonacci[length(fibonacci)-1] < K:
        next_fib = fibonacci[length(fibonacci)-1] + fibonacci[length(fibonacci)-2]
        fibonacci.append(next_fib)
    
    // Step 2: Use greedy approach
    terms_used = []
    remaining = K
    
    // Start from the largest Fibonacci number
    FOR i = length(fibonacci) - 1 down to 0:
        current_fib = fibonacci[i]
        
        // Use this Fibonacci number as many times as possible
        WHILE remaining >= current_fib:
            terms_used.append(current_fib)
            remaining = remaining - current_fib
            PRINT "Used Fibonacci number:", current_fib, 
                  "Remaining:", remaining
        
        IF remaining == 0:
            BREAK
    
    RETURN terms_used
```

**Example Walkthrough:**
- K = 10
- Fibonacci numbers ≤ 10: [1, 1, 2, 3, 5, 8]
- Use 8: remaining = 10 - 8 = 2
- Use 2: remaining = 2 - 2 = 0
- Result: [8, 2], count = 2

### 7. Minimum Railway Platforms
**Problem Statement:** Given arrival and departure times of trains, find the minimum number of platforms needed.

**Real-World Example:** A railway station needs to determine how many platforms to build based on train schedules.

**Greedy Strategy:** Treat arrivals as +1 platform needed and departures as -1 platform freed. Process events chronologically.

**Why This Works:** At any moment, the number of platforms needed equals the number of trains currently at the station.

**Detailed Pseudocode:**
```
ALGORITHM MinimumPlatforms(arrivals[], departures[]):
    // Step 1: Sort both arrays
    SORT arrivals in ascending order
    SORT departures in ascending order
    
    // Step 2: Use two pointers to simulate timeline
    i = 0  // pointer for arrivals
    j = 0  // pointer for departures
    platforms_needed = 0
    max_platforms = 0
    
    // Step 3: Process events chronologically
    WHILE i < length(arrivals) AND j < length(departures):
        // If next event is an arrival
        IF arrivals[i] <= departures[j]:
            platforms_needed = platforms_needed + 1
            max_platforms = maximum(max_platforms, platforms_needed)
            
            PRINT "Train arrives at", arrivals[i], 
                  "Platforms needed:", platforms_needed
            i = i + 1
        ELSE:
            // Next event is a departure
            platforms_needed = platforms_needed - 1
            PRINT "Train departs at", departures[j], 
                  "Platforms needed:", platforms_needed
            j = j + 1
    
    RETURN max_platforms
```

**Example Walkthrough:**
- Arrivals: [9:00, 9:40, 9:50, 11:00, 15:00, 18:00]
- Departures: [9:10, 12:00, 11:20, 11:30, 19:00, 20:00]
- Timeline simulation:
  - 9:00: Arrival (+1) → platforms = 1
  - 9:10: Departure (-1) → platforms = 0
  - 9:40: Arrival (+1) → platforms = 1
  - 9:50: Arrival (+1) → platforms = 2
  - 11:00: Arrival (+1) → platforms = 3
  - 11:20: Departure (-1) → platforms = 2
  - 11:30: Departure (-1) → platforms = 1
  - 12:00: Departure (-1) → platforms = 0
- Maximum platforms needed: 3

### 8. Minimum Cost to Connect N Ropes
**Problem Statement:** Connect all ropes into one rope. The cost of connecting two ropes equals the sum of their lengths.

**Real-World Example:** You have rope segments to join. Each joining operation costs the sum of the lengths being joined. Minimize total cost.

**Greedy Strategy:** Always connect the two shortest ropes first. This minimizes the cost since shorter ropes are used in more operations.

**Why This Works:** By connecting shorter ropes first, we avoid repeatedly adding large lengths to the total cost.

**Detailed Pseudocode:**
```
ALGORITHM MinCostConnectRopes(ropes[]):
    // Step 1: Create a min-heap from all rope lengths
    min_heap = create_min_heap(ropes)
    total_cost = 0
    
    // Step 2: Keep connecting until only one rope remains
    WHILE size(min_heap) > 1:
        // Extract two shortest ropes
        first_rope = extract_min(min_heap)
        second_rope = extract_min(min_heap)
        
        // Calculate cost of connecting these ropes
        connection_cost = first_rope + second_rope
        total_cost = total_cost + connection_cost
        
        PRINT "Connected ropes of length", first_rope, "and", second_rope
        PRINT "Connection cost:", connection_cost, "Total cost:", total_cost
        
        // Insert the new combined rope back into heap
        insert(min_heap, connection_cost)
    
    RETURN total_cost
```

**Example Walkthrough:**
- Ropes: [20, 4, 8, 10]
- Min-heap: [4, 8, 10, 20]
- Connect 4 and 8: cost = 12, total = 12, heap = [10, 12, 20]
- Connect 10 and 12: cost = 22, total = 34, heap = [20, 22]
- Connect 20 and 22: cost = 42, total = 76, heap = [42]
- Result: Total cost = 76

### 9. Maximum Trains on a Platform
**Problem Statement:** A platform can handle one train at a time. Given arrival and departure times, maximize the number of trains that can be accommodated.

**Real-World Example:** A single-track railway station wants to serve as many trains as possible without conflicts.

**Greedy Strategy:** This is identical to the Activity Selection Problem - choose trains that depart earliest.

**Why This Works:** Early-departing trains free up the platform sooner, allowing more trains to be scheduled.

**Detailed Pseudocode:**
```
ALGORITHM MaxTrainsOnPlatform(trains[]):
    // Step 1: Sort trains by departure time
    SORT trains by departure_time in ascending order
    
    // Step 2: Select trains greedily
    selected_trains = []
    last_departure = -infinity
    
    FOR each train in sorted_trains:
        // Check if train can arrive after the last selected train departs
        IF train.arrival_time >= last_departure:
            selected_trains.append(train)
            last_departure = train.departure_time
            
            PRINT "Selected train: Arrives at", train.arrival_time, 
                  "Departs at", train.departure_time
        ELSE:
            PRINT "Rejected train: Arrives at", train.arrival_time, 
                  "Departs at", train.departure_time, "(conflicts)"
    
    RETURN selected_trains
```

### 10. Partition Numbers 1 to N into Two Groups with Minimum Difference
**Problem Statement:** Divide numbers 1, 2, 3, ..., N into two groups such that the absolute difference between their sums is minimized.

**Real-World Example:** Split a team of people (numbered 1 to N) into two groups with nearly equal total "skill points."

**Greedy Strategy:** Calculate the total sum, then try to make one group as close to half the total as possible.

**Why This Works:** If we can make one group sum to exactly half the total, the difference is 0. Otherwise, we want to get as close as possible to half.

**Detailed Pseudocode:**
```
ALGORITHM PartitionMinDifference(N):
    // Step 1: Calculate total sum
    total_sum = N * (N + 1) / 2
    target = total_sum / 2
    
    // Step 2: Try to fill one group to be close to target
    group1 = []
    group2 = []
    current_sum = 0
    
    // Step 3: Use greedy approach - add largest numbers to group1 until close to target
    FOR i = N down to 1:
        IF current_sum + i <= target:
            group1.append(i)
            current_sum = current_sum + i
        ELSE:
            group2.append(i)
    
    // Step 4: Calculate actual difference
    sum1 = sum_of_array(group1)
    sum2 = sum_of_array(group2)
    difference = absolute_value(sum1 - sum2)
    
    PRINT "Group 1:", group1, "Sum:", sum1
    PRINT "Group 2:", group2, "Sum:", sum2
    PRINT "Difference:", difference
    
    RETURN group1, group2, difference
```

**Example Walkthrough:**
- N = 6, numbers: [1, 2, 3, 4, 5, 6]
- Total sum = 21, target = 10.5
- Try to get group1 close to 10.5:
  - Add 6: sum = 6 (≤ 10.5)
  - Add 5: sum = 11 (> 10.5), so add 5 to group2
  - Add 4: sum = 10 (≤ 10.5)
  - Add 3: sum = 13 (> 10.5), so add 3 to group2
  - Add 2: sum = 12 (> 10.5), so add 2 to group2
  - Add 1: sum = 11 (> 10.5), so add 1 to group2
- Group1: [6, 4], sum = 10
- Group2: [5, 3, 2, 1], sum = 11
- Difference: |10 - 11| = 1

---

## Hard Problems on Greedy Algorithm

### 1. Minimize the Maximum Height Difference (Towers Problem)
**Problem Statement:** Given heights of N towers and a value K, you must increase or decrease each tower's height by exactly K. Minimize the difference between the tallest and shortest towers.

**Real-World Example:** You're landscaping and can raise or lower each hill by exactly K meters. You want to make the terrain as flat as possible.

**Greedy Strategy:** For each tower, decide whether to increase or decrease its height based on what minimizes the overall range. Sort towers and try different "break points."

**Why This Works:** After sorting, there's an optimal point where all towers to the left should be increased and all towers to the right should be decreased.

**Detailed Pseudocode:**
```
ALGORITHM MinimizeMaxHeightDifference(heights[], K):
    n = length(heights)
    IF n <= 1:
        RETURN 0
    
    // Step 1: Sort the heights
    SORT heights in ascending order
    
    // Step 2: Initial difference without any changes
    original_diff = heights[n-1] - heights[0]
    result = original_diff
    
    // Step 3: Try different break points
    FOR i = 1 to n-1:
        // All towers from 0 to i-1 will be increased by K
        // All towers from i to n-1 will be decreased by K
        
        // Check if any tower becomes negative
        IF heights[i] - K < 0:
            CONTINUE  // Skip this configuration
        
        // Calculate new minimum and maximum
        new_min = minimum(heights[0] + K, heights[i] - K)
        new_max = maximum(heights[i-1] + K, heights[n-1] - K)
        
        new_diff = new_max - new_min
        result = minimum(result, new_diff)
        
        PRINT "Break point at index", i
        PRINT "New min:", new_min, "New max:", new_max, "Difference:", new_diff
    
    RETURN result
```

**Example Walkthrough:**
- Heights: [1, 5, 8, 10], K = 2
- Sorted: [1, 5, 8, 10]
- Original difference: 10 - 1 = 9
- Try break point at index 1: [1+2, 5-2, 8-2, 10-2] = [3, 3, 6, 8]
  - Min = 3, Max = 8, Difference = 5
- Try break point at index 2: [1+2, 5+2, 8-2, 10-2] = [3, 7, 6, 8]
  - Min = 3, Max = 8, Difference = 5
- Try break point at index 3: [1+2, 5+2, 8+2, 10-2] = [3, 7, 10, 8]
  - Min = 3, Max = 10, Difference = 7
- Result: Minimum difference = 5

### 2. Making All Elements Equal with K Operations
**Problem Statement:** Given an array and K operations, where each operation can increment any element by 1, make all elements equal. Return the final equal value, or -1 if impossible.

**Real-World Example:** You have K enhancement points to distribute among team members' skills. What's the highest skill level everyone can reach?

**Greedy Strategy:** Try each element as the target final value. For each target, calculate if K operations are sufficient to bring all elements up to that level.

**Why This Works:** The optimal target is always one of the existing elements in the array. We check each possibility and pick the highest achievable one.

**Detailed Pseudocode:**
```
ALGORITHM MakeAllEqual(arr[], K):
    n = length(arr)
    SORT arr in descending order
    
    // Step 1: Try each element as the target
    FOR target_index = 0 to n-1:
        target_value = arr[target_index]
        operations_needed = 0
        
        // Step 2: Calculate operations needed to make all elements equal to target
        FOR i = 0 to n-1:
            IF arr[i] < target_value:
                operations_needed = operations_needed + (target_value - arr[i])
        
        // Step 3: Check if this target is achievable
        IF operations_needed <= K:
            PRINT "Target", target_value, "is achievable with", operations_needed, "operations"
            RETURN target_value
        ELSE:
            PRINT "Target", target_value, "needs", operations_needed, "operations (too many)"
    
    // Step 4: If no existing element works, find the maximum possible value
    // We need to use binary search or mathematical approach
    min_val = minimum_element(arr)
    max_possible = min_val + K
    
    // Calculate if we can achieve max_possible
    total_operations = 0
    FOR i = 0 to n-1:
        IF arr[i] < max_possible:
            total_operations = total_operations + (max_possible - arr[i])
    
    IF total_operations <= K:
        RETURN max_possible
    ELSE:
        RETURN -1
```

**Example Walkthrough:**
- Array: [2, 5, 3, 1], K = 5
- Sorted: [5, 3, 2, 1]
- Try target = 5: operations = 0+2+3+4 = 9 > 5 (too many)
- Try target = 3: operations = 0+0+1+2 = 3 ≤ 5 ✓
- Result: All elements can be made equal to 3

### 3. Minimize Cash Flow Among Friends
**Problem Statement:** A group of friends borrowed money from each other. Minimize the number of transactions needed to settle all debts.

**Real-World Example:** After a trip, everyone owes different amounts to different people. You want to minimize the number of money transfers needed.

**Greedy Strategy:** Calculate each person's net balance (amount owed - amount they're owed). Then match the largest creditor with the largest debtor repeatedly.

**Why This Works:** Matching extremes minimizes the number of transactions by clearing the largest imbalances first.

**Detailed Pseudocode:**
```
ALGORITHM MinimizeCashFlow(debts[][]):
    n = length(debts)
    
    // Step 1: Calculate net balance for each person
    net_balance = array of size n, initialized to 0
    
    FOR i = 0 to n-1:
        FOR j = 0 to n-1:
            net_balance[i] = net_balance[i] + debts[j][i] - debts[i][j]
    
    // Step 2: Print initial balances
    FOR i = 0 to n-1:
        IF net_balance[i] > 0:
            PRINT "Person", i, "should receive", net_balance[i]
        ELSE IF net_balance[i] < 0:
            PRINT "Person", i, "should pay", -net_balance[i]
        ELSE:
            PRINT "Person", i, "is settled"
    
    // Step 3: Minimize transactions
    transactions = []
    
    WHILE NOT all_zeros(net_balance):
        // Find person with maximum credit (positive balance)
        max_credit_person = index_of_maximum(net_balance)
        max_credit = net_balance[max_credit_person]
        
        // Find person with maximum debt (most negative balance)
        max_debt_person = index_of_minimum(net_balance)
        max_debt = -net_balance[max_debt_person]
        
        // Transfer minimum of the two amounts
        transfer_amount = minimum(max_credit, max_debt)
        
        // Record the transaction
        transactions.append((max_debt_person, max_credit_person, transfer_amount))
        PRINT "Transaction:", max_debt_person, "pays", transfer_amount, "to", max_credit_person
        
        // Update balances
        net_balance[max_credit_person] = net_balance[max_credit_person] - transfer_amount
        net_balance[max_debt_person] = net_balance[max_debt_person] + transfer_amount
    
    RETURN transactions
```

**Example Walkthrough:**
- Debts: Person 0 owes 1000 to Person 1, Person 1 owes 2000 to Person 2
- Net balances: [−1000, +1000−2000, +2000] = [−1000, −1000, +2000]
- Transaction 1: Person 0 pays 1000 to Person 2
  - Balances: [0, −1000, +1000]
- Transaction 2: Person 1 pays 1000 to Person 2
  - Balances: [0, 0, 0]
 


## 4. Minimum Cost to Cut a Board into Squares

**Problem**: Cut a board into squares with given vertical and horizontal cut costs.

**Key Insight**: Always make the most expensive cuts first when you have fewer pieces to cut through.

**Why Greedy Works**: Each cut divides the board into more pieces. Making expensive cuts early minimizes the total cost because you multiply the cut cost by fewer pieces.

**Algorithm**:
1. Sort both cut arrays in descending order (highest cost first)
2. Use two pointers to always pick the higher cost cut
3. Track how many pieces each cut will create

```pseudocode
FUNCTION minCostToCutBoard(vertical_cuts[], horizontal_cuts[]):
    SORT vertical_cuts[] in descending order
    SORT horizontal_cuts[] in descending order
    
    v_pieces = 1  // number of vertical pieces
    h_pieces = 1  // number of horizontal pieces
    total_cost = 0
    i = 0, j = 0
    
    WHILE i < vertical_cuts.length OR j < horizontal_cuts.length:
        IF j >= horizontal_cuts.length OR (i < vertical_cuts.length AND vertical_cuts[i] >= horizontal_cuts[j]):
            total_cost += vertical_cuts[i] * h_pieces
            v_pieces += 1
            i += 1
        ELSE:
            total_cost += horizontal_cuts[j] * v_pieces
            h_pieces += 1
            j += 1
    
    RETURN total_cost
```

**Example**: Board 6x4, vertical cuts [2,1,3], horizontal cuts [1,2]
- Sort: vertical [3,2,1], horizontal [2,1]
- Pick 3 (vertical) → cost = 3×1 = 3, v_pieces = 2
- Pick 2 (horizontal) → cost = 2×2 = 4, h_pieces = 2
- Pick 2 (vertical) → cost = 2×2 = 4, v_pieces = 3
- Total = 3 + 4 + 4 = 11

---

## 5. Minimum Cost to Process m Tasks with Switching Cost

**Problem**: Each task belongs to a type. Switching types incurs cost. Find minimum total cost.

**Key Insight**: This is actually a Dynamic Programming problem, not pure greedy. We need to consider all possible previous states.

**Why DP is Needed**: At each step, we need to decide whether to switch or stay, considering all possible previous types.

**Algorithm**:
1. Use DP where `dp[i][type]` = minimum cost to process first i tasks with last task of given type
2. For each task, try all possible types
3. Consider both staying with same type and switching from all other types

```pseudocode
FUNCTION minCostToProcessTasks(tasks[], task_costs[][], switch_cost):
    n = tasks.length
    types = number of different types
    
    // dp[i][type] = min cost to process first i tasks, last task is of 'type'
    dp = 2D array of size (n+1) x types, initialized to INFINITY
    dp[0][all types] = 0
    
    FOR i = 1 to n:
        current_task_type = tasks[i-1]
        
        FOR last_type = 0 to types-1:
            IF dp[i-1][last_type] != INFINITY:
                // Option 1: Keep same type (if current task can be this type)
                IF current_task_type == last_type:
                    dp[i][last_type] = MIN(dp[i][last_type], 
                                          dp[i-1][last_type] + task_costs[i-1][last_type])
                
                // Option 2: Switch to different type
                FOR new_type = 0 to types-1:
                    IF new_type != last_type:
                        dp[i][new_type] = MIN(dp[i][new_type], 
                                             dp[i-1][last_type] + switch_cost + task_costs[i-1][new_type])
    
    RETURN MIN(dp[n][all types])
```

---

## 6. Minimum Time to Finish All Jobs (with Worker Constraints)

**Problem**: Assign jobs to workers with constraints such as max time per worker.

**Key Insight**: Binary search on the answer (maximum time limit) + greedy assignment verification.

**Why This Works**: If we can complete all jobs in time T, we can also complete them in time T+1. This monotonic property allows binary search.

**Algorithm**:
1. Binary search on the maximum time any worker takes
2. For each candidate time, greedily check if assignment is possible
3. Use backtracking for optimal assignment verification

```pseudocode
FUNCTION minTimeToFinishJobs(jobs[], k_workers):
    SORT jobs[] in descending order  // optimization: assign bigger jobs first
    
    low = MAX(jobs[])  // at least one job time
    high = SUM(jobs[])  // all jobs to one worker
    
    WHILE low < high:
        mid = (low + high) / 2
        
        IF canAssignJobs(jobs, k_workers, mid):
            high = mid
        ELSE:
            low = mid + 1
    
    RETURN low

FUNCTION canAssignJobs(jobs[], k, time_limit):
    workers = array of k workers, each with current_time = 0
    
    RETURN backtrack(jobs, 0, workers, time_limit)

FUNCTION backtrack(jobs[], job_index, workers[], time_limit):
    IF job_index == jobs.length:
        RETURN true
    
    current_job = jobs[job_index]
    
    FOR i = 0 to workers.length-1:
        IF workers[i] + current_job <= time_limit:
            workers[i] += current_job
            
            IF backtrack(jobs, job_index + 1, workers, time_limit):
                RETURN true
            
            workers[i] -= current_job  // backtrack
            
            // Optimization: if worker was empty, no need to try other empty workers
            IF workers[i] == 0:
                BREAK
    
    RETURN false
```

---

## 7. Minimize the Maximum Difference Between Tower Heights

This is the same as problem #1 (covered in previous problems), where we add/subtract K from each tower height to minimize the difference between max and min heights.

---

## 8. Minimum Edges to Reverse for Path from Source to Destination

**Problem**: Given a directed graph, reverse minimum edges to make a path from source to destination.

**Key Insight**: Model as 0-1 BFS where original edges have weight 0, reversed edges have weight 1.

**Why This Works**: We want the shortest path in terms of number of edge reversals. Each reversal costs 1.

**Algorithm**:
1. Create a new graph where each original edge has weight 0
2. Add reverse edges with weight 1
3. Use 0-1 BFS (deque-based BFS) to find shortest path

```pseudocode
FUNCTION minEdgesToReverse(graph[][], source, destination):
    n = number of nodes
    
    // Create new graph with both original and reversed edges
    new_graph = adjacency list
    
    FOR each edge (u, v) in original graph:
        new_graph[u].add((v, 0))  // original edge, cost 0
        new_graph[v].add((u, 1))  // reversed edge, cost 1
    
    // 0-1 BFS
    distance = array of size n, initialized to INFINITY
    distance[source] = 0
    deque = empty deque
    deque.push_front(source)
    
    WHILE deque is not empty:
        current = deque.pop_front()
        
        FOR each neighbor (next_node, cost) of current:
            new_distance = distance[current] + cost
            
            IF new_distance < distance[next_node]:
                distance[next_node] = new_distance
                
                IF cost == 0:
                    deque.push_front(next_node)
                ELSE:
                    deque.push_back(next_node)
    
    RETURN distance[destination] == INFINITY ? -1 : distance[destination]
```

---

## 9. Largest Cube by Deleting Minimum Digits

**Problem**: Delete minimum digits to form the largest possible perfect cube.

**Key Insight**: Try all possible cubes in descending order and check if they can be formed as a subsequence.

**Why This Works**: We want the largest cube, so we check from largest to smallest. The first valid cube we find is the answer.

**Algorithm**:
1. Generate all perfect cubes up to reasonable limit
2. For each cube (largest first), check if it's a subsequence of input
3. Return the first valid cube found

```pseudocode
FUNCTION largestCubeAfterDeleting(number_string):
    cubes = []
    
    // Generate all cubes up to 10^9
    FOR i = 1 to 1000:
        cube = i * i * i
        IF cube > 10^9:
            BREAK
        cubes.add(cube)
    
    // Sort cubes in descending order
    SORT cubes in descending order
    
    // Try each cube
    FOR each cube in cubes:
        cube_string = toString(cube)
        
        IF isSubsequence(cube_string, number_string):
            RETURN cube
    
    RETURN -1

FUNCTION isSubsequence(pattern, text):
    i = 0  // pointer for pattern
    j = 0  // pointer for text
    
    WHILE i < pattern.length AND j < text.length:
        IF pattern[i] == text[j]:
            i += 1
        j += 1
    
    RETURN i == pattern.length
```

**Example**: Input "8674235", cubes to try: [729, 512, 343, 216, 125, 64, 27, 8, 1]
- Try 729: not a subsequence
- Try 512: not a subsequence  
- Try 343: not a subsequence
- Try 216: not a subsequence
- Try 125: not a subsequence
- Try 64: "64" is subsequence of "8674235" ✓
- Return 64

---

## 10. Rearrange Characters So No Two Adjacent Are Same

**Problem**: Rearrange string so no two adjacent characters are the same.

**Key Insight**: Always place the most frequent available character, but avoid placing the same character consecutively.

**Why Greedy Works**: By always using the most frequent character available, we balance the frequencies and avoid getting stuck with too many of one character.

**Algorithm**:
1. Count character frequencies
2. Use max heap to always get the most frequent character
3. Keep track of previously used character to avoid consecutive placement

```pseudocode
FUNCTION rearrangeString(s):
    // Count frequencies
    freq = map of character to frequency
    FOR each char in s:
        freq[char] += 1
    
    // Create max heap of (frequency, character)
    max_heap = priority queue (max heap)
    FOR each (char, count) in freq:
        max_heap.push((count, char))
    
    result = ""
    prev_char = null
    prev_freq = 0
    
    WHILE max_heap is not empty:
        // Get most frequent character
        current_freq, current_char = max_heap.pop()
        result += current_char
        
        // Put back previous character if it still has frequency
        IF prev_freq > 0:
            max_heap.push((prev_freq, prev_char))
        
        // Update previous character
        prev_char = current_char
        prev_freq = current_freq - 1
    
    // Check if we used all characters
    IF result.length == s.length:
        RETURN result
    ELSE:
        RETURN "Not possible"
```

**Example**: Input "aab"
- Frequencies: a=2, b=1
- Heap: [(2,'a'), (1,'b')]
- Step 1: Use 'a', result="a", prev='a',freq=1
- Step 2: Use 'b', result="ab", put back (1,'a'), prev='b',freq=0  
- Step 3: Use 'a', result="aba"
- Return "aba"

---

## 11. Rearrange String with d Distance Between Same Characters

**Problem**: Rearrange string so same characters are at least d positions apart.

**Key Insight**: Similar to problem 10, but maintain a cooldown queue for characters that can't be used yet.

**Why This Works**: We track when each character becomes available again and use a max heap for the most frequent available character.

**Algorithm**:
1. Use max heap for available characters
2. Use a queue to track characters in cooldown
3. At each step, try to use the most frequent available character

```pseudocode
FUNCTION rearrangeStringWithDistance(s, d):
    // Count frequencies
    freq = map of character to frequency
    FOR each char in s:
        freq[char] += 1
    
    // Max heap of (frequency, character)
    max_heap = priority queue (max heap)
    FOR each (char, count) in freq:
        max_heap.push((count, char))
    
    wait_queue = queue  // stores (char, frequency, available_time)
    result = ""
    time = 0
    
    WHILE max_heap is not empty OR wait_queue is not empty:
        // Check if any character in wait queue is ready
        WHILE wait_queue is not empty AND wait_queue.front().available_time <= time:
            char, freq, _ = wait_queue.pop()
            IF freq > 0:
                max_heap.push((freq, char))
        
        // Use most frequent available character
        IF max_heap is not empty:
            current_freq, current_char = max_heap.pop()
            result += current_char
            
            // Put character in cooldown if it still has frequency
            IF current_freq - 1 > 0:
                wait_queue.push((current_char, current_freq - 1, time + d))
        ELSE:
            // No character available, impossible to arrange
            RETURN "Not possible"
        
        time += 1
    
    IF result.length == s.length:
        RETURN result
    ELSE:
        RETURN "Not possible"
```

**Example**: Input "aabbcc", d=3
- Frequencies: a=2, b=2, c=2
- Step 1: Use 'a', result="a", 'a' available at time 4
- Step 2: Use 'b', result="ab", 'b' available at time 5  
- Step 3: Use 'c', result="abc", 'c' available at time 6
- Step 4: Use 'a' (now available), result="abca"
- Continue pattern...
- Result: "abcabc"

## Summary

These problems showcase different greedy strategies:

1. **Cost minimization**: Always choose the most expensive option first when it affects fewer future choices
2. **Binary search + greedy verification**: When the answer has a monotonic property
3. **Frequency-based greedy**: Use max heap to always process the most frequent item first
4. **Graph transformation**: Convert the problem to shortest path with appropriate edge weights
5. **Subsequence checking**: Try all possibilities in optimal order

The key to solving hard greedy problems is identifying the right greedy choice and proving why it leads to the optimal solution.
- Result: 2 transactions instead of the original 2

### 4. Minimum Cost to Cut a Board into Squares
**Problem Statement:** Given a rectangular board and costsproperty holds for your specific problem!
