A, B, C, D, E, F = map(int, input().split())

for x in range(-10000, 10001):
    for y in range(-10000, 10001):
        if A * x + B * y == C:
            if D * x + E * y == F:
                print(x, y)
                break