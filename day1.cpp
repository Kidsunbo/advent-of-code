#include <iostream>
#include <fstream>
#include <vector>

int part_1(const std::vector<std::string>& data_set){
    std::int32_t max = 0;
    std::int32_t current = 0;
    for(auto& line: data_set){
        if(line.empty()){
            max = std::max(max, current);
            current = 0;
            continue;
        }
        current += std::stoi(line);
    }
    return max;
}

int part_2(const std::vector<std::string>& data_set){
    std::vector<std::int32_t> container;
    std::int32_t current=0;
    for(auto& line: data_set){
        if(line.empty()){
            container.push_back(current);
            current = 0;
            continue;
        }
        current += std::stoi(line);
    }
    std::make_heap(container.begin(),container.end());
    std::int32_t max = 0;
    for(int i=0; i<3; i++){
        max += container[i];
    }
    return max;
}

int main() {
    std::fstream file("../dataset.txt", std::ios::in);
    std::string line;
    std::vector<std::string> vec;
    while(std::getline(file, line)){
        vec.push_back(line);
    }
    std::cout<<part_1(vec)<<std::endl;
    std::cout<<part_2(vec)<<std::endl;
    return 0;
}
