using System.Diagnostics;
using System.Text.RegularExpressions;

namespace Kies
{
    public class CrateNode
    {
        public CrateNode? next;
        public char value;
    }

    public class Kies
    {
        static readonly uint size = 9;

        static async Task Main()
        {
            var all_lines = new List<string>();
            await foreach (var line in File.ReadLinesAsync("./dataset.txt"))
            {
                all_lines.Add(line);
            }
            Part1(all_lines);
            Part2(all_lines);
        }

        static void Part1(List<string> all_lines)
        {
            var (all_crates, line_count) = Part1ParseAllCrate(all_lines);
            Part1StartMove(all_lines.Skip(line_count + 1).ToList(), all_crates);
            foreach (var stack in all_crates)
            {
                System.Console.Write(stack.Peek());
            }
            System.Console.WriteLine();
        }


        static void Part1StartMove(List<string> all_lines, List<Stack<char>> all_crates)
        {
            var reg = new Regex(@"move (\d+) from (\d+) to (\d+)");
            foreach (var line in all_lines)
            {
                var res = reg.Match(line);
                if (res.Groups.Count != 4)
                {
                    throw new Exception("WTF");
                }
                var count = int.Parse(res.Groups[1].Value);
                var from = int.Parse(res.Groups[2].Value);
                var to = int.Parse(res.Groups[3].Value);
                Part1Move(all_crates, count, from, to);
            }
        }

        static void Part1Move(List<Stack<char>> all_crates, int count, in int from, in int to)
        {
            for (; count > 0; count--)
            {
                all_crates[to - 1].Push(all_crates[from - 1].Pop());
            }
        }

        static (List<Stack<char>>, int) Part1ParseAllCrate(List<string> all_lines)
        {
            var result = new List<Stack<char>>();
            for (int i = 0; i < size; i++)
            {
                result.Add(new Stack<char>());
            }
            var line_count = -1;
            while (all_lines[++line_count].Length != 0) ;
            for (var i = line_count - 2; i >= 0; i--)
            {
                var line = all_lines[i];
                for (var j = 0; j < line.Length;)
                {
                    if (line[j] != ' ')
                    {
                        result[j / 4].Push(line[j + 1]);
                    }
                    j += 4;
                }
            }
            return (result, line_count);
        }



        static void Part2(List<string> all_lines)
        {
            var (all_crates, line_count) = Part2ParseAllCrate(all_lines);
            Part2StartMove(all_lines.Skip(line_count + 1).ToList(), all_crates);
            foreach (var stack in all_crates)
            {
                System.Console.Write(stack?.value);
            }
            System.Console.WriteLine();
        }

        static void Part2StartMove(List<string> all_lines, CrateNode?[] all_crates)
        {
            var reg = new Regex(@"move (\d+) from (\d+) to (\d+)");
            foreach (var line in all_lines)
            {
                var res = reg.Match(line);
                if (res.Groups.Count != 4)
                {
                    throw new Exception("WTF");
                }
                var count = int.Parse(res.Groups[1].Value);
                var from = int.Parse(res.Groups[2].Value);
                var to = int.Parse(res.Groups[3].Value);
                Part2Move(all_crates, count, from, to);
            }
        }

        static void Part2Move(CrateNode?[] all_crates, int count, in int from, in int to)
        {
            var top = all_crates[from - 1];
            var head = top;
            while (--count > 0)
            {
                head = head!.next;
            }
            all_crates[from - 1] = head!.next;
            head!.next = all_crates[to - 1];
            all_crates[to - 1] = top;
        }

        static (CrateNode?[], int) Part2ParseAllCrate(List<string> all_lines)
        {
            var result = new CrateNode?[size];
            var line_count = -1;
            while (all_lines[++line_count].Length != 0) ;
            for (var i = line_count - 2; i >= 0; i--)
            {
                var line = all_lines[i];
                for (var j = 0; j < line.Length;)
                {
                    if (line[j] != ' ')
                    {
                        var crate = new CrateNode
                        {
                            next = result[j / 4],
                            value = line[j + 1]
                        };
                        result[j / 4] = crate;
                    }
                    j += 4;
                }
            }
            return (result, line_count);
        }
    }
}