n = int(input())
a = list(map(int, input().split()))

sum = 0
cnt = 0

for x in a:
    if x == 1:
        cnt += 1
    else:
        cnt = 0
    sum += cnt

print(sum)