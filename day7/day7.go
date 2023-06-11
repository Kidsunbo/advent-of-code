package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

type NodeType int8

const (
	NodeTypeFile NodeType = iota
	NodeTypeDir
)

type Node struct {
	Name string
	Type   NodeType
	Parent *Node
	Child  map[string]*Node
	Size   int64
}

type FileSystem struct {
	Root    *Node
	Current *Node
}

func CreateFileSystemFromCommands(allCommands []string) FileSystem {
	//assume all the command is valid
	fs := FileSystem{
		Root: &Node{
			Name:   "/",
			Type:   NodeTypeDir,
			Parent: nil,
			Child:  map[string]*Node{},
			Size:   0,
		},
	}
	fs.Current = fs.Root
	for i := 0; i < len(allCommands); i++ {
		commandParts := strings.Split(allCommands[i], " ")
		if len(commandParts) == 0 {
			panic("empty command")
		}
		if commandParts[0] == "$" {
			switch commandParts[1] {
			case "cd":
				switch commandParts[2] {
				case "..":
					if fs.Current == fs.Root{
						continue
					}
					fs.Current = fs.Current.Parent
				case "/":
					fs.Current = fs.Root
				default:
					if node, exist := fs.Current.Child[commandParts[2]]; exist{
						fs.Current = node
					}else{
						fs.Current.Child[commandParts[2]] = &Node{
							Name:   commandParts[2],
							Type:   NodeTypeDir,
							Parent: fs.Current,
							Child:  map[string]*Node{},
							Size:   0,
						}
						fs.Current = fs.Current.Child[commandParts[2]]
					}
				}
			case "ls":
				j := i+1
				for j<len(allCommands){
					lsResultParts := strings.Split(allCommands[j], " ")
					if lsResultParts[0] == "$"{
						j--
						break
					}else {
						var newNode *Node
						if lsResultParts[0] == "dir"{
							newNode = &Node{
								Name:   lsResultParts[1],
								Type:   NodeTypeDir,
								Parent: fs.Current,
								Child:  map[string]*Node{},
								Size:   0,
							}
						}else{
							size, err := strconv.ParseInt(lsResultParts[0], 10, 64)
							if err != nil{
								panic(err)
							}
							newNode = &Node{
								Name:   lsResultParts[1],
								Type:   NodeTypeFile,
								Parent: fs.Current,
								Child:  nil,
								Size:   size,
							}
						}
						fs.Current.Child[newNode.Name] = newNode
						j++
					}
				}
				i = j
			}
		}
	}
	fillDirTotalSize(fs.Root)
	fs.Current = fs.Root
	return fs
}

func fillDirTotalSize(node *Node)int64{
	if node.Type == NodeTypeFile{
		return node.Size
	}
	var result int64
	for _, item := range node.Child{
		result += fillDirTotalSize(item)
	}
	node.Size = result
	return result
}

func sumDirectoryWithSizeLess100000(node *Node)int64{
	if node.Type == NodeTypeFile{
		return 0
	}
	curSize := node.Size
	if curSize > 100000{
		curSize = 0
	}
	for _, item := range node.Child{
		curSize += sumDirectoryWithSizeLess100000(item)
	}
	return curSize
}


func part1(allCommands []string) {
	fs := CreateFileSystemFromCommands(allCommands)
	fmt.Println("part1", sumDirectoryWithSizeLess100000(fs.Root))
}

func findMinimumStorage(node *Node, stillNeed int, result *int){
	if node.Type == NodeTypeDir{
		if node.Size > int64(stillNeed) && node.Size < int64(*result){
			*result = int(node.Size)
		}
		for _, item := range node.Child{
			findMinimumStorage(item, stillNeed, result)
		}
	}
}

func part2(allCommands []string) {
	fs := CreateFileSystemFromCommands(allCommands)
	stillNeed := fs.Root.Size - 40000000
	result := 70000000
	findMinimumStorage(fs.Root, int(stillNeed), &result)
	fmt.Println("part2", result)
}

func main() {
	allContent, err := os.ReadFile("./dataset.txt")
	if err != nil {
		panic(err)
	}
	allCommands := strings.Split(string(allContent), "\n")
	part1(allCommands)
	part2(allCommands)

}
