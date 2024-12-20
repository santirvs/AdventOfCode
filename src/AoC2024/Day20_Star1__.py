package AoC2024.Day20_Star1

/*  Python

grid = {i+j*1j: c for i,r in enumerate(open('in.txt'))
                  for j,c in enumerate(r) if c != '#'}

start, = (p for p in grid if grid[p] == 'S')

dist = {start: 0}
todo = [start]

for pos in todo:
    for new in pos-1, pos+1, pos-1j, pos+1j:
        if new in grid and new not in dist:
            dist[new] = dist[pos] + 1
            todo.append(new)


a = b = 0

for p in dist:
    for q in dist:
        d = abs((p-q).real) + abs((p-q).imag)
        if d == 2 and dist[p]-dist[q]-d >= 100: a += 1
        if d < 21 and dist[p]-dist[q]-d >= 100: b += 1

print(a, b)

 */