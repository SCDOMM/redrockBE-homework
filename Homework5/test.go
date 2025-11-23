package main

import (
	"Homework5/utils"
	"fmt"
)

func main() {

	// 调用示例
	access, refresh, err := utils.CreateToken(114, "user")
	if err != nil {
		panic(err)
	}
	if ac, err := utils.VerifyAccess(access); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(ac.UserID, ac.Role)
	}

	if re, err := utils.VerifyRefresh(refresh); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(re.UserID, re.Role)
	}

}
