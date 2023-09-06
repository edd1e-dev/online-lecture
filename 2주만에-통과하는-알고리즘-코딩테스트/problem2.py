candy = int(input()) # 6

answer = 0
for A in range(candy + 1):
    for B in range(candy + 1):
        for C in range(candy + 1):
            if A + B + C == 6:
                if A >= B + 2:
                    if A != 0 and B != 0 and C != 0:
                        if C % 2 == 0:
                            answer += 1

print(answer)