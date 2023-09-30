a = list(range(1, 21))
for _ in range(10):
    s, e = map(int, input().split())
    for i in range((e-s+1) // 2):
        a[s+i-1], a[e-i-1] = a[e-i-1], a[s+i-1]

print(a)