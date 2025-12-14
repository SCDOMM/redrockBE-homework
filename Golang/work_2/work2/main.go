package main

import (
	"fmt"
	"sync"
	"time"
)

func download(filename string, wg *sync.WaitGroup, results chan<- string, VIP bool) {
	defer wg.Done()
	if VIP {
		time.Sleep(1 * time.Second)
		results <- filename + "下载完成"
	} else {
		time.Sleep(3 * time.Second)
		results <- filename + "下载失败"
	}

}
func Loading(delay time.Duration) {
	for {
		for _, r := range "01" {
			d := ""
			if r == '0' {
				d = ""
			} else {
				d = "   "
			}
			fmt.Printf("%s8===⊃\r", d)
			time.Sleep(delay)
		}
	}
}
func main() {
	fmt.Println("欢迎使用赤石下载器！你充会员了吗?")
	var judge string
	fmt.Scanf("%s", &judge)
	var VIP bool
	if judge == "是" || judge == "Yes" || judge == "true" {
		VIP = true
	} else if judge == "否" || judge == "No" || judge == "false" {
		VIP = false
	}

	var wg sync.WaitGroup
	var File = []string{"file1.zip",
		"file2.pdf",
		"file3.mp4",
	}

	wg.Add(3)
	channel := make(chan string, 3)
	fmt.Println("开始下载")
	for _, file := range File {
		go download(file, &wg, channel, VIP)
		go Loading(100 * time.Millisecond)
		fmt.Println(<-channel)
	}

	go func() {
		wg.Wait()
		close(channel)
	}()
	if !VIP {
		time.Sleep(5 * time.Second)
		fmt.Println("下载失败！！")
	} else {
		fmt.Println("下载全部完成！")
	}

}
