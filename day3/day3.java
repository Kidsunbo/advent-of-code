import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class day3 {
    public static void main(String[] args) {
        try {
            var lines = Files.readAllLines(Path.of("./dataset.txt"));
            System.out.println(part1(lines));
            System.out.println(part2(lines));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long part1(List<String> lines){
        long sum = 0;
        for(var item : lines){
            Set<Character> set1 = item.substring(0, item.length()/2).chars().mapToObj(i->(char)i).collect(Collectors.toSet());
            Set<Character> set2 = item.substring(item.length()/2).chars().mapToObj(i->(char)i).collect(Collectors.toSet());
            set1.retainAll(set2);
            sum += set1.stream().mapToInt(i-> Character.isLowerCase(i)?i-'a'+1:i-'A'+27).sum();
        }
        return sum;
    }

    public static long part2(List<String> lines) throws Exception {
        long sum = 0;
        if(lines.size()%3 !=0){
            throw new Exception("Wierd dataset");
        }
        for(int i=0; i<lines.size(); i+=3){
            Set<Character> set1 = lines.get(i).chars().mapToObj(v->(char)v).collect(Collectors.toSet());
            Set<Character> set2 = lines.get(i+1).chars().mapToObj(v->(char)v).collect(Collectors.toSet());
            Set<Character> set3 = lines.get(i+2).chars().mapToObj(v->(char)v).collect(Collectors.toSet());
            set1.retainAll(set2);
            set1.retainAll(set3);
            sum += set1.stream().mapToInt(v-> Character.isLowerCase(v)?v-'a'+1:v-'A'+27).sum();
        }
        return sum;
    }
}
