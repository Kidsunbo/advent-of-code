const fs = require('fs');

function main() {
    try {
        const data = fs.readFileSync('./dataset.txt', 'utf8');
        let lines = data.split('\n');
        let dataset = []
        for(let line of lines){
            dataset.push(line.split(",").map((x)=>x.split("-").map(x=>parseInt(x))));
        }
        console.log(day1(dataset));
        console.log(day2(dataset));
    } catch (err) {
        console.error(err);
    }
}

function day1(dataset){
    let count = 0;
    for(let [a, b] of dataset){
        if(a[0]<= b[0] && a[1]>=b[1] || a[0] >=b[0] && a[1] <=b[1]) count++;
    }
    return count;
}

function day2(dataset){
    let count = 0;
    for(let [a, b] of dataset){
        if(a[0]>= b[0] && a[0]<=b[1] || a[1] >=b[0] && a[1] <=b[1] || b[0]>= a[0] && b[0]<=a[1] || b[1] >=a[0] && b[1] <=a[1]) count++;
    }
    return count;
}

main();
