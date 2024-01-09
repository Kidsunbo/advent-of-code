def parse_input():
    result = []
    with open("dataset.txt") as f:
        for line in f.readlines():
            direction, length = line.strip().split(" ")
            result.append((direction, int(length)))
    return result

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def move(self, previous):
        if self.x != previous.x:
            self.x += -1 if self.x > previous.x else 1
        if self.y != previous.y:
            self.y += -1 if self.y > previous.y else 1
    
    def is_adjacent(self, other):
        return abs(self.x - other.x) <=1 and abs(self.y - other.y) <= 1

    def move_forward(self, direction):
        match direction:
            case 'R':
                self.x += 1
            case 'L':
                self.x -= 1
            case 'U':
                self.y += 1
            case 'D':
                self.y -= 1
    
    def __eq__(self, other):
        return self.x == other.x and self.y == other.y

    def __hash__(self):
        return hash(f"{self.x}_{self.y}")

    def __repr__(self):
        return f"Point({self.x}, {self.y})"

    def __str__(self):
        return f"[{self.x}, {self.y}]"

def simulate(knot_nums):
    knots = [Point(0, 0) for _ in range(knot_nums)]
    steps = parse_input()
    tail_path = set()
    for direction, step_count in steps:
        for _ in range(step_count):
            knots[0].move_forward(direction)
            for i in range(1, knot_nums):
                if not knots[i].is_adjacent(knots[i-1]):
                    knots[i].move(knots[i-1])
            tail_path.add(knots[-1])
    print(len(tail_path))

if __name__ == "__main__":
    simulate(2)
    simulate(10)