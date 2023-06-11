import 'dart:io';

int part1(String lines) {
  var multiMap = <int, int>{};
  for (var item in lines.substring(0,4).runes) {
    multiMap.update(item, (value) => value + 1, ifAbsent: () => 1);
  }
  var left = 0, right = 3;
  while (right < lines.length) {
    if (multiMap.length == 4) {
      return right + 1;
    }
    right++;
    if(multiMap[lines.codeUnits[left]] == 1){
      multiMap.remove(lines.codeUnits[left]);
    }else{
      multiMap.update(lines.codeUnits[left], (value) => value -1);
    }
    multiMap.update(lines.codeUnits[right], (value) => value + 1, ifAbsent: () => 1);
    left++;
  }
  return -1;
}

int part2(String lines) {
  var multiMap = <int, int>{};
  for (var item in lines.substring(0,14).runes) {
    multiMap.update(item, (value) => value + 1, ifAbsent: () => 1);
  }
  var left = 0, right = 13;
  while (right < lines.length) {
    if (multiMap.length == 14) {
      return right + 1;
    }
    right++;
    if(multiMap[lines.codeUnits[left]] == 1){
      multiMap.remove(lines.codeUnits[left]);
    }else{
      multiMap.update(lines.codeUnits[left], (value) => value -1);
    }
    multiMap.update(lines.codeUnits[right], (value) => value + 1, ifAbsent: () => 1);
    left++;
  }
  return -1;
}

Future<void> main(List<String> arguments) async {
  var file = File("./dataset.txt");
  var lines = await file.readAsString();
  print("day6: part1: ${part1(lines)}");
  print("day6: part1: ${part2(lines)}");
}
