package main

import (
	"fmt"
	"strconv"
)

type Product struct {
	Name  string
	Price float64
	Stock int // 库存数量
}

func TotalValue(Price float64, Stock int) float64 {
	return Price * float64(Stock)
}
func IsInStock(Stock int) bool {
	return Stock > 0
}
func Info(Name string, Price float64, Stock int) string {
	P := strconv.FormatFloat(Price, 'f', 2, 64)
	S := strconv.Itoa(Stock)
	A := "商品：" + Name + "  单价" + P + "  库存" + S
	return A
}
func (Stock *Product) Restock(amount int) {
	Stock.Stock += amount
	fmt.Println("进货了孩子们,进了", amount, "单位的货,现在有", Stock.Stock, "的货")
}
func (Stock *Product) Sell(amount int) (success bool, message string) {
	if Stock.Stock >= amount {
		Stock.Stock -= amount
		A := strconv.Itoa(Stock.Stock)
		success = true
		message = "售卖成功,剩余库存为" + A
		return success, message
	} else {
		success = false
		message = "售卖失败，亿万学go人必须放弃，，，"
		return success, message
	}
}

func main() {
	Go := Product{
		"Go语言从入门到精通",
		89.5,
		10,
	}
	fmt.Println(Go)
	success, message := Go.Sell(5)
	fmt.Println(success, message)
	Go.Restock(20)
	success, message = Go.Sell(30)
	fmt.Println(success, message)
	fmt.Println(Info(Go.Name, Go.Price, Go.Stock))
	fmt.Println("库存总价值", TotalValue(Go.Price, Go.Stock), "铁证如山！")
}
