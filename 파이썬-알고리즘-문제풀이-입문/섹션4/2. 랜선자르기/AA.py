def Count(len):
    cnt = 0
    for x in Line:
        cnt += x // len
    return cnt

k, n = map(int, input().split())
Line = [int(input()) for _ in range(k)]
res = 0
largest = 0

for x in Line:
    largest = max(largest, x)

lt = 1
rt = largest

while lt <= rt:
    mid = (lt+rt) // 2
    if Count(mid) >= n:
        res = mid
        lt = mid + 1
    else:
        rt = mid - 1

print(res)