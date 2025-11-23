package main

import (
	"Homework5/dao"
	"Homework5/routers"

	"github.com/gin-gonic/gin"
)

func main() {
	dao.Setup()
	h := gin.Default()
	routers.Router(h)

}
