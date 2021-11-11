import sys
import random

def main():
    with open(sys.argv[1]) as f:
        for line in f:
            for c in line:
                if random.random() < .1:
                    random_char = chr(random.randint(58, 127))
                    print(random_char, end='')

                print(c, end='')

if __name__ == "__main__":
    main()