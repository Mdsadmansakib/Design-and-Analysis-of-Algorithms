# 🔁 Eulerian Path in Graph Theory

## 🧠 What is an Eulerian Path?

An **Eulerian Path** is a trail in a graph that visits **every edge exactly once**.  
If the path starts and ends at the same vertex, it is called an **Eulerian Circuit**.

---

## 🌉 The Königsberg Bridge Problem

This was the origin of Eulerian Path theory.

### 🔍 Problem Statement:
Can a person walk through the city of **Königsberg** crossing **each of its 7 bridges exactly once**?

### 🏙️ Abstraction:
- Land masses → **Vertices**
- Bridges → **Edges**

Euler turned this into a **graph theory** problem and proved:

> ❌ No such walk exists.

---

## ✅ Euler's Theorem

Given an **undirected connected graph**:

- An **Eulerian Circuit** exists if **all vertices have even degree**.
- An **Eulerian Path** (not a circuit) exists if **exactly 2 vertices have odd degree**.
- If **more than 2** vertices have odd degree → ❌ No Eulerian Path

---

## 🔢 Algorithm to Check Eulerian Path (Undirected Graph)

### Pseudocode:

```java
boolean hasEulerianPath(List<Integer>[] adj, int V) {
    int odd = 0;
    for (int i = 0; i < V; i++) {
        if (adj[i].size() % 2 != 0)
            odd++;
    }
    // Eulerian Path exists if 0 or 2 vertices have odd degree
    return (odd == 0 || odd == 2);
}
