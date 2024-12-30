from typing import List


def part_1(data: List[List]):
    card_score = {'X': 1, 'Y': 2, 'Z': 3}  # X for rock, Y for paper and Z for scissors

    # A for Rock, B for paper and C for scissors
    def judgement_as_b(a: str, b: str) -> int:
        match a, b:
            case 'A', 'X':
                return 3
            case 'A', 'Y':
                return 6
            case 'A', 'Z':
                return 0
            case 'B', 'X':
                return 0
            case 'B', 'Y':
                return 3
            case 'B', 'Z':
                return 6
            case 'C', 'X':
                return 6
            case 'C', 'Y':
                return 0
            case 'C', 'Z':
                return 3
        raise Exception("should not be here")

    return sum((judgement_as_b(a, b) + card_score[b] for a, b in data))


def part_2(data: List[List]):
    result_score = {'X': 0, 'Y': 3, 'Z': 6}  # X for lose, Y for draw and Z for win

    # A for Rock, B for paper and C for scissors
    def card_score_for_b(a: str, b: str) -> int:
        match a, b:
            case 'A', 'X':
                return 3  # scissors
            case 'A', 'Y':
                return 1  # rock
            case 'A', 'Z':
                return 2  # paper
            case 'B', 'X':
                return 1  # rock
            case 'B', 'Y':
                return 2  # paper
            case 'B', 'Z':
                return 3  # scissors
            case 'C', 'X':
                return 2  # paper
            case 'C', 'Y':
                return 3  # scissors
            case 'C', 'Z':
                return 1  # rock
        raise Exception("should not be here")

    return sum((card_score_for_b(a, b) + result_score[b] for a, b in data))


if __name__ == '__main__':
    with open('dataset.txt', 'r') as f:
        content = [item.strip().split(' ') for item in f.readlines()]
        print(part_1(content))
        print(part_2(content))
