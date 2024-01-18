package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

record Instruction(String operation, int argument) {}

class Program {
    List<Instruction> instructions;

    public Program(String filepath) throws FileNotFoundException {
        var reader = new BufferedReader(new FileReader(new File(filepath)));
        this.instructions = reader.lines().map(x->{
            var split = x.split(" ");
            if(split.length == 1){
                return new Instruction(split[0], 0);
            }else{
                return new Instruction(split[0], Integer.parseInt(split[1]));
            }
        }).collect(Collectors.toList());
    }

    public void executePart1() throws Exception{
        int cycle = 0;
        long x = 1;
        long sum = 0;
        for(var instruction : instructions) {
            x += switch(instruction.operation()){
                case "noop"-> {
                    cycle++;
                    sum += signal(cycle, x);
                    yield 0;
                }
                case "addx"-> {
                    cycle++;
                    sum += signal(cycle, x);
                    cycle++;
                    sum += signal(cycle, x);
                    yield instruction.argument();
                }
                default-> {
                    throw new Exception("unknown instruction");
                }
            };
        }
        System.out.println(sum);
    }

    public void executePart2() throws Exception{
        int cycle = 0;
        long x = 1;
        char[][] board = new char[6][40];
        for(var instruction : instructions) {
            x += switch(instruction.operation()){
                case "noop"-> {
                    cycle++;
                    draw(cycle, x, board);
                    yield 0;
                }
                case "addx"-> {
                    cycle++;
                    draw(cycle, x, board);
                    cycle++;
                    draw(cycle, x, board);
                    yield instruction.argument();
                }
                default-> {
                    throw new Exception("unknown instruction");
                }
            };
        }
        for(var row : board){
            for(var col : row){
                System.out.print(col);
            }
            System.out.println();
        }
    }

    public long signal(int cycle, long x) {
        switch(cycle){
            case 20, 60, 100, 140, 180, 220:
                return cycle * x;
            default:
                return 0;
        }
    }

    public void draw(int cycle, long x, char[][] board){
        int row = (cycle - 1) / 40;
        int col = (cycle - 1) % 40;
        if(row >= board.length || col >= board[row].length) {
            return;
        }
        if(col >= x - 1 && col <= x + 1){
            board[row][col] = '#';
        }else{
            board[row][col] = '.';
        }
    }
}


public class day10 {
    public static void main(String[] args) throws Exception {
            var program = new Program("dataset.txt");
            program.executePart1();
            program.executePart2();
    }
}