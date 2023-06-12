#include <iostream>
#include <fstream>
#include <vector>
#include <exception>


void part1(const std::vector<std::string>& content){
    int result = 0;
    if(content.size() <= 0){
        throw std::runtime_error("content does not has any content");
    }
    result += content[0].size() * 2 + content.size()*2 - 4;
    std::vector<std::vector<bool>> bitmap(content.size()-2, std::vector<bool>(content[0].size()-2, false));

    // vertical part
    for(int i=1; i< static_cast<int>(content[0].size() -1); i++){
        // top to bottom
        int max = content[0][i];
        for(int j = 1; j < static_cast<int>(content.size()-1); j++){
            if(content[j][i] > max){
                bitmap[j-1][i-1] = true;
                max = content[j][i];
            }
        }

        // bottom to top
        max = content[content.size()-1][i];
        for(int j = content.size()-2; j >0; j--){
            if(content[j][i] > max){
                bitmap[j-1][i-1] = true;
                max = content[j][i];
            }
        }
    }

    // horizontal part
    for(int i=1; i< static_cast<int>(content.size() -1); i++){
        // left to right
        int max = content[i][0];
        for(int j = 1; j < static_cast<int>(content[i].size()-1); j++){
            if(content[i][j] > max){
                bitmap[i-1][j-1] = true;
                max = content[i][j];
            }
        }

        // bottom to top
        max = content[i][content[i].size()-1];
        for(int j = content.size()-2; j >0; j--){
            if(content[i][j] > max){
                bitmap[i-1][j-1] = true;
                max = content[i][j];
            }
        }
    }

    for(const auto& line : bitmap){
        for(const auto bit : line){
            if(bit){
                result++;
            }
        }
    }

    std::cout<< "part1 "<<result<<std::endl;
}

std::int64_t calculate_score(const std::vector<std::string>& content, int i, int j){
    // left
    int left = 0;
    for(int idx = j - 1; idx >= 0; idx--){
        left++;
        if(content[i][idx] >= content[i][j]){
            break;
        }
    }

    // right
    int right = 0;
    for(int idx = j + 1; idx < static_cast<int>(content[i].size()); idx++){
        right++;
        if(content[i][idx] >= content[i][j]){
            break;
        }
    }

    // up
    int up = 0;
    for(int idx = i - 1; idx >= 0; idx--){
        up++;
        if(content[idx][j] >= content[i][j]){
            break;
        }
    }

    // down
    int down = 0;
    for(int idx = i + 1; idx < static_cast<int>(content.size()); idx++){
        down++;
        if(content[idx][j] >= content[i][j]){
            break;
        }
    }

    return left * right * up * down;
}

void part2(const std::vector<std::string>& content){
    std::int64_t result = 0;
    
    for(int i=1; i<static_cast<int>(content.size()-1); i++){
        for(int j=1; j<static_cast<int>(content[i].size()-1); j++){
            result = std::max(result, calculate_score(content, i ,j));
        }
    }
    std::cout<< "part2 "<<result<<std::endl;
}


int main(int, char**){
    std::fstream file("../dataset.txt", std::ios::in);
    std::vector<std::string> content;
    std::string line;
    while(std::getline(file, line)){
        content.push_back(line);
    }
    part1(content);
    part2(content);
}
